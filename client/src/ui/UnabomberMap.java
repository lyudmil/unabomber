package ui;

import unabomber.client.R;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

import engine.GameEngine;
import engine.PlayerData;

public class UnabomberMap extends MapActivity {
    private MapView mapView;
	private PlayerLocationOverlay playerLocationOverlay;
	private GameEngine gameEngine;
	private Intent worldUpdateIntent;
	private OtherPlayersOverlay otherPlayersOverlay;
	private PlayerData playerData;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        setUpMap();
        showPlayerLocation();
        authenticatePlayer();
        followPlayers();
    }

	@Override
	protected void onDestroy() {
		super.onDestroy();
		stopFollowingPlayers();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		stopFollowingPlayers();
	}

	@Override
	protected void onResume() {
		super.onResume();
		followPlayers();
	}


	private void stopFollowingPlayers() {
		stopService(worldUpdateIntent);
	}

	private void followPlayers() {
		Drawable defaultMarker = getResources().getDrawable(R.drawable.androidmarker);
		int playerId = playerData.getPlayerId();
		otherPlayersOverlay = new OtherPlayersOverlay(defaultMarker, playerId);
		
		WorldUpdateService.setActivity(this);
        worldUpdateIntent = new Intent(this, WorldUpdateService.class);
        startService(worldUpdateIntent);
	}

	private void authenticatePlayer() {
		playerData = gameEngine.authenticate();
	}

	private void showPlayerLocation() {
		playerLocationOverlay = new PlayerLocationOverlay(this, mapView);
        playerLocationOverlay.enableMyLocation();
        mapView.getOverlays().add(playerLocationOverlay);
	}

	private void setUpMap() {
		mapView = (MapView)findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        gameEngine = new GameEngine(deviceId());
	}
	
	private String deviceId() {
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String deviceId = telephonyManager.getDeviceId() + Math.round(Math.random() * 1000);
		return deviceId;
	}

	public GameEngine getEngine() {
		return gameEngine;
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	public MapView getMapView() {
		return mapView;
	}
	
	public OtherPlayersOverlay getOtherPlayersOverlay() {
		return otherPlayersOverlay;
	}
	
	public PlayerData getPlayerData() {
		return playerData;
	}
}