package com.example.comdroid;

import com.comdroid.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ChatContacts extends Activity {

	private static final String TAG = "_tag:";
	DbHelper dbHelper;
	SQLiteDatabase db;

	public static String ipaddress=null;
	public static String name=null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chatcontacts);

		gettingListView();
	
		
	}	

	public int gettingListView() {

		dbHelper = new DbHelper(getApplicationContext());
		db = dbHelper.getReadableDatabase();
		Cursor cursor = null;

		try {

			ListView tv = (ListView) findViewById(R.id.ContactsList);
			cursor = db.query(DbHelper.TABLE, new String[] { DbHelper._ID,
					DbHelper.Noti_Name, DbHelper.Noti_Ipaddress }, null, null,null, null, null);

			startManagingCursor(cursor);
			SimpleCursorAdapter adapter;

			String[] from = {dbHelper.Noti_Name,
					dbHelper.Noti_Ipaddress };
			int[] to = { R.id.heading, R.id.description};

			adapter = new SimpleCursorAdapter(getApplicationContext(),
					R.layout.list_view, cursor, from, to, 0);

			View empty = findViewById(R.id.empty);
			tv.setEmptyView(empty);

			tv.setAdapter(adapter);
			
			tv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					Cursor c;
					c = db.query(DbHelper.TABLE, new String[] {
							DbHelper.Noti_Name, DbHelper.Noti_Ipaddress }, null, null,
							null, null, null);
					int columnIndex=0 ;
					int columnName=0;
					while(position>=0){
						c.moveToNext();
						columnIndex = c.getColumnIndex(dbHelper.Noti_Ipaddress);
						columnName= c.getColumnIndex(DbHelper.Noti_Name);
						position--;
					}
					
					
					ipaddress = c.getString(columnIndex);
					name= c.getString(columnName);
					
					System.out.println("IP ADDRESS:"+ipaddress);
					
					Intent i = new Intent(ChatContacts.this,Chat.class);
					startActivity(i);
				}

			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (cursor != null)
			return cursor.getCount();
		else
			return 0;
	}

	@Override
	public void onBackPressed() {
		
		Log.d(TAG, "onBackPressed Called");
		dbHelper.close();
		db.close();
		finish();
	}
}
