package my.excercise.travel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import my.excercise.ApplicationConfig;
import my.excercise.request.Fare;
import my.excercise.request.Location;
import my.excercise.stats.StatisticsInterceptor;

@Service
@Scope("application")
public class TravelServiceCaller {

	@Autowired
	ApplicationConfig config;

	private String getAirportUrl;
	private String fareUrl;

	@PostConstruct
	public void setGetAirportUrl() {
		this.getAirportUrl = config.properties.getProperty(config.TRAVELAPI_ADDRESS)
				+ config.properties.getProperty(config.AIRPORTS);
		this.fareUrl = config.properties.getProperty(config.TRAVELAPI_ADDRESS)
				+ config.properties.getProperty(config.FARES);
	}

	public List<Location> getAirports() {

		try {
			HttpEntity<String> response = getAirportListResponse();

			List<Location> airports = parseResponseToAirports(response);

			return airports;

		} catch (Exception ex) {
			return new ArrayList<>();
		}
	}

	public FareModel getFare(String depPort, String arrPort) {

		OAuth2RestTemplate restTemplate = getRestTemplate();

		HttpEntity<String> entity = createHeaderEntity();
		try {
			FareModel fareModel = getFareModel(depPort, arrPort, restTemplate, entity);
			return fareModel;

		} catch (InterruptedException | ExecutionException e) {
			return null;
		}

	}

	private List<Location> parseResponseToAirports(HttpEntity<String> response)
			throws IOException, JsonParseException, JsonMappingException {
		String body = response.getBody();

		ObjectMapper objectMapper = new ObjectMapper();

		JsonNode rootNode = objectMapper.readTree(body);
		JsonNode locationsNode = rootNode.findValue("locations");

		List<Location> airports = objectMapper.readValue(locationsNode.toString(), new TypeReference<List<Location>>() {
		});
		return airports;
	}

	private HttpEntity<String> getAirportListResponse() {
		OAuth2RestTemplate restTemplate = getRestTemplate();

		HttpEntity<String> entity = createHeaderEntity();

		HttpEntity<String> response = restTemplate.exchange(getAirportUrl, HttpMethod.GET, entity, String.class);
		return response;
	}

	private HttpEntity<String> createHeaderEntity() {
		HttpHeaders headers = createHeader();
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		return entity;
	}

	private HttpHeaders createHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

	private FareModel getFareModel(String depPort, String arrPort, OAuth2RestTemplate restTemplate,
			HttpEntity<String> entity) throws InterruptedException, ExecutionException {
		Future<Location> depPortInfo = getAirportDetail(depPort, restTemplate, entity);
		Future<Location> arrPortInfo = getAirportDetail(arrPort, restTemplate, entity);
		Future<Fare> fare = getFare(depPort, arrPort, restTemplate, entity);

		while (!(depPortInfo.isDone() && arrPortInfo.isDone() && fare.isDone())) {
			Thread.sleep(10);
		}

		FareModel fareModel = new FareModel();
		fareModel.fare = fare.get();
		fareModel.depPort = depPortInfo.get();
		fareModel.arrPort = arrPortInfo.get();

		return fareModel;
	}

	@Async
	private AsyncResult<Fare> getFare(String depPort, String arrPort, OAuth2RestTemplate restTemplate,
			HttpEntity<String> entity) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("currency", "EUR");

		ResponseEntity<Fare> response = restTemplate.exchange(fareUrl + "/" + depPort + "/" + arrPort, HttpMethod.GET,
				entity, Fare.class, params);
		Fare fare = response.getBody();
		return new AsyncResult<Fare>(fare);

	}

	@Async
	private AsyncResult<Location> getAirportDetail(String depPort, OAuth2RestTemplate restTemplate,
			HttpEntity<String> entity) {

		ResponseEntity<Location> response = restTemplate.exchange(getAirportUrl + "/" + depPort, HttpMethod.GET, entity,
				Location.class);
		Location airport = response.getBody();
		return new AsyncResult<Location>(airport);

	}

	@Bean
	protected ClientCredentialsResourceDetails getOAuthDetails() {
		ClientCredentialsResourceDetails credentials = new ClientCredentialsResourceDetails();
		credentials.setAccessTokenUri(config.properties.getProperty(config.TOKEN_ADDRESS));
		credentials.setClientId(config.properties.getProperty(config.USERNAME));
		credentials.setClientSecret(config.properties.getProperty(config.PASSWORD));
		credentials.setGrantType("client_credentials");
		return credentials;
	}

	@Bean
	protected OAuth2RestTemplate getRestTemplate() {

		DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();

		OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(getOAuthDetails(), clientContext);

		List<ClientHttpRequestInterceptor> ints = new ArrayList<>();
		ints.add(new StatisticsInterceptor());

		restTemplate.setInterceptors(ints);

		return restTemplate;
	}

}
