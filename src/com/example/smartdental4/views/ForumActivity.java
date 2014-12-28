package com.example.smartdental4.views;

import com.example.smartdental4.views.ForumActivity;
import com.example.smartdental4.views.DiaryActivity;
import com.example.smartdental4.views.StatisticActivity;
import com.example.smartdental4.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

@SuppressLint("SetJavaScriptEnabled")
public class ForumActivity extends Activity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forum);
		
		WebView webview = (WebView) findViewById(R.id.forum_webView);
		webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDatabaseEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            webview.getSettings().setDatabasePath("/data/data/" + webview.getContext().getPackageName() + "/databases/");
        }    
        webview.loadUrl("file:///android_asset/index.html");  
//		setContentView(webview);
		
	}
	public void toStatisticView(View v){
		Intent statView = new Intent(ForumActivity.this, StatisticActivity.class);
		startActivity(statView);
		finish();
	}
	
	public void toDiaryView(View v){
		Intent diaryView = new Intent();
		diaryView.setClass(this, DiaryActivity.class);
		startActivity(diaryView);
		finish();
	}
	public void toForumView(View v){
		Intent statView = new Intent(ForumActivity.this, ForumActivity.class);
		startActivity(statView);
		finish();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.forum, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
