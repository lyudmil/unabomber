package http;

import java.util.Arrays;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import junit.framework.TestCase;

public class AuthenticateUserParametersTest extends TestCase {
	
	private AuthenticateUserParameters authenticationParameters;
	
	@Override
	protected void setUp() {
		authenticationParameters = new AuthenticateUserParameters("device-id");
	}

	public void testParameterList() {	
		List<BasicNameValuePair> expectedParameters = Arrays.asList(new BasicNameValuePair("device_id", "device-id"));
		
		assertEquals(expectedParameters, authenticationParameters.getParameters());
	}
}
