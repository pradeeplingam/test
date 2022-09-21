package in.dudeapp.userservice.service;

import in.dudeapp.userservice.dto.request.DocumentUploadRequest;
import in.dudeapp.userservice.dto.request.SendOTPRequest;
import in.dudeapp.userservice.dto.request.UpdateUserRequest;
import in.dudeapp.userservice.dto.request.VerifyOTPRequest;
import in.dudeapp.userservice.dto.response.SelfieResponse;
import in.dudeapp.userservice.dto.response.UserOTPResponse;
import in.dudeapp.userservice.dto.response.VerifyOTPResponse;
import in.dudeapp.userservice.entity.User;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by mohan on 10/06/22
 */
public interface UserService {
    UserOTPResponse sendMobileOTP(SendOTPRequest request);

    UserOTPResponse sendEmailOTP(User user, SendOTPRequest email);

    User validateUser(SendOTPRequest userId);

    User getUserByMobile(String mobileNumber);

    User getUserById(Integer userId);

    User updateUser(User user, UpdateUserRequest request);

    VerifyOTPResponse verifyOTP(Integer userId, VerifyOTPRequest request);

    SelfieResponse uploadSelfie(User user, MultipartFile file, DocumentUploadRequest request);

    void deleteUserById(int id);
}
