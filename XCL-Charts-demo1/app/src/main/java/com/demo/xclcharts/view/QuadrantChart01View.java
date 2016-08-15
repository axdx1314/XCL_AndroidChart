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
 * @Description Android图表基类库
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * @Copyright Copyright (c) 2014 XCL-Charts (www.xclcharts.com)
 * @license http://www.apache.org/licenses/  Apache v2 License
 * @version 2.1
 */
package com.demo.xclcharts.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.xclcharts.chart.BubbleChart;
import org.xclcharts.chart.BubbleData;
import org.xclcharts.chart.PointD;
import org.xclcharts.chart.ScatterChart;
import org.xclcharts.chart.ScatterData;
import org.xclcharts.chart.SplineChart;
import org.xclcharts.chart.SplineData;
import org.xclcharts.common.IFormatterTextCallBack;
import org.xclcharts.event.click.PointPosition;
import org.xclcharts.renderer.XEnum;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class QuadrantChart01View extends DemoView {
	
	private static final String TAG = "QuadrantChart01View";
	
	
	private BubbleChart chart = new BubbleChart();
	//分类轴标签集合
	private LinkedList<String> labels = new LinkedList<String>();
	private List<BubbleData> chartData = new LinkedList<BubbleData>();
	
	
	private ScatterChart chartScat = new ScatterChart();
	//分类轴标签集合
	private List<ScatterData> chartDataScat = new LinkedList<ScatterData>();
	
	
	private SplineChart chartSp = new SplineChart();
	private LinkedList<SplineData> chartDataSp = new LinkedList<SplineData>();
	
	
	public QuadrantChart01View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}	 
	
	public QuadrantChart01View(Context context, AttributeSet attrs){   
        super(context, attrs);   
        initView();
	 }
	 
	 public QuadrantChart01View(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			initView();
	 }
	 
	 private void initView()
	 {
		 chartLabels();
		 chartDataSet();	
		 chartRender();
		 
		 chartDataSetScat();	
		 chartRenderScat();
		 
		 chartDataSetSp();	
		 chartRenderSp();
		 
		//綁定手势滑动事件
			this.bindTouch(this,chart); 
			this.bindTouch(this,chartScat); 
			this.bindTouch(this,chartSp); 
	 }
	 
	 
	 @Override  
	    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
	        super.onSizeChanged(w, h, oldw, oldh);  
	       //图所占范围大小
	        chart.setChartRange(w,h);	        
	        chartScat.setChartRange(w,h);
	        chartSp.setChartRange(w,h);
	    }  				
		
		
		private void chartRender()
		{
			try {
							
				//设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....		
				int [] ltrb = getBarLnDefaultSpadding();
				chart.setPadding(10, ltrb[1], 10, ltrb[3]);	
				
				//显示边框
				//chart.showRoundBorder();
				
				//数据源	
				chart.setCategories(labels);
				chart.setDataSource(chartData);
							
				//坐标系
				//数据轴最大值
				chart.getDataAxis().setAxisMax(100);
				//数据轴刻度间隔
				chart.getDataAxis().setAxisSteps(10);
				
				//标签轴最大值
				chart.setCategoryAxisMax(100);	
				//标签轴最小值
				chart.setCategoryAxisMin(0);	
				
				//指定气泡半径的最大大小
				chart.setBubbleMaxSize(200);
				//指定气泡半径的最小大小
				chart.setBubbleMinSize(10);
				
				//指定最大气泡大小的实际数据值
				chart.setBubbleScaleMax(100);
				//指定最小气泡大小的实际数据值
				chart.setBubbleScaleMin(0);
						
				chart.getCategoryAxis().hideTickMarks();
				
				chart.getDataAxis().setHorizontalTickAlign(Align.RIGHT);
				chart.getDataAxis().getTickLabelPaint().setTextAlign(Align.LEFT);
				
				chart.getDataAxis().getAxisPaint().setStrokeWidth(3);
				chart.getCategoryAxis().getAxisPaint().setStrokeWidth(3);
				
				chart.getDataAxis().getAxisPaint().setColor(Color.rgb(127, 204, 204));
				chart.getCategoryAxis().getAxisPaint().setColor(Color.rgb(127, 204, 204));
				
				chart.getDataAxis().getTickMarksPaint().setColor(Color.rgb(127, 204, 204));
				chart.getCategoryAxis().getTickMarksPaint().setColor(Color.rgb(127, 204, 204));
									
				
				//定义交叉点标签显示格式,特别备注,因曲线图的特殊性，所以返回格式为:  x值,y值
				//请自行分析定制
				chart.setDotLabelFormatter(new IFormatterTextCallBack(){
		
					@Override
					public String textFormatter(String value) {
						// TODO Auto-generated method stub						
						String label = "("+value+")";				
						return (label);
					}
					
				});
				//标题
				chart.setTitle("象限图");
				chart.addSubtitle("(XCL-Charts Demo)");
				
				//激活点击监听
				chart.ActiveListenItemClick();
				//为了让触发更灵敏，可以扩大5px的点击监听范围
				chart.extPointClickRange(5);
				chart.showClikedFocus();
				
				
				//图例
				chart.getPlotLegend().setVerticalAlign(XEnum.VerticalAlign.BOTTOM);
				chart.getPlotLegend().setHorizontalAlign(XEnum.HorizontalAlign.RIGHT);	
				
				//象限设置
				chart.getPlotQuadrant().show(45d, 55d);
				chart.getPlotQuadrant().setBgColor(Color.rgb(106, 79, 193), 
						Color.rgb(34, 174, 215), 
						Color.rgb(82, 187, 197),
						Color.rgb(217, 78, 69));
				
				chart.getPlotQuadrant().getHorizontalLinePaint().setColor(Color.rgb(127, 204, 204));
				chart.getPlotQuadrant().getVerticalLinePaint().setColor(Color.rgb(127, 204, 204));												
				
				chart.getPlotQuadrant().getHorizontalLinePaint().setStrokeWidth(8);
				chart.getPlotQuadrant().getVerticalLinePaint().setStrokeWidth(8);
												
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, e.toString());
			}
		}
		private void chartDataSet()
		{
			
			//线1的数据集
			List<PointD> linePoint1 = new ArrayList<PointD>();
	
			linePoint1.add(new PointD(30d, 30d));
			linePoint1.add(new PointD(45d, 25d));
			
			linePoint1.add(new PointD(55d, 33d));
			linePoint1.add(new PointD(62d, 45d));
			//气泡大小
			ArrayList<Double> linePoint1_bubble = new ArrayList<Double>();		
			linePoint1_bubble.add(55d);
			linePoint1_bubble.add(60d);
			linePoint1_bubble.add(65d);
			linePoint1_bubble.add(95d);				
			linePoint1_bubble.add(75d);	
			linePoint1_bubble.add(78d);	
			
			
			BubbleData dataSeries1 = new BubbleData("气泡1",linePoint1,linePoint1_bubble,
					Color.rgb(72, 117, 14) );			
			dataSeries1.setLabelVisible(true);	
			dataSeries1.getDotLabelPaint().setTextAlign(Align.CENTER);		
			dataSeries1.setBorderColor(Color.RED);
			
			//线2的数据集
			List<PointD> linePoint2 = new ArrayList<PointD>();
			linePoint2.add(new PointD(40d, 50d));
			linePoint2.add(new PointD(55d, 55d));
			linePoint2.add(new PointD(60d, 65d));
			linePoint2.add(new PointD(65d, 85d));				
			linePoint2.add(new PointD(72d, 70d));	
			linePoint2.add(new PointD(85d, 68d));	
			
			ArrayList<Double> linePoint2_bubble = new ArrayList<Double>();		
			linePoint2_bubble.add(55d);
			linePoint2_bubble.add(60d);
			linePoint2_bubble.add(65d);
			linePoint2_bubble.add(95d);				
			linePoint2_bubble.add(75d);	
			linePoint2_bubble.add(78d);	
					
			BubbleData dataSeries2 = new BubbleData("气泡2",
					linePoint2,linePoint2_bubble,Color.rgb(59, 59, 59));
		
						
			dataSeries2.setLabelVisible(true);		
			//dataSeries2.setDotStyle(XEnum.DotStyle.RECT);				
			dataSeries2.getDotLabelPaint().setColor(Color.rgb(69, 199, 101));
			dataSeries2.setItemLabelRotateAngle(45.f);
			
			List<PointD> linePoint3= new ArrayList<PointD>();
			linePoint3.add(new PointD(10d, 70d));
			linePoint3.add(new PointD(25d, 85d));
			linePoint3.add(new PointD(30d, 95d));
			
			ArrayList<Double> linePoint3_bubble = new ArrayList<Double>();		
			linePoint3_bubble.add(55d);
			linePoint3_bubble.add(60d);
			linePoint3_bubble.add(65d);
			
			BubbleData dataSeries3 = new BubbleData("气泡3",
					linePoint2,linePoint3_bubble,Color.rgb(247, 178, 79));
			
			dataSeries3.setBorderColor(Color.rgb(47, 254, 225));
						
				
			//设定数据源		
			chartData.add(dataSeries1);				
			chartData.add(dataSeries2);	
			chartData.add(dataSeries3);	
		
		}
		
		
		private void chartRenderScat()
		{
			try {
							
				//设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....		
				int [] ltrb = getBarLnDefaultSpadding();
				chartScat .setPadding(10, ltrb[1], 10, ltrb[3]);							
				
				//数据源	
				chartScat .setCategories(labels);
				chartScat .setDataSource(chartDataScat );
							
				//坐标系
				//数据轴最大值
				chartScat.getDataAxis().setAxisMax(100);
				//chart.getDataAxis().setAxisMin(0);
				//数据轴刻度间隔
				chartScat.getDataAxis().setAxisSteps(10);
				
				//标签轴最大值
				chartScat.setCategoryAxisMax(100);	
				//标签轴最小值
				chartScat.setCategoryAxisMin(0);					
				
				chartScat.getDataAxis().hide();
				chartScat.getCategoryAxis().hide();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, e.toString());
			}
		}
		private void chartDataSetScat()
		{
		
			//线1的数据集
			List<PointD> linePoint1 = new ArrayList<PointD>();
			linePoint1.add(new PointD(15d, 68d));
			
			linePoint1.add(new PointD(32d, 62d));
			linePoint1.add(new PointD(25d, 55d));
			linePoint1.add(new PointD(60d, 80d));
			ScatterData dataSeries1 = new ScatterData("散点1",linePoint1,
					Color.rgb(41, 161, 64),XEnum.DotStyle.DOT );	
			dataSeries1.setLabelVisible(true);	
			dataSeries1.getDotLabelPaint().setColor(Color.rgb(41, 161, 64));
			
			
			//线2的数据集
			List<PointD> linePoint2 = new ArrayList<PointD>();
			linePoint2.add(new PointD(43d,70d));
			linePoint2.add(new PointD(56d, 85d));
			linePoint2.add(new PointD(37d, 65d));
			ScatterData dataSeries2 = new ScatterData("散点2",linePoint2,
					Color.YELLOW,XEnum.DotStyle.PRISMATIC );
							
			//设定数据源		
			chartDataScat.add(dataSeries1);				
			chartDataScat.add(dataSeries2);	
			
		}
		
				
		private void chartRenderSp()
		{
			try {
							
				//设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....		
				int [] ltrb = getBarLnDefaultSpadding();
				chartSp.setPadding(10, ltrb[1], 10, ltrb[3]);	
				
				//显示边框
				//chartSp.showRoundBorder();
				
				//数据源	
				chartSp.setCategories(labels);
				chartSp.setDataSource(chartDataSp);
							
				//坐标系
				//数据轴最大值
				chartSp.getDataAxis().setAxisMax(100);
				//chart.getDataAxis().setAxisMin(0);
				//数据轴刻度间隔
				chartSp.getDataAxis().setAxisSteps(10);
				
				//标签轴最大值
				chartSp.setCategoryAxisMax(100);	
				//标签轴最小值
				chartSp.setCategoryAxisMin(0);	
				
				
				chartSp.getPlotLegend().hide();				
				chartSp.getDataAxis().hide();
				chartSp.getCategoryAxis().hide();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, e.toString());
			}
		}
		private void chartDataSetSp()
		{
			//线1的数据集
			List<PointD> linePoint1 = new ArrayList<PointD>();		
		
			linePoint1.add(new PointD(25d, 15d));
			linePoint1.add(new PointD(45d, 35d));
			linePoint1.add(new PointD(50d, 40d));
						
			linePoint1.add(new PointD(65d, 45d));
			linePoint1.add(new PointD(70d, 50d));
			linePoint1.add(new PointD(75d, 65d));
			
			linePoint1.add(new PointD(85d, 73d));
			linePoint1.add(new PointD(92d, 85d));
			
			SplineData dataSeries1 = new SplineData("指向线",linePoint1,
					Color.RED );	 //(int)Color.rgb(54, 141, 238)
			//把线弄细点
			dataSeries1.getLinePaint().setStrokeWidth(10);
			dataSeries1.setDotStyle(XEnum.DotStyle.HIDE);
						
			//设定数据源		
			chartDataSp.add(dataSeries1);	
		}
						
		private void chartLabels()
		{
			labels.add("b1");
			labels.add("b2");
			labels.add("b3");
			labels.add("b4");
			labels.add("b5");
			labels.add("b6");
			labels.add("b7");
		}

		@Override
	    public void render(Canvas canvas) {
	        try{
	            chart.render(canvas);
	            chartScat.render(canvas);
	            chartSp.render(canvas);
	        } catch (Exception e){
	        	Log.e(TAG, e.toString());
	        }
	    }

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			// TODO Auto-generated method stub		
			
			super.onTouchEvent(event);
					
			if(event.getAction() == MotionEvent.ACTION_UP) 
			{			
				triggerClick(event.getX(),event.getY());	
			}
			return true;
		}
		
		
		//触发监听
		private void triggerClick(float x,float y)
		{		
			//交叉线
			if(chart.getDyLineVisible())chart.getDyLine().setCurrentXY(x,y);		
			if(!chart.getListenItemClickStatus())
			{
				if(chart.getDyLineVisible()&&chart.getDyLine().isInvalidate())this.invalidate();
			}else{			
				PointPosition record = chart.getPositionRecord(x,y);			
				if( null == record) return;
		
				BubbleData lData = chartData.get(record.getDataID());
				List<PointD> mapPoint =  lData.getDataSet();	
				int pos = record.getDataChildID();
				int i = 0;
				Iterator it = mapPoint.iterator();
				while(it.hasNext())
				{
					PointD  entry=(PointD)it.next();	
					
					if(pos == i)
					{							 						
					     	Double xValue = entry.x;
					     	Double yValue = entry.y;
					   				     
					     	float r = record.getRadius();		
							chart.showFocusPointF(record.getPosition(),r + r*0.5f);		
							chart.getFocusPaint().setStyle(Style.STROKE);
							chart.getFocusPaint().setStrokeWidth(3);		
							if(record.getDataID() >= 3)
							{
								chart.getFocusPaint().setColor(Color.WHITE);
							}else{
								chart.getFocusPaint().setColor(Color.RED);
							}	
							
							this.invalidate();						
					     break;
					}
			        i++;
				}//end while
			}//end if
		}
		
	 

}
