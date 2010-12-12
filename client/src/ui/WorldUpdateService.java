package ui;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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
				ArrayList<Location> locations = activity.getEngine().getLocations();
				activity.updatePlayerLocations(locations);
			}
		}, 0, 2000);
	}
	
	@Override
	public void onDestroy() {
		timer.cancel();
	}

	public static void setActivity(UnabomberMap map) {
		activity = map;
	}
}
