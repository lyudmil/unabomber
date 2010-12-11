package json;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import junit.framework.TestCase;
import android.location.Location;

public class JSONUtilTest extends TestCase {
	
	public void testConvertsServerLocationsResponseToGeoPoints() throws Exception {
		String json = "[{\"location\":{\"altitude\":\"30.0\",\"created_at\":\"2010-12-11T19:06:57Z\",\"id\":22,\"latitude\":\"49.1\",\"longitude\":\"9.1\",\"player_id\":11,\"updated_at\":\"2010-12-11T19:06:57Z\"}}]";
		InputStream responseEntityContent = new ByteArrayInputStream(json.getBytes("UTF-8"));
		
		ArrayList<Location> locations = JSONUtil.locationsFrom(responseEntityContent);
		
		assertEquals(1, locations.size());
		assertEquals(49.1, locations.get(0).getLatitude());
		assertEquals(9.1, locations.get(0).getLongitude());
		assertEquals(30.0, locations.get(0).getAltitude());
	}
}
