package ui.dialogs;

import ui.UnabomberMap;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;

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
				activity.placeBomb();
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
