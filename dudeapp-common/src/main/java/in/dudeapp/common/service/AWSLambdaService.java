package in.dudeapp.common.service;

import in.dudeapp.common.dto.lambda.AWSLambdaRequest;
import in.dudeapp.common.dto.lambda.AWSLambdaRequestBody;
import in.dudeapp.common.dto.lambda.AWSLambdaResponse;
import in.dudeapp.common.exception.AWSLambdaException;
import in.dudeapp.common.util.GsonUtil;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;

import java.lang.reflect.Type;

/**
 * Created by mohan on 16/07/22
 */
@Service
public class AWSLambdaService {

    private final LambdaClient lambdaClient;

    public AWSLambdaService(LambdaClient lambdaClient) {
        this.lambdaClient = lambdaClient;
    }

    public final <T> T invoke(String functionName, AWSLambdaRequest requestBody, Class<T> responseType) {
        AWSLambdaResponse res = invoke(functionName, requestBody);
        return GsonUtil.getInstance().fromJson(res.getBody(), responseType);
    }

    public final <T> T invoke(String functionName, AWSLambdaRequest requestBody, Type type) {
        AWSLambdaResponse res = invoke(functionName, requestBody);
        return GsonUtil.getInstance().fromJson(res.getBody(), type);
    }

    private AWSLambdaResponse invoke(String functionName, AWSLambdaRequest request) {
        AWSLambdaRequestBody body = AWSLambdaRequestBody.builder().body(request).build();
        String json = GsonUtil.getInstance().toJson(body);
        SdkBytes payload = SdkBytes.fromUtf8String(json);
        InvokeRequest invokeRequest = InvokeRequest.builder()
                .functionName(functionName)
                .payload(payload)
                .build();

        InvokeResponse res = this.lambdaClient.invoke(invokeRequest);
        if (!res.statusCode().equals(200)) {
            throw new AWSLambdaException(res.statusCode(), res.functionError());
        }
        String response =  StringEscapeUtils.unescapeJson(res.payload().asUtf8String());
        return GsonUtil.getInstance().fromJson(response,AWSLambdaResponse.class);
    }
}
