package ui.overlays;

import ui.UnabomberMap;
import ui.dialogs.Dialogs;
import unabomber.client.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

import engine.GameEngine;
import engine.PlayerLocation;

public class OtherPlayersOverlay extends UnabomberItemsOverlay {
	private static final int SEND_MESSAGE = 1;
	private static final int ARREST = 0;
	private int myPlayerId;
	private GameEngine mEngine;
	private UnabomberMap app;
	private int targetPlayerId;
	
	public OtherPlayersOverlay(UnabomberMap app) {
		super(app.getResources().getDrawable(R.drawable.androidmarker));
		this.myPlayerId = app.getPlayerData().getPlayerId();
		this.mEngine = app.getEngine();
		this.app = app;
	}

	@Override
	protected boolean onTap(final int index) {
		final CharSequence[] items = { "Send to jail", "Send message" };
		
		AlertDialog.Builder builder = new AlertDialog.Builder(app);
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

	protected synchronized void handleMenuOption(final int optionIndex, final int playerIndex) {
		OverlayItem overlayItem = locations.get(playerIndex);
		if (overlayItem == null) return;
		
		targetPlayerId = Integer.parseInt(overlayItem.getSnippet());

		switch (optionIndex) {
		case ARREST:
			removeItemAt(playerIndex);
			app.getMapView().invalidate();			
			mEngine.sendPlayerToJail(myPlayerId, targetPlayerId);

			Toast.makeText(app, R.string.sent_to_jail, Toast.LENGTH_SHORT).show();
			break;

		case SEND_MESSAGE:
			app.showDialog(Dialogs.SEND_MESSAGE);
			break;
			
		default:
			Toast.makeText(app, R.string.unknown_action, Toast.LENGTH_SHORT).show();
		}

	}
	
	public synchronized void addOverlayFor(PlayerLocation location) {
		if (displayingMyself(location)) {
			return;
		}

		Double latitude = location.getLocation().getLatitude() * 1E6;
		Double longitude = location.getLocation().getLongitude() * 1E6;
		GeoPoint point = new GeoPoint(latitude.intValue(), longitude.intValue());
		
		locations.add(new OverlayItem(point, "Player", String.valueOf(location.getPlayerId())));
		readyToPopulate();
	}

	private boolean displayingMyself(PlayerLocation location) {
		return myPlayerId == location.getPlayerId();
	}
	
	public int getTargetPlayerId(){
		return targetPlayerId;
	}
}
