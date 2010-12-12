package ui;

import ui.dialogs.Dialogs;
import android.location.Location;

import com.google.android.maps.MyLocationOverlay;

import engine.GameEngine;

public class PlayerLocationOverlay extends MyLocationOverlay {
	private GameEngine engine;
	private UnabomberMap context;

	public PlayerLocationOverlay(UnabomberMap context) {
		super(context, context.getMapView());
		this.context = context;
		engine = context.getEngine();
	}

	@Override
	public synchronized void onLocationChanged(Location location) {
		super.onLocationChanged(location);
		engine.sendLocation(location);
	}

	@Override
	protected boolean dispatchTap() {
		context.runOnUiThread(new Runnable() {
			public void run() {
				context.createDialog(Dialogs.PLACE_BOMB);
			}
		});
		return true;
	}
	
	
}