package ui;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
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
				updateWorld();
				
//				final ArrayList<PlayerMessage> messages = activity.getEngine().getMessages();
//				activity.setMessages(messages);
				
				
//				final GameStatus gameStatus = activity.getEngine().updateGameStatus(activity.getPlayerData().getDeviceId());
//				
//				
//				if (gameStatus != GameStatus.STARTED) {
//					
//					//set the status of the match
//					MatchResult.gameStatus = gameStatus;
//					
//					//initiate the new intent
//					Intent myIntent = new Intent(activity, MatchResult.class);
//					
//					activity.startActivity(myIntent);
//					
//					this.cancel();
//					
//					activity.finish();
//
//				}
				
				
			}

			private synchronized void updateWorld() {
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
			activity.getMapView().invalidate();
			
			for(PlayerLocation location : locations) {
				otherPlayersOverlay.addOverlayFor(location);
			}
			otherPlayersOverlay.readyToPopulate();
			return otherPlayersOverlay;
		}
	}
}
