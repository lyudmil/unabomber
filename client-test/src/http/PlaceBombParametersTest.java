package http;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.apache.http.message.BasicNameValuePair;

import android.location.Location;

public class PlaceBombParametersTest extends TestCase {
	
	private Location location;
	private PlaceBombParameters parameters;

	public void setUp() {
		location = new Location("gps");
		location.setLatitude(44.0);
		location.setLongitude(9.0);
		location.setAltitude(50.0);
		
		parameters = new PlaceBombParameters(location);
	}
	
	public void testParameterList() {
		List<BasicNameValuePair> expectedParameters = Arrays.asList(
				new BasicNameValuePair("latitude", "44.0"),
				new BasicNameValuePair("longitude", "9.0"),
				new BasicNameValuePair("altitude", "50.0")
		);
		
		assertEquals(expectedParameters, parameters.getParameters());
	}
}
