package my.excercise;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.hibernate.validator.internal.util.privilegedactions.GetClassLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("application")
public class ApplicationConfig {

	public final String TRAVELAPI_ADDRESS = "travelapi-address";
	public final String TOKEN_ADDRESS = "travelapi-tokenaddress";
	public final String AIRPORTS = "travelapi-airports";
	public final String FARES = "travelapi-fares";
	public final String USERNAME = "client-id";
	public final String PASSWORD = "password";

	@Autowired
	public Properties properties;

	@PostConstruct
	public void setProperties() {
		InputStream serviceprop = this.getClass().getResourceAsStream("/service.properties");
		try {
			properties.load(serviceprop);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
