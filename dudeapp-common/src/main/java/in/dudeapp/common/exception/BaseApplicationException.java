package in.dudeapp.common.exception;

import lombok.Data;

/**
 * Created by mohan on 11/06/22
 */
@Data
public class BaseApplicationException extends RuntimeException {
    private static final long serialVersionUID = 6170552889756454108L;

    private final String errorCode;

    private final String errorMessage;

    private final ErrorType errorType;

    public BaseApplicationException() {
        super();
        this.errorCode = "";
        this.errorMessage = "";
        this.errorType = ErrorType.UNKNOWN;
    }

    public BaseApplicationException(String errorMessage) {
        super(errorMessage);
        this.errorCode = "";
        this.errorMessage = errorMessage;
        this.errorType = ErrorType.UNKNOWN;
    }

    public BaseApplicationException(String errorMessage,ErrorType errorType) {
        super(errorMessage);
        this.errorCode = "";
        this.errorMessage = errorMessage;
        this.errorType = errorType;
    }

    public BaseApplicationException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorType = ErrorType.UNKNOWN;
    }

    public BaseApplicationException(String errorCode, String errorMessage, ErrorType errorType) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorType = errorType;
    }
}
