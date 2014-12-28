package com.example.smartdental4.models;
import android.content.Context;

public class DAOFactory{
	public static DiaryDAOImpl getDiaryDAO(Context context){
		return new DiaryDAOImpl(context);
	}
}
