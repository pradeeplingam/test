package in.dudeapp.common.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.kms.KmsClient;
import software.amazon.awssdk.services.kms.model.DecryptRequest;
import software.amazon.awssdk.services.kms.model.DecryptResponse;
import software.amazon.awssdk.services.kms.model.EncryptRequest;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Created by mohan on 13/07/22
 */
@Component
public class KMSService {

    private final KmsClient kmsClient;

    public KMSService(KmsClient kmsClient) {
        this.kmsClient = kmsClient;
    }

    public final String encrypt(String kmsARN, String data) {
        if (StringUtils.isBlank(data)) {
            return data;
        }
        EncryptRequest encryptRequest =
                EncryptRequest.builder().keyId(kmsARN).plaintext(SdkBytes.fromUtf8String(data)).build();
        byte[] ciphertext = this.kmsClient
                .encrypt(encryptRequest).ciphertextBlob().asByteArray();

        byte[] base64EncodedValue = Base64.getEncoder().encode(ciphertext);
        return new String(base64EncodedValue, StandardCharsets.UTF_8);

    }

    public final String decrypt(String encryptedData) {
        if(StringUtils.isBlank(encryptedData)) {
            return encryptedData;
        }
        byte[]  ciphertext = Base64.getDecoder().decode(encryptedData);
        DecryptRequest decryptRequest = DecryptRequest.builder()
                .ciphertextBlob(SdkBytes.fromByteArray(ciphertext)).build();
        DecryptResponse decryptResponse = this.kmsClient
                .decrypt(decryptRequest);
        return decryptResponse.plaintext().asUtf8String();
    }
}
