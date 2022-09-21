package in.dudeapp.collegeservice.exception;

import in.dudeapp.common.exception.BaseApplicationException;
import in.dudeapp.common.exception.ErrorType;

/**
 * @author nandhan, Created on 22/07/22
 */
public class CollegeException extends BaseApplicationException {
    public CollegeException(ErrorMessage errorMessage) {
        super(errorMessage.getErrorCode(), errorMessage.getErrorMessage(), ErrorType.COLLEGE_SERVICE);
    }
}
