package com.massiveinfinity.slidingmenu.util;

import java.util.ArrayList;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotificationGetData extends SQLiteOpenHelper {
	 private static final int DATABASE_VERSION = 1;//ts the database version
	 private static final String DATABASE_NAME = "ndp";
	 
	    
	    private static final String TABLE_NOTIFICATION = "table1";//table name
	 
	    // Contacts Table Columns names
	    private static final String KEY_ID="id";
	    private static final String KEY_MESSAGE = "message";
	    private static final String KEY_DATE = "date";
	    private static final String KEY_STATUS = "status";
	    
	
	
	

	public NotificationGetData(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	


	@Override
	public void onCreate(SQLiteDatabase db) {
		
		String CREATE_NOTIFICATION_TABLE = "CREATE TABLE " + TABLE_NOTIFICATION + "(" 
	            + KEY_ID + "INTEGER  PRIMARY KEY AUTOINCREMENT," + KEY_MESSAGE + " TEXT," + KEY_DATE + " TEXT,"+ KEY_STATUS + " NUMERIC " + ")";
	        db.execSQL(CREATE_NOTIFICATION_TABLE);

		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
		
	}
	public void addNotification(NotificationDTO notificate)
	{
		 SQLiteDatabase db = this.getWritableDatabase();//get instance of database
		 
			//need to use content values to store data 
			ContentValues content=new ContentValues();
			content.put(KEY_MESSAGE, notificate.getMessage());
			content.put(KEY_DATE, notificate.getDate());
			content.put(KEY_STATUS, notificate.isFlag());
			db.insert(TABLE_NOTIFICATION, null, content);
			db.close();
	}
	public  void updateStatus(int rowId)
	{
		//update tablename set status where id=int
		SQLiteDatabase db = this.getWritableDatabase();
		String updateQuery="UPDATE "+TABLE_NOTIFICATION+" SET "+ KEY_STATUS +" =true where"+" KEY_ID="+rowId;
		db.execSQL(updateQuery);
		db.close();
	}

	public List<NotificationDTO> getAllNotifications()
	{
		List<NotificationDTO> notificationList=new ArrayList<NotificationDTO>();
		String countQuery = "SELECT  * FROM " + TABLE_NOTIFICATION;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

//       int n=cursor.getCount();
		if (cursor.moveToFirst()) 
		{
			do {
				NotificationDTO notification = new NotificationDTO();
				notification.setMessage((cursor.getString(0)));
				notification.setDate(cursor.getString(1));
				
		// contact.setPhoneNumber(cursor.getString(2));
        // Adding contact to list
				notificationList.add(notification);
				} while (cursor.moveToNext());
		}
		db.close();
		cursor.close();
		return notificationList;
	}
}
