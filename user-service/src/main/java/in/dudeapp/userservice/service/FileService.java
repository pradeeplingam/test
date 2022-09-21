package in.dudeapp.userservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by mohan on 03/07/22
 */
public interface FileService {
    String upload(MultipartFile file, String bucketName , String objectName) throws IOException;

    String getPreSignedUrl(String bucketName, String objectName);
}
