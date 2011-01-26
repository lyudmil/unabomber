package ui.dialogs;

import engine.PlayerMessage;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationManager;
import android.widget.EditText;
import ui.BombsOverlay;
import ui.OtherPlayersOverlay;
import ui.UnabomberMap;

public class SendMessageDialogBuilder implements DialogBuilder {
	
	private UnabomberMap activity;

	public SendMessageDialogBuilder(UnabomberMap activity) {
		this.activity = activity;
	}
	
	public Dialog build() {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		final EditText input = new EditText(activity);
		builder.setView(input);
		
		builder.setMessage("Send message" )
		       .setCancelable(false)
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	  
		        	   String message_string = input.getText().toString().trim();
		        	   OtherPlayersOverlay others = activity.getOtherPlayersOverlay();
		        	//   PlayerMessage message = new PlayerMessage(message_string, others.getTargetPlayerId());
		        	   
		        	   activity.getEngine().sendMessageTo(others.getTargetPlayerId(), message_string);
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
