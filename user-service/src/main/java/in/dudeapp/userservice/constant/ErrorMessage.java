package in.dudeapp.userservice.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by mohan on 11/06/22
 */
@Getter
@AllArgsConstructor
public enum ErrorMessage {

    USER_ERROR_CODE_0001("USR_0001","User mobile verified"),
    USER_ERROR_CODE_0002("USER_0002","Invalid OTP Type"),
    USER_ERROR_CODE_0003("USER_0003","User doesnot exists"),
    USER_ERROR_CODE_0004("USER_0004","Graduation year required"),
    USER_ERROR_CODE_0005("USER_0005","Graduation year should be less than current date"),
    USER_ERROR_CODE_0006("USER_0006","Graduation year should be less than current date"),
    USER_ERROR_CODE_0007("USER_0007","Graduation year should be less than current date"),
    USER_ERROR_CODE_0008("USER_0008","Mobile number is not verified"),
    USER_ERROR_CODE_0009("USER_0009","Email is not verified"),
    USER_ERROR_CODE_0010("USER_0010","OTP expired"),
    USER_ERROR_CODE_0011("USER_0011","Invalid OTP"),
    USER_ERROR_CODE_0012("USER_0012","Please verify mobile number before email verification"),
    USER_ERROR_CODE_0013("USER_0013","Invalid user id"),
    USER_ERROR_CODE_0014("USER_0014","Invalid verification type"),
    USER_ERROR_CODE_0015("USER_0015","Selfie Uploading Error"),

    COLLEGE_ERROR_CODE_0001("COLLEGE_0001", "College Not Exist!"),
    COLLEGE_ERROR_CODE_0002("COLLEGE_0002", "Invalid ID"),
    COLLEGE_ERROR_CODE_0003("COLLEGE_0003", "Invalid File Format!"),
    COLLEGE_ERROR_CODE_0004("COLLEGE_0004", "Empty File!"),
    COLLEGE_ERROR_CODE_0005("COLLEGE_0005", "Unauthorized API Call"),
    COLLEGE_ERROR_CODE_0006("CLG_0001","College exists with same address"),

    REFERENCES_ERROR_CODE_0001("REFR_0001","Reference does not exists with the given Id"),
    REFERENCES_ERROR_CODE_0002("REFR_0002","Invalid Parameters"),

    GENERAL_ERROR_CODE_0001("GEN_0001","Invalid Request Body");

    private final String errorCode;
    private final String message;
}
