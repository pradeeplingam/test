package in.dudeapp.userservice.service.impl;

import in.dudeapp.common.config.AWSProperties;
import in.dudeapp.userservice.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.time.Duration;

/**
 * Created by mohan on 03/07/22
 */
@Service
public class FileServiceImpl implements FileService {

    private final AWSProperties awsProperties;

    private final S3Client s3Client;

    private final S3Presigner s3Presigner;

    public FileServiceImpl(AWSProperties awsProperties, S3Client s3Client, S3Presigner s3Presigner) {
        this.awsProperties = awsProperties;
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
    }

    @Override
    public String upload(MultipartFile file, String bucketName, String objectName) throws IOException {

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName).key(objectName)
//                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();

        StringBuilder S3ObjectUrlBuilder = new StringBuilder("https://");
        this.s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        S3ObjectUrlBuilder.append(bucketName);
        S3ObjectUrlBuilder.append(".s3-");
        S3ObjectUrlBuilder.append(awsProperties.getRegion());
        S3ObjectUrlBuilder.append(".amazonaws.com/");
        S3ObjectUrlBuilder.append(objectName);
        return S3ObjectUrlBuilder.toString();
    }

    @Override
    public String getPreSignedUrl(String bucketName, String objectName) {

        GetObjectRequest getObjectRequest =
                GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build();

        GetObjectPresignRequest getObjectPresignRequest =
                GetObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(10))
                        .getObjectRequest(getObjectRequest)
                        .build();

        PresignedGetObjectRequest presignedGetObjectRequest =
                this.s3Presigner.presignGetObject(getObjectPresignRequest);

        String url = presignedGetObjectRequest.url().toString();
        this.s3Presigner.close();
        return url;
    }
}
