package in.dudeapp.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;

/**
 * Created by mohan on 14/07/22
 */
@Configuration
public class AWSCredentialConfig {

    @Bean
    public StaticCredentialsProvider staticCredentialsProvider() {
        AWSProperties properties = awsProperties();
        AwsBasicCredentials credentials = AwsBasicCredentials.create(properties.getKey(), properties.getSecret());
        return StaticCredentialsProvider.create(credentials);
    }

    @Bean
    public AWSProperties awsProperties(){
        return new AWSProperties();
    }

}