package com.example.comdroid;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.comdroid.R;

public class AddContacts extends Activity {

	private static final String TAG = "_tag:";
	DbHelper dbHelper;
	SQLiteDatabase db;
	int i=0;
	
	Contacts myobject;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_contact);
		
		OnClickOk_add_contacts();
		OnClickCancel();
	}

	private void OnClickOk_add_contacts() {
		
		Button b1 = (Button) findViewById(R.id.button1);
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dbHelper = new DbHelper(getApplicationContext());
				db = dbHelper.getWritableDatabase();
				
				ContentValues values = new ContentValues();
				
				String UserName,ipaddress;
				
				EditText Name = (EditText) findViewById(R.id.editTextName);
				UserName=Name.getText().toString();
				
				EditText Ip = (EditText) findViewById(R.id.editTextIp);
				ipaddress=Ip.getText().toString();
				
				long time= System.currentTimeMillis();
				i = (int)time;
				values.put(DbHelper._ID,i);
				values.put(DbHelper.Noti_Name, UserName);
				values.put(DbHelper.Noti_Ipaddress, ipaddress);
				db.insertWithOnConflict(DbHelper.TABLE, null, values,
				SQLiteDatabase.CONFLICT_REPLACE);
				
				Toast.makeText(getApplicationContext(), "A New Contact Is Added", Toast.LENGTH_SHORT).show();
				
				Intent i = new Intent(AddContacts.this, Contacts.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.putExtra("flag", "modify");
				startActivity(i);
				finish();
			}
		});
				
	}

	private void OnClickCancel() {
		Button b1 = (Button) findViewById(R.id.button2);
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
				finish();
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
