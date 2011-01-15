package connectivity;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;



//class for GPS, Connection and Server test
public class GCSTester {

	private Context c;
	private boolean reachable=false; 
	private LocationManager loc_man;

	public GCSTester(Context con){
		this.c=con;
	}

	public boolean testGCS(){
		
		testInternet();
		testGps();
		return testServer();
	}



	//testServer method
	//I tried a lot of ways to test if server is running, but some doesn't work properly on android,
	//some are not very reliable. The actual method checks if the machine on which server is running, is reachable.
	//(change the ip with your machine's ip)
	private boolean testServer(){

		try {
			String ip="192.168.0.2";
			InetAddress net= InetAddress.getByName(ip);
			if(net.isReachable(3000)){
				reachable=true;

			}else{
				reachable=false;
				showServerAlert();
			}



		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		return reachable;

	}

	//showAlert() method
	private void showServerAlert(){

		AlertDialog.Builder builder = new AlertDialog.Builder(c);  
		builder.setMessage("Server not reachable")  
		.setCancelable(false)  
		.setPositiveButton("Try again",  
				new DialogInterface.OnClickListener(){  
			public void onClick(DialogInterface dialog, int id){ 

				if(testServer()){
					dialog.cancel();
				}
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
		loc_man=(LocationManager)c.getSystemService(Context.LOCATION_SERVICE);
		if(!loc_man.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			showGpsAlert();
		}
	}

	//testInternet method
	private boolean testInternet(){


		ConnectivityManager conMgr = (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE); 

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

		AlertDialog.Builder builder = new AlertDialog.Builder(c);  
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

		c.startActivity(gpsOptionsIntent); 

	}



	//showNetAlert method shows an alert dialog which allows the user to turn on internet connection if he wish to do it
	private void showNetAlert(){

		AlertDialog.Builder builder = new AlertDialog.Builder(c);  
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

		c.startActivity(gpsOptionsIntent); 

	}

	//


}
