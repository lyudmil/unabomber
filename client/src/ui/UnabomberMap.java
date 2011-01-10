package ui;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import ui.dialogs.Dialogs;
import unabomber.client.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
	private BombsOverlay bombsOverlay;

	//gps&internet check variables
	private LocationManager loc_man;
	private NetworkInfo net_info;
	//
	private URL url;
	private HttpURLConnection url_con;
	//

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Dialogs.setActivity(this);


		if(testInternet()){

			if(testServer()){

				//gps test
				testGps();


				setUpMap();
				showPlayerLocation();
				authenticatePlayer();
				followPlayers();

				bombsOverlay = new BombsOverlay(getResources().getDrawable(R.drawable.bomb));

			}else{
				showServerAlert();
			}
		}
	}


	//testServer method
	private Boolean testServer(){

		return true;

	}

	//showAlert() method
	private void showServerAlert(){

		AlertDialog.Builder builder = new AlertDialog.Builder(this);  
		builder.setMessage("Server not reachable")  
		.setCancelable(false)  
		.setPositiveButton("Try again",  
				new DialogInterface.OnClickListener(){  
			public void onClick(DialogInterface dialog, int id){  
				showGpsOptions();  
			}  
		});  
		builder.setNegativeButton("Exit",  
				new DialogInterface.OnClickListener(){  
			public void onClick(DialogInterface dialog, int id){  
				dialog.cancel();  
			}  
		});  
		AlertDialog alert = builder.create();  
		alert.show();  

	}



	//testGps method
	private void testGps(){
		loc_man=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
		if(!loc_man.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			showGpsAlert();
		}
	}

	//testInternet method
	private Boolean testInternet(){


		ConnectivityManager conMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE); 

		if(   conMgr.getActiveNetworkInfo() != null &&
				conMgr.getActiveNetworkInfo().isAvailable() &&
				conMgr.getActiveNetworkInfo().isConnected()   ){
			//connected
			return true;
		}else{
			showNetAlert();
			return false;
		}


	}

	//showGpsAlert method shows an alert dialog which allows the user to turn on gps if he wish to do it
	private void showGpsAlert(){

		AlertDialog.Builder builder = new AlertDialog.Builder(this);  
		builder.setMessage("Your GPS is disabled! Would you like to enable it?")  
		.setCancelable(false)  
		.setPositiveButton("Enable GPS",  
				new DialogInterface.OnClickListener(){  
			public void onClick(DialogInterface dialog, int id){  
				showGpsOptions();  
			}  
		});  
		builder.setNegativeButton("Do nothing",  
				new DialogInterface.OnClickListener(){  
			public void onClick(DialogInterface dialog, int id){  
				dialog.cancel();  
			}  
		});  
		AlertDialog alert = builder.create();  
		alert.show();  



	}

	//shpwGpsOprtions sends the user to the "Settings" page, where the user can activare GPS
	private void showGpsOptions(){
		Intent gpsOptionsIntent = new Intent(  
				android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);

		startActivity(gpsOptionsIntent); 

	}



	//showNetAlert method shows an alert dialog which allows the user to turn on internet connection if he wish to do it
	private void showNetAlert(){

		AlertDialog.Builder builder = new AlertDialog.Builder(this);  
		builder.setMessage("You are not connected to the internet! Would you like to connect?")  
		.setCancelable(false)  
		.setPositiveButton("Connect",  
				new DialogInterface.OnClickListener(){  
			public void onClick(DialogInterface dialog, int id){  
				showNetOptions();  
			}  
		});  
		builder.setNegativeButton("Do nothing",  
				new DialogInterface.OnClickListener(){  
			public void onClick(DialogInterface dialog, int id){  
				dialog.cancel();  
			}  
		});  
		AlertDialog alert = builder.create();  
		alert.show();  



	}

	//shpwNetOprtions sends the user to the "Settings" page, where the user can activare internet connection
	private void showNetOptions(){
		Intent gpsOptionsIntent = new Intent(  
				android.provider.Settings.ACTION_WIRELESS_SETTINGS);

		startActivity(gpsOptionsIntent); 

	}

	//



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