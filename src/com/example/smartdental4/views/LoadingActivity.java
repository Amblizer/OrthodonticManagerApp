package com.example.smartdental4.views;

import java.util.Timer;
import com.example.smartdental4.R;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class LoadingActivity extends Activity {
	Timer timer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		
		new AsyncTask<Void, Void, Integer>() {
			 
            @Override
            protected Integer doInBackground(Void... params) {
            	try {
        			Thread.sleep(2000);
        		} catch (InterruptedException e) {
        			
        			e.printStackTrace();
        		}
                return 1;
            }
 
            @Override
            protected void onPostExecute(Integer result) {
            	Intent intent = new Intent();
        	    intent.setClass(LoadingActivity.this, DiaryActivity.class);
        	    startActivity(intent);
        	    finish();
        	    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            };
        }.execute(new Void[]{});
	}
}