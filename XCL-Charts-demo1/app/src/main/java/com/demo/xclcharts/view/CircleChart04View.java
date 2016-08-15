package com.demo.xclcharts.view;

import java.util.LinkedList;

import org.xclcharts.chart.CircleChart;
import org.xclcharts.chart.PieData;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;


/**
 * @ClassName CircleChart04View
 * @Description  图形图例子
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 */
public class CircleChart04View extends DemoView {
	
	private String TAG = "CircleChart04View";
	private CircleChart chart = new CircleChart();
	
	//设置图表数据源
	private LinkedList<PieData> mlPieData = new LinkedList<PieData>();	
	private String mDataInfo = "";

	public CircleChart04View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setPercentage(80);
		chartRender();
	}
	
	public CircleChart04View(Context context, AttributeSet attrs){   
        super(context, attrs);   
        setPercentage(80);
		chartRender();
	 }
	 
	 public CircleChart04View(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			setPercentage(80);
			chartRender();
	 }
	
	 
	 @Override  
	    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
	        super.onSizeChanged(w, h, oldw, oldh);  
	       //图所占范围大小
	        chart.setChartRange(w,h);
	    }  
	 
	 
	public void chartRender()
	{
		try {	
		
			//设置信息			
			chart.setAttributeInfo(mDataInfo); 	
			//数据源
			chart.setDataSource(mlPieData);
			
			//背景色
			chart.getBgCirclePaint().setColor(Color.rgb(117, 197, 141));
			//深色
			chart.getFillCirclePaint().setColor(Color.rgb(77, 180, 123));
			//信息颜色
			chart.getDataInfoPaint().setColor(Color.rgb(243, 75, 125));
			
			//chart.hideInnerFill();
			
			chart.setORadius(0.7f);
			chart.setIRadius(0.7f);
			
			chart.getDataInfoPaint().setTextSize(28);
			
			//显示箭头
			chart.ShowCap();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
	}
	
	//百分比
	public void setPercentage(int per)
	{					
		//PieData(标签，百分比，在饼图中对应的颜色)
		mlPieData.clear();	
		int color = Color.rgb(72, 201, 176);
		
		mDataInfo = "研究进行中......";
		color = Color.rgb(243, 75, 125);		
		mlPieData.add(new PieData("Golang GO!",per,color));		
			
	}

	@Override
    public void render(Canvas canvas) {
        try{
            chart.render(canvas);
        } catch (Exception e){
        	Log.e(TAG, e.toString());
        }
    }

}
