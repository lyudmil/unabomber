package unabomber.client;

import org.apache.http.client.methods.HttpPut;
import org.apache.http.params.BasicHttpParams;

import android.location.Location;


public class GameEngine {
	private static final String SERVER = "http://10.0.2.2:3000";
	private UnabomberHttpClient httpClient;
	private String playerUrl;

	public GameEngine(UnabomberHttpClient httpClient, String deviceId) {
		this.httpClient = httpClient;
		playerUrl = "/players/" + deviceId;
	}
	
	public GameEngine(String deviceId) {
		this(new UnabomberHttpClient(), deviceId);
	}

	public void sendLocation(Location currentLocation) {
		HttpPut request = new HttpPut(SERVER + playerUrl + "/update");
		BasicHttpParams params = new BasicHttpParams();
		params.setParameter("current_location", currentLocation);
		request.setParams(params);
		httpClient.executeRequest(request);
	}
}
