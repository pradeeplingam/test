package in.dudeapp.common.dto.lambda;

import lombok.Builder;
import lombok.Data;

/**
 * Created by mohan on 16/07/22
 */
@Builder
@Data
public final class AWSLambdaRequestBody {
    private AWSLambdaRequest body;
}
