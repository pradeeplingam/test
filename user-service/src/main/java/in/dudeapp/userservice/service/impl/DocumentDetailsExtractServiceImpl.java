package in.dudeapp.userservice.service.impl;

import com.google.gson.Gson;
import in.dudeapp.common.service.AWSLambdaService;
import in.dudeapp.userservice.dto.request.lambda.DocumentDetailsExtractRequest;
import in.dudeapp.userservice.dto.response.lambda.DocumentResponse;
import in.dudeapp.userservice.dto.response.lambda.PanCardData;
import in.dudeapp.userservice.service.DocumentDetailsExtractService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static in.dudeapp.common.constant.MDCConstants.REQUEST_ID;

/**
 * Created by mohan on 04/07/22
 */
@Service
@Slf4j
public class DocumentDetailsExtractServiceImpl implements DocumentDetailsExtractService {

    private final AWSLambdaService awsLambdaService;

    @Value("${aws.lambda.panCardFunctionName}")
    private String panCardFunctionName;

    private static final Gson gson = new Gson();

    public DocumentDetailsExtractServiceImpl(AWSLambdaService awsLambdaService) {
        this.awsLambdaService = awsLambdaService;
    }


    @Override
    public PanCardData getPanCardDetails(String objectID) {
        DocumentDetailsExtractRequest extractRequest = DocumentDetailsExtractRequest
                .builder()
                .requestId(MDC.get(REQUEST_ID))
                .objectID(objectID).build();
        DocumentResponse documentResponse = this.awsLambdaService.invoke(panCardFunctionName, extractRequest, DocumentResponse.class);
        log.info("body : {}", documentResponse);
        return documentResponse.getOcrData();
    }
}
