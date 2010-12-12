package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.google.android.maps.Overlay;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;

public class WorldUpdateService extends Service {
	private Timer timer = new Timer();
	private static UnabomberMap activity;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		timer.scheduleAtFixedRate(new TimerTask() {		
			@Override
			public void run() {
				final ArrayList<Location> locations = activity.getEngine().getLocations();
				activity.runOnUiThread(new UpdateMap(locations));
			}
		}, 0, 4000);
	}
	
	@Override
	public void onDestroy() {
		timer.cancel();
	}

	public static void setActivity(UnabomberMap map) {
		activity = map;
	}
	
	private final class UpdateMap implements Runnable {
		private final ArrayList<Location> locations;

		private UpdateMap(ArrayList<Location> locations) {
			this.locations = locations;
		}

		public void run() {
			OtherPlayersOverlay otherPlayersOverlay = refreshLocationsUsing(locations);
			display(otherPlayersOverlay);
		}

		private void display(OtherPlayersOverlay otherPlayersOverlay) {
			List<Overlay> overlays = activity.getMapView().getOverlays();
			if(overlays.contains(otherPlayersOverlay)) return;
			overlays.add(otherPlayersOverlay);
		}

		private OtherPlayersOverlay refreshLocationsUsing(
				final ArrayList<Location> locations) {
			OtherPlayersOverlay otherPlayersOverlay = activity.getOtherPlayersOverlay();
			otherPlayersOverlay.clear();
			
			for(Location location : locations) {
				otherPlayersOverlay.addOverlayFor(location);
			}
			otherPlayersOverlay.readyToPopulate();
			return otherPlayersOverlay;
		}
	}
}
