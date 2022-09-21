package in.dudeapp.userservice.exception;

import in.dudeapp.common.dto.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mohan on 11/06/22
 */
@ControllerAdvice
@Slf4j
public class UserServiceControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> handleUserException(UserException e) {
        return ResponseEntity.badRequest().body(
                ErrorResponse.builder().errorMessage(e.getMessage())
                        .errorCode(e.getErrorCode())
                        .build()
        );
    }


    @ExceptionHandler(CollegeException.class)
    public ResponseEntity<ErrorResponse> handleCollegeException(
            CollegeException exception) {
        log.error("Exception!. The requested college is not present in the database.");
        return ResponseEntity.badRequest().body(
                ErrorResponse.builder()
                        .errorMessage(exception.getErrorMessage())
                        .errorCode(exception.getErrorCode()).build());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
