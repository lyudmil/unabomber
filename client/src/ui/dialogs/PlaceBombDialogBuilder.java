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
				Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

				int bombId = activity.getEngine().placeBombAt(lastKnownLocation);

				BombsOverlay bombs = activity.getBombsOverlay();
				bombs.addBombAt(lastKnownLocation, bombId);
				bombs.showOn(activity.getMapView());


	/*
				Location bomb_location = null;
				ArrayList<PlayerLocation> p_loc = activity.getEngine().getLocations();
				for(int i=0; i<p_loc.size();i++){

					if(p_loc.get(i).getPlayerId()==activity.getOtherPlayersOverlay().getMyId()){
						bomb_location=p_loc.get(i).getLocation();
					}
				}
				int bombId = activity.getEngine().placeBombAt(bomb_location);

				BombsOverlay bombs = activity.getBombsOverlay();
				bombs.addBombAt(bomb_location, bombId);
				bombs.showOn(activity.getMapView());
				 
*/
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
