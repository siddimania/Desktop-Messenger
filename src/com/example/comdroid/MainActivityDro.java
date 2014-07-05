package com.example.comdroid;

import com.comdroid.R;

import android.R.mipmap;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivityDro extends Activity {
	private static final String TAG = MainActivityDro.class.getSimpleName();

	public static String ipaddress;


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
			
		submitOnClickListener();
		checkWifi();
	}

	private void submitOnClickListener() {
		
		Button button= (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View arg0) {
				
				EditText edit = (EditText) findViewById(R.id.editText1);
				
				ipaddress=edit.getText().toString();
				
				System.out.println(ipaddress);
				
				Intent intent =  new Intent(MainActivityDro.this, SendMessage.class);
				startActivity(intent);
				
			}
		});
		
	}

	private void checkWifi(){      
	     IntentFilter filter = new IntentFilter();
	     filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
	     final WifiManager wifiManager = 
	                         (WifiManager)this.getSystemService(Context.WIFI_SERVICE);;
	                          registerReceiver(new BroadcastReceiver(){
	        @Override
	        public void onReceive(Context arg0, Intent arg1) {
	            // TODO Auto-generated method stub
	            Log.d("wifi","Open Wifimanager");

	            String scanList = wifiManager.getScanResults().toString();
	            int wifi1=wifiManager.getWifiState();
	           
	            Log.d("wifi","Scan:"+scanList+"wifi1"+wifi1);
	        }           
	      },filter);        
	        wifiManager.startScan();
	      }
	
	@Override
	public void onBackPressed() {
		finish();
		super.onBackPressed();
	}
	

}
