/*
@author: siddhartha dimania
*/
package com.example.comdroid;

import com.comdroid.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Email extends Activity {
	private static final String TAG = SendMessage.class.getSimpleName();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.email);
		sendOnClickListener();
		
	}

	private void sendOnClickListener() {
		
		Button button= (Button) findViewById(R.id.buttonSend);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				EditText editEmail =(EditText)findViewById(R.id.editText1);
				System.out.println("this is string "+editEmail.getText().toString());
				String email=editEmail.getText().toString();
				
				String emailaddress[]={ email };
				EditText edit2 =(EditText)findViewById(R.id.editText2);
				String message = edit2.getText().toString();
				
				
				Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, emailaddress);
				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"this is app");
				emailIntent.setType("plain/text");
				emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,message);
				startActivity(emailIntent);
				
				
				
			}
		});
		
	}
	
	@Override
	public void onBackPressed() {
		finish();
		super.onBackPressed();
	}
}
