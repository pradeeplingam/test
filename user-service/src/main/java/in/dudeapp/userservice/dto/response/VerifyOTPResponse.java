package in.dudeapp.userservice.dto.response;

import in.dudeapp.userservice.constant.VerificationType;
import lombok.Builder;
import lombok.Data;

/**
 * Created by mohan on 20/07/22
 */
@Data
@Builder
public class VerifyOTPResponse {
    private String message;
    private String userId;
    private VerificationType verificationType;
}
