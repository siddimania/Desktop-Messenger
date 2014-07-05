package com.example.comdroid;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.comdroid.R;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendMessage extends Activity {
	private static final String TAG = SendMessage.class.getSimpleName();

	private EditText textOut;
	private Socket socket = null;
	private DataOutputStream dataOutputStream = null;
	private DataInputStream dataInputStream = null;

	Contacts mainObject;
	
	private class SendDataAsyncTask extends AsyncTask<String, String, String> {
		protected String doInBackground(String... params) {
			String message = null;
			try {
				ensureSocket();
				//System.out.println("this is it");
				dataOutputStream.writeUTF("Notification:"+params[0]+"\n");
				
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
		setContentView(R.layout.sendmessage);
		sendOnClickListener();
		
	}

	private void sendOnClickListener() {
		
		Button button= (Button) findViewById(R.id.buttonSend);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				EditText edit =(EditText)findViewById(R.id.editText1);
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
			socket = new Socket(mainObject.ipaddress,5000);
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
