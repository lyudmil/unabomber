package ui;


import android.graphics.drawable.Drawable;
import engine.PlayerLocation;

public class OtherPlayersOverlay extends UnabomberItemsOverlay {
	private int myPlayerId;

	public OtherPlayersOverlay(Drawable defaultMarker, int myPlayerId) {
		super(defaultMarker);
		this.myPlayerId = myPlayerId;
	}

	public void addOverlayFor(PlayerLocation location) {
		if(displayingMyself(location)) return;
		
		addItemAt(location.getLocation());
	}

	private boolean displayingMyself(PlayerLocation location) {
		return myPlayerId == location.getPlayerId();
	}

}
