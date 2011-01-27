package ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;

public abstract class GameEndedMessageDialogBuilder implements DialogBuilder {

	public Dialog build() {
		AlertDialog.Builder builder = new AlertDialog.Builder(Dialogs.activity);
		builder.setMessage(getMessage())
		.setCancelable(false);
		AlertDialog alert = builder.create();
		return alert;
	}
	
	protected abstract String getMessage();

}