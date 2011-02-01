package ui.dialogs;

import ui.UnabomberMap;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.widget.EditText;

public class SendMessageDialogBuilder implements DialogBuilder {
	
	private UnabomberMap activity;
	private EditText input;

	public SendMessageDialogBuilder(UnabomberMap activity) {
		this.activity = activity;
		input = new EditText(activity);
	}
	
	public Dialog build() {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setView(input);
		
		builder.setMessage("Send message" )
		       .setCancelable(false)
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   String content = input.getText().toString().trim();
		        	   activity.sendMessage(content);
		        	   
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
