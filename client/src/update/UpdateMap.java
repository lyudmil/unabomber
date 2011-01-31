package update;

import java.util.ArrayList;

import ui.UnabomberMap;
import ui.overlays.OtherPlayersOverlay;
import engine.PlayerLocation;

public class UpdateMap implements Runnable {
	private final ArrayList<PlayerLocation> locations;
	private UnabomberMap activity;
	
	public UpdateMap(UnabomberMap activity, ArrayList<PlayerLocation> locations) {
		this.activity = activity;
		this.locations = locations;
	}

	public synchronized void run() {
		OtherPlayersOverlay otherPlayersOverlay = refreshLocationsUsing(locations);
		otherPlayersOverlay.showOn(activity.getMapView());
	}

	private OtherPlayersOverlay refreshLocationsUsing(ArrayList<PlayerLocation> locations) {
		OtherPlayersOverlay otherPlayersOverlay = activity.getOtherPlayersOverlay();
		otherPlayersOverlay.clear();

		for (PlayerLocation location : locations) {
			otherPlayersOverlay.addOverlayFor(location);
		}
		otherPlayersOverlay.readyToPopulate();
		return otherPlayersOverlay;
	}
}