package json;

import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;

public class JSONUtil {

	public static ArrayList<Location> locationsFrom(InputStream responseEntityContent) {
		try {
			JSONArray locationsJson = new JSONArray(IOUtil.convertToString(responseEntityContent));
			return locationsFrom(locationsJson);
		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private static ArrayList<Location> locationsFrom(JSONArray locationsJson) throws JSONException {
		ArrayList<Location> points = new ArrayList<Location>();
		
		for(int i = 0; i < locationsJson.length(); i ++) {
			JSONObject locationJson = locationsJson.getJSONObject(i).getJSONObject("location");
			Location location = locationFrom(locationJson);
			points.add(location);
		}
		
		return points;
	}

	private static Location locationFrom(JSONObject locationJson) throws JSONException {
		Location location = new Location("gps");
		location.setLatitude(locationJson.getDouble("latitude"));
		location.setLongitude(locationJson.getDouble("longitude"));
		location.setAltitude(locationJson.getDouble("altitude"));
		return location;
	}

}
