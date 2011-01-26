package ui.dialogs;

import ui.OtherPlayersOverlay;
import ui.UnabomberMap;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.widget.EditText;
import engine.PlayerMessage;

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
		        	   PlayerMessage message = new PlayerMessage(message_string, activity.getOtherPlayersOverlay().getMyId());
		        	   
		        	   
		        	   activity.getEngine().sendMessageTo(message);
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
