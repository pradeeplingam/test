package in.dudeapp.userservice.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import in.dudeapp.userservice.dto.request.GoogleSignUpRequest;
import in.dudeapp.userservice.dto.response.GoogleSignUpResponse;
import in.dudeapp.userservice.entity.User;
import in.dudeapp.userservice.service.GoogleService;
import in.dudeapp.userservice.service.UserService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Created by mohan on 11/06/22
 */
@Service
public class GoogleServiceImpl implements GoogleService {

    private final UserService userService;

    private final GoogleIdTokenVerifier verifier;

    public GoogleServiceImpl(UserService userService, GoogleIdTokenVerifier verifier) {
        this.userService = userService;
        this.verifier = verifier;
    }

    @Override
    public GoogleSignUpResponse verifyTokenAndSaveUser(GoogleSignUpRequest request) {


        GoogleIdToken idToken;
        try {
            idToken = verifier.verify(request.getToken());
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);

            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = payload.getEmailVerified();
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");

            // Use or store profile information
            // ...
            User user= new User();
            user.setEmail(email);
            user.setEmailVerified(emailVerified);
            user.setFirstName(name);
            user.setProfileImageUrl(pictureUrl);
            user.setLastName(familyName);

        } else {
            System.out.println("Invalid ID token.");
        }
        return null;
    }
}
