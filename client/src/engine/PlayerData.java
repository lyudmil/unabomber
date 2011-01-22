package engine;

import org.json.JSONException;
import org.json.JSONObject;

import engine.GameEngine.GameStatus;

public class PlayerData {
	private JSONObject data;
	
	public PlayerData(JSONObject jsonObject) {
		data = jsonObject;
	}

	public String getDeviceId() {
		try {
			return data.getString("device_id");
		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public int getPlayerId() {
		try {
			return data.getInt("id");
		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public String getRole() {
		try {
			return data.getString("role");
		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	


}
