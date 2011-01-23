package engine;

import http.ArrestedPlayerParameters;
import http.AuthenticateUserParameters;
import http.PostMessageParameters;

import http.PlaceBombParameters;
import http.DetonatedBombParameters;

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
	
	//define the enum
	public enum GameStatus { STARTED, FINISHEDWIN, FINISHEDLOSE, FINISHEDKILLED, FINISHEDJAILED };
	
	private static final String PLACE_BOMBS_ACTION = "/bombs/place";
	private static final String LOCATIONS_CONTROLLER = "/players";
	private static final String PLAYERS_CONTROLLER = "/players";
	private static final String SERVER = "http://10.0.2.2:3000";
	private static final String R_SERVER = "http://unabomber.heroku.com";
	private static final String AGENT_ARREST = "/arrest";
	private static final String GAME_STATUS = "/status";
	private static final String BOMBS = "/bombs";
	
	//
	private static final String DETONATION="/detonate";
	
	private static final String MESSAGES_CONTROLLER = "/messages";
	
	private UnabomberHttpClient httpClient;
	private String playerUrl;
	private String deviceId;
	
	GameStatus gameStatus;
	
	
	
	
	

	public GameStatus getGameStatus() {
		return gameStatus;
	}

	public GameStatus updateGameStatus(int playerId) {
		this.gameStatus = getStatusOfTheGame(playerId);
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

	public void sendLocation(Location currentLocation) {
		HttpPut request = new HttpPut(R_SERVER + PLAYERS_CONTROLLER + playerUrl + "/update");
		request.setEntity(new PostLocationParameters(currentLocation).encode());
		httpClient.executeRequest(request);
	}

	public PlayerData authenticate() {
		HttpPost request = new HttpPost(R_SERVER + PLAYERS_CONTROLLER + "/create");
		request.setEntity(new AuthenticateUserParameters(deviceId).encode());
		HttpResponse response = httpClient.executeRequest(request);
		return JSONUtil.playerDataFrom(response);
	}
	
	public void sendPlayerToJail(int agentPlayer, int arrestedPlayer) {
		String uri = R_SERVER + PLAYERS_CONTROLLER + "/" + String.valueOf(agentPlayer) + AGENT_ARREST;
		HttpPost request = new HttpPost(uri);
		request.setEntity(new ArrestedPlayerParameters(String.valueOf(arrestedPlayer)).encode());
		httpClient.executeRequest(request);
	}
	
	//
	public void sendMessageTo(int sender, int receiver, String message){
		
		//code to send the message to the player; not sure about using HttpPur or HttpPost
		HttpPut request = new HttpPut(R_SERVER +"/" + deviceId + MESSAGES_CONTROLLER + "/create");
		request.setEntity(new PostMessageParameters(message, receiver).encode());
		httpClient.executeRequest(request);
		
		
		
	}
	public ArrayList<PlayerMessage> getMessages(){
		HttpGet request = new HttpGet(R_SERVER + "/"+ deviceId + MESSAGES_CONTROLLER);
		HttpResponse response = httpClient.executeRequest(request);
		return JSONUtil.messagesFrom(response);
		
	}
	
	
	//
	public void detonateBomb(int detonatedBomb){
		String uri = SERVER + BOMBS + "/" + String.valueOf(detonatedBomb) +  DETONATION;

		HttpPost request = new HttpPost(uri);
		httpClient.executeRequest(request);
	}
	//

	public ArrayList<PlayerLocation> getLocations() {
		HttpGet request = new HttpGet(R_SERVER + LOCATIONS_CONTROLLER);
		HttpResponse response = httpClient.executeRequest(request);
		return JSONUtil.locationsFrom(response);
	}

	public int placeBombAt(Location currentLocation) {
		
		HttpPost request = new HttpPost(R_SERVER + "/" + deviceId + PLACE_BOMBS_ACTION);
		request.setEntity(new PlaceBombParameters(currentLocation).encode());
		//httpClient.executeRequest(request);
		HttpResponse response = httpClient.executeRequest(request);
		return JSONUtil.bombIndexFrom(response);
	}
	
	
	public GameStatus getStatusOfTheGame(int playerId) {
		HttpGet request = new HttpGet(SERVER + "/" + playerId + GAME_STATUS);
		HttpResponse response = httpClient.executeRequest(request);
		return JSONUtil.gameStatusFrom(response);
	}
	
	
	
	
}
