package ui;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.location.Location;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public abstract class UnabomberItemsOverlay extends	ItemizedOverlay<OverlayItem> {

	protected ArrayList<OverlayItem> locations = new ArrayList<OverlayItem>();

	public UnabomberItemsOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
		readyToPopulate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return itemAt(i);
	}

	private synchronized OverlayItem itemAt(int i) {
		return locations.get(i);
	}

	@Override
	public int size() {
		return numberOfItems();
	}

	private synchronized int numberOfItems() {
		return locations.size();
	}

	public synchronized void readyToPopulate() {
		setLastFocusedIndex(-1);
		populate();
	}

	public synchronized void clear() {
		locations.clear();
		readyToPopulate();
	}

	protected synchronized void addItemAt(Location location) {
		Double latitude = location.getLatitude() * 1E6;
		Double longitude = location.getLongitude() * 1E6;
		GeoPoint point = new GeoPoint(latitude.intValue(), longitude.intValue());
		locations.add(new OverlayItem(point, "", ""));
		readyToPopulate();
	}
	
	public synchronized void showOn(MapView mapView) {
		List<Overlay> overlays = mapView.getOverlays();
		if(overlays.contains(this)) return;
		
		overlays.add(this);
	}

	protected void removeItemAt(final int bombIndex) {
		locations.remove(bombIndex);
		readyToPopulate();
	}

}