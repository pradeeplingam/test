package in.dudeapp.common.config;

import in.dudeapp.common.dto.DatabaseProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;

/**
 * Created by mohan on 27/07/22
 */
@Configuration
public class AWSSecretManagerConfig {

    @Autowired
    private StaticCredentialsProvider staticCredentialsProvider;

    @Autowired
    private AWSProperties awsProperties;

    @Bean
    public SecretsManagerClient secretsManagerClient(){
        return SecretsManagerClient.builder()
                .credentialsProvider(staticCredentialsProvider)
                .region(Region.of(awsProperties.getRegion()))
                .build();
    }

}
