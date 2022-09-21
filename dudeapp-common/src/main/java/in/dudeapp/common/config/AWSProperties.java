package in.dudeapp.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by mohan on 03/07/22
 */
@Data
@ConfigurationProperties(prefix = "aws")
public class AWSProperties {

    private String key;

    private String secret;

    private String region;

    private KMSProperties kms;

}

