package http;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;

public class ParamUtils {

	public static UrlEncodedFormEntity encoded(ArrayList<NameValuePair> params) {
		try {
			return new UrlEncodedFormEntity(params);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
