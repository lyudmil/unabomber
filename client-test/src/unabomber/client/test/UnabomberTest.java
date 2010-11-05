package unabomber.client.test;

import unabomber.client.Unabomber;
import android.test.ActivityInstrumentationTestCase2;

public class UnabomberTest extends ActivityInstrumentationTestCase2<Unabomber> {

	public UnabomberTest() {
		super("unabomber.client", Unabomber.class);
	}
	
	public void testTautology() {
		assertTrue(true);
	}

}
