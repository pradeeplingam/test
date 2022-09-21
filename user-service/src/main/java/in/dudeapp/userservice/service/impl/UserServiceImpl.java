package in.dudeapp.userservice.service.impl;

import in.dudeapp.common.service.KMSService;
import in.dudeapp.userservice.constant.ErrorMessage;
import in.dudeapp.userservice.constant.VerificationType;
import in.dudeapp.userservice.dto.OTPAPIResponse;
import in.dudeapp.userservice.dto.request.DocumentUploadRequest;
import in.dudeapp.userservice.dto.request.SendOTPRequest;
import in.dudeapp.userservice.dto.request.UpdateUserRequest;
import in.dudeapp.userservice.dto.request.VerifyOTPRequest;
import in.dudeapp.userservice.dto.response.SelfieResponse;
import in.dudeapp.userservice.dto.response.UserOTPResponse;
import in.dudeapp.userservice.dto.response.VerifyOTPResponse;
import in.dudeapp.userservice.entity.StudentDetails;
import in.dudeapp.userservice.entity.User;
import in.dudeapp.userservice.entity.Verification;
import in.dudeapp.userservice.entity.constants.OnBoardingStage;
import in.dudeapp.userservice.entity.constants.UserType;
import in.dudeapp.userservice.exception.UserException;
import in.dudeapp.userservice.repository.StudentDetailsRepository;
import in.dudeapp.userservice.repository.UserRepository;
import in.dudeapp.userservice.repository.VerificationRepository;
import in.dudeapp.userservice.service.FileService;
import in.dudeapp.userservice.service.OTPService;
import in.dudeapp.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static in.dudeapp.userservice.constant.ErrorMessage.*;
import static in.dudeapp.userservice.entity.constants.OnBoardingStage.*;

/**
 * Created by mohan on 10/06/22
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final VerificationRepository verificationRepository;

    private final OTPService otpService;

    private final KMSService kmsService;


    private final FileService fileService;
    private final StudentDetailsRepository studentDetailsRepository;

    @Value("${aws.kms.arn}")
    private String keyID;

    @Value("${aws.kycDocumentBucket}")
    private String kycDocumentBucket;

    public UserServiceImpl(UserRepository userRepository, VerificationRepository verificationRepository, OTPService otpService, KMSService kmsService, FileService fileService, StudentDetailsRepository studentDetailsRepository) {
        this.userRepository = userRepository;
        this.verificationRepository = verificationRepository;
        this.otpService = otpService;
        this.kmsService = kmsService;
        this.fileService = fileService;
        this.studentDetailsRepository = studentDetailsRepository;
    }

    @Override
    @Transactional
    public UserOTPResponse sendMobileOTP(SendOTPRequest request) {
        Optional<User> optionalUser = userRepository.findByMobileNumber(request.getData());
        if (optionalUser.isPresent()) {
            if (optionalUser.get().getMobileVerified()) {
                throw new UserException(ErrorMessage.USER_ERROR_CODE_0001);
            }
        }
        String mobileNumber = request.getData();
        OTPAPIResponse response = otpService.sendOTP(mobileNumber);
        User user = optionalUser.orElse(new User());
        user.setMobileNumber(mobileNumber);
        addVerificationToUser(user, response, request.getVerificationType());
        user.setOnBoardingStage(OnBoardingStage.MOBILE_OTP_SENT);
        this.userRepository.saveAndFlush(user);
        return UserOTPResponse.builder().message("OTP Sent ").otp(response.getOtp())
                .userId(String.valueOf(user.getId())).build();
    }

    @Override
    @Transactional
    public UserOTPResponse sendEmailOTP(User user, SendOTPRequest request) {
        if(!user.getMobileVerified()){
            throw new UserException(USER_ERROR_CODE_0012);
        }
        String email = request.getData();
        OTPAPIResponse response = otpService.sendOTP(email);
        user.setEmail(email);
        addVerificationToUser(user, response, request.getVerificationType());
        user.setOnBoardingStage(OnBoardingStage.EMAIL_OTP_SENT);
        this.userRepository.saveAndFlush(user);
        return UserOTPResponse.builder().message("OTP Sent ").otp(response.getOtp())
                .userId(String.valueOf(user.getId())).build();
    }

    private void addVerificationToUser(User user, OTPAPIResponse response, VerificationType verificationType){
        Verification verification = new Verification();
        verification.setVerificationType(verificationType);
        verification.setData(response.getOtp());
        this.verificationRepository.save(verification);
        List<Verification> verifications = user.getVerifications();
        if(CollectionUtils.isEmpty(verifications)){
            verifications = new ArrayList<>();
        }
        verifications.add(verification);
        user.setVerifications(verifications);
    }

    @Override
    public User validateUser(SendOTPRequest request) {
        return switch (request.getVerificationType()) {
            case MOBILE -> getUserByMobile(request.getData());
            case EMAIL -> getUserByEmail(request.getData());
        };
    }

    private User getUserByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new UserException(USER_ERROR_CODE_0003);
        }
        return optionalUser.get();
    }

    @Override
    public User getUserByMobile(String mobileNumber) {
        log.info("Mobile number : {}", mobileNumber);
        Optional<User> optionalUser = userRepository.findByMobileNumber(mobileNumber);
        if (optionalUser.isEmpty()) {
            throw new UserException(USER_ERROR_CODE_0003);
        }
        return optionalUser.get();
    }

    @Override
    public User getUserById(Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserException(USER_ERROR_CODE_0003);
        }
        return optionalUser.get();
    }

    @Override
    @Transactional
    public User updateUser(User user, UpdateUserRequest request) {
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setMotherName(request.getMotherName());
        user.setPanCardNumber(request.getPanCardNumber());
        if (request.getUserType().equals(UserType.STUDENT)) {
            StudentDetails studentDetails = Optional.ofNullable(user.getStudentDetails()).orElse(new StudentDetails());
            studentDetails.setStay(request.getStay());
            studentDetails.setGraduationDate(request.getGraduationDate());
            studentDetails.setParentsOccupation(request.getParentsOccupation());
            this.studentDetailsRepository.save(studentDetails);
            user.setStudentDetails(studentDetails);
        }
        user.setOnBoardingStage(USER_DETAILS_SAVED);
        return this.userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public VerifyOTPResponse verifyOTP(Integer userId, VerifyOTPRequest request) {
        User user = getUserById(userId);
        Verification verification = user.getVerifications()
                .stream()
                .filter(v -> v.getVerificationType().name().equals(request.getVerificationType().name()))
                .findAny().get();

        VerifyOTPResponse response = this.otpService.verifyOTP(verification, request);
        switch (verification.getVerificationType()) {
            case EMAIL -> {
                user.setEmailVerified(Boolean.TRUE);
                user.setOnBoardingStage(EMAIL_VERIFIED);
            }
            case MOBILE -> {
                user.setMobileVerified(Boolean.TRUE);
                user.setOnBoardingStage(MOBILE_OTP_VERIFIED);
            }
        }
        this.userRepository.save(user);
        response.setUserId(String.valueOf(userId));
        return response;
    }

    @Override
    @Transactional
    public SelfieResponse uploadSelfie(User user, MultipartFile file, DocumentUploadRequest request) {
        String objectName = generateDocID(request);
        try {
            String s3Url = this.fileService.upload(file, kycDocumentBucket, objectName);
            user.setSelfieUrl(s3Url);
            user = this.userRepository.save(user);
            String preSignedUrl = this.fileService.getPreSignedUrl(kycDocumentBucket, objectName);
            return SelfieResponse.builder()
                    .userId(user.getId())
                    .s3Url(s3Url)
                    .preSignedUrl(preSignedUrl)
                    .build();
        } catch (IOException e) {
            throw new UserException(USER_ERROR_CODE_0015);
        }
    }

    @Override
    @Transactional
    public void deleteUserById(int id) {
        this.userRepository.delete(this.getUserById(id));
    }

    private String generateDocID(DocumentUploadRequest request){
        return request.getUserId()+"_"+request.getDocumentType()+".jpeg";
    }
}
