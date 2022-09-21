package in.dudeapp.userservice.controller;

import in.dudeapp.common.annotation.TrackExecutionTime;
import in.dudeapp.userservice.dto.request.*;
import in.dudeapp.userservice.dto.response.*;
import in.dudeapp.userservice.entity.User;
import in.dudeapp.userservice.entity.constants.DocumentType;
import in.dudeapp.userservice.entity.constants.UserType;
import in.dudeapp.userservice.exception.UserException;
import in.dudeapp.userservice.service.FileService;
import in.dudeapp.userservice.service.GoogleService;
import in.dudeapp.userservice.service.UserService;
import in.dudeapp.userservice.validator.UserRequestValidator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Map;
import java.util.Objects;

import static in.dudeapp.userservice.constant.ErrorMessage.USER_ERROR_CODE_0013;
import static in.dudeapp.userservice.constant.ErrorMessage.USER_ERROR_CODE_0014;

/**
 * Created by mohan on 11/06/22
 */
@RestController
public class UserController {

    private final UserService userService;

    private final GoogleService googleService;

    private final UserRequestValidator userRequestValidator;

    private final FileService fileService;

    public UserController(UserService userService, GoogleService googleService, UserRequestValidator userRequestValidator, FileService fileService) {
        this.userService = userService;
        this.googleService = googleService;
        this.userRequestValidator = userRequestValidator;
        this.fileService = fileService;
    }

    @PostMapping("sendOTP")
    @TrackExecutionTime
    public UserOTPResponse sendOTP(@RequestBody @Valid SendOTPRequest request) {
        switch (request.getVerificationType()) {
            case MOBILE -> {
                return this.userService.sendMobileOTP(request);
            }
            case EMAIL -> {
                if (Objects.isNull(request.getUserId())) {
                    throw new UserException(USER_ERROR_CODE_0013);
                }
                User user = this.userService.getUserById(request.getUserId());
                return this.userService.sendEmailOTP(user, request);
            }
        }
        throw new UserException(USER_ERROR_CODE_0014);

    }

    @PutMapping("verifyOTP/{userId}")
    @TrackExecutionTime
    public VerifyOTPResponse verifyOTP(@PathVariable("userId") Integer userId, @RequestBody @Valid VerifyOTPRequest request) {
        return this.userService.verifyOTP(userId, request);
    }

    @PostMapping("googleSignUp")
    @TrackExecutionTime
    public GoogleSignUpResponse googleSignUp(@RequestBody GoogleSignUpRequest request) {
        return googleService.verifyTokenAndSaveUser(request);
    }

    @GetMapping("{userId}")
    @TrackExecutionTime
    public User getUser(@PathVariable("userId") Integer userId) {
        return this.userService.getUserById(userId);
    }

    @PostMapping("validate")
    @ResponseBody
    @TrackExecutionTime
    public ValidateUserResponse validateUser(@Valid @RequestBody SendOTPRequest request) {
        User user = this.userService.validateUser(request);
        return ValidateUserResponse.builder().isValid(Boolean.TRUE)
                .onBoardingStage(user.getOnBoardingStage())
                .userId(String.valueOf(user.getId()))
                .build();
    }

    @PutMapping("{userId}")
    @TrackExecutionTime
    public User updateUser(@PathVariable("userId") Integer userId, @RequestBody @Valid UpdateUserRequest request) {
        User user = this.userService.getUserById(userId);
        if (request.getUserType().equals(UserType.STUDENT))
            this.userRequestValidator.validateUpdateStudentUserRequest(user, request);
        else
            this.userRequestValidator.validateUserVerification(user);
        user = this.userService.updateUser(user, request);
        return user;
    }

    @PutMapping("/{userId}/upload/{documentType}")
    @TrackExecutionTime
    public SelfieResponse uploadSelfie(@PathVariable("userId") Integer userId,
                                       @PathVariable("documentType") DocumentType documentType,
                                       @RequestPart("file") MultipartFile file) {
        if (Objects.isNull(file)) {
            throw new RuntimeException("Please add file");
        }
        User user = this.userService.getUserById(userId);
        DocumentUploadRequest request = DocumentUploadRequest.builder()
                .documentType(documentType)
                .userId(userId)
                .build();

        return this.userService.uploadSelfie(user, file, request);
    }

    @GetMapping("preSignedURL")
    @TrackExecutionTime
    public Map<String, String> getPreSignedURL(@RequestParam("bucketName") String bucketName, @RequestParam("objectName") String objectName) {
        String url = this.fileService.getPreSignedUrl(bucketName, objectName);
        return Map.of("preSignedURL", url);
    }

    @DeleteMapping("{userId}")
    @TrackExecutionTime
    public String deleteUserById(@PathVariable("userId") Integer id) {
        // TODO add delete method in userservice.
        this.userService.deleteUserById(id);
        return "User Deleted Successfully";
    }
}