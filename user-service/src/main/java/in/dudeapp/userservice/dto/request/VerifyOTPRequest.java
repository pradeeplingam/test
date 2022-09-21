package in.dudeapp.userservice.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Created by mohan on 20/07/22
 */
@Data
public class VerifyOTPRequest extends SendOTPRequest {
    @NotEmpty
    private String otp;
}
