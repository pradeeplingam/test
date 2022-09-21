package in.dudeapp.userservice.dto.response;

import lombok.Builder;
import lombok.Data;

/**
 * Created by mohan on 11/06/22
 */
@Data
@Builder
public class UserOTPResponse {

    private String message;

    private String otp;

    private String userId;
}
