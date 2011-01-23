package http;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class PostMessageParameters extends ParameterList {
	
	private String message;
	private int receiverId;
	
	public PostMessageParameters(String message, int receiver){
		this.message=message;
		this.receiverId=receiver;
	}

	@Override
	public ArrayList<NameValuePair> getParameters() {
		
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("content", message));
		params.add(new BasicNameValuePair("recepient_id", String.valueOf(receiverId)));

		
		return params;
	}

}
