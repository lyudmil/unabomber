package json;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import junit.framework.TestCase;
import engine.PlayerData;
import engine.PlayerLocation;

public class JSONUtilTest extends TestCase {

	public void testConvertsServerLocationsResponseToGeoPoints()
			throws Exception {
		String json = "[{\"location\":{\"altitude\":\"30.0\",\"created_at\":\"2010-12-11T19:06:57Z\",\"id\":22,\"latitude\":\"49.1\",\"longitude\":\"9.1\",\"player_id\":11,\"updated_at\":\"2010-12-11T19:06:57Z\"}}]";
		InputStream responseEntityContent = streamFor(json);

		ArrayList<PlayerLocation> locations = JSONUtil
				.locationsFrom(responseEntityContent);

		assertEquals(1, locations.size());

		PlayerLocation playerLocation = locations.get(0);
		assertEquals(49.1, playerLocation.getLocation().getLatitude());
		assertEquals(9.1, playerLocation.getLocation().getLongitude());
		assertEquals(30.0, playerLocation.getLocation().getAltitude());
		assertEquals(11, playerLocation.getPlayerId());
	}

	public void testConvertsPlayerResponseToData() throws Exception {
		String json = "{\"player\":{\"created_at\":\"2010-12-12T08:47:17Z\",\"device_id\":\"000000000000000451\",\"id\":66,\"updated_at\":\"2010-12-12T08:47:17Z\"}}";
		InputStream responseEntityContent = streamFor(json);

		PlayerData playerData = JSONUtil.playerDataFrom(responseEntityContent);
		assertEquals("000000000000000451", playerData.getDeviceId());
		assertEquals(66, playerData.getPlayerId());
	}

	private InputStream streamFor(String json) throws UnsupportedEncodingException {
		InputStream responseEntityContent = new ByteArrayInputStream(json.getBytes("UTF-8"));
		return responseEntityContent;
	}

}
