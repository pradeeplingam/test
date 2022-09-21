package in.dudeapp.userservice.controller;

import in.dudeapp.userservice.dto.request.DocumentUploadRequest;
import in.dudeapp.userservice.entity.Document;
import in.dudeapp.userservice.entity.constants.DocumentType;
import in.dudeapp.userservice.service.KYCService;
import in.dudeapp.userservice.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * Created by mohan on 03/07/22
 */
@RestController
@RequestMapping("/{userId}/kyc")
public class KYCController {

    private final KYCService kycService;

    public KYCController(KYCService kycService, UserService userService) {
        this.kycService = kycService;
    }

    @PostMapping("/{documentType}/extractDetails")
    public Document extractKYCDetails(@PathVariable("userId") Integer userId,
                                      @PathVariable("documentType") DocumentType documentType,
                                      @RequestPart("file") MultipartFile file) {
        if (Objects.isNull(file)) {
            throw new RuntimeException("Please add file");
        }
//        this.userService.validateUser(userId);
        DocumentUploadRequest request = DocumentUploadRequest.builder()
                .documentType(documentType)
                .userId(userId)
                .build();

        return this.kycService.uploadKYCDocument(file, request);
    }
}
