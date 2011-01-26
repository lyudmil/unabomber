package ui.dialogs;

import ui.BombsOverlay;
import ui.UnabomberMap;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationManager;

public class PlaceBombDialogBuilder implements DialogBuilder {

	private UnabomberMap activity;

	public PlaceBombDialogBuilder(UnabomberMap activity) {
		this.activity = activity;
	}

	public Dialog build() {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage("Place a bomb here?")
		.setCancelable(false)
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Location lastKnownLocation = determineLocation();
				if (lastKnownLocation == null) return;

				int bombId = activity.getEngine().placeBombAt(lastKnownLocation);

				BombsOverlay bombs = activity.getBombsOverlay();
				bombs.addBombAt(lastKnownLocation, bombId);
				bombs.showOn(activity.getMapView());
			}

			private Location determineLocation() {
				LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
				Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				if (location == null) location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				return location;
			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();
		return alert;
	}

}
