package http;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class ArrestedPlayerParameters extends ParameterList {
	
	private String deviceId;

	public ArrestedPlayerParameters(String deviceId) {
		this.deviceId = deviceId;
	}

	@Override
	public ArrayList<NameValuePair> getParameters() {
		ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("id", deviceId));
		return parameters;
	}

}
