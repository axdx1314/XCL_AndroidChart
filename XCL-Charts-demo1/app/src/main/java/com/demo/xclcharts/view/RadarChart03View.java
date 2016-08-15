/**
 * Copyright 2014  XCL-Charts
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 	
 * @Project XCL-Charts 
 * @Description Android图表基类库演示
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * @license http://www.apache.org/licenses/  Apache v2 License
 * @version 1.3
 */
package com.demo.xclcharts.view;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.xclcharts.chart.PieData;
import org.xclcharts.chart.RoseChart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;


/**
 * @ClassName RadarChart03View
 * @Description  玫瑰风向图(wind rose)的例子
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 */

public class RadarChart03View extends DemoView {

	private String TAG = "RadarChart03View";	
	private RoseChart chartRose = new RoseChart();	
	LinkedList<PieData> roseData = new LinkedList<PieData>();
	
	private RoseChart chartRose3 = new RoseChart();	
	LinkedList<PieData> roseData3 = new LinkedList<PieData>();
	
	private RoseChart chartRose1 = new RoseChart();	
	LinkedList<PieData> roseData1 = new LinkedList<PieData>();	
	
	//标签集合
	private List<String> labels = new LinkedList<String>();
	
	//private RadarChart chart = new RadarChart();
//	private List<RadarData> chartData = new LinkedList<RadarData>();
	
	public RadarChart03View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		 initView();
	}
	
	public RadarChart03View(Context context, AttributeSet attrs){   
        super(context, attrs);   
        initView();
	 }
	 
	 public RadarChart03View(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			initView();
	 }
	 
	 private void initView()
	 {
		 	chartLabels();
		 				
			chartRender1();
			chartDataSet1();
			
			chartRender2();
			chartDataSet2();
			
			chartRender3();
			chartDataSet3();
			
			//綁定手势滑动事件
			this.bindTouch(this,chartRose);
			this.bindTouch(this,chartRose3);
			this.bindTouch(this,chartRose1);
	 }
	 	 
	
	@Override  
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
        super.onSizeChanged(w, h, oldw, oldh);  
       //图所占范围大小
        chartRose.setChartRange(w,h);
        chartRose3.setChartRange(w,h);
        chartRose1.setChartRange(w,h);
    }  	
			
	private void chartLabels()
	{
		labels.add("A1");
		labels.add("A2");
		labels.add("A3");
		labels.add("A4");
		labels.add("A5");
		labels.add("A6");
		labels.add("A7");
		labels.add("A8");
		
	}
	
	
	private void chartRender2()
	{
		try {						
			
			//设置绘图区默认缩进px值
			int [] ltrb = getPieDefaultSpadding();
			chartRose.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);	
			//数据源
			chartRose.setDataSource(roseData);							
			chartRose.getInnerPaint().setStyle(Style.STROKE);			
			chartRose.setInitialAngle(270 + 72/2);
			
			//设置标签显示位置,当前设置标签显示在扇区中间
			//chartRose.setLabelStyle(XEnum.SliceLabelStyle.OUTSIDE);
			
			chartRose.setIntervalAngle(3);
			
			//--------------------------------------
			Map<Float,Integer>  mapBgSeg = new HashMap<Float,Integer>();
			mapBgSeg.put(0.8f, Color.rgb(39, 161, 237));  
			mapBgSeg.put(0.6f, Color.rgb(246, 137, 31));  
			chartRose.showBgCircle(mapBgSeg);
			
			chartRose.showBgLines(Color.BLUE);
			//chartRose.setLabelStyle(XEnum.SliceLabelStyle.OUTSIDE);
			
			chartRose.showOuterLabels();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
	}
	
	
	private void chartDataSet3()
	{
		//设置图表数据源
							
		//PieData(标签，百分比，在饼图中对应的颜色)
		
		roseData.add(new PieData("",40,Color.rgb(149, 206, 255)));  //(int)Color.rgb(39, 51, 72)));		
		roseData.add(new PieData("",50,Color.rgb(149, 206, 255)));  //(int)Color.rgb(39, 51, 72)));
		roseData.add(new PieData("",60,Color.rgb(149, 206, 255)));  //(int)Color.rgb(39, 51, 72)));
				
		roseData.add(new PieData("",70,Color.rgb(149, 206, 255)));  //(int)Color.rgb(77, 83, 97) ));
		roseData.add(new PieData(""	,80,Color.rgb(149, 206, 255)));  //(int)Color.rgb(148, 159, 181)));
		roseData.add(new PieData(""	,90,Color.rgb(149, 206, 255)));  //(int)Color.rgb(253, 180, 90)));
		roseData.add(new PieData(""	,95,Color.rgb(149, 206, 255)));  //(int)Color.rgb(52, 194, 188)));
		roseData.add(new PieData("",97,Color.rgb(149, 206, 255)));  //(int)Color.rgb(39, 51, 72)));		
	}
	
	private void chartRender3()
	{
		try {						
			
			//设置绘图区默认缩进px值
			int [] ltrb = getPieDefaultSpadding();
			chartRose3.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);	
			//数据源
			chartRose3.setDataSource(roseData3);							
			chartRose3.getInnerPaint().setStyle(Style.STROKE);			
			chartRose3.setInitialAngle(270 + 72/2);
			
			//设置标签显示位置,当前设置标签显示在扇区中间
			//chartRose3.setLabelStyle(XEnum.SliceLabelStyle.INSIDE);
			
			chartRose3.getLabelPaint().setColor(Color.parseColor("#D92222"));
			
			chartRose3.setIntervalAngle(3);
			//chartRose3.setLabelStyle(XEnum.SliceLabelStyle.OUTSIDE);
			
			chartRose3.showOuterLabels();
						
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
	}
	
	
	private void chartDataSet2()
	{
		//设置图表数据源
							
		//PieData(标签，百分比，在饼图中对应的颜色)
		/*
		roseData3.add(new PieData("看",40,(int)Color.rgb(31, 59, 123) ));
		roseData3.add(new PieData("图"	 ,50,(int)Color.rgb(173, 214, 224)));
		roseData3.add(new PieData("的"		 ,60,(int)Color.rgb(233, 77, 67)));
		roseData3.add(new PieData("那" ,45,(int)Color.rgb(191, 225, 84)));
		roseData3.add(new PieData("个",70,(int)Color.rgb(0, 156, 214)));
	*/
		
		roseData3.add(new PieData("A1",10,Color.rgb(92, 92, 97) ));
		roseData3.add(new PieData("A2",20,Color.rgb(92, 92, 97)));
		roseData3.add(new PieData("A3",30,Color.rgb(92, 92, 97)));
		
		roseData3.add(new PieData("A4",40,Color.rgb(92, 92, 97) ));
		roseData3.add(new PieData("A5",50,Color.rgb(92, 92, 97)));
		roseData3.add(new PieData("A6",60,Color.rgb(92, 92, 97)));
		roseData3.add(new PieData("A7",70,Color.rgb(92, 92, 97)));
		roseData3.add(new PieData("A8",80,Color.rgb(92, 92, 97)));
		
	}
	
	
	private void chartDataSet1()
	{
		//设置图表数据源							
		//PieData(标签，百分比，在饼图中对应的颜色)		
		roseData1.add(new PieData("",7,Color.rgb(190, 254, 175)));  //(int)Color.rgb(39, 51, 72)));		
		roseData1.add(new PieData("",12,Color.rgb(190, 254, 175)));  //(int)Color.rgb(39, 51, 72)));
		roseData1.add(new PieData("",13,Color.rgb(190, 254, 175)));  //(int)Color.rgb(39, 51, 72)));
				
		roseData1.add(new PieData("",15,Color.rgb(190, 254, 175)));  //(int)Color.rgb(77, 83, 97) ));
		roseData1.add(new PieData("",27,Color.rgb(190, 254, 175)));  //(int)Color.rgb(148, 159, 181)));
		roseData1.add(new PieData("",32,Color.rgb(190, 254, 175)));  //(int)Color.rgb(253, 180, 90)));
		roseData1.add(new PieData("",55,Color.rgb(190, 254, 175)));  //(int)Color.rgb(52, 194, 188)));
		roseData1.add(new PieData("",35,Color.rgb(190, 254, 175)));  //(int)Color.rgb(39, 51, 72)));		
	}
	
	private void chartRender1()
	{
		try {						
			
			//设置绘图区默认缩进px值
			int [] ltrb = getPieDefaultSpadding();
			chartRose1.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);	
			//数据源
			chartRose1.setDataSource(roseData1);							
			chartRose1.getInnerPaint().setStyle(Style.STROKE);			
			chartRose1.setInitialAngle(270 + 72/2);
			
			//设置标签显示位置,当前设置标签显示在扇区中间
			//chartRose1.setLabelStyle(XEnum.SliceLabelStyle.INSIDE);
			
			chartRose1.getLabelPaint().setColor(Color.parseColor("#D92222"));
			
			chartRose1.setIntervalAngle(3);
			chartRose1.showOuterLabels();
			//chartRose1.hideOuterLabels();
			
			
			chartRose1.setTitle("玫瑰风向图");
			chartRose1.addSubtitle("(XCL-Charts Demo)");
								
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
	}
	
	
	@Override
    public void render(Canvas canvas) {
        try{
        	chartRose.setBgLines(roseData.size());
            chartRose.render(canvas);
            chartRose3.render(canvas);
            chartRose1.render(canvas);
        } catch (Exception e){
        	Log.e(TAG, e.toString());
        }
    }


}
