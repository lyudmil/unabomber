package engine;

import android.location.Location;

public class PlayerLocation {
	
	private Location location;
	private int playerId;

	public PlayerLocation(Location location, int playerId) {
		this.location = location;
		this.playerId = playerId;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public int getPlayerId() {
		return playerId;
	}

}
