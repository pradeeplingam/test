package in.dudeapp.userservice.dto.request.lambda;

import in.dudeapp.common.dto.lambda.AWSLambdaRequest;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * Created by mohan on 04/07/22
 */
@SuperBuilder
public class DocumentDetailsExtractRequest extends AWSLambdaRequest {

    private String objectID;
}
