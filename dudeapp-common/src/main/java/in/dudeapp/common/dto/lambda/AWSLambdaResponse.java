package in.dudeapp.common.dto.lambda;

import lombok.Data;

/**
 * Created by mohan on 04/07/22
 */
@Data
public class AWSLambdaResponse {
    private int statusCode;
    private String body;
}
