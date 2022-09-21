package in.dudeapp.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kms.KmsClient;

/**
 * Created by mohan on 15/07/22
 */
@Configuration
public class AWSKMSConfig {

    @Autowired
    private AwsCredentialsProvider credentialsProvider;

    @Autowired
    private AWSProperties awsProperties;

    @Bean
    public KmsClient kmsClient(){
        return KmsClient.builder()
                .credentialsProvider(credentialsProvider)
                .region(Region.of(awsProperties.getRegion())).build();
    }

}
