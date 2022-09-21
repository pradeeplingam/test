package in.dudeapp.userservice.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Created by mohan on 10/06/22
 */
@Data
@Builder
public class OTPAPIResponse {

    private String message;

    private String otp;

}
