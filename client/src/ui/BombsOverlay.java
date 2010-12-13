package ui;

import com.google.android.maps.MapView;

import android.graphics.drawable.Drawable;
import android.location.Location;

public class BombsOverlay extends UnabomberItemsOverlay {
	private boolean shown;

	public BombsOverlay(Drawable marker) {
		super(marker);
		shown = false;
	}
	
	public void addBombAt(Location location) {
		addItemAt(location);
		populate();
	}
	
	public boolean isShown() {
		return shown;
	}
	
	public void showOn(MapView mapView) {
		mapView.getOverlays().add(this);
		shown = false;
	}
}
