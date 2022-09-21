package in.dudeapp.common.service;

import com.google.gson.Gson;
import in.dudeapp.common.dto.DatabaseProperties;
import in.dudeapp.common.util.GsonUtil;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.*;

import java.util.Base64;

/**
 * Created by mohan on 27/07/22
 */
@Service
public class AWSSecretsMangerService {

    private final SecretsManagerClient secretsManagerClient;


    public AWSSecretsMangerService(SecretsManagerClient secretsManagerClient) {
        this.secretsManagerClient = secretsManagerClient;
    }

    public DatabaseProperties getDataBaseProperties(String secretName) {
        String secret = getSecretValue(secretName);
        return GsonUtil.getInstance().fromJson(secret, DatabaseProperties.class);
    }

    public String getSecretValue(String secretName) {
        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();
        GetSecretValueResponse getSecretValueResponse = null;

        try {
            getSecretValueResponse = secretsManagerClient.getSecretValue(getSecretValueRequest);
            String secret;
            if (getSecretValueResponse.secretString() != null) {
                secret = getSecretValueResponse.secretString();
            } else {
                secret = new String(Base64.getDecoder().decode(getSecretValueResponse.secretBinary().asByteBuffer()).array());
            }
            return secret;
        } catch (DecryptionFailureException | InternalServiceErrorException | InvalidParameterException |
                 InvalidRequestException | ResourceNotFoundException e) {
            throw e;
        }
    }
}
