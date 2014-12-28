package com.example.smartdental4.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.smartdental4.models.DAOFactory;
import com.example.smartdental4.models.Diary;
import com.example.smartdental4.models.DiaryDAO;
import com.example.smartdental4.models.LocalDB;


public class DiaryController{
	
	private List<Diary> diaries = null;
	
	private DiaryDAO diaryDAO = null;
	
	private Diary diary = null;
	
	private int _id;
	
	private LocalDB localDB;
	
	public DiaryController(){
		;
	}
	
	public DiaryController(Context context){
		diaryDAO = DAOFactory.getDiaryDAO(context);
		diaryDAO.open();
		
		localDB = new LocalDB(context);
	}
	
	public void setDate(String date){
		diary = diaryDAO.getDiaryByDate(date);
		_id = diary.get_id();
		diaries = diaryDAO.getDiaries();
	}
	
	public int getMeetingDay(){
		int days = -1;
		for (Diary diary : diaries)
			if (diary.getDate().compareTo(this.diary.getDate()) >= 0)
				if (diary.isHasMeeting()){
					days = getBetweenDays(this.diary.getDate(), diary.getDate());
					break;
				}
		return days;
	}
	
	public boolean getHasMeeting(){
		return diary.isHasMeeting();
	}
	
	public boolean getHasRemind(){
		return diary.isHasRemind();
	}
	
	public int getPainIndex(){
		return diary.getPainIndex();
	}
	
	public int getMoodIndex(){
		return diary.getMoodIndex();
	}
	
	public int getTeethIndex(){
		return diary.getTeethIndex();
	}
	
	public int getStage(){
		int nowStage = diary.getStage();
		
		if (nowStage == 0){
			for (Diary diary : diaries)
				if (diary.getDate().compareTo(this.diary.getDate()) < 0)
					if (diary.getStage() > 0){
						nowStage = diary.getStage();
						//this.changeStage(diary.getStage());
						//this.diary.setStage(diary.getStage());
					}
		}
				
		return nowStage;
	}
	
	public String getDoctorAdvice(){
		if (diary.getDoctorAdvice().equals("")){
			for (Diary diary : diaries)
				if (diary.getDate().compareTo(this.diary.getDate()) < 0)
					if (!diary.getDoctorAdvice().equals("")){
						this.changeDoctorAdvice(diary.getDoctorAdvice());
						this.diary.setDoctorAdvice(diary.getDoctorAdvice());
					}
		}
	
		return diary.getDoctorAdvice();
	}
	
	public boolean changeHasMeeting(boolean hasMeeting){
		this.diary.setHasMeeting(hasMeeting);
		return diaryDAO.changeHasMeeting(_id, hasMeeting);
	}
	
	public boolean changeHasRemind(boolean hasRemind){
		this.diary.setHasMeeting(hasRemind);
		return diaryDAO.changeHasRemind(_id, hasRemind);
	}
	
	public boolean changePainIndex(int painIndex){
		this.diary.setPainIndex(painIndex);
		return diaryDAO.changePainIndex(_id, painIndex);
	}
	
	public boolean changeMoodIndex(int moodIndex){
		this.diary.setMoodIndex(moodIndex);
		return diaryDAO.changeMoodIndex(_id, moodIndex);
	}
	
	public boolean changeTeethIndex(int teethIndex){
		this.diary.setTeethIndex(teethIndex);
		return diaryDAO.changeTeethIndex(_id, teethIndex);
	}
	
	public boolean changeStage(int stage){
		this.diary.setStage(stage);
		return diaryDAO.changeStage(_id, stage);
	}
	
	public boolean changeDoctorAdvice(String doctorAdvice){
		this.diary.setDoctorAdvice(doctorAdvice);
		return diaryDAO.changeDoctorAdvice(_id, doctorAdvice);
	}
	
	@SuppressLint("SimpleDateFormat")
	public int getBetweenDays(String dateString1, String dateString2) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		Date date1 = null;
		Date date2 = null;
		
		try {
			date1 = simpleDateFormat.parse(dateString1);
			date2 = simpleDateFormat.parse(dateString2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		calendar1.setTime(date1);
		calendar2.setTime(date2);
		int betweenYears = calendar2.get(Calendar.YEAR) - calendar1.get(Calendar.YEAR);
		int betweenDays = calendar2.get(Calendar.DAY_OF_YEAR) - calendar1.get(Calendar.DAY_OF_YEAR);
		for (int i = 0; i < betweenYears; i ++){
			calendar1.set(Calendar.YEAR, calendar1.get(Calendar.YEAR) + 1);
			betweenDays += calendar1.getMaximum(Calendar.DAY_OF_YEAR);
		}
		
		return betweenDays;
	}
	
	public long getAlarmIdInDB(){
		return localDB.findIdByDate(this.diary.getDate());
	}
	
	public void deleteAlarmIdInDB(){
		localDB.delete(this.diary.getDate());
	}
}