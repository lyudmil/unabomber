package engine;

import http.AuthenticateUserParameters;
import http.PlaceBombParameters;
import http.PostLocationParameters;
import http.UnabomberHttpClient;

import java.util.ArrayList;

import json.JSONUtil;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;

import android.location.Location;


public class GameEngine {
	private static final String PLACE_BOMBS_ACTION = "/bombs/place";
	private static final String LOCATIONS_CONTROLLER = "/locations";
	private static final String PLAYERS_CONTROLLER = "/players";
	private static final String SERVER = "http://10.0.2.2:3000";
	
	private UnabomberHttpClient httpClient;
	private String playerUrl;
	private String deviceId;

	public GameEngine(UnabomberHttpClient httpClient, String deviceId) {
		this.httpClient = httpClient;
		this.deviceId = deviceId;
		playerUrl = "/" + deviceId;
	}
	
	public GameEngine(String deviceId) {
		this(new UnabomberHttpClient(), deviceId);
	}

	public void sendLocation(Location currentLocation) {
		HttpPut request = new HttpPut(SERVER + PLAYERS_CONTROLLER + playerUrl + "/update");
		request.setEntity(new PostLocationParameters(currentLocation).encode());
		httpClient.executeRequest(request);
	}

	public PlayerData authenticate() {
		HttpPost request = new HttpPost(SERVER + PLAYERS_CONTROLLER + "/create");
		request.setEntity(new AuthenticateUserParameters(deviceId).encode());
		HttpResponse response = httpClient.executeRequest(request);
		return JSONUtil.playerDataFrom(response);
	}

	public ArrayList<PlayerLocation> getLocations() {
		HttpGet request = new HttpGet(SERVER + LOCATIONS_CONTROLLER);
		HttpResponse response = httpClient.executeRequest(request);
		return JSONUtil.locationsFrom(response);
	}

	public void placeBombAt(Location currentLocation) {
		HttpPost request = new HttpPost(SERVER + "/" + deviceId + PLACE_BOMBS_ACTION);
		request.setEntity(new PlaceBombParameters(currentLocation).encode());
		httpClient.executeRequest(request);
	}
}
