package com.example.comdroid;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.comdroid.R;


public class Chat extends Activity {
	private static final String TAG = SendMessage.class.getSimpleName();

	private EditText textOut;
	private Socket socket = null;
	private DataOutputStream dataOutputStream = null;
	private DataInputStream dataInputStream = null;

	DbHelper dbHelper;
	SQLiteDatabase db;
	ChatContacts mainObject;
	private Handler handler = new Handler();
	static int i =0;
	
	private class SendDataAsyncTask extends AsyncTask<String, String, String> {
		protected String doInBackground(String... params) {
			String message = null;
			try {
				ensureSocket();
				//System.out.println("this is it");
				dataOutputStream.writeUTF(params[0]+"\n");
				
				final String received=dataInputStream.readUTF();
				handler.post(new Runnable(){
					public void run() {
						Toast.makeText(getApplicationContext(), "message:"+received,Toast.LENGTH_SHORT).show();
						
						dbHelper = new DbHelper(getApplicationContext());
						db = dbHelper.getWritableDatabase();
						
						ContentValues values = new ContentValues();
						
						long time= System.currentTimeMillis();
						i = (int)time;
						values.put(DbHelper._ID,i);
						values.put(DbHelper.chat_Name, received);
						values.put(DbHelper.chat_Status,"Received");
						db.insertWithOnConflict(DbHelper.TABLE3, null, values,
						SQLiteDatabase.CONFLICT_REPLACE);
						
						//Toast.makeText(getApplicationContext(), "A New Contact Is Added", Toast.LENGTH_SHORT).show();
						
						Intent i = new Intent(Chat.this, Chat.class);
						i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						i.putExtra("flag", "modify");
						startActivity(i);
						finish();
					}
					});
				
				closeSocket();
				
			} catch (IOException e) {
				Log.e(TAG, "Error in reading/writing from socket", e);
				publishProgress(e.getMessage());
			}
			return message;
		}

		@Override
		protected void onProgressUpdate(String... message) {
			Toast.makeText(getApplicationContext(), message[0],
					Toast.LENGTH_LONG).show();
		}

		@Override
		protected void onPostExecute(String result) {
			if (!TextUtils.isEmpty(result)) {
				textOut.setText(textOut.getText() + result);
			}
		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);
		sendOnClickListener();
		gettingListView();
	}
	
	public int gettingListView() {

		dbHelper = new DbHelper(getApplicationContext());
		db = dbHelper.getReadableDatabase();
		Cursor cursor = null;

		try {

			ListView tv = (ListView) findViewById(R.id.ContactsList2);
			cursor = db.query(DbHelper.TABLE3, new String[] { DbHelper._ID,
					DbHelper.chat_Name, DbHelper.chat_Status }, null, null,null, null, null);

			startManagingCursor(cursor);
			SimpleCursorAdapter adapter;

			String[] from = {dbHelper.chat_Name,
					dbHelper.chat_Status};
			int[] to = { R.id.heading, R.id.description};

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
	
	
	private void sendOnClickListener() {
		
		Button button= (Button) findViewById(R.id.buttonSend);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				EditText edit =(EditText)findViewById(R.id.editText2);
				System.out.println("this is string "+edit.getText().toString());
				
				System.out.println("ip address::"+mainObject.ipaddress);
				System.out.println("username::"+ mainObject.name);
				
				new SendDataAsyncTask().execute(edit.getText().toString());
			}
		});
		
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		closeSocket();
	}

	private void ensureSocket() throws UnknownHostException, IOException {
		if (socket == null) {
			socket = new Socket(mainObject.ipaddress,5001);
			Log.d("this is socket:", "socket connection");
			dataOutputStream = new DataOutputStream(socket.getOutputStream());
			dataInputStream = new DataInputStream(socket.getInputStream());
		}
	}

	private void closeSocket() {
		if (socket != null) {
			try {
				socket.close();
				socket = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (dataOutputStream != null) {
			try {
				dataOutputStream.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}

		if (dataInputStream != null) {
			try {
				dataInputStream.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}
	@Override
	public void onBackPressed() {
		finish();
		super.onBackPressed();
	}
}
