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
 * @version 2.4
 */
package com.demo.xclcharts.view;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import org.xclcharts.chart.AreaChart;
import org.xclcharts.chart.AreaData;
import org.xclcharts.chart.CustomLineData;
import org.xclcharts.common.IFormatterDoubleCallBack;
import org.xclcharts.common.IFormatterTextCallBack;
import org.xclcharts.renderer.XEnum;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.util.Log;


/**
 * @ClassName AreaChart03View
 * @Description  面积图例子
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 */

public class AreaChart03View extends DemoView  {
	
	private String TAG = "AreaChart03View";
	
	private AreaChart chart = new AreaChart();	
	//标签集合
	private LinkedList<String> mLabels = new LinkedList<String>();
	//数据集合
	private LinkedList<AreaData> mDataset = new LinkedList<AreaData>();
	
	private AreaChart chartBg = new AreaChart();	
	private LinkedList<String> mBgLabels = new LinkedList<String>();
	
	private List<CustomLineData> mCustomLineDataset = new LinkedList<CustomLineData>();
	
	private double mStdValue = 30d;

	public AreaChart03View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}	 
	
	public AreaChart03View(Context context, AttributeSet attrs){   
        super(context, attrs);   
        initView();
	 }
	 
	 public AreaChart03View(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			initView();
	 }
	 
	 private void initView()
	 {		 		 
		 
		chartLabels();
		chartDataSet();		
		
		chartBgRender();
		chartRender();
		
		//綁定手势滑动事件
		//this.bindTouch(this,chart);
	 }
	
	@Override  
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
        super.onSizeChanged(w, h, oldw, oldh);  
       //图所占范围大小
        chartBg.setChartRange(w,h);
        chart.setChartRange(w,h);
        
    }  
			 
	private void chartRender()
	{
		try{												 
				//设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....		
				int [] ltrb = getBarLnDefaultSpadding();
				chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);
				
				chart.disableHighPrecision();
				chart.disablePanMode();
											
				//轴数据源						
				//标签轴
				chart.setCategories(mLabels);
				//数据轴
				chart.setDataSource(mDataset);
				
				chart.setCrurveLineStyle(XEnum.CrurveLineStyle.BEELINE);
							
				//数据轴最大值
				chart.getDataAxis().setAxisMax(100);
				//数据轴刻度间隔
				chart.getDataAxis().setAxisSteps(10);
				
				//网格
				chart.getPlotGrid().hideHorizontalLines();
				chart.getPlotGrid().hideVerticalLines();
				//把顶轴和右轴隐藏
				//chart.hideTopAxis();
				//chart.hideRightAxis();
				//把轴线和刻度线给隐藏起来
				chart.getDataAxis().hideAxisLine();
				chart.getDataAxis().hideTickMarks();			
				chart.getCategoryAxis().hideAxisLine();
				chart.getCategoryAxis().hideTickMarks();				
				
				
				//标题
				chart.setTitle("区域图(Area Chart)");
				chart.addSubtitle("(XCL-Charts Demo)");	
		
				
				//设定交叉点标签显示格式
				chart.setItemLabelFormatter(new IFormatterDoubleCallBack() {
					@Override
					public String doubleFormatter(Double value) {
						// TODO Auto-generated method stub
						DecimalFormat df=new DecimalFormat("#0");					 
						String label = df.format(value).toString();
						return label;
					}});
				
				//chart.getBackgroundPaint().setAlpha(254);
				//背景渐变
				//chart.getPlotArea().setBackgroundColor(true, Color.rgb(163, 69, 213));
				//chart.getPlotArea().setApplayGradient(true);
				//chart.getPlotArea().setEndColor(Color.WHITE);
				
				chart.getAreaFillPaint().setAlpha(254);
				chart.setAreaAlpha(254);
				
				chart.getDataAxis().hide();
				chart.getCategoryAxis().hide();
				
				CustomLineData line1 = new CustomLineData("30",mStdValue,Color.RED,7);				
				line1.getLineLabelPaint().setColor(Color.RED);
				line1.setLabelHorizontalPostion(Align.LEFT);
				line1.setLineStyle(XEnum.LineStyle.DASH);		
				line1.setLabelOffset( chart.getDataAxis().getTickLabelMargin() );
				mCustomLineDataset.add(line1);
				
				CustomLineData line2 = new CustomLineData("20",20d,Color.RED,7);	
				line2.setLabelHorizontalPostion(Align.LEFT);
				line2.hideLine();
				line2.setLabelOffset( chart.getDataAxis().getTickLabelMargin() );
				line2.getLineLabelPaint().setColor(Color.RED);
				mCustomLineDataset.add(line2);
				
				CustomLineData line3 = new CustomLineData("10",10d,Color.RED,7);	
				line3.setLabelHorizontalPostion(Align.LEFT);
				line3.hideLine();
				line3.getLineLabelPaint().setColor(Color.RED);
				line3.setLabelOffset( chart.getDataAxis().getTickLabelMargin() );
				mCustomLineDataset.add(line3);
				
				chart.setCustomLines(mCustomLineDataset);
				
				chart.getPlotLegend().hide();
			
				//chart.disablePanMode();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, e.toString());
		}
	}
	
	private void chartDataSet()
	{
		//将标签与对应的数据集分别绑定
		//标签对应的数据集
		List<Double> dataSeries1= new LinkedList<Double>();	
		
		double testData[] = {40,21,32,56,40,54,46,32,89,76,53,62,66,78,47,53,90,80,60,82,77,67,79,85,83,90} ;  
		for(int i=0;i< testData.length;i++){
			dataSeries1.add( testData[i]);			
			mLabels.add( Integer.toString(i));
		}
				
		//设置每条线各自的显示属性
		//key,数据集,线颜色,区域颜色
		AreaData line1 = new AreaData("小熊",dataSeries1,Color.rgb(108, 180, 223),Color.YELLOW);
		//不显示点
		line1.setDotStyle(XEnum.DotStyle.HIDE);
		
		line1.setApplayGradient(true);
		line1.setAreaBeginColor(Color.WHITE);
		line1.setAreaEndColor(Color.rgb(108, 180, 223));
		line1.setGradientDirection(XEnum.Direction.VERTICAL);
		
		mDataset.add(line1);	
							
	}
	
	private void chartBgRender()
	{
		try{												 
				//设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....		
				int [] ltrb = getBarLnDefaultSpadding();
				chartBg.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);
				
				chartBg.disableHighPrecision();
				chartBg.disablePanMode();
				
				//轴数据源						
				//标签轴
				chartBg.setCategories(mBgLabels);
				//数据轴
				//chartBg.setDataSource(mDataset);
				
				chartBg.setCrurveLineStyle(XEnum.CrurveLineStyle.BEELINE);
							
				//数据轴最大值
				chartBg.getDataAxis().setAxisMax(100);
				//数据轴刻度间隔
				chartBg.getDataAxis().setAxisSteps(10);
				
				//网格
				chartBg.getPlotGrid().showHorizontalLines();
				chartBg.getPlotGrid().showVerticalLines();	
				chartBg.getPlotGrid().setHorizontalLineStyle(XEnum.LineStyle.DOT);
				chartBg.getPlotGrid().setVerticalLineStyle(XEnum.LineStyle.DOT);
				
				//把轴线和刻度线给隐藏起来
				chartBg.getDataAxis().hideAxisLine();
				chartBg.getDataAxis().hideTickMarks();			
				chartBg.getCategoryAxis().hideAxisLine();
				chartBg.getCategoryAxis().hideTickMarks();
				
				chartBg.getDataAxis().getTickLabelPaint().setColor(Color.GREEN);
				
				//定义数据轴标签显示格式
				chartBg.getDataAxis().setLabelFormatter(new IFormatterTextCallBack(){
		
					@Override
					public String textFormatter(String value) {
						// TODO Auto-generated method stub		
						Double tmp = Double.parseDouble(value);
						if(Double.compare(tmp, mStdValue) == -1 || Double.compare(tmp, mStdValue) == 0){
							return "";
						}else{							
							DecimalFormat df=new DecimalFormat("#0");
							String label = df.format(tmp).toString();				
							return (label);
						}
						
					}
					
				});
				
				chartBg.getPlotLegend().hide();
				//chart.disablePanMode();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, e.toString());
			}
		}
		
	
	private void chartLabels()
	{	
		mBgLabels.add("9:00");
		mBgLabels.add("9:30");
		mBgLabels.add("10:00");
		mBgLabels.add("10:30");
		mBgLabels.add("11:00");
	}
	
	@Override
    public void render(Canvas canvas) {
        try{            
            chartBg.render(canvas);
            chart.render(canvas);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.rgb(11, 35, 122));
            paint.setTextSize(22);
            
            canvas.drawText("2015/10/26 晚上12点  周日  价位:xxxx", 
            		chart.getPlotArea().getLeft(), chart.getPlotArea().getTop() - 10.f, paint);
        } catch (Exception e){
        	Log.e(TAG, e.toString());
        }
    }


	
}

