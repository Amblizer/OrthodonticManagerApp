package com.example.smartdental4.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import com.example.smartdental4.models.DAOFactory;
import com.example.smartdental4.models.Diary;
import com.example.smartdental4.models.DiaryDAO;

public class StatisticController{
	private DiaryDAO diaryDAO = null;
	
	private List<Diary> diaries;
	
	public StatisticController(){
		;
	}
	
	public StatisticController(Context context){
		diaryDAO = DAOFactory.getDiaryDAO(context);
		diaryDAO.open();
		diaries = diaryDAO.getDiaries();
	}
	
	public List<Bundle> getPainData(){
		List<Bundle> bundles = new ArrayList<Bundle>();
		
		for (Diary diary : diaries)
			if (diary.getPainIndex() != 0){
				Bundle bundle = new Bundle();
				bundle.putCharSequence("date", diary.getDate());
				bundle.putDouble("pain", diary.getPainIndex() / 2.0);
				bundles.add(bundle);
			}
		
		return bundles;
	}
	
	public List<Bundle> getMoodData(){
		List<Bundle> bundles = new ArrayList<Bundle>();
		
		for (Diary diary : diaries)
			if (diary.getMoodIndex() != 0){
				Bundle bundle = new Bundle();
				bundle.putCharSequence("date", diary.getDate());
				bundle.putDouble("pain", diary.getMoodIndex() / 2.0);
				bundles.add(bundle);
			}
		
		return bundles;
	}
	
	public List<Bundle> getTeethData(){
		List<Bundle> bundles = new ArrayList<Bundle>();
		
		for (Diary diary : diaries)
			if (diary.getTeethIndex() != 0){
				Bundle bundle = new Bundle();
				bundle.putCharSequence("date", diary.getDate());
				bundle.putDouble("pain", diary.getTeethIndex() / 2.0);
				bundles.add(bundle);
			}
		
		return bundles;
	}
	
	public List<Bundle> getStageData(){
		List<Bundle> bundles = new ArrayList<Bundle>();
		int nowStage = 0;
		int nowI = -1;
		String lastDate = "";
		
		for (int i = 0; i < diaries.size(); i ++){
			int tempStage = diaries.get(i).getStage();
			if (diaries.get(i).getStage() > 0){
				if (nowStage == 0){
					nowStage = tempStage;
					nowI = i;
				}
				else{
					if (tempStage != nowStage){
						Bundle bundle = new Bundle();
						bundle.putInt("stage", nowStage); 
						bundle.putInt("days", getBetweenDays(diaries.get(nowI).getDate(), diaries.get(i).getDate()));
						bundles.add(bundle);
						nowStage = tempStage;
						nowI = i;
					}
				}
				lastDate = diaries.get(i).getDate();
			}
		}
		if (nowStage > 0){
			Bundle bundle = new Bundle();
			bundle.putInt("stage", nowStage);
			bundle.putInt("days", getBetweenDays(diaries.get(nowI).getDate(), lastDate) + 1);
			bundles.add(bundle);
		}
		
		return bundles;
	}
	
	public List<Integer> getPeriodData(){
		 List<Integer> periods = new ArrayList<Integer>();
		 int nowI = -1;
		 
		 for (int i = 0; i < diaries.size(); i ++)
			 if (diaries.get(i).isHasMeeting()){
				 if (nowI == -1)
					 nowI = i;
				 else{
					 periods.add(getBetweenDays(diaries.get(nowI).getDate(), diaries.get(i).getDate()));
					 nowI = i;
				 }
			 }
		 
		 return periods;
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
}