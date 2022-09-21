package in.dudeapp.userservice.config;

import in.dudeapp.common.config.AWSProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * Created by mohan on 22/06/22
 */
@Configuration
public class AWSConfig {

    @Autowired
    private AWSProperties awsProperties;

    @Autowired
    private StaticCredentialsProvider staticCredentialsProvider;


    @Bean
    public S3Client s3Client() {
        return S3Client
                .builder()
                .credentialsProvider(staticCredentialsProvider)
                .region(Region.of(awsProperties.getRegion()))
                .build();
    }
}
