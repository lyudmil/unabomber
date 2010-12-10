package http;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class AuthenticateUserParameters extends ParameterList {

	private String deviceId;

	public AuthenticateUserParameters(String deviceId) {
		this.deviceId = deviceId;
	}

	@Override
	public ArrayList<NameValuePair> getParameters() {
		ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("device_id", deviceId));
		return parameters;
	}

}
