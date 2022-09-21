package in.dudeapp.collegeservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
@OpenAPIDefinition(
        info = @Info(
                title = "College Service API",
                description = "This application exposes REST end-points for College.",
                contact = @Contact(
                        name = "admin-dudeapp",
                        email = "admin@dudeapp.in",
                        url = "https://dudeapp.in"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"
                )
        ),
        servers = @Server(
                url = "https://dudeapp.in",
                description = "Development Server"
        )
)
public class CollegeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollegeServiceApplication.class, args);
    }
}
