package in.dudeapp.userservice.config;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by mohan on 18/06/22
 */
@Configuration
public class GoogleConfig {
    private static final HttpTransport transport = new NetHttpTransport();
    private static final JsonFactory jsonFactory = new GsonFactory();
    private static final String CLIENT_ID = "689042834369-1115a4r3kuk3gu2a637c8gkk01liu39h.apps.googleusercontent.com";


    @Bean
    public GoogleIdTokenVerifier googleIdTokenVerifier(){
        return new GoogleIdTokenVerifier.
                Builder(transport, jsonFactory)
                .setIssuers(Arrays.asList("https://accounts.google.com", "accounts.google.com"))
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();
    }
}
