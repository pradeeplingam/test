package in.dudeapp.userservice.validator;

import in.dudeapp.userservice.constant.ErrorMessage;
import in.dudeapp.userservice.dto.request.UpdateUserRequest;
import in.dudeapp.userservice.entity.User;
import in.dudeapp.userservice.exception.UserException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

import static in.dudeapp.userservice.constant.ErrorMessage.USER_ERROR_CODE_0004;
import static in.dudeapp.userservice.constant.ErrorMessage.USER_ERROR_CODE_0005;

/**
 * Created by mohan on 20/07/22
 */
@Component
public class UserRequestValidator {

    public void validateUserVerification(User user) {
        if (!user.getMobileVerified()) {
            throw new UserException(ErrorMessage.USER_ERROR_CODE_0008);
        }
        if (!user.getEmailVerified()) {
            throw new UserException(ErrorMessage.USER_ERROR_CODE_0009);
        }
    }

    public void validateUpdateStudentUserRequest(User user, UpdateUserRequest request) {
        validateUserVerification(user);
        if (Objects.isNull(request.getGraduationDate())) {
            throw new UserException(USER_ERROR_CODE_0004);
        }
        if (request.getGraduationDate().isBefore(LocalDate.now())) {
            throw new UserException(USER_ERROR_CODE_0005);
        }

        if (Objects.isNull(request.getParentsOccupation())) {
            throw new UserException(USER_ERROR_CODE_0004);
        }
    }
}
