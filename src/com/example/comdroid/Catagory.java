package com.example.comdroid;

import com.comdroid.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class Catagory extends Activity {

	private static final String TAG = "_tag:";
	DbHelper dbHelper;
	SQLiteDatabase db;
	int i=0;
	
	Contacts myobject;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.catagory);
		
		OnClickNotification();
		OnSmsClick();
		OnEmailClick();
		OnChatClick();
	}

	private void OnChatClick() {
		ImageView im = (ImageView)findViewById(R.id.imageView4);
		im.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(Catagory.this, ChatContacts.class);
				startActivity(i);
			}
		});
		
	}

	private void OnEmailClick() {
		
		ImageView im = (ImageView)findViewById(R.id.imageView1);
		im.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(Catagory.this, Email.class);
				startActivity(i);
			}
		});
		
	}

	private void OnSmsClick() {
		ImageView im = (ImageView)findViewById(R.id.imageView2);
		im.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(Catagory.this, Sms.class);
				startActivity(i);
				
			}
		});
		
	}

	private void OnClickNotification() {
		ImageView im = (ImageView)findViewById(R.id.imageView3);
		im.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(Catagory.this, Contacts.class);
				startActivity(i);
				
			}
		});
		
	}

	@Override
	public void onBackPressed() {
		Log.d(TAG, "onBackPressed Called");
		Toast.makeText(getApplicationContext(), "All Contacts", Toast.LENGTH_SHORT).show();
		finish();
	}
}
