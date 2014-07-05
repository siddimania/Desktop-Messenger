/*
@author: siddhartha dimania
*/
package com.example.comdroid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

import com.comdroid.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Ip_Ping extends Activity {

	private static final String TAG = "_tag:";
	DbHelper dbHelper;
	SQLiteDatabase db;
	int i = 0;
	public static String ipaddress = null;
	public static String name = null;
	String pingResult = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ip_ping);
		ping();
	}

	private void ping() {
		new SendDataAsyncTask().execute("hello");
		//Toast.makeText(getApplicationContext(), "Ping Completed",
				//Toast.LENGTH_SHORT).show();
		gettingListView();
	}

	private int gettingListView() {

		dbHelper = new DbHelper(getApplicationContext());
		db = dbHelper.getReadableDatabase();
		Cursor cursor = null;

		try {

			ListView tv = (ListView) findViewById(R.id.PingList);
			cursor = db.query(DbHelper.TABLE2, new String[] { DbHelper._ID,
					DbHelper.Noti_Name, DbHelper.Noti_PingStatus }, null, null,
					null, null, null);

			startManagingCursor(cursor);
			SimpleCursorAdapter adapter;

			String[] from = { dbHelper.Noti_Name, dbHelper.Noti_PingStatus };
			int[] to = { R.id.heading, R.id.description };

			adapter = new SimpleCursorAdapter(getApplicationContext(),
					R.layout.list_view, cursor, from, to, 0);

			View empty = findViewById(R.id.empty);
			tv.setEmptyView(empty);

			tv.setAdapter(adapter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (cursor != null)
			return cursor.getCount();
		else
			return 0;
	}

	private class SendDataAsyncTask extends AsyncTask<String, String, String> {
		protected String doInBackground(String... params) {

			InetAddress addr = null;

			dbHelper = new DbHelper(getApplicationContext());
			db = dbHelper.getReadableDatabase();

			Cursor c;
			c = db.query(DbHelper.TABLE, new String[] { DbHelper.Noti_Name,
					DbHelper.Noti_Ipaddress }, null, null, null, null, null);
			int columnIndex = 0;
			int columnName = 0;

			while (c != null && c.moveToNext()) {

				columnIndex = c.getColumnIndex(dbHelper.Noti_Ipaddress);
				columnName = c.getColumnIndex(DbHelper.Noti_Name);

				ipaddress = c.getString(columnIndex);
				name = c.getString(columnName);
				if (!ipaddress.equalsIgnoreCase("")) {
					try {
						String pingCmd = "ping -c 5 " + ipaddress;
						String pingResult = "";
						Runtime r = Runtime.getRuntime();
						Process p = r.exec(pingCmd);

						BufferedReader in = new BufferedReader(
								new InputStreamReader(p.getInputStream()));
						String inputLine;

						while ((inputLine = in.readLine()) != null) {
							System.out.println(inputLine);
							pingResult = pingResult + inputLine;
						}

						System.out.println(pingResult);
						System.out.println(pingResult.indexOf("received"));
						int index = pingResult.indexOf("received");

						System.out.println(pingResult.charAt(index - 2));

						dbHelper = new DbHelper(getApplicationContext());
						db = dbHelper.getWritableDatabase();

						ContentValues values = new ContentValues();
						
						if ((int) pingResult.charAt(index - 2) > 48) {
							System.out.println("this is the result:"
									+ (int) pingResult.charAt(index - 2));
							System.out.println("connected");
							values.put(DbHelper._ID, i);
							values.put(DbHelper.Noti_Name, name);
							values.put(DbHelper.Noti_PingStatus, "Connected");
							db.insertWithOnConflict(DbHelper.TABLE2, null,
									values, SQLiteDatabase.CONFLICT_REPLACE);
							
						}

						else{
							System.out.println("not");
							values.put(DbHelper._ID, i);
							values.put(DbHelper.Noti_Name, name);
							values.put(DbHelper.Noti_PingStatus, "Not Connected");
							db.insertWithOnConflict(DbHelper.TABLE2, null,
									values, SQLiteDatabase.CONFLICT_REPLACE);
						}
						
						i = i + 1;
						in.close();

					} catch (IOException e) {
						System.out.println(e);
					}
				}
			}

			return "hello";
		}

		@Override
		protected void onProgressUpdate(String... message) {
			Toast.makeText(getApplicationContext(), message[0],
					Toast.LENGTH_LONG).show();
		}

		@Override
		protected void onPostExecute(String result) {

		}

	};

	@Override
	public void onBackPressed() {

		Log.d(TAG, "onBackPressed Called");
		finish();
	}
}
