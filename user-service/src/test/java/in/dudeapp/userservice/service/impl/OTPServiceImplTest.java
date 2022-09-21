package in.dudeapp.userservice.service.impl;

import in.dudeapp.userservice.constant.VerificationType;
import in.dudeapp.userservice.dto.OTPAPIResponse;
import in.dudeapp.userservice.dto.request.VerifyOTPRequest;
import in.dudeapp.userservice.dto.response.VerifyOTPResponse;
import in.dudeapp.userservice.entity.Verification;
import in.dudeapp.userservice.repository.VerificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author nandhan, Created on 21/07/22
 */
@ExtendWith(MockitoExtension.class)
class OTPServiceImplTest {

    @Mock private VerificationRepository repository;
    @InjectMocks private OTPServiceImpl service;

    private Verification verification;
    private VerifyOTPRequest otpRequest;

    @BeforeEach
    public void setup() {

        ReflectionTestUtils.setField(service,"otpExpirationMinutes",2);
        verification = new Verification();
        verification.setId(1); verification.setData("1234");
        verification.setVerificationType(VerificationType.EMAIL);
        verification.setCreatedOn(LocalDateTime.now());
        verification.setVerifiedOn(LocalDateTime.now());

        otpRequest = new VerifyOTPRequest();
        otpRequest.setOtp("1234");
        /*
        otpResponse = VerifyOTPResponse.builder().message("some message")
                .verificationType(verification.getVerificationType())
                .build();
         */
    }

    @Test
    public void testSendOtp() {
        // don't have much to tell
        OTPAPIResponse otpapiResponse = service.sendOTP("0000");
        assertThat(otpapiResponse.getOtp()).isEqualTo("1234");
    }

    @Test
    public void testVerifyOtp() {
        when(repository.save(any(Verification.class))).thenReturn(verification);
        VerifyOTPResponse verifyOTPResponse = service.verifyOTP(verification,otpRequest);
        assertThat(verifyOTPResponse).isNotNull();
        assertThat(verifyOTPResponse.getMessage()).isEqualTo("Verified successfully");

    }
}