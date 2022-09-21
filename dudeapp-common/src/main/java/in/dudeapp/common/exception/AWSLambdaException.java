package in.dudeapp.common.exception;

import lombok.Data;

/**
 * Created by mohan on 16/07/22
 */
@Data
public class AWSLambdaException extends BaseApplicationException {
    public AWSLambdaException(Integer errorCode, String errorMessage) {
        super(String.valueOf(errorCode), errorMessage, ErrorType.AWS_LAMBDA);
    }
}
