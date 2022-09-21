package in.dudeapp.common.converter;

import in.dudeapp.common.config.AWSProperties;
import in.dudeapp.common.service.KMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by mohan on 13/07/22
 */
@Converter
@Component
public class SecuredConverter implements AttributeConverter<String,String> {

    @Autowired
    private KMSService kmsService;

    @Autowired
    private AWSProperties awsProperties;

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return kmsService.encrypt(awsProperties.getKms().getArn(), attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return kmsService.decrypt(dbData);
    }
}
