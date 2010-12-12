package json;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import junit.framework.TestCase;
import engine.PlayerLocation;

public class JSONUtilTest extends TestCase {
	
	public void testConvertsServerLocationsResponseToGeoPoints() throws Exception {
		String json = "[{\"location\":{\"altitude\":\"30.0\",\"created_at\":\"2010-12-11T19:06:57Z\",\"id\":22,\"latitude\":\"49.1\",\"longitude\":\"9.1\",\"player_id\":11,\"updated_at\":\"2010-12-11T19:06:57Z\"}}]";
		InputStream responseEntityContent = new ByteArrayInputStream(json.getBytes("UTF-8"));
		
		ArrayList<PlayerLocation> locations = JSONUtil.locationsFrom(responseEntityContent);
		
		assertEquals(1, locations.size());
		
		PlayerLocation playerLocation = locations.get(0);
		assertEquals(49.1, playerLocation.getLocation().getLatitude());
		assertEquals(9.1, playerLocation.getLocation().getLongitude());
		assertEquals(30.0, playerLocation.getLocation().getAltitude());
		assertEquals(11, playerLocation.getPlayerId());
	}
}
