package http;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class DetonatedBombParameters extends ParameterList {
	
	private String bombId;
	
	public DetonatedBombParameters(String bombId) {
		this.bombId = bombId;
	}


	@Override
	public ArrayList<NameValuePair> getParameters() {
		ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("id", bombId));
		return parameters;
	}

}
