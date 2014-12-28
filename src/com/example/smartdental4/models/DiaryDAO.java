package com.example.smartdental4.models;

import java.util.List;

import android.database.SQLException;

public interface DiaryDAO {
	public List<Diary> getDiaries();

	public Diary getDiaryByDate(String date);

	public boolean changeHasMeeting(int _id, boolean hasMeeting);

	public boolean changeHasRemind(int _id, boolean hasRemind);

	public boolean changePainIndex(int _id, int painIndex);

	public boolean changeMoodIndex(int _id, int moodIndex);

	public boolean changeTeethIndex(int _id, int teethIndex);

	public boolean changeStage(int _id, int stage);

	public boolean changeDoctorAdvice(int _id, String doctorAdvice);

	public void open() throws SQLException;

	public void close();
}