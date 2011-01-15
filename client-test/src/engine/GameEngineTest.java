package engine;

import http.AuthenticateUserParameters;
import http.PlaceBombParameters;
import http.PostLocationParameters;
import http.UnabomberHttpClient;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import junit.framework.TestCase;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHttpResponse;

import android.location.Location;

public class GameEngineTest extends TestCase {
	private GameEngine gameEngine;
	private MockHttpClient httpClient;
	private static final String DEVICE_ID = "fake-device-id";
	private Location currentLocation;

	@Override
	protected void setUp() throws Exception {
		currentLocation = new Location("GPS");
		currentLocation.setLatitude(9);
		currentLocation.setLongitude(49);
	}

	public void testSendLocationSendsPutRequest() {
		setUpLocationsResponse();
		gameEngine.sendLocation(currentLocation);

		HttpUriRequest request = httpClient.getRequest();
		assertEquals("PUT", request.getMethod());
	}

	public void testGetLocationsRequestProperlySent() {
		setUpLocationsResponse();
		gameEngine.getLocations();
		
		HttpGet request = (HttpGet) httpClient.getRequest();
		
		assertEquals("GET", request.getMethod());
		assertEquals("http://10.0.2.2:3000/locations", request.getURI().toString());
	}
	
	public void testSendLocationRequestContainsCurrentLocation() throws Exception {
		setUpLocationsResponse();
		gameEngine.sendLocation(currentLocation);

		HttpPut request = (HttpPut) httpClient.getRequest();
		UrlEncodedFormEntity expectedEncodedParameters = new PostLocationParameters(currentLocation).encode();
		UrlEncodedFormEntity actualEncodedParameters = (UrlEncodedFormEntity) request
				.getEntity();

		assertEquals(parametersFrom(expectedEncodedParameters),
				parametersFrom(actualEncodedParameters));
	}

	public void testSendLocationRequestIsToTheProperUrl() {
		setUpLocationsResponse();
		gameEngine.sendLocation(currentLocation);

		HttpUriRequest request = httpClient.getRequest();
		assertEquals("http://10.0.2.2:3000/players/" + DEVICE_ID + "/update",
				request.getURI().toString());
	}
	
	public void testUserAuthenticationRequestIsToTheProperUrl() {
		setUpAuthenticationResponse();
		gameEngine.authenticate();
		
		HttpUriRequest request = httpClient.getRequest();
		assertEquals("http://10.0.2.2:3000/players/create",
				request.getURI().toString());
	}
	
	public void testUserAuthenticationRequestIsAPost() {
		setUpAuthenticationResponse();
		gameEngine.authenticate();
		
		HttpUriRequest request = httpClient.getRequest();
		assertEquals("POST", request.getMethod());
	}
	
	public void testUserAuthenticationRequestContainsDeviceId() throws Exception {
		setUpAuthenticationResponse();
		gameEngine.authenticate();
		
		HttpPost request = (HttpPost) httpClient.getRequest();
		UrlEncodedFormEntity expectedEncodedParameters = new AuthenticateUserParameters(DEVICE_ID).encode();
		UrlEncodedFormEntity actualEncodedParameters = (UrlEncodedFormEntity) request.getEntity();
		
		assertEquals(parametersFrom(expectedEncodedParameters), parametersFrom(actualEncodedParameters));
	}
	
	public void testUserAuthenticationRequestRetrievesPlayerData() throws Exception {
		setUpAuthenticationResponse();
		
		PlayerData playerData = gameEngine.authenticate();
		
		assertEquals(66, playerData.getPlayerId());
		assertEquals("000000000000000451", playerData.getDeviceId());
	}
	
	public void testSendsProperRequestForPlacingBombs() {
		setUpAuthenticationResponse();
		
		gameEngine.placeBombAt(currentLocation);
		HttpPost request = (HttpPost) httpClient.getRequest();
		
		assertEquals("POST", request.getMethod());
		assertEquals("http://10.0.2.2:3000/" + DEVICE_ID + "/bombs/place",	request.getURI().toString());
	}
	
	public void testRequestForPlacingABombContainsLocation() throws Exception {
		setUpAuthenticationResponse();
		
		gameEngine.placeBombAt(currentLocation);
		HttpPost request = (HttpPost) httpClient.getRequest();
		
		UrlEncodedFormEntity expectedEncodedParameters = new PlaceBombParameters(currentLocation).encode();
		UrlEncodedFormEntity actualEncodedParameters = (UrlEncodedFormEntity) request.getEntity();
		
		assertEquals(parametersFrom(expectedEncodedParameters), parametersFrom(actualEncodedParameters));
	}

	private String parametersFrom(UrlEncodedFormEntity encodedParameters) throws IOException {
		BufferedReader expectedParametersReader = new BufferedReader(new InputStreamReader(encodedParameters.getContent()));
		String parameters = expectedParametersReader.readLine();
		encodedParameters.getContent().close();
		return parameters;
	}
	
	private void setUpLocationsResponse() {
		httpClient = new MockHttpClient(new MockHttpResponse("[]"));
		gameEngine = new GameEngine(httpClient, DEVICE_ID);
	}
	
	private void setUpAuthenticationResponse() {
		String playerJson = "{\"player\":{\"created_at\":\"2010-12-12T08:47:17Z\",\"device_id\":\"000000000000000451\",\"id\":66,\"updated_at\":\"2010-12-12T08:47:17Z\"}}";
		httpClient = new MockHttpClient(new MockHttpResponse(playerJson));
		gameEngine = new GameEngine(httpClient, DEVICE_ID);
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
	
	public final class MockHttpResponse extends BasicHttpResponse {

		private MockEntity entity;

		public MockHttpResponse(String content) {
			super(new ProtocolVersion("HTTP", 1, 0), 0, "");
			this.entity = new MockEntity(content);
		}
		
		@Override
		public HttpEntity getEntity() {
			return entity;
		}

		private class MockEntity extends BasicHttpEntity {
			private String content;

			private MockEntity(String content) {
				this.content = content;
			}

			@Override
			public InputStream getContent() throws IllegalStateException {
				return new ByteArrayInputStream(content.getBytes());
			}
			
			
		}
		
	}
}
