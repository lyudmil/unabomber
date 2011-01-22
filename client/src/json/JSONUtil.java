package json;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import engine.PlayerData;
import engine.PlayerLocation;
import engine.PlayerMessage;

import android.location.Location;

public class JSONUtil {

	public static synchronized ArrayList<PlayerLocation> locationsFrom(HttpResponse response) {
		try {
			return locationsFrom(response.getEntity().getContent());
		} catch (IllegalStateException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static synchronized PlayerData playerDataFrom(HttpResponse response) {
		try {
			return playerDataFrom(response.getEntity().getContent());
		} catch (IllegalStateException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	//code to receive messages
	//it gets all the messages
	public static ArrayList<PlayerMessage> messagesFrom(HttpResponse response){
		try {
			return messagesFrom(response.getEntity().getContent());
		} catch (IllegalStateException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	public static ArrayList<PlayerMessage> messagesFrom(InputStream responseEntityContent) {
		try {
			String json = IOUtil.convertToString(responseEntityContent);
			JSONArray messagesJson = new JSONArray(json);
			//	JSONArray locationsJson = new JSONArray(json);
			return messagesFrom(messagesJson);
		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private static ArrayList<PlayerMessage> messagesFrom(JSONArray messagesJson) throws JSONException {

		ArrayList<PlayerMessage> messages = new ArrayList<PlayerMessage>();

		for(int i = 0; i < messagesJson.length(); i ++) {
			JSONObject messageJson = messagesJson.getJSONObject(i).getJSONObject("message");
			PlayerMessage message = messageFrom(messageJson);
			messages.add(message);
		}

		return messages;
	}
	private static PlayerMessage messageFrom(JSONObject messageJson) throws JSONException {
		String message = new String(messageJson.getString("message"));
		int playerId = messageJson.getInt("player_id");

		return new PlayerMessage(message, playerId);
	}

	//

	public static PlayerData playerDataFrom(InputStream responseEntityContent) {
		String json = IOUtil.convertToString(responseEntityContent);
		try {
			JSONObject jsonObject = new JSONObject(json).getJSONObject("player");
			PlayerData playerData = new PlayerData(jsonObject);
			return playerData;
		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static synchronized ArrayList<PlayerLocation> locationsFrom(InputStream responseEntityContent) {
		try {
			String json = IOUtil.convertToString(responseEntityContent);
			JSONArray locationsJson = new JSONArray(json);
			return locationsFrom(locationsJson);
		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private static ArrayList<PlayerLocation> locationsFrom(JSONArray locationsJson) throws JSONException {
		ArrayList<PlayerLocation> locations = new ArrayList<PlayerLocation>();

		for(int i = 0; i < locationsJson.length(); i ++) {
			JSONObject locationJson = locationsJson.getJSONObject(i).getJSONObject("location");
			PlayerLocation location = locationFrom(locationJson);
			locations.add(location);
		}

		return locations;
	}

	private static PlayerLocation locationFrom(JSONObject locationJson) throws JSONException {
		Location location = new Location("gps");
		location.setLatitude(locationJson.getDouble("latitude"));
		location.setLongitude(locationJson.getDouble("longitude"));
		location.setAltitude(locationJson.getDouble("altitude"));
		int playerId = locationJson.getInt("player_id");
		return new PlayerLocation(location, playerId);
	}
}
