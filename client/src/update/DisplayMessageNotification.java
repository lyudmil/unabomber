package update;

import ui.UnabomberMap;
import android.widget.Toast;

public class DisplayMessageNotification implements Runnable {
	private UnabomberMap map;

	public DisplayMessageNotification(UnabomberMap unabomberMap) {
		this.map = unabomberMap;
	}

	public void run() {
		CharSequence text = "You have new messages!";
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(map, text, duration);
		toast.show();	
	}
}