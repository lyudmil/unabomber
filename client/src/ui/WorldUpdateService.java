package ui;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;
import engine.PlayerLocation;
import engine.PlayerMessage;

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
				handleMessages();
			}

			private void handleMessages() {
				final ArrayList<PlayerMessage> messages = activity.getEngine().getMessages();
				if(activity.getMessages() != null){
					if(activity.getMessages().size() < messages.size()){
						activity.runOnUiThread(new DisplayMessageNotification(activity));
					}
				}
				activity.setMessages(messages);
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

		public synchronized void run() {
			OtherPlayersOverlay otherPlayersOverlay = refreshLocationsUsing(locations);
			otherPlayersOverlay.showOn(activity.getMapView());
		}

		private OtherPlayersOverlay refreshLocationsUsing(
				final ArrayList<PlayerLocation> locations) {
			OtherPlayersOverlay otherPlayersOverlay = activity.getOtherPlayersOverlay();
			otherPlayersOverlay.clear();

			for (PlayerLocation location : locations) {
				otherPlayersOverlay.addOverlayFor(location);
			}
			otherPlayersOverlay.readyToPopulate();
			return otherPlayersOverlay;
		}
	}
	
	private final class DisplayMessageNotification implements Runnable {
		private UnabomberMap map;

		public DisplayMessageNotification(UnabomberMap unabomberMap) {
			this.map = unabomberMap;
		}

		public void run() {
			CharSequence text = "You have new messages!";
			int duration = Toast.LENGTH_LONG;

			Toast toast = Toast.makeText(map, text, duration);
			toast.show();	
		}
	}

}
