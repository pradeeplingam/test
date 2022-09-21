package in.dudeapp.userservice.service;

import in.dudeapp.userservice.dto.request.DocumentUploadRequest;
import in.dudeapp.userservice.entity.Document;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by mohan on 03/07/22
 */
public interface KYCService {
    Document uploadKYCDocument(MultipartFile file, DocumentUploadRequest request);
}
