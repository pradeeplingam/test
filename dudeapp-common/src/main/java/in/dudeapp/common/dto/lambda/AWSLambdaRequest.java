package in.dudeapp.common.dto.lambda;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * Created by mohan on 16/07/22
 */
@Data
@SuperBuilder
public abstract class AWSLambdaRequest {
    private String requestId;
}
