package in.dudeapp.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

/**
 * Created by mohan on 21/07/22
 */
@Configuration
public class AWSS3PreSigner {

    private final StaticCredentialsProvider staticCredentialsProvider;

    private final AWSProperties awsProperties;

    public AWSS3PreSigner(StaticCredentialsProvider staticCredentialsProvider, AWSProperties awsProperties) {
        this.staticCredentialsProvider = staticCredentialsProvider;
        this.awsProperties = awsProperties;
    }

    @Bean
    public S3Presigner s3Presigner() {
        return S3Presigner.builder().credentialsProvider(staticCredentialsProvider)
                .region(Region.of(awsProperties.getRegion())).build();
    }

}