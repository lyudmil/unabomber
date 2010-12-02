package unabomber.client;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class Unabomber extends MapActivity {
    private MapView mapView;
	private MyLocationOverlay myLocationOverlay;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mapView = (MapView)findViewById(R.id.mapview);
        myLocationOverlay = new MyLocationOverlay(this, mapView);
        myLocationOverlay.enableMyLocation();
        mapView.setBuiltInZoomControls(true);
        mapView.getOverlays().add(myLocationOverlay);
    }

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}