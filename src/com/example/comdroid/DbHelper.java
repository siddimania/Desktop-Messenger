
/*
@author: siddhartha dimania
*/
package com.example.comdroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DbHelper extends SQLiteOpenHelper {

	private static final String TAG = DbHelper.class.getSimpleName();
	public static final String DB_NAME = "NotiDatabase.db";
	public static final int DB_VERSION = 1;

	public static final String TABLE = "Jyc_news"; 
	public static final String TABLE2="ping_table";
	public static final String TABLE3="chat_table";
	
	public static final String _ID = "_id";
	public static final String Noti_Name= "Noti_name";
	public static final String chat_Name= "chat_Name";
	public static final String chat_Status= "chat_Status";
	public static final String Noti_Ipaddress = "Noti_Ipaddress";
	public static final String Noti_PingStatus="Noti_PingStatus";
	public static final String Noti_ReceiveMessage="Noti_ReceiveMessage";
	
	public DbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + TABLE + "(" + _ID+ " INTEGER PRIMARY KEY, " + Noti_Name +" TEXT, "+ Noti_Ipaddress + " TEXT );";
		
		String sql2 = "CREATE TABLE " + TABLE2 + "(" + _ID+ " INTEGER PRIMARY KEY, " + Noti_Name +" TEXT, "+ Noti_PingStatus + " TEXT );";
		
		String sql3 = "CREATE TABLE " + TABLE3 + "(" + _ID+ " INTEGER PRIMARY KEY, " + chat_Name +" TEXT, "+ chat_Status + " TEXT );";
		
		Log.d(TAG, "CREATING SQL " + sql);
		
		db.execSQL(sql);
		db.execSQL(sql2);
		db.execSQL(sql3);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

	
}
