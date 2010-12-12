package ui;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

import engine.PlayerLocation;

public class OtherPlayersOverlay extends ItemizedOverlay<OverlayItem> {
	ArrayList<OverlayItem> locations = new ArrayList<OverlayItem>();
	private int myPlayerId;

	public OtherPlayersOverlay(Drawable defaultMarker, int myPlayerId) {
		super(boundCenterBottom(defaultMarker));
		this.myPlayerId = myPlayerId;
	}

	@Override
	protected OverlayItem createItem(int i) {
		return locations.get(i);
	}

	@Override
	public int size() {
		return locations.size();
	}
	
	public void addOverlayFor(PlayerLocation location) {
		if(myPlayerId == location.getPlayerId()) return;
		
		Double latitude = location.getLocation().getLatitude() * 1E6;
		Double longitude = location.getLocation().getLongitude() * 1E6;
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
