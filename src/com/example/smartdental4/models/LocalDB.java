package com.example.smartdental4.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LocalDB{
	private SQLiteDatabase db;
	
	public LocalDB(Context cnt)
	{
		db = SQLiteDatabase.openOrCreateDatabase(cnt.getFilesDir().toString() + "/my.db3", null);
		db = SQLiteDatabase.openDatabase(cnt.getFilesDir().toString() + "/my.db3", null, SQLiteDatabase.OPEN_READWRITE);
		boolean flag = tabbleIsExist("AlarmClock");
		if (!flag)
			db.execSQL("create table if not exists AlarmClock (_id integer primary key autoincrement,"
					+ " date text,"
					+ " time text,"
					+ " title text,"
					+ " content text,"
					+ " sound integer,"
					+ " music text"
					+ ")");
		
		flag = tabbleIsExist("Orthodontic");
		if (!flag)
			db.execSQL("create table if not exists Orthodontic (_id integer primary key autoincrement,"
					+ " date text,"
					+ " alarmId integer"
					+ ")");
			//db.execSQL("create table AlarmClock");
	}
	
	public void close()
	{
		db.close();
	}
	
	public long insert(AlarmClock dt)
	{
		ContentValues values = new ContentValues();
		values.put("date", dt.date);
		values.put("time", dt.time);
		values.put("title", dt.title);
		values.put("content", dt.content);
		values.put("sound", dt.sound);
		values.put("music", dt.music);
		long flag = db.insert("AlarmClock", null, values);
		return flag;
	}
	
	public boolean update(long id, AlarmClock dt)
	{
		Cursor cursor = db.query("AlarmClock",new String[]{"_id"}, "_id=?",new String[]{id + ""},null,null,"_id asc",null);
		if (cursor.isAfterLast())
		{
			cursor.close();
			return false;
		}
		ContentValues values = new ContentValues();
		values.put("date", dt.date);
		values.put("time", dt.time);
		values.put("title", dt.title);
		values.put("content", dt.content);
//		values.put("way", dt.way + "");
		values.put("sound", dt.sound + "");
		values.put("music", dt.music);
		int result = db.update("AlarmClock", values, "_id=?", new String[]{id + ""});
		cursor.close();
		if (result == 0)
			return false;
		else
			return true;
	}
	
	public long findIdByDate(String dateQ) /*用dateQ(yyyy-mm-dd字符串)来找日程的id*/
	{
		Cursor cursor = db.query("AlarmClock", null, "date=?", new String[] {dateQ}, null, null, "time asc", null);
		cursor.moveToFirst();
		long id = cursor.getLong(cursor.getColumnIndex("_id"));
		cursor.close();
		return id;
	}
	
	public boolean delete(String dateQ) /*用dateQ(yyyy-mm-dd字符串)来删除日程*/
	{
		int result = db.delete("AlarmClock", "date=?", new String[]{dateQ});
		if (result == 0)
			return false;
		else
			return true;
	}
	
	public AlarmClock find(long id)
	{
		Cursor cursor = db.query("AlarmClock", null, "_id=?", new String[]{id + ""}, null, null, "_id asc", null);
		if (cursor.isAfterLast())
		{
			cursor.close();
			return null;
		}
		AlarmClock ml = new AlarmClock();
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			ml._id = cursor.getLong(cursor.getColumnIndex("_id"));
			ml.date = cursor.getString(cursor.getColumnIndex("date"));
			ml.time = cursor.getString(cursor.getColumnIndex("time"));
			ml.title = cursor.getString(cursor.getColumnIndex("title"));
			ml.content = cursor.getString(cursor.getColumnIndex("content"));
//			ml.way = cursor.getInt(cursor.getColumnIndex("way"));
			ml.sound = cursor.getInt(cursor.getColumnIndex("sound"));
			ml.music = cursor.getString(cursor.getColumnIndex("music"));
		}
		cursor.close();
		return ml;
	}
	
	public boolean delete(long id)
	{
		int result = db.delete("AlarmClock", "_id=?", new String[]{id+""});
		if (result == 0)
			return false;
		else
			return true;
	}
	
	public AlarmClock[] findList(String dateQ)
	{
		Cursor cursor = db.query("AlarmClock", null, "date=?", new String[] {dateQ}, null, null, "time asc", null);
		int len = cursor.getCount();
		AlarmClock[] mll = new AlarmClock[len];
		int i;
		for (cursor.moveToFirst(), i = 0; i < len; cursor.moveToNext(), i++) {
			AlarmClock ml = new AlarmClock();
			ml._id = cursor.getLong(cursor.getColumnIndex("_id"));
			ml.date = cursor.getString(cursor.getColumnIndex("date"));
			ml.time = cursor.getString(cursor.getColumnIndex("time"));
			ml.title = cursor.getString(cursor.getColumnIndex("title"));
			ml.content = cursor.getString(cursor.getColumnIndex("content"));
//			ml.way = cursor.getInt(cursor.getColumnIndex("way"));
			ml.sound = cursor.getInt(cursor.getColumnIndex("sound"));
			ml.music = cursor.getString(cursor.getColumnIndex("music"));
			mll[i] = ml;
		}
		cursor.close();
		return mll;
	}
	
	public boolean[] checkDateList(String[] dateL)
	{
		boolean[] bL = new boolean[dateL.length];
		Cursor cursor;
		for (int i = 0; i < dateL.length; i++)
		{
			cursor = db.query("AlarmClock", new String[]{"_id"}, "date=?", new String[]{dateL[i]}, null, null, null, null);
			if (cursor.getCount() == 0)
				bL[i] = false;
			else
				bL[i] = true;
			cursor.close();
		}
		return bL;
	}
	
	
	public boolean tabbleIsExist(String tableName){
        boolean result = false;
        if(tableName == null){
                return false;
        }
        Cursor cursor = null;
        try {
                String sql = "select count(*) as c from sqlite_master where type ='table' and name ='"+tableName.trim()+"' ";
                cursor = db.rawQuery(sql, null);
                if(cursor.moveToNext()){
                        int count = cursor.getInt(0);
                        if(count>0){
                                result = true;
                        }
                }
                
        } catch (Exception e) {
        	e.printStackTrace();
        }           
        cursor.close();
        return result;
    }
	
	public void setOrthodontic(String dateQ, AlarmClock dt)
	{
		Cursor cursor = db.query("Orthodontic", null, "date=?", new String[] {dateQ}, null, null, "time asc", null);
		int len = cursor.getCount();
		if (len == 0)
			insert(dt);
		else
		{
			int i;
			long _id;
			int alarmId;
			for (cursor.moveToFirst(), i = 0; i < len; cursor.moveToNext(), i++) {
				_id = cursor.getLong(cursor.getColumnIndex("_id"));
				alarmId = cursor.getInt(cursor.getColumnIndex("alarmId"));
				if (i == 0)
					update(alarmId, dt);
				else
				{
					db.delete("Orthodontic", "_id=?", new String[]{_id+""});
					delete(alarmId);
				}
			}
		}
	}
	
	public void cancelOrthodontic(String dateQ)
	{
		int len;
		int i;
		long _id;
		int alarmId;
		
		Cursor cursor = db.query("Orthodontic", null, "date=?", new String[] {dateQ}, null, null, "time asc", null);
		len = cursor.getCount();
		for (cursor.moveToFirst(), i = 0; i < len; cursor.moveToNext(), i++) {
			_id = cursor.getLong(cursor.getColumnIndex("_id"));
			alarmId = cursor.getInt(cursor.getColumnIndex("alarmId"));
			db.delete("Orthodontic", "_id=?", new String[]{_id+""});
			delete(alarmId);
		}
	}
	
	public int findOrthodontic(String dateQ)
	{
		Cursor cursor = db.query("Orthodontic", null, "date=?", new String[] {dateQ}, null, null, "time asc", null);
		int len = cursor.getCount();
		if (len == 0)
			return -1;
		else
		{
			cursor.moveToFirst();
			return cursor.getInt(cursor.getColumnIndex("alarmId"));
		}
	}
}
