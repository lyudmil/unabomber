package unabomber.client;

import android.content.Context;
import android.location.Location;

import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class PlayerLocationOverlay extends MyLocationOverlay {
	public PlayerLocationOverlay(Context context, MapView mapView) {
		super(context, mapView);
	}

	@Override
	public synchronized void onLocationChanged(Location location) {
		super.onLocationChanged(location);
		GameEngine engine = new GameEngine("DEVICE--ID");
		engine.sendLocation(location);
	}
}