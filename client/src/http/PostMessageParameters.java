package http;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class PostMessageParameters extends ParameterList {
	
	private String content;
	private int recepientId;
	
	public PostMessageParameters(int recepient, String content){
		this.recepientId = recepient;
		this.content = content;
	}

	@Override
	public ArrayList<NameValuePair> getParameters() {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("recepient_id", String.valueOf(recepientId)));
		params.add(new BasicNameValuePair("content", content));

		return params;
	}
}
