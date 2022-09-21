package in.dudeapp.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;

/**
 * Created by mohan on 16/07/22
 */
@Configuration
public class AWSLambdaConfig {

    @Autowired
    private AwsCredentialsProvider credentialsProvider;

    @Autowired
    private AWSProperties awsProperties;

    @Bean
    public LambdaClient lambdaClient(){
        return LambdaClient.builder()
                .region(Region.of(awsProperties.getRegion()))
                .credentialsProvider(credentialsProvider)
                .build();
    }
}
