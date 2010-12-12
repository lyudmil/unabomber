package ui;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.location.Location;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class OtherPlayersOverlay extends ItemizedOverlay<OverlayItem> {
	ArrayList<OverlayItem> locations = new ArrayList<OverlayItem>();

	public OtherPlayersOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
	}

	@Override
	protected OverlayItem createItem(int i) {
		return locations.get(i);
	}

	@Override
	public int size() {
		return locations.size();
	}
	
	public void addOverlayFor(Location location) {
		Double latitude = location.getLatitude() * 1E6;
		Double longitude = location.getLongitude() * 1E6;
		GeoPoint point = new GeoPoint(latitude.intValue(), longitude.intValue());
		locations.add(new OverlayItem(point, "", ""));
	}
	
	public void readyToPopulate() {
		populate();
	}
	
	public void clear() {
		locations.clear();
	}

}
