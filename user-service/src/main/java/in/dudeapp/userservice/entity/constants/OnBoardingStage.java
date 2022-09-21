package in.dudeapp.userservice.entity.constants;

import lombok.Getter;

/**
 * Created by mohan on 10/06/22
 */
@Getter
public enum OnBoardingStage {
    EMAIL_OTP_SENT,
    EMAIL_VERIFIED,
    MOBILE_OTP_SENT,
    MOBILE_OTP_VERIFIED,
    USER_DETAILS_SAVED;
}
