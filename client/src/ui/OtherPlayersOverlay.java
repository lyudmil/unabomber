package ui;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

import ui.dialogs.Dialogs;
import unabomber.client.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.widget.Toast;
import engine.GameEngine;
import engine.PlayerLocation;

public class OtherPlayersOverlay extends UnabomberItemsOverlay {
	private int myPlayerId;
	private Context mContext;
	private GameEngine mEngine;
	private UnabomberMap mApp;
	
	//
	private int targetPlayerID;
	
	
	public OtherPlayersOverlay(Drawable defaultMarker, int myPlayerId) {
		super(defaultMarker);
		this.myPlayerId = myPlayerId;
	}

	//changed
	public OtherPlayersOverlay(Drawable defaultMarker, int myPlayerId, GameEngine engine,
			Context context, UnabomberMap app) {
		super(defaultMarker);
		this.myPlayerId = myPlayerId;
		this.mContext = context;
		this.mEngine = engine;
		this.mApp=app;
	}

	// Handle a tap on other player icons, this is a "system" callback
	@Override
	protected boolean onTap(final int index) {
		
		// option to use as action
		final CharSequence[] items = { "Send to jail", "Send message" };
		
		
		// if you have added something, change handleMenuOption
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle(R.string.other_player_menu_title);
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				handleMenuOption(item, index);
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
		return true;
	}

	// handle the single option that a player has done
	protected void handleMenuOption(final int optionIndex, final int playerIndex) {
		
		
		//get target player ID
		targetPlayerID = Integer.parseInt(locations.get(playerIndex).getSnippet());
		

		// obtain the user decision
		switch (optionIndex) {
		case 0: // send another player to jail

			mEngine.sendPlayerToJail(myPlayerId, targetPlayerID);

			//feedback the results
			Toast.makeText(mContext, R.string.sent_to_jail, Toast.LENGTH_SHORT)
					.show();
			break;

		case 1: // send a message to player
		//	mEngine.sendMessageTo(myPlayerId, targetPlayerID, );
			mApp.showDialog(Dialogs.SEND_MESSAGE);
			
		default: // defensive programming
			Toast.makeText(mContext, R.string.unknown_action,
					Toast.LENGTH_SHORT).show();
		}

	}

	//add a player on the list
	public void addOverlayFor(PlayerLocation location) {
		
		//if i am the player then return
		if (displayingMyself(location))
			return;

		
		//else create the overlay item
		Double latitude = location.getLocation().getLatitude() * 1E6;
		Double longitude = location.getLocation().getLongitude() * 1E6;
		GeoPoint point = new GeoPoint(latitude.intValue(), longitude.intValue());
		
		//add the playerID as message of the overlay item
		locations.add(new OverlayItem(point, "Player", String.valueOf(location.getPlayerId())));
	}

	private boolean displayingMyself(PlayerLocation location) {
		return myPlayerId == location.getPlayerId();
	}

	//
	public int getMyId(){
		return myPlayerId;
	}
	
	public int getTargetPlayerId(){
		return targetPlayerID;
	}
}
