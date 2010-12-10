package http;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;

public abstract class ParameterList {

	public ParameterList() {
		super();
	}

	public abstract ArrayList<NameValuePair> getParameters();

	public UrlEncodedFormEntity encode() {
		return ParamUtils.encoded(getParameters());
	}

}