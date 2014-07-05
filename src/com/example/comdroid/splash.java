package com.example.comdroid;


import com.comdroid.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

public class splash extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);	
		countdowntimer();
	}

	public void countdowntimer() {
		new CountDownTimer(1500, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
			}

			@Override
			public void onFinish() {
				Toast.makeText(getApplicationContext(), "Categories", Toast.LENGTH_SHORT).show();
				
				
				
				Intent i = new Intent(splash.this, Catagory.class);
				startActivity(i);
				finish();
			}
		}.start();
	}
}
