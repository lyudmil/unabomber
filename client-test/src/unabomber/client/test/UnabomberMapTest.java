package unabomber.client.test;

import unabomber.client.UnabomberMap;
import android.test.ActivityInstrumentationTestCase2;

public class UnabomberMapTest extends ActivityInstrumentationTestCase2<UnabomberMap> {

	public UnabomberMapTest() {
		super("unabomber.client", UnabomberMap.class);
	}
	
	public void testTautology() {
		assertTrue(true);
	}
}
