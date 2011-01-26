package http;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import engine.PlayerMessage;

public class PostMessageParameters extends ParameterList {
	
	private String message;
	private int senderId;
	
	public PostMessageParameters(PlayerMessage message){
		this.message=message.getMessage();
		this.senderId=message.getPlayer_id();
	}

	@Override
	public ArrayList<NameValuePair> getParameters() {
		
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("content", message));
		params.add(new BasicNameValuePair("recepient_id", String.valueOf(senderId)));

		
		return params;
	}

}
