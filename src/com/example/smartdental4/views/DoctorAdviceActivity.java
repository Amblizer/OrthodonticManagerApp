package com.example.smartdental4.views;

import com.example.smartdental4.R;
import com.example.smartdental4.R.id;
import com.example.smartdental4.R.layout;
import com.example.smartdental4.R.menu;
import com.example.smartdental4.controllers.DiaryController;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class DoctorAdviceActivity extends DiaryActivity {
	private EditText doctorAdvice;              //文本框
	private String doctorAdviceContent;			//医嘱内容
	Button buttonCertify; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		doctorAdviceContent="";
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctor_advice);
		
		doctorAdvice = (EditText)findViewById(R.id.doctorAdviceFull);   
		doctorAdvice.addTextChangedListener(doctorAdviceWatcher); 
		
		buttonCertify = (Button)findViewById(R.id.certify);  
		buttonCertify.setOnClickListener(certifyListener);
	}
	
	private TextWatcher doctorAdviceWatcher = new TextWatcher() {

		@Override
		public void afterTextChanged(Editable arg0) {
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {	
		}

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			doctorAdviceContent = doctorAdvice.getText().toString();
		}
    };  
    
    private OnClickListener certifyListener = new OnClickListener(){
		@Override
		public void onClick(View arg0) {
			diaryController.changeDoctorAdvice(doctorAdviceContent);
			Intent intent = new Intent();
    	    intent.setClass(DoctorAdviceActivity.this, DiaryActivity.class);
    	    startActivity(intent);
			finish();
		}  
    };
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.doctor_advice, menu);
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
