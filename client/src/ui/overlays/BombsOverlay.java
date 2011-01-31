package ui.overlays;

import ui.UnabomberMap;
import unabomber.client.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Location;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

import engine.GameEngine;

public class BombsOverlay extends UnabomberItemsOverlay {

	private UnabomberMap map;
	private GameEngine engine;

	public BombsOverlay(UnabomberMap context) {
		super(context.getResources().getDrawable(R.drawable.bomb));
		this.map = context;
		this.engine = map.getEngine();
	}

	public synchronized void addBombAt(Location location, int bombIndex) {
		Double latitude = location.getLatitude() * 1E6;
		Double longitude = location.getLongitude() * 1E6;
		GeoPoint point = new GeoPoint(latitude.intValue(), longitude.intValue());
		locations.add(new OverlayItem(point, "Bomb", String.valueOf(bombIndex)));
		readyToPopulate();
	}

	@Override
	protected boolean onTap(final int index) {
		final CharSequence[] items = { "Detonate" };

		AlertDialog.Builder builder = new AlertDialog.Builder(map);
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

	protected synchronized void handleMenuOption(final int optionIndex, final int bombIndex) {
		OverlayItem bombOverlay = locations.get(bombIndex);
		int targetBombID = Integer.parseInt(bombOverlay.getSnippet());

		switch (optionIndex) {
		case 0:
			removeItemAt(bombIndex);
			engine.detonateBomb(targetBombID);
			map.getMapView().invalidate();
			Toast.makeText(map, R.string.bomb_detonated, Toast.LENGTH_SHORT).show();
			break;

		default:
			Toast.makeText(map, R.string.unknown_action, Toast.LENGTH_SHORT).show();
		}

	}
}
