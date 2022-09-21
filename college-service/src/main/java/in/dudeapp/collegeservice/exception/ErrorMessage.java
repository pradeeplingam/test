package in.dudeapp.collegeservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author nandhan, Created on 22/07/22
 */
@Getter
@AllArgsConstructor
public enum ErrorMessage {

    COLLEGE_ERROR_CODE_0001("COLLEGE_0001", "College Not Exist!"),
    COLLEGE_ERROR_CODE_0002("COLLEGE_0002", "Invalid ID"),
    COLLEGE_ERROR_CODE_0003("COLLEGE_0003", "Invalid File Format!"),
    COLLEGE_ERROR_CODE_0004("COLLEGE_0004", "Empty File!"),
    COLLEGE_ERROR_CODE_0005("COLLEGE_0005", "Unauthorized API Call");

    private final String errorCode;
    private final String errorMessage;
}
