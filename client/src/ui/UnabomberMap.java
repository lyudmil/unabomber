package ui;

import java.util.ArrayList;

import ui.dialogs.Dialogs;
import ui.overlays.BombsOverlay;
import ui.overlays.OtherPlayersOverlay;
import ui.overlays.PlayerLocationOverlay;
import ui.views.InfoView;
import ui.views.MessagesView;
import unabomber.client.R;
import update.WorldUpdateService;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

import engine.GameEngine;
import engine.PlayerData;
import engine.PlayerMessage;

public class UnabomberMap extends MapActivity {
	private MapView mapView;
	private PlayerLocationOverlay playerLocationOverlay;
	private GameEngine gameEngine;
	private Intent worldUpdateIntent;
	private OtherPlayersOverlay otherPlayersOverlay;
	private PlayerData playerData;
	private BombsOverlay bombsOverlay;
	private ArrayList<PlayerMessage> messages = new ArrayList<PlayerMessage>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Dialogs.setActivity(this);

		setUpMap();
		showPlayerLocation();
		authenticatePlayer();
		followPlayers();
		bombsOverlay = new BombsOverlay(this);
	}

	public void startMixare(Context c) {
		Intent i = new Intent();
		i.setAction(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("http://unabomber.heroku.com/bombs"), "application/mixare-json");
		startActivity(i);    
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.game_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public synchronized boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.show:
			Intent mixare = new Intent();
			mixare.setAction(Intent.ACTION_VIEW);			
			mixare.setDataAndType(Uri.parse("http://unabomber.heroku.com/bombs"), "application/mixare-json");
			startActivity(mixare);
			return true;
		case R.id.messages:
			Intent messages = new Intent(this, MessagesView.class);
			messages.putExtra("messages", getMessages());
			startActivity(messages);
			return true;
		case R.id.info:
			Intent info = new Intent(this, InfoView.class);
			UnabomberMap.this.startActivity(info);
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
		otherPlayersOverlay = new OtherPlayersOverlay(this);

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

	public String deviceId() {
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

	public synchronized BombsOverlay getBombsOverlay() {
		return bombsOverlay;
	}

	public PlayerData getPlayerData() {
		return playerData;
	}

	public synchronized void setMessages(ArrayList<PlayerMessage> messages) {
		this.messages = messages;
	}

	public synchronized ArrayList<PlayerMessage> getMessages() {
		return this.messages;
	}

	public void placeBomb() {
		Location lastKnownLocation = determineLocation();
		if (lastKnownLocation == null) return;
	
		int bombId = getEngine().placeBombAt(lastKnownLocation);
	
		BombsOverlay bombs = getBombsOverlay();
		bombs.addBombAt(lastKnownLocation, bombId);
		bombs.showOn(getMapView());
	}

	private Location determineLocation() {
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (lastKnownLocation == null) lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		return lastKnownLocation;
	}

	public void sendMessage(String content) {
		OtherPlayersOverlay others = getOtherPlayersOverlay();
		getEngine().sendMessageTo(others.getTargetPlayerId(), content);
	}
}