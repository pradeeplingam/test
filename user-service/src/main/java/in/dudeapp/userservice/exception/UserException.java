package in.dudeapp.userservice.exception;

import in.dudeapp.common.exception.BaseApplicationException;
import in.dudeapp.common.exception.ErrorType;
import in.dudeapp.userservice.constant.ErrorMessage;

/**
 * Created by mohan on 11/06/22
 */
public class UserException extends BaseApplicationException {

    public UserException(ErrorMessage errorMessage){
        super(errorMessage.getErrorCode(), errorMessage.getMessage(), ErrorType.USER_SERVICE);
    }
}
