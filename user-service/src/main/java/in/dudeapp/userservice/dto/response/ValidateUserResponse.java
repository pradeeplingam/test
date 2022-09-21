package in.dudeapp.userservice.dto.response;

import in.dudeapp.userservice.entity.constants.OnBoardingStage;
import lombok.Builder;
import lombok.Data;

/**
 * Created by mohan on 20/07/22
 */
@Builder
@Data
public class ValidateUserResponse {
    private boolean isValid;
    private OnBoardingStage onBoardingStage;
    private String userId;
}
