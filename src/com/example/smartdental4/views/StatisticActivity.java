package com.example.smartdental4.views;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.smartdental4.R;
import com.example.smartdental4.controllers.StatisticController;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.BarLineChartBase.BorderPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.LargeValueFormatter;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.XLabels.XLabelPosition;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("SimpleDateFormat")
public class StatisticActivity extends Activity {

	LinearLayout layout;
	TextView title;
	StatisticController statistics;
	ImageView nodata;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistic);
		
		statistics = new StatisticController(StatisticActivity.this); ;
		
		layout = (LinearLayout) findViewById(R.id.Charts);
		title = (TextView) findViewById(R.id.Title);
		nodata = (ImageView) this.findViewById(R.id.NoData_Image);
		showMoodChart(this.getCurrentFocus());
	}
	
	private void removeView() {
        int count = layout.getChildCount();
        if (count > 0) {
            layout.removeViewAt(0);
        }
    }

	public void showPainChart(View target) {
			title.setText("疼痛指数");
			removeView();
			LineChart chart = new LineChart(this);
			
			chart.setDescription("");
	        chart.setNoDataTextDescription("缺乏数据");
			chart.setDrawYLabels(false);
			XLabels xLabels = chart.getXLabels();
		    xLabels.setPosition(XLabelPosition.BOTTOM);
		    xLabels.setTextColor(Color.BLACK);
		    xLabels.setTextSize(20f);
			chart.setDrawYValues(false);
			chart.setDrawLegend(false);
			chart.setDrawVerticalGrid(false);
			chart.setBorderPositions(new BorderPosition[] {
		            BorderPosition.BOTTOM
			});
			chart.setStartAtZero(true);
			chart.setDrawGridBackground(false);
	        chart.setGridColor(Color.WHITE & 0x70FFFFFF);
	        chart.setGridWidth(1.25f);
	        
	        List<Bundle> painIndexs = statistics.getPainData();
			
			int num = painIndexs.size();
			if(num > 0)
			{
				ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
				ArrayList<Entry> vals = new ArrayList<Entry>();
				ArrayList<String> xVals = new ArrayList<String>();
				
				
				int timescale = statistics.getBetweenDays(painIndexs.get(0).getString("date"), painIndexs.get(num-1).getString("date"));
				Calendar c = Calendar.getInstance();
				Date startDate = new Date();
				try {
					startDate = new SimpleDateFormat("yyyy-MM-dd").parse(painIndexs.get(0).getString("date"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				c.setTime(startDate);
		
				for (int i = 0; i <= timescale; i ++)
				{
					c.add(Calendar.DATE, 1);
					xVals.add(new SimpleDateFormat("MM-dd").format(c.getTime()));
				}
				
				for (int i = 0; i < num; i++)
				{
					vals.add(new Entry((float)painIndexs.get(i).getDouble("pain"), 
										statistics.getBetweenDays(painIndexs.get(0).getString("date"), painIndexs.get(i).getString("date"))));
				}
				
				LineDataSet set1 = new LineDataSet(vals, "");
				
				set1.setColor(Color.RED);
		        set1.setCircleColor(Color.DKGRAY);
		        set1.setLineWidth(5f);
		        set1.setCircleSize(7f);
		        set1.setFillAlpha(65);
		        
			    dataSets.add(set1);

			    LineData data = new LineData(xVals, dataSets);
				chart.setData(data);
			}
			layout.addView(chart);
			if(chart.isEmpty() || num < 2){ 
				nodata.setVisibility(View.VISIBLE); 
				chart.setVisibility(View.GONE);
			}
			else 
				nodata.setVisibility(View.GONE); 
	}
 
	public void showMoodChart(View target) {

		title.setText("心情指数");
		removeView();
		LineChart chart = new LineChart(this);
		
		chart.setDescription("");
        chart.setNoDataTextDescription("缺乏足够数据");
		chart.setDrawYLabels(false);
		XLabels xLabels = chart.getXLabels();
	    xLabels.setPosition(XLabelPosition.BOTTOM);
	    xLabels.setTextColor(Color.BLACK);
	    xLabels.setTextSize(20f);
		chart.setDrawYValues(false);
		chart.setDrawLegend(false);
		chart.setDrawVerticalGrid(false);
		chart.setBorderPositions(new BorderPosition[] {
	            BorderPosition.BOTTOM
	    });
		chart.setStartAtZero(true);
		chart.setDrawGridBackground(false);
        chart.setGridColor(Color.WHITE & 0x70FFFFFF);
        chart.setGridWidth(1.25f);
        
        List<Bundle> moodIndexs = statistics.getMoodData();
		
		int num = moodIndexs.size();
		if(num > 0)
		{
			ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
			ArrayList<Entry> vals = new ArrayList<Entry>();
			ArrayList<String> xVals = new ArrayList<String>();
			int timescale = statistics.getBetweenDays(moodIndexs.get(0).getString("date"), moodIndexs.get(num-1).getString("date"));
			Calendar c = Calendar.getInstance();
			Date startDate = new Date();
			try {
				startDate = new SimpleDateFormat("yyyy-MM-dd").parse(moodIndexs.get(0).getString("date"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			c.setTime(startDate);
	
			for (int i = 0; i <= timescale; i ++)
			{
				c.add(Calendar.DATE, 1);
				xVals.add(new SimpleDateFormat("MM-dd").format(c.getTime()));
			}
			
			for (int i = 0; i < num; i++)
			{
				vals.add(new Entry((float)moodIndexs.get(i).getDouble("pain"), 
									statistics.getBetweenDays(moodIndexs.get(0).getString("date"), moodIndexs.get(i).getString("date"))));
			}
			
			LineDataSet set1 = new LineDataSet(vals, "");
			
			set1.setColor(ColorTemplate.getHoloBlue());
	        set1.setCircleColor(ColorTemplate.getHoloBlue());
	        set1.setLineWidth(5f);
	        set1.setCircleSize(7f);
	        set1.setFillAlpha(65);
	        set1.setFillColor(ColorTemplate.getHoloBlue());
	        //set1.setHighLightColor(Color.rgb(244, 117, 117));
	        
		    dataSets.add(set1);
	
		    LineData data = new LineData(xVals, dataSets);
			chart.setData(data);
		}
		
		layout.addView(chart);
		if(chart.isEmpty() || num < 2){ 
			nodata.setVisibility(View.VISIBLE); 
			chart.setVisibility(View.GONE);
		}
		else 
			nodata.setVisibility(View.GONE); 
	}
	
	public void showStepChart(View target) {

		title.setText("阶段图");
		removeView();
		
		PieChart chart = new PieChart(this);
		
		chart.setDescription("");
		chart.setDrawLegend(false); 
		chart.setUsePercentValues(false);
		chart.setUnit("天");
		chart.setDrawUnitsInChart(true);
		chart.setValueFormatter(new LargeValueFormatter());
		
		List<Bundle> steps = statistics.getStageData();
		int num = steps.size();
		if(num>0)
		{
			ArrayList<PieDataSet> dataSets = new ArrayList<PieDataSet>();
			ArrayList<Entry> vals = new ArrayList<Entry>();
			ArrayList<String> xVals = new ArrayList<String>();
			
			String[]  ls = getResources().getStringArray(R.array.surgeryChoice);
			
			for (int i = 0; i < num; i++)
			{
				vals.add(new Entry(steps.get(i).getInt("days"), i));
			}
			for (int i = 0; i < num; i ++)
			{
				xVals.add(ls[steps.get(i).getInt("stage")]);
			}
		
			PieDataSet dataSet = new PieDataSet(vals, ""); 
			dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
			dataSets.add(dataSet);
			chart.setData(new PieData(xVals,dataSet));
		}
		layout.addView(chart);
		if(chart.isEmpty()){ 
			nodata.setVisibility(View.VISIBLE); 
			chart.setVisibility(View.GONE);
		}
		else 
			nodata.setVisibility(View.GONE);  
		
	}
	
	public void showCircleChart(View target) {
		title.setText("预约周期图");
		removeView();
		
		BarChart chart = new BarChart(this);
	
		chart.setDescription("");
		chart.setDrawXLabels(true);
		XLabels xlabel = chart.getXLabels();
		xlabel.setTextSize(25f);
		xlabel.setPosition(XLabelPosition.BOTTOM);
		xlabel.setAvoidFirstLastClipping(true);
		xlabel.setCenterXLabelText(true);
		chart.setUnit("天");
		chart.setDrawUnitsInChart(true);
		chart.setValueFormatter(new LargeValueFormatter());
		chart.setDrawYValues(true);
		chart.setDrawBarShadow(false);
	    chart.setDrawValuesForWholeStack(false);
		chart.setDrawYLabels(false);
	
		chart.setDrawLegend(false);
		chart.setBorderPositions(new BorderPosition[] {
	            BorderPosition.BOTTOM, BorderPosition.LEFT
	    });
		
		List<Integer> periods = statistics.getPeriodData();
		int num = periods.size();
		if(num > 0)
		{
			ArrayList<BarEntry> vals = new ArrayList<BarEntry>();
			ArrayList<String> xVals = new ArrayList<String>();
			
			for(int i = 0; i < num; i++)
			{
				vals.add(new BarEntry((float)periods.get(i), i));
				xVals.add(""+(i+1));
			}
	
			BarDataSet dataSet = new BarDataSet(vals, ""); 
			
			dataSet.setBarSpacePercent(30);
			dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
			
			chart.setData(new BarData(xVals,dataSet));
		}
		layout.addView(chart);
		if(chart.isEmpty() || num < 2){ 
			nodata.setVisibility(View.VISIBLE); 
			chart.setVisibility(View.GONE);
		}
		else 
			nodata.setVisibility(View.GONE); 
	}
	
	public void toStatisticView(View v){
		Intent statView = new Intent(this, StatisticActivity.class);
		startActivity(statView);
		finish();
	}
	
	public void toDiaryView(View v){
		Intent diaryView = new Intent();
		diaryView.setClass(StatisticActivity.this, DiaryActivity.class);
		startActivity(diaryView);
		finish();
	}
	public void toForumView(View v){
		Intent statView = new Intent(StatisticActivity.this, ForumActivity.class);
		startActivity(statView);
		finish();
	}
}
