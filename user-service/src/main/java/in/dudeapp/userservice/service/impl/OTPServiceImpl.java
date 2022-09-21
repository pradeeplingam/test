package in.dudeapp.userservice.service.impl;

import in.dudeapp.userservice.dto.OTPAPIResponse;
import in.dudeapp.userservice.dto.request.VerifyOTPRequest;
import in.dudeapp.userservice.dto.response.VerifyOTPResponse;
import in.dudeapp.userservice.entity.Verification;
import in.dudeapp.userservice.exception.UserException;
import in.dudeapp.userservice.repository.VerificationRepository;
import in.dudeapp.userservice.service.OTPService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static in.dudeapp.userservice.constant.ErrorMessage.USER_ERROR_CODE_0010;
import static in.dudeapp.userservice.constant.ErrorMessage.USER_ERROR_CODE_0011;

/**
 * Created by mohan on 11/06/22
 */
@Service
public class OTPServiceImpl implements OTPService {

    @Value("${otp.expirationMinutes}")
    private Integer otpExpirationMinutes;

    private final VerificationRepository verificationRepository;

    public OTPServiceImpl(VerificationRepository verificationRepository) {
        this.verificationRepository = verificationRepository;
    }

    @Override
    public OTPAPIResponse sendOTP(String mobileNumber) {
        return OTPAPIResponse.builder()
                .otp("1234").build();
    }

    @Override
    public VerifyOTPResponse verifyOTP(Verification verification, VerifyOTPRequest request) {
        LocalDateTime createdOn = verification.getCreatedOn();
        LocalDateTime currentDateTime = LocalDateTime.now();

        if (ChronoUnit.SECONDS.between(currentDateTime, createdOn) > otpExpirationMinutes * 60) {
            throw new UserException(USER_ERROR_CODE_0010);
        }

        if (!verification.getData().equals(request.getOtp())) {
            throw new UserException(USER_ERROR_CODE_0011);
        }
        verification.setVerifiedOn(LocalDateTime.now());
        this.verificationRepository.save(verification);
        return VerifyOTPResponse.builder().message("Verified successfully")
                .verificationType(request.getVerificationType()).build();
    }
}