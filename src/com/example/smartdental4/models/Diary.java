package com.example.smartdental4.models;

import android.database.Cursor;

public class Diary {
	private int _id;
	private String date;
	private boolean hasMeeting;
	private boolean hasRemind;
	private int painIndex;
	private int moodIndex;
	private int teethIndex;
	private int stage;
	private String doctorAdvice;

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public boolean isHasMeeting() {
		return hasMeeting;
	}

	public void setHasMeeting(boolean hasMeeting) {
		this.hasMeeting = hasMeeting;
	}

	public boolean isHasRemind() {
		return hasRemind;
	}

	public void setHasRemind(boolean hasRemind) {
		this.hasRemind = hasRemind;
	}

	public int getPainIndex() {
		return painIndex;
	}

	public void setPainIndex(int painIndex) {
		this.painIndex = painIndex;
	}

	public int getMoodIndex() {
		return moodIndex;
	}

	public void setMoodIndex(int moodIndex) {
		this.moodIndex = moodIndex;
	}

	public int getTeethIndex() {
		return teethIndex;
	}

	public void setTeethIndex(int teethIndex) {
		this.teethIndex = teethIndex;
	}

	public int getStage() {
		return stage;
	}

	public void setStage(int stage) {
		this.stage = stage;
	}

	public String getDoctorAdvice() {
		return doctorAdvice;
	}

	public void setDoctorAdvice(String doctorAdvice) {
		this.doctorAdvice = doctorAdvice;
	}

	public Diary() {
		;
	}

	public Diary(Cursor cursor) {
		this._id = cursor.getInt(0);
		this.date = cursor.getString(1);
		this.hasMeeting = (cursor.getInt(2) != 0);
		this.hasRemind = (cursor.getInt(3) != 0);
		this.painIndex = cursor.getInt(4);
		this.moodIndex = cursor.getInt(5);
		this.teethIndex = cursor.getInt(6);
		this.stage = cursor.getInt(7);
		this.doctorAdvice = cursor.getString(8);
	}

	public boolean empty() {
		if (hasMeeting || hasRemind || (painIndex != 0) || (moodIndex != 0)
				|| (teethIndex != 0) || (stage != 0)
				|| !doctorAdvice.equals(""))
			return false;
		return true;
	}
}