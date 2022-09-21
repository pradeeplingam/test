package in.dudeapp.userservice.service;

import in.dudeapp.userservice.dto.response.lambda.PanCardData;

/**
 * Created by mohan on 04/07/22
 */
public interface DocumentDetailsExtractService {

    PanCardData getPanCardDetails(String objectName);
}
