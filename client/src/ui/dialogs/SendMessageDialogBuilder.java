package ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationManager;
import ui.BombsOverlay;
import ui.OtherPlayersOverlay;
import ui.UnabomberMap;

public class SendMessageDialogBuilder {
	
	private UnabomberMap activity;

	public SendMessageDialogBuilder(UnabomberMap activity) {
		this.activity = activity;
	}
	
	public Dialog build() {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage("Send message" )
		       .setCancelable(false)
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	  
		        	   //here goes the code to write a message
		        	   //a function like
		        	   //public String writeMessage(){
		        	   		//code
		        	   //}
		        	   //at the moment it uses the following String
		        	   String message = "this is a try";
		        	   OtherPlayersOverlay others = activity.getOtherPlayersOverlay();
		        	   activity.getEngine().sendMessageTo(others.getMyId(), others.getTargetPlayerId(), message);
		        	   
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
