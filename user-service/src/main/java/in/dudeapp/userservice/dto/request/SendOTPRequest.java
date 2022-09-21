package in.dudeapp.userservice.dto.request;

import in.dudeapp.userservice.constant.VerificationType;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by mohan on 11/06/22
 */
@Data
public class SendOTPRequest extends OnBoardingRequest {
    @NotEmpty
    private String data;

    private VerificationType verificationType;

    private Integer userId;
}