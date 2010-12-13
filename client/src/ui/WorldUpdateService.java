package ui;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import engine.PlayerLocation;

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
				final ArrayList<PlayerLocation> locations = activity.getEngine().getLocations();
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
		private final ArrayList<PlayerLocation> locations;

		private UpdateMap(ArrayList<PlayerLocation> locations) {
			this.locations = locations;
		}

		public void run() {
			OtherPlayersOverlay otherPlayersOverlay = refreshLocationsUsing(locations);
			otherPlayersOverlay.showOn(activity.getMapView());
		}

		private OtherPlayersOverlay refreshLocationsUsing(final ArrayList<PlayerLocation> locations) {
			OtherPlayersOverlay otherPlayersOverlay = activity.getOtherPlayersOverlay();
			otherPlayersOverlay.clear();
			
			for(PlayerLocation location : locations) {
				otherPlayersOverlay.addOverlayFor(location);
			}
			otherPlayersOverlay.readyToPopulate();
			return otherPlayersOverlay;
		}
	}
}
