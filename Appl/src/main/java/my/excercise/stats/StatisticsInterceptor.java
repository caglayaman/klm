package my.excercise.stats;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StopWatch;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

public class StatisticsInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {

		try {
			StopWatch stopwatch = new StopWatch();
			stopwatch.start();
			ClientHttpResponse response = execution.execute(request, body);
			stopwatch.stop();
			long responseTime = stopwatch.getTotalTimeMillis();
			Statistics.Ok();
			Statistics.newResponseTime(responseTime);
			return response;

		} catch (InternalServerError e) {
			Statistics.err500();
		} catch (BadRequest e) {
			Statistics.err400();
		}
		return null;
	}
}
