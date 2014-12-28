package com.example.smartdental4.models;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.database.SQLException;

public class DiaryDAOImpl implements DiaryDAO {
	public SmartDentalSQLiteHelper diariesHelper;
	public SQLiteDatabase diaries;

	public DiaryDAOImpl(Context context) {
		diariesHelper = new SmartDentalSQLiteHelper(context);
	}

	public void open() throws SQLException {
		diaries = diariesHelper.getWritableDatabase();
	}

	public void close() {
		diariesHelper.close();
	}
	
	@Override
	public List<Diary> getDiaries(){
		Cursor cursor = diaries.rawQuery("select * from diaries order by date asc", null);
		List<Diary> diaryList = new ArrayList<Diary>();
		
		while (cursor.moveToNext())
			diaryList.add(new Diary(cursor));
		
		return diaryList;
	}

	@Override
	public Diary getDiaryByDate(String date) {
		Cursor cursor = diaries.rawQuery(
				"select * from diaries where date = ?", new String[] { date });
		Diary result = null;
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			result = new Diary(cursor);
		} else {
			diaries.execSQL("INSERT INTO diaries(date) VALUES(?)",
					new String[] { date });
			cursor = diaries.rawQuery("select * from diaries where date = ?",
					new String[] { date });
			cursor.moveToFirst();
			result = new Diary(cursor);
		}

		cursor.close();
		return result;
	}

	@Override
	public boolean changeHasMeeting(int _id, boolean hasMeeting) {
		String hasMeetingString = "0";
		if(hasMeeting)
			hasMeetingString = "1";
		diaries.execSQL("update diaries set has_meeting = ? where _id = ?",
				new String[] { hasMeetingString,
						Integer.valueOf(_id).toString() });
		return true;
	}

	@Override
	public boolean changeHasRemind(int _id, boolean hasRemind){
		String hasRemindString = "0";
		if(hasRemind)
			hasRemindString = "1";
		diaries.execSQL("update diaries set has_remind = ? where _id = ?",
				new String[] { hasRemindString,
						Integer.valueOf(_id).toString() });
		return true;
	}
	
	@Override
	public boolean changePainIndex(int _id, int painIndex) {
		String painIndexString = Integer.valueOf(painIndex).toString();
		diaries.execSQL(
				"update diaries set pain_index = ? where _id = ?",
				new String[] { painIndexString, Integer.valueOf(_id).toString() });
		return true;
	}

	@Override
	public boolean changeMoodIndex(int _id, int moodIndex) {
		String moodIndexString = Integer.valueOf(moodIndex).toString();
		diaries.execSQL(
				"update diaries set mood_index = ? where _id = ?",
				new String[] { moodIndexString, Integer.valueOf(_id).toString() });
		return true;
	}

	@Override
	public boolean changeTeethIndex(int _id, int teethIndex) {
		String teethIndexString = Integer.valueOf(teethIndex).toString();
		diaries.execSQL(
				"update diaries set teeth_index = ? where _id = ?",
				new String[] { teethIndexString, Integer.valueOf(_id).toString() });
		return true;
	}
	
	@Override
	public boolean changeStage(int _id, int stage) {
		String stageString = Integer.valueOf(stage).toString();
		diaries.execSQL("update diaries set stage = ? where _id = ?",
				new String[] { stageString, Integer.valueOf(_id).toString() });
		return true;
	}

	@Override
	public boolean changeDoctorAdvice(int _id, String doctorAdvice) {
		/*String doctorAdviceString = "\'" + doctorAdvice.replace('\'', '\"')
				+ "\'";*/
		diaries.execSQL("update diaries set doctor_advice = ? where _id = ?",
				new String[] { doctorAdvice,
						Integer.valueOf(_id).toString() });
		return true;
	}
}