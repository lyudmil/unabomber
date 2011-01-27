package ui.dialogs;

import java.util.HashMap;

import ui.UnabomberMap;
import android.app.Dialog;

public class Dialogs {

	public static UnabomberMap activity;
	private static HashMap<Integer, DialogBuilder> builders;
	
	public static final int PLACE_BOMB = 1;
	public static final int SEND_MESSAGE = 2;
	public static final int WON_GAME = 3;
	public static final int GOT_ARRESTED = 4;
	public static final int GOT_KILLED = 5;
	public static final int LOST_GAME = 6;
	
	public static Dialog get(int dialogId) {
		Dialog dialog = builders.get(dialogId).build();
		dialog.setOwnerActivity(activity);
		return dialog;
	}
	
	@SuppressWarnings("serial")
	public static void setActivity(final UnabomberMap newActivity) {
		activity = newActivity;
		builders = new HashMap<Integer, DialogBuilder>() {
			{
				put(PLACE_BOMB, new PlaceBombDialogBuilder(activity));
				put(SEND_MESSAGE, new SendMessageDialogBuilder(activity));
				put(GOT_ARRESTED, new ArrestedDialogBuilder());
				put(GOT_KILLED, new KilledDialogBuilder());
				put(WON_GAME, new WinDialogBuilder());
				put(LOST_GAME, new LoseDialogBuilder());
			}
		};
		
	}
}
