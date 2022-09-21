package in.dudeapp.userservice.exception;

import in.dudeapp.common.exception.BaseApplicationException;
import in.dudeapp.common.exception.ErrorType;
import in.dudeapp.userservice.constant.ErrorMessage;

/**
 * @author nandhan, Created on 22/07/22
 */
public class CollegeException extends BaseApplicationException {
    public CollegeException(ErrorMessage errorMessage) {
        super(errorMessage.getErrorCode(), errorMessage.getMessage(), ErrorType.COLLEGE_SERVICE);
    }
}