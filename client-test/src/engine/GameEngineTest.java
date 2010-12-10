package engine;

import http.AuthenticateUserParameters;
import http.PostLocationParameters;
import http.UnabomberHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import junit.framework.TestCase;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;

import engine.GameEngine;

import android.location.Location;

public class GameEngineTest extends TestCase {
	private GameEngine gameEngine;
	private MockHttpClient httpClient;
	private static final String DEVICE_ID = "fake-device-id";
	private Location currentLocation;

	@Override
	protected void setUp() throws Exception {
		httpClient = new MockHttpClient(null);
		gameEngine = new GameEngine(httpClient, DEVICE_ID);

		currentLocation = new Location("GPS");
		currentLocation.setLatitude(9);
		currentLocation.setLongitude(49);
	}

	public void testSendLocationSendsPutRequest() {
		gameEngine.sendLocation(currentLocation);

		HttpUriRequest request = httpClient.getRequest();
		assertEquals("PUT", request.getMethod());
	}

	public void testSendLocationRequestContainsCurrentLocation()
			throws Exception {
		gameEngine.sendLocation(currentLocation);

		HttpPut request = (HttpPut) httpClient.getRequest();
		UrlEncodedFormEntity expectedEncodedParameters = new PostLocationParameters(currentLocation).encode();
		UrlEncodedFormEntity actualEncodedParameters = (UrlEncodedFormEntity) request
				.getEntity();

		assertEquals(parametersFrom(expectedEncodedParameters),
				parametersFrom(actualEncodedParameters));
	}

	public void testSendLocationRequestIsToTheProperUrl() {
		gameEngine.sendLocation(currentLocation);

		HttpUriRequest request = httpClient.getRequest();
		assertEquals("http://10.0.2.2:3000/players/" + DEVICE_ID + "/update",
				request.getURI().toString());
	}
	
	public void testUserAuthenticationRequestIsToTheProperUrl() {
		gameEngine.authenticate();
		
		HttpUriRequest request = httpClient.getRequest();
		assertEquals("http://10.0.2.2:3000/players/create",
				request.getURI().toString());
	}
	
	public void testUserAuthenticationRequestIsAPost() {
		gameEngine.authenticate();
		
		HttpUriRequest request = httpClient.getRequest();
		assertEquals("POST", request.getMethod());
	}
	
	public void testUserAuthenticationRequestContainsDeviceId() throws Exception {
		gameEngine.authenticate();
		
		HttpPost request = (HttpPost) httpClient.getRequest();
		UrlEncodedFormEntity expectedEncodedParameters = new AuthenticateUserParameters(DEVICE_ID).encode();
		UrlEncodedFormEntity actualEncodedParameters = (UrlEncodedFormEntity) request.getEntity();
		
		assertEquals(parametersFrom(expectedEncodedParameters), parametersFrom(actualEncodedParameters));
	}

	private String parametersFrom(UrlEncodedFormEntity encodedParameters) throws IOException {
		BufferedReader expectedParametersReader = new BufferedReader(new InputStreamReader(encodedParameters.getContent()));
		String parameters = expectedParametersReader.readLine();
		encodedParameters.getContent().close();
		return parameters;
	}

	public final class MockHttpClient extends UnabomberHttpClient {
		private HttpResponse response;
		private HttpUriRequest request;

		private MockHttpClient(HttpResponse response) {
			this.response = response;
		}

		public HttpUriRequest getRequest() {
			return request;
		}

		@Override
		public HttpResponse executeRequest(HttpUriRequest request) {
			this.request = request;
			return response;
		}
	}
}
