package in.dudeapp.userservice.service;

import in.dudeapp.userservice.dto.request.GoogleSignUpRequest;
import in.dudeapp.userservice.dto.response.GoogleSignUpResponse;

/**
 * Created by mohan on 11/06/22
 */
public interface GoogleService {


    GoogleSignUpResponse verifyTokenAndSaveUser(GoogleSignUpRequest request);
}
