package com.example.smartdental4.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SmartDentalSQLiteHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "smartdental.db3";
	public static final int DATABASE_VERSION = 4;

	private static final String DATABASE_CREATE = "create table diaries (_id integer primary key, date text not null, has_meeting boolean default 0, has_remind boolean default 0, pain_index integer default 0, mood_index integer default 0, teeth_index integer default 0, stage integer default 0, doctor_advice text default '');";

	public SmartDentalSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS diaries");
		onCreate(db);
	}
}
