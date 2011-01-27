package engine;

import http.ArrestedPlayerParameters;
import http.AuthenticateUserParameters;
import http.PlaceBombParameters;
import http.PostLocationParameters;
import http.PostMessageParameters;
import http.UnabomberHttpClient;

import java.util.ArrayList;

import json.JSONUtil;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;

import android.location.Location;


public class GameEngine {
	
	//define the enum
	public enum GameStatus { STARTED, FINISHEDWIN, FINISHEDLOSE, FINISHEDKILLED, FINISHEDJAILED };
	
	private static final String PLACE_BOMBS_ACTION = "/bombs/place";
	private static final String LOCATIONS_CONTROLLER = "/players";
	private static final String PLAYERS_CONTROLLER = "/players";

//	private static final String SERVER = "http://10.0.2.2:3000";
	private static final String SERVER = "http://unabomber.heroku.com";

	private static final String AGENT_ARREST = "/arrest";
	private static final String GAME_STATUS = "/status";
	private static final String BOMBS = "/bombs";
	

	private static final String DETONATION="/detonate";
	
	private static final String MESSAGES_CONTROLLER = "/messages";
	
	private UnabomberHttpClient httpClient;
	private String playerUrl;
	private String deviceId;
	
	GameStatus gameStatus;
	
	public GameStatus getGameStatus() {
		return gameStatus;
	}

	public synchronized GameStatus updateGameStatus(String deviceId) {
		this.gameStatus = getStatusOfTheGame(deviceId);
		return this.getGameStatus();
	}


	public GameEngine(UnabomberHttpClient httpClient, String deviceId) {
		this.httpClient = httpClient;
		this.deviceId = deviceId;
		this.gameStatus = GameStatus.STARTED;
		playerUrl = "/" + deviceId;
		
	}
	
	public GameEngine(String deviceId) {
		this(new UnabomberHttpClient(), deviceId);
	}

	public synchronized void sendLocation(Location currentLocation) {
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
	
	public synchronized void sendPlayerToJail(int agentPlayer, int arrestedPlayer) {
		String uri = SERVER + PLAYERS_CONTROLLER + "/" + String.valueOf(agentPlayer) + AGENT_ARREST;
		HttpPost request = new HttpPost(uri);
		request.setEntity(new ArrestedPlayerParameters(String.valueOf(arrestedPlayer)).encode());
		httpClient.executeRequest(request);
	}
	
	//deviceId or receiver?
	public synchronized void sendMessageTo(int receiver, String message){
		HttpPut request = new HttpPut(SERVER +"/" + deviceId + MESSAGES_CONTROLLER + "/create");
		//HttpPut request = new HttpPut(SERVER +"/" + String.valueOf(receiver) + MESSAGES_CONTROLLER + "/create");
		request.setEntity(new PostMessageParameters(receiver, message).encode());
		httpClient.executeRequest(request);	
	}
	//getMassages
	public synchronized ArrayList<PlayerMessage> getMessages(){
		HttpGet request = new HttpGet(SERVER + "/"+ deviceId + MESSAGES_CONTROLLER);
		//HttpGet request = new HttpGet(SERVER + "/"+ playerUrl + MESSAGES_CONTROLLER);
		HttpResponse response = httpClient.executeRequest(request);
		return JSONUtil.messagesFrom(response);
		
	}
	
	public synchronized void detonateBomb(int detonatedBomb){
		String uri = SERVER + BOMBS + "/" + String.valueOf(detonatedBomb) +  DETONATION;
		HttpPost request = new HttpPost(uri);
		httpClient.executeRequest(request);
	}

	public synchronized ArrayList<PlayerLocation> getLocations() {
		HttpGet request = new HttpGet(SERVER + LOCATIONS_CONTROLLER);
		HttpResponse response = httpClient.executeRequest(request);
		return JSONUtil.locationsFrom(response);
	}

	public synchronized int placeBombAt(Location currentLocation) {
		HttpPost request = new HttpPost(SERVER + "/" + deviceId + PLACE_BOMBS_ACTION);
		request.setEntity(new PlaceBombParameters(currentLocation).encode());
		HttpResponse response = httpClient.executeRequest(request);
		return JSONUtil.bombIndexFrom(response);
	}
	
	
	public synchronized GameStatus getStatusOfTheGame(String deviceId) {
		HttpGet request = new HttpGet(SERVER + "/" + deviceId + GAME_STATUS);
		HttpResponse response = httpClient.executeRequest(request);
		return JSONUtil.gameStatusFrom(response);
	}
}
