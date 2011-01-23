package http;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.location.Location;

public class PostLocationParameters extends ParameterList {
	private Location location;

	public PostLocationParameters(Location location) {
		this.location = location;
	}

	@Override
	public ArrayList<NameValuePair> getParameters() {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("longitude", String.valueOf(location.getLongitude())));
		params.add(new BasicNameValuePair("latitude", String.valueOf(location.getLatitude())));
		params.add(new BasicNameValuePair("altitude", String.valueOf(location.getAltitude())));

		return params;
	}
}
