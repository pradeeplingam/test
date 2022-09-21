package in.dudeapp.collegeservice.exception;

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
 * @author nandhan, Created on 19/07/22
 */
// TODO: CHECK STATUS CODES.
@Slf4j
@ControllerAdvice
public class CollegeServiceControllerAdvice extends ResponseEntityExceptionHandler {

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
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


}