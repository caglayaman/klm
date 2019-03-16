package my.excercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@SpringBootApplication
@EnableAutoConfiguration
@EnableOAuth2Client
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}