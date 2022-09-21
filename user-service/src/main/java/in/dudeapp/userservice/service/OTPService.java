package in.dudeapp.userservice.service;

import in.dudeapp.userservice.dto.OTPAPIResponse;
import in.dudeapp.userservice.dto.request.VerifyOTPRequest;
import in.dudeapp.userservice.dto.response.VerifyOTPResponse;
import in.dudeapp.userservice.entity.Verification;

/**
 * Created by mohan on 10/06/22
 */
public interface OTPService {
    OTPAPIResponse sendOTP(String mobileNumber);

    VerifyOTPResponse verifyOTP(Verification userId, VerifyOTPRequest request);
}
