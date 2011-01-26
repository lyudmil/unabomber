package ui;

import unabomber.client.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

import engine.GameEngine;

public class BombsOverlay extends UnabomberItemsOverlay {

	private Context mContext;
	private GameEngine mEngine;

	public BombsOverlay(Drawable defaultMarker, int myPlayerId,
			GameEngine engine, Context context) {
		super(defaultMarker);
		this.mContext = context;
		this.mEngine = engine;
	}

	public void addBombAt(Location location, int bombIndex) {
		Double latitude = location.getLatitude() * 1E6;
		Double longitude = location.getLongitude() * 1E6;
		GeoPoint point = new GeoPoint(latitude.intValue(), longitude.intValue());
		locations.add(new OverlayItem(point, "Bomb", String.valueOf(bombIndex)));
		populate();
	}

	@Override
	protected boolean onTap(final int index) {
		// option to use as action
		final CharSequence[] items = { "Detonate" };

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

	protected void handleMenuOption(final int optionIndex, final int bombIndex) {
		OverlayItem bombOverlay = locations.get(bombIndex);
		int targetBombID = Integer.parseInt(bombOverlay.getSnippet());

		switch (optionIndex) {
		case 0:
			locations.remove(bombIndex);
			mEngine.detonateBomb(targetBombID);
			Toast.makeText(mContext, R.string.bomb_detonated, Toast.LENGTH_SHORT).show();
			break;

		default:
			Toast.makeText(mContext, R.string.unknown_action, Toast.LENGTH_SHORT).show();
		}

	}
}
