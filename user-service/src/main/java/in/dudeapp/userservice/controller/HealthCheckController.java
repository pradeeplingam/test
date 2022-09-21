package in.dudeapp.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by mohan on 11/06/22
 */
@RestController
public class HealthCheckController {
    @GetMapping
    public Map<String,String> healthCheck(){
        return Map.of("status","User service is Up");
    }
}
