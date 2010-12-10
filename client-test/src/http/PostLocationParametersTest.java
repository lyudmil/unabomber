package http;

import java.util.Arrays;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.location.Location;
import junit.framework.TestCase;

public class PostLocationParametersTest extends TestCase {
	
	private Location location;
	private PostLocationParameters postLocationParameters;

	@Override
	protected void setUp() {
		location = new Location("gps");
		location.setLatitude(9);
		location.setLongitude(49);
		location.setAltitude(440);
		postLocationParameters = new PostLocationParameters(location);
	}
	
	public void testParameterList() {
		List<BasicNameValuePair> expectedParameters = Arrays.asList(
				new BasicNameValuePair("longitude", "49.0"),
				new BasicNameValuePair("latitude", "9.0"),  
				new BasicNameValuePair("altitude", "440.0"));
		
		assertEquals(expectedParameters, postLocationParameters.getParameters());		
	}
}
