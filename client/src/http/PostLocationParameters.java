package http;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import android.location.Location;

public class PostLocationParameters {
	private Location location;

	public PostLocationParameters(Location location) {
		this.location = location;
	}
	
	public UrlEncodedFormEntity encode() {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>(1);
		params.add(new BasicNameValuePair("longitude", String.valueOf(location.getLongitude())));
		params.add(new BasicNameValuePair("latitude", String.valueOf(location.getLatitude())));
		params.add(new BasicNameValuePair("altitude", String.valueOf(location.getAltitude())));
		try {
			return new UrlEncodedFormEntity(params);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
