package com.android.jialin.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Administrator
 * 
 */
public class DBOpenHelper extends SQLiteOpenHelper {
	private static final String DATABASENAME = "jialin.db";
	private static final int DATABASEVERSION = 2;

	public DBOpenHelper(Context context) {
		super(context, DATABASENAME, null, DATABASEVERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE city (_id integer primary key autoincrement, name varchar(500),  longitude varchar(500), latitude varchar(500),population varchar(500),postcode varchar(500))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS city");
		onCreate(db);
	}

}
