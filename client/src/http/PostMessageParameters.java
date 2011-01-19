package http;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class PostMessageParameters extends ParameterList {
	
	private String message;
	
	public PostMessageParameters(String message){
		this.message=message;
	}

	@Override
	public ArrayList<NameValuePair> getParameters() {
		
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("message", message));

		
		return null;
	}

}
