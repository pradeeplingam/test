package in.dudeapp.userservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ConfigurationPropertiesScan
@ComponentScan(basePackages = "in.dudeapp.*")
@OpenAPIDefinition(
		info = @Info(
				title = "User Service API",
				description = "This application exposes REST end-points for User.",
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
				url = "http://dudeapp-dev-326533990.ap-south-1.elb.amazonaws.com/user/",
				description = "Development Server"
		)
)
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
