package com.example.smartdental4.views;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;

import com.example.smartdental4.views.ForumActivity;
import com.example.smartdental4.views.DiaryActivity;
import com.example.smartdental4.R;
import com.example.smartdental4.controllers.DiaryController;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;

@SuppressLint("SimpleDateFormat")
public class DiaryActivity extends Activity {
	
	String currentDate = null;
	ArrayList<String> stageList=new ArrayList<String>();
	ArrayAdapter<String> spinnerAdapter;
	Spinner spinner;					//下拉菜单
	RatingBar moodRatingBar;			//心情指数rating bar
	RatingBar painRatingBar;			//牙痛指数rating bar
	RatingBar teethHealthRatingBar;		//牙齿颜色指数rating bar
	CheckBox appointment;				//当天是否预约
	Switch alarm;						//预约是否提醒
	TextView doctorAdviceShow;          //医嘱显示
	DiaryController diaryController;    //日期控制类
	String doctorAdviceContent;         //医嘱内容
	TextView remind;                    //提醒文字
	TextView alarmEdit;                 //提醒编辑
	ScrollView dateScrollView;          //滚动视图
	GestureDetector detector;           //手势控制
	RelativeLayout DateLayout;          //日期视图layout
	int radius = 100;                   //按钮宽度
	int layoutHeight = 350;             //日期视图layout高度
	int backgroundNumber = 0;           //日期视图背景编号
	boolean setViewNow;
	static final int[] BackgroundArray =//日期视图背景
	{ 
		R.drawable.bg0,
		R.drawable.bg1,
		R.drawable.bg2,
		R.drawable.bg3,
		R.drawable.bg4
	};
	static final String[] monthName =   //月份名称
	{    
		"Jan. ", 
		"Feb. ",
		"Mar. ",
		"Apr. ",
		"May. ",
		"Jun. ",
		"Jul. ",
		"Aug. ", 
		"Sep. ",
		"Oct. ",
		"Nov. ",
		"Dec. "
	};
	
	private class GestureListener extends SimpleOnGestureListener{
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			int y = dateScrollView.getScrollY();
			layoutHeight = DateLayout.getHeight();
			RelativeLayout ll = (RelativeLayout) findViewById(R.id.date_btns_wrapper);
			int delta = ll.getHeight();
			if(e1.getY() + y > delta && e1.getY() + y < layoutHeight+delta)
			{
				if(e1.getX()-e2.getX() > 50)
					setLeftScroll();
				if(e2.getX()-e1.getX() > 50)
					setRightScroll();
			}
			return super.onFling(e1, e2, velocityX, velocityY);
		}
	}
	
	/**
	 * 当从右往左滚动的时候
	 */
	public void setLeftScroll(){
		AnimationSet animationSet = new AnimationSet(true);
		TranslateAnimation translateAnimation = new TranslateAnimation(
				                     Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT,
				                     -0.2f, Animation.ABSOLUTE, 0f,
				                     Animation.ABSOLUTE, 0f);
		//设置X轴的平移方式和值，设置平移到的X轴的平移方式和值，Y轴的平移方式和值，设置平移到的Y轴的平移方式和值
        translateAnimation.setDuration(300);
        animationSet.setFillAfter(true);
        animationSet.setFillBefore(false);
        animationSet.addAnimation(translateAnimation);
		Button ib0 = (Button)findViewById(0); //获取第零个按钮       
		Button ib1 = (Button)findViewById(1);//获取第一个按钮	
		Button ib2 = (Button)findViewById(2);//获取第二个按钮		
		Button ib3 = (Button)findViewById(3);//获取第三个按钮		
		Button ib4 = (Button)findViewById(4);//获取第四个按钮		
		Button ib5 = (Button)findViewById(5);//获取第五个按钮
		Button ib6 = (Button)findViewById(6);//获取第六个按钮	
		ib0.startAnimation(animationSet);
		ib1.startAnimation(animationSet);
		ib2.startAnimation(animationSet);
		ib3.startAnimation(animationSet);
		ib4.startAnimation(animationSet);
		ib5.startAnimation(animationSet);
		ib6.startAnimation(animationSet);
		backgroundNumber = (backgroundNumber + 1) % 5;
		updateDate(1);
		currentDate = getDayAfter(currentDate);
		updateView();
	}
	
	/**
	 * 当从左往右滚动的时候
	 */
	public void setRightScroll(){
		AnimationSet animationSet = new AnimationSet(true);
		TranslateAnimation translateAnimation = new TranslateAnimation(
				                     Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT,
				                     0.2f, Animation.ABSOLUTE, 0f,
				                     Animation.ABSOLUTE, 0f);
		//设置X轴的平移方式和值，设置平移到的X轴的平移方式和值，Y轴的平移方式和值，设置平移到的Y轴的平移方式和值
        translateAnimation.setDuration(300);
        animationSet.setFillAfter(true);
        animationSet.setFillBefore(false);
        animationSet.addAnimation(translateAnimation);
		Button ib0 = (Button)findViewById(0); //获取第零个按钮
		Button ib1 = (Button)findViewById(1);//获取第一个按钮
		Button ib2 = (Button)findViewById(2);//获取第二个按钮		
		Button ib3 = (Button)findViewById(3);//获取第三个按钮
		Button ib4 = (Button)findViewById(4);//获取第四个按钮
		Button ib5 = (Button)findViewById(5);//获取第五个按钮
		Button ib6 = (Button)findViewById(6);//获取第六个按钮
		ib0.startAnimation(animationSet);
		ib1.startAnimation(animationSet);
		ib2.startAnimation(animationSet);
		ib3.startAnimation(animationSet);
		ib4.startAnimation(animationSet);
		ib5.startAnimation(animationSet);
		ib6.startAnimation(animationSet);
		backgroundNumber = (backgroundNumber + 4) % 5;
		updateDate(2);
		currentDate = getDayBefore(currentDate);
		updateView();
	}
	
	public boolean dispatchTouchEvent(MotionEvent ev) {
		onTouchEvent(ev);
		return super.dispatchTouchEvent(ev);
	};
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return detector.onTouchEvent(event);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_diary);
		
		diaryController = new DiaryController(getApplication());
		
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		currentDate = dateFormat.format(date);
		
		//记录按钮
		appointment = (CheckBox) findViewById(R.id.appointment);
		appointment.setOnCheckedChangeListener(appointmentListener);
		
		//提醒
		alarmEdit = (TextView)findViewById(R.id.AlarmEdit);
		alarmEdit.setOnClickListener(alarmEditListerner);
		alarmEdit.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		
		//是否提醒
		alarm = (Switch) findViewById(R.id.alarm);
		alarm.setOnCheckedChangeListener(alarmListener);
		
		//疗程管理下拉菜单
		String[]  ls = getResources().getStringArray(R.array.surgeryChoice);
		for(int i=0;i<ls.length;i++){
			stageList.add(ls[i]);
        }
		
		spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, stageList);
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner = (Spinner)findViewById(R.id.SurgeryChoiceRecording);
		spinner.setOnItemSelectedListener(stageListener);
		
		//设置下拉菜单风格
		spinner.setAdapter(spinnerAdapter);
		spinner.setPrompt("治疗阶段");
		
		//rating bars
		moodRatingBar = (RatingBar) findViewById(R.id.moodRecording);  
		moodRatingBar.setOnRatingBarChangeListener(moodRecordingListener);
		painRatingBar = (RatingBar) findViewById(R.id.painStageRecording);  
		painRatingBar.setOnRatingBarChangeListener(painRecordingListener);
		teethHealthRatingBar = (RatingBar) findViewById(R.id.teethHealthRecording);  
		teethHealthRatingBar.setOnRatingBarChangeListener(teethHealthRecordingListener);
		
		//文本框
		doctorAdviceShow = (TextView)findViewById(R.id.doctorAdvice);   
		//doctorAdvice.addTextChangedListener(doctorAdviceWatcher); 
		
		remind = (TextView)findViewById(R.id.remind1);
		
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		int width = metric.widthPixels;     // 屏幕宽度（像素）		
		RelativeLayout ll = (RelativeLayout) findViewById(R.id.date_btns_wrapper);
		int w = width;
		radius = w / 5;
	    for(int i = 0 ; i < 7 ; i++){
	    	LayoutParams pa = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	        Button ib = new Button(this);
	        switch(i){
	        	case 0:
	        		pa.setMargins(w/2-radius/2-radius*3, 0, 0, 0);
	        		ib.setBackgroundResource(R.drawable.lbtn);
	        		ib.setWidth(radius);
	        		ib.setHeight(radius);
	        		ib.setTextColor(255);
	                ll.addView(ib, pa);
	        		break;
	        	case 1:
	        		pa.setMargins(w/2-radius/2-radius*2, 0, 0, 0);
	        		ib.setBackgroundResource(R.drawable.lbtn);
	        		ib.setWidth(radius);
	        		ib.setHeight(radius);
	                ll.addView(ib, pa);
	        		break;
	        	case 2:
	        		pa.setMargins(w/2-radius/2-radius, 0, 0, 0);
	        		ib.setBackgroundResource(R.drawable.lbtn);
	        		ib.setWidth(radius);
	        		ib.setHeight(radius);
	                ll.addView(ib, pa);
	        		break;
	        	case 3:
	        		pa.setMargins(w/2-radius/2, 0, 0, 0);
	        		ib.setBackgroundResource(R.drawable.lbtn);
	        		ib.setWidth(radius);
	        		ib.setHeight(radius);
	        		ll.addView(ib, pa);
	        		break;
	        	case 4:
	        		pa.setMargins(w/2+radius/2, 0, 0, 0);
	        		ib.setBackgroundResource(R.drawable.lbtn);
	        		ib.setWidth(radius);
	        		ib.setHeight(radius);
	        		ll.addView(ib, pa);
	        		break;
	        	case 5:
	        		pa.setMargins(w/2+radius/2+radius, 0, 0, 0);
	        		ib.setBackgroundResource(R.drawable.lbtn);
	        		ib.setWidth(radius);
	        		ib.setHeight(radius);
	        		ll.addView(ib, pa);
	        		break;
	        	case 6:
	                pa.setMargins(w/2+radius/2+radius*2, 0, -150, 0);
	                ib.setBackgroundResource(R.drawable.lbtn);
	                ib.setWidth(radius);
	        		ib.setHeight(radius);
	                ll.addView(ib, pa);
	        		break;
	        }
	        ib.setId(i);
	    }
		updateDate(0);
		detector = new GestureDetector(this, new GestureListener());
		dateScrollView = (ScrollView)findViewById(R.id.scrollView1); 
		updateView();
	}
	
	//stage spinner 监听函数
	OnItemSelectedListener stageListener=  new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			if (setViewNow)
				return;
			int pos = 0;
			String[]  ls = getResources().getStringArray(R.array.surgeryChoice);
			String result = parent.getItemAtPosition(position).toString();
			for (int i = 0; i < ls.length; i++){
				if (result.equals(ls[i])){
					pos = i;
				}
			}
			diaryController.changeStage(pos);
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
		}
    };
    
	//是否预约选框监听函数
	CompoundButton.OnCheckedChangeListener appointmentListener = new CompoundButton.OnCheckedChangeListener(){ 
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (setViewNow)
				return;
			
        	diaryController.changeHasMeeting(isChecked);
        	if (isChecked)
        		remind.setText("今天就有预约");
        	else {
        		setRemind();
			}
        } 
    };
    
    android.view.View.OnClickListener alarmEditListerner = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (setViewNow)
				return;

			Intent intent = new Intent(DiaryActivity.this, SetClockActivity.class);
			intent.putExtra("id", diaryController.getAlarmIdInDB());
			intent.putExtra("date", DiaryActivity.this.currentDate);
			startActivityForResult(intent, 0);
		}
	};

	//是否提醒监听函数
    CompoundButton.OnCheckedChangeListener alarmListener = new CompoundButton.OnCheckedChangeListener(){ 
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (setViewNow)
				return;
			
        	//Log.d("isChecked", isChecked + "");
            
            if (isChecked){
            	Intent intent = new Intent(DiaryActivity.this, SetClockActivity.class);
            	intent.putExtra("id", -1l);
            	intent.putExtra("date", DiaryActivity.this.currentDate);
            	startActivityForResult(intent, 0);
            }
            else{
            	alarmEdit.setVisibility(View.INVISIBLE);
            	diaryController.deleteAlarmIdInDB();
            	diaryController.changeHasRemind(false);
            }
        } 
    }; 
	
	//心情指数rating bar监听函数
	RatingBar.OnRatingBarChangeListener moodRecordingListener = new RatingBar.OnRatingBarChangeListener() {
		
	    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
			if (setViewNow)
				return;
			
	        diaryController.changeMoodIndex((int)(2 * rating));
	    }
	};
	
	//牙痛指数rating bar监听函数
	RatingBar.OnRatingBarChangeListener painRecordingListener = new RatingBar.OnRatingBarChangeListener() {
		
	    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
			if (setViewNow)
				return;
			
	        diaryController.changePainIndex((int)(2 * rating));
	    }
	};
	
	//牙康指数rating bar监听函数
	RatingBar.OnRatingBarChangeListener teethHealthRecordingListener = new RatingBar.OnRatingBarChangeListener() {
		
	    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
			if (setViewNow)
				return;
			
	        diaryController.changeTeethIndex((int)(2 * rating));
	    }
	};
	
    //获取前一天
	public static String getDayBefore(String specifiedDay) {//鍙互鐢╪ew Date().toLocalString()浼犻�鍙傛暟
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);
        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
                .getTime());
        return dayBefore;
    }

    //获取下一天
    public static String getDayAfter(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);
        String dayAfter = new SimpleDateFormat("yyyy-MM-dd")
                .format(c.getTime());
        return dayAfter;
    }
    
    //更新按钮点击事件
    protected void updateButtonClickEvent(int mode){
    	Button b1,b2,b3,b4,b5;
    	b1 = (Button)findViewById(1);
		b2 = (Button)findViewById(2);
		b3 = (Button)findViewById(3);
		b4 = (Button)findViewById(4);
		b5 = (Button)findViewById(5);
		b1.setOnClickListener(prevTwoDateOnClickListener);  
		b2.setOnClickListener(prevDateOnClickListener);  
		b3.setOnClickListener(null);
		b4.setOnClickListener(nextDateOnClickListener);  
		b5.setOnClickListener(nextTwoDateOnClickListener);  
    }
    
    // 日期视图重渲染
    // mode 0:正常更新 mode 1:←滑动更新 mode 2:→滑动更新
    protected void updateDate(int mode){ 
       	Button b0, b1,b2,b3,b4,b5,b6;
       	b0 = (Button)findViewById(0);
    	b1 = (Button)findViewById(1);
		b2 = (Button)findViewById(2);
		b3 = (Button)findViewById(3);
		b4 = (Button)findViewById(4);
		b5 = (Button)findViewById(5);
		b6 = (Button)findViewById(6);
		String s;
		int month;
		
		s = getDayBefore(getDayBefore(getDayBefore(currentDate)));
		s = s.substring(s.lastIndexOf('-')+1, s.length());
		b0.setText(s);
		b0.setTextColor(Color.rgb(255, 255, 255));
		
		s = getDayBefore(getDayBefore(currentDate));
		s = s.substring(s.lastIndexOf('-')+1, s.length());
		b1.setText(s);
		b1.setTextColor(Color.rgb(255, 255, 255));
		
		s = getDayBefore(currentDate);
		month = Integer.valueOf(s.substring(s.indexOf('-')+1, s.lastIndexOf('-'))).intValue() - 1;
		s = s.substring(s.lastIndexOf('-')+1, s.length());
		if (mode == 2){
			b2.setText(monthName[month]+s);
			b2.setBackgroundResource(R.drawable.btm);
		}
		else{
			b2.setText(s);
			b2.setBackgroundResource(R.drawable.lbtn);
		}
		b2.setTextColor(Color.rgb(255, 255, 255));
		
		s = currentDate;
		month = Integer.valueOf(s.substring(s.indexOf('-')+1, s.lastIndexOf('-'))).intValue() - 1;
		s = s.substring(s.lastIndexOf('-')+1, s.length());
		if (mode == 0){
			b3.setText(monthName[month]+s);
			b3.setBackgroundResource(R.drawable.btm);
		}
		else
		{
			b3.setText(s);
			b3.setBackgroundResource(R.drawable.lbtn);
		}
		b3.setTextColor(Color.rgb(255, 255, 255));
		
		s = getDayAfter(currentDate);
		month = Integer.valueOf(s.substring(s.indexOf('-')+1, s.lastIndexOf('-'))).intValue() - 1;
		s = s.substring(s.lastIndexOf('-')+1, s.length());
		if (mode == 1){
			b4.setText(monthName[month]+s);
			b4.setBackgroundResource(R.drawable.btm);
		}
		else{
			b4.setText(s);
			b4.setBackgroundResource(R.drawable.lbtn);
		}
		b4.setTextColor(Color.rgb(255, 255, 255));
		
		s = getDayAfter(getDayAfter(currentDate));
		s = s.substring(s.lastIndexOf('-')+1, s.length());
		b5.setText(s);
		b5.setTextColor(Color.rgb(255, 255, 255));
		
		s = getDayAfter(getDayAfter(getDayAfter(currentDate)));
		s = s.substring(s.lastIndexOf('-')+1, s.length());
		b6.setText(s);
		b6.setTextColor(Color.rgb(255, 255, 255));
		
		DateLayout = (RelativeLayout) findViewById(R.id.relativeLayout1);
		DateLayout.setBackgroundResource(BackgroundArray[backgroundNumber]);
		updateButtonClickEvent(mode);
    }
    
    private void setRemind(){
    	int days = diaryController.getMeetingDay();
    	if (days == -1)
    		remind.setText("暂无预约");
    	else if (days == 0)
    		remind.setText("今天就有预约");
    	else
    		remind.setText("距离下次预约还有" + days + "天");
    }
    
    protected void updateView() {
    	diaryController.setDate(currentDate);
    	
    	setRemind();

    	setViewNow = true;
    	
    	moodRatingBar.setRating((float)(diaryController.getMoodIndex() / 2.0));
    	painRatingBar.setRating((float)(diaryController.getPainIndex() / 2.0));
    	teethHealthRatingBar.setRating((float)(diaryController.getTeethIndex() / 2.0));
    	doctorAdviceShow.setText(diaryController.getDoctorAdvice());
		String[]  ls = getResources().getStringArray(R.array.surgeryChoice);
		ArrayList<String> stageList1=new ArrayList<String>();
		int startStage = diaryController.getStage();
		for(int i= startStage; i < ls.length; i++){
			stageList1.add(ls[i]);
        }
		spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, stageList1);
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner = (Spinner)findViewById(R.id.SurgeryChoiceRecording);
		spinner.setOnItemSelectedListener(stageListener);
		
		//设置下拉菜单风格
		spinner.setAdapter(spinnerAdapter);
		spinner.setPrompt("治疗阶段");
    	spinner.setSelection(0);
    	appointment.setChecked(diaryController.getHasMeeting());
    	alarm.setChecked(diaryController.getHasRemind());
    	if (diaryController.getHasRemind()){
    		alarmEdit.setVisibility(View.VISIBLE);
    	}
    	else{
    		alarmEdit.setVisibility(View.INVISIBLE);
    	}
    	
    	setViewNow = false;
	}
	
    private OnClickListener prevDateOnClickListener = new OnClickListener() {  
        public void onClick(View v) {  
        	setRightScroll();
        }         
    };  
    
    private OnClickListener prevTwoDateOnClickListener = new OnClickListener() {  
        public void onClick(View v) {  
        	setRightScroll();
    		setRightScroll();
        }         
    };  
    
    private OnClickListener nextDateOnClickListener = new OnClickListener() {  
        public void onClick(View v) {  
        	setLeftScroll();
        }         
    };  
    
    private OnClickListener nextTwoDateOnClickListener = new OnClickListener() {  
        public void onClick(View v) {  
        	setLeftScroll();
        	setLeftScroll();
        }         
    };  
	
	public void toStatisticView(View v){
		Intent statView = new Intent(DiaryActivity.this, StatisticActivity.class);
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
		Intent statView = new Intent(DiaryActivity.this, ForumActivity.class);
		startActivity(statView);
		finish();
	}
	
	public void toEditAdviceView(View v){
		Intent intent = new Intent();
	    intent.setClass(DiaryActivity.this, DoctorAdviceActivity.class);
	    startActivity(intent);
	    finish();
	}
	
	
	public void toEditShareView(View v){
		Intent intent = new Intent();
	    intent.setClass(DiaryActivity.this, ShareActivity.class);
	    Bundle bundle = new Bundle();
        bundle.putString("username", "");
        bundle.putString("shareContent", "心情指数："+moodRatingBar.getRating()+"星      " +
        								 "疼痛指数："+painRatingBar.getRating()+"星       " + 
        		                         "牙齿健康程度："+teethHealthRatingBar.getRating()+"星     ");
        
        intent.putExtra("bundle", bundle);
	    startActivity(intent);
	    finish();
	}
	
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
    	
    	//Log.d("result", resultCode + "");
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == 1){
        	diaryController.changeHasRemind(true);
        	updateView();
		}
		else
			if (resultCode == 2){
				setViewNow = true;
				alarm.setChecked(false);
				setViewNow = false;
			}
	}
    
    @Override  
    public boolean onKeyDown(int keyCode, KeyEvent event)  
    {  
        if (keyCode == KeyEvent.KEYCODE_BACK )  
        {  
            // 创建退出对话框  
            AlertDialog isExit = new AlertDialog.Builder(this).create();  
            // 设置对话框标题  
            isExit.setTitle("系统提示");  
            // 设置对话框消息  
            isExit.setMessage("确定要退出吗"); 
            // 添加选择按钮并注册监听  
            isExit.setButton(AlertDialog.BUTTON_POSITIVE, "确定", QuitListener);
            isExit.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", QuitListener);  
            isExit.show();  
        }  
        return false;  
          
    }  
    
    DialogInterface.OnClickListener QuitListener = new DialogInterface.OnClickListener()  
    {  
        public void onClick(DialogInterface dialog, int which)  
        {  
            switch (which)  
            {  
            case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序  
                finish();  
                break;  
            case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框  
                break;  
            default:  
                break;  
            }  
        }  
    };    

}
