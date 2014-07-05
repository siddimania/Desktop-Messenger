package com.example.comdroid;

import com.comdroid.R;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Sms extends Activity {
	private static final String TAG = SendMessage.class.getSimpleName();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sms);
		sendOnClickListener();
		
	}

	private void sendOnClickListener() {
		
		Button button= (Button) findViewById(R.id.buttonSend);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				EditText phoneno_name =(EditText)findViewById(R.id.editText1);
				System.out.println("this is string "+phoneno_name.getText().toString());
				String phoneNo=phoneno_name.getText().toString();
				EditText edit2 =(EditText)findViewById(R.id.editText2);
				String message = edit2.getText().toString();
				
				if (phoneNo.matches("[0-9]+") && phoneNo.length() > 2) {
					   sendSMS(phoneNo, message);
					} else{
					  
					}
				
			}
		});
		
	}


	   private void sendSMS(String phoneNumber, String message)
	   {
	       SmsManager sms = SmsManager.getDefault();
	       Toast.makeText(getApplicationContext(), "message send", Toast.LENGTH_SHORT).show();
	       sms.sendTextMessage(phoneNumber, null, message, null, null);
	    }

	

	@Override
	public void onBackPressed() {
		finish();
		super.onBackPressed();
	}
}
