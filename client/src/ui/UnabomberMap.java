package ui;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import ui.dialogs.Dialogs;
import unabomber.client.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

import connectivity.GCSTester;

import engine.GameEngine;
import engine.PlayerData;

public class UnabomberMap extends MapActivity {
	private MapView mapView;
	private PlayerLocationOverlay playerLocationOverlay;
	private GameEngine gameEngine;
	private Intent worldUpdateIntent;
	private OtherPlayersOverlay otherPlayersOverlay;
	private PlayerData playerData;
	private BombsOverlay bombsOverlay;

	//gps&internet check variables
	private LocationManager loc_man;
	private NetworkInfo net_info;

	//server check var
	private Boolean reachable=false;
	private GCSTester tester;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Dialogs.setActivity(this);

		tester=new GCSTester(this);

		reachable=tester.testGCS();
		
		
		if(reachable){
			
			setUpMap();
			showPlayerLocation();
			authenticatePlayer();
			followPlayers();
			bombsOverlay = new BombsOverlay(getResources().getDrawable(R.drawable.bomb));

		}
		



	}


	//menu
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater=getMenuInflater();
		inflater.inflate(R.menu.game_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// handle item selection
		switch (item.getItemId()) {
		case R.id.show:
			//codice
			return true;
		case R.id.options:
			//codice
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}




	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = Dialogs.get(id);
		return dialog;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(reachable!=false)
			stopFollowingPlayers();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if(reachable!=false)
			stopFollowingPlayers();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(reachable!=false)
			followPlayers();
	}

	private void stopFollowingPlayers() {
		stopService(worldUpdateIntent);
	}

	private void followPlayers() {
		Drawable defaultMarker = getResources().getDrawable(R.drawable.androidmarker);
		int playerId = playerData.getPlayerId();
		otherPlayersOverlay = new OtherPlayersOverlay(defaultMarker, playerId, this.gameEngine, this);

		WorldUpdateService.setActivity(this);
		worldUpdateIntent = new Intent(this, WorldUpdateService.class);
		startService(worldUpdateIntent);
	}

	private void authenticatePlayer() {
		playerData = gameEngine.authenticate();
	}

	private void showPlayerLocation() {
		playerLocationOverlay = new PlayerLocationOverlay(this);
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

	public BombsOverlay getBombsOverlay() {
		return bombsOverlay;
	}

	public PlayerData getPlayerData() {
		return playerData;
	}
}