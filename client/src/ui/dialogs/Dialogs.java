package ui.dialogs;

import java.util.HashMap;

import ui.UnabomberMap;
import android.app.Dialog;

public class Dialogs {

	public static UnabomberMap activity;
	private static HashMap<Integer, PlaceBombDialogBuilder> builders;
	
	//added builders2, correct?
	private static HashMap<Integer, SendMessageDialogBuilder> builders2;
	
	public static final int PLACE_BOMB = 1;
	
	//
	public static final int SEND_MESSAGE = 2;
	
	public static Dialog get(int dialogId) {
		Dialog dialog = builders.get(dialogId).build();
		dialog.setOwnerActivity(activity);
		return dialog;
	}
	
	@SuppressWarnings("serial")
	public static void setActivity(final UnabomberMap newActivity) {
		activity = newActivity;
		builders = new HashMap<Integer, PlaceBombDialogBuilder>() {
			{
				put(PLACE_BOMB, new PlaceBombDialogBuilder(newActivity));
				
			}
		};
		
		//builders2
		builders2 = new HashMap<Integer, SendMessageDialogBuilder>(){
			{
				put(SEND_MESSAGE, new SendMessageDialogBuilder(newActivity));
			}
		};
		
	}
}
