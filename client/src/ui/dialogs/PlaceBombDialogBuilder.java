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

	/* (non-Javadoc)
	 * @see ui.dialogs.DialogBuilder#build()
	 */
	public Dialog build() {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage("Place a bomb here?")
		.setCancelable(false)
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {

			
		        	   LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
		        	   Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		        	   int bombId = activity.getEngine().placeBombAt(lastKnownLocation);

		        	   BombsOverlay bombs = activity.getBombsOverlay();
		        	   bombs.addBombAt(lastKnownLocation, bombId);
		        	   bombs.showOn(activity.getMapView());
				 

				/* FAKE LOCATION used to take screenshots: the app doesn't crash using it
				Location fake = activity.getEngine().getLocations().get(0).getLocation();

				int bombId = activity.getEngine().placeBombAt(fake);

				BombsOverlay bombs = activity.getBombsOverlay();
				bombs.addBombAt(fake, bombId);
				bombs.showOn(activity.getMapView());
				*/


				dialog.dismiss();

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
