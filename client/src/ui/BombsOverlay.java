package ui;



import android.graphics.drawable.Drawable;
import android.location.Location;

public class BombsOverlay extends UnabomberItemsOverlay {

	public BombsOverlay(Drawable marker) {
		super(marker);
	}
	
	public void addBombAt(Location location) {
		addItemAt(location);
		populate();
	}
}
