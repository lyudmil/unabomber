package ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;
import engine.GameEngine.GameStatus;
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
				
				final GameStatus gameStatus = activity.getEngine().getGameStatus();
				
				
				if (gameStatus != GameStatus.STARTED) {
					
					//set the status of the match
					MatchResult.gameStatus = gameStatus;
					
					//initiate the new intent
					Intent myIntent = new Intent(activity, MatchResult.class);
					
					activity.startActivity(myIntent);
					
				}
				

				
				activity.runOnUiThread(new UpdateMap(locations));
				
				final ArrayList<PlayerMessage> messages = activity.getEngine().getMessages();
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
	
	//update messages
	private final class UpdateMessages{
		private final ArrayList<String> messages;
		
		private UpdateMessages(ArrayList<String> messages){
			//add some code to check for new messages

			if(this.messages!=messages){
	
				CharSequence text = "You have new message/s, check the menu to read it/them";
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(activity, text, duration);
				toast.show();
			}
			
			
			this.messages=messages;
			
			
			
		}
	}
}
