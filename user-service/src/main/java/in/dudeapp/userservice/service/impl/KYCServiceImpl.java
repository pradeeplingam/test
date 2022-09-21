package in.dudeapp.userservice.service.impl;

import com.google.gson.Gson;
import in.dudeapp.userservice.dto.request.DocumentUploadRequest;
import in.dudeapp.userservice.dto.response.lambda.PanCardData;
import in.dudeapp.userservice.entity.Document;
import in.dudeapp.userservice.repository.DocumentRepo;
import in.dudeapp.userservice.service.DocumentDetailsExtractService;
import in.dudeapp.userservice.service.FileService;
import in.dudeapp.userservice.service.KYCService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by mohan on 03/07/22
 */
@Service
@Slf4j
public class KYCServiceImpl implements KYCService {

    private final FileService fileService;

    private final DocumentDetailsExtractService documentDetailsExtractService;

    private final DocumentRepo documentRepo;

    private static final Gson gson=new Gson();

    @Value("${aws.kycDocumentBucket}")
    private String kycDocumentBucket;

    public KYCServiceImpl(FileService fileService, DocumentDetailsExtractService documentDetailsExtractService, DocumentRepo documentRepo) {
        this.fileService = fileService;
        this.documentDetailsExtractService = documentDetailsExtractService;
        this.documentRepo = documentRepo;
    }

    @Override
    public Document uploadKYCDocument(MultipartFile file, DocumentUploadRequest request) {
        try {
            String objectName = generateDocID(request);
            String s3Url = this.fileService.upload(file, kycDocumentBucket, objectName);
            switch (request.getDocumentType()){
                case PAN : return getPanCardDetails(objectName,s3Url,request);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private Document getPanCardDetails(String objectName,String s3Url, DocumentUploadRequest request) {
        PanCardData panCardData = this.documentDetailsExtractService.getPanCardDetails(objectName);
        Optional<Document> optional = this.documentRepo.findByUserId(String.valueOf(request.getUserId()));
        Document document = optional.orElse(new Document());
        document.setDocumentType(request.getDocumentType());
        document.setImageUrl(s3Url);
        document.setFullName(panCardData.getName());
        document.setFatherName(panCardData.getFatherName());
        String date = panCardData.getDob();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime dateOfBirth = LocalDate.parse(date, format).atStartOfDay();
        document.setDateOfBirth(dateOfBirth);
        log.info("panCardData : {}", gson.toJson(panCardData));
        if (Objects.nonNull(panCardData.getPan()) && Objects.nonNull(panCardData.getName())
                && Objects.nonNull(panCardData.getFatherName())
                && Objects.nonNull(panCardData.getDob())) {
            document.setExtracted(true);
        }
        document = this.documentRepo.save(document);
        return document;
    }

    private String generateDocID(DocumentUploadRequest request){
        return request.getUserId()+"_"+request.getDocumentType()+".jpeg";
    }

}
