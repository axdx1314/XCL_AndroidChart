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
 * @version 1.0
 */

package com.demo.xclcharts.view;

import java.util.ArrayList;
import java.util.LinkedList;

import org.xclcharts.chart.PieChart;
import org.xclcharts.chart.PieData;
import org.xclcharts.common.DensityUtil;
import org.xclcharts.common.MathHelper;
import org.xclcharts.event.click.ArcPosition;
import org.xclcharts.renderer.XEnum;
import org.xclcharts.renderer.plot.PlotLegend;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * @ClassName PieChart02View
 * @Description  平面饼图的例子
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * 
 */

public class PieChart02View extends DemoView implements Runnable{

	 private String TAG = "PieChart02View";
	 private PieChart chart = new PieChart();	
	 private LinkedList<PieData> chartData = new LinkedList<PieData>();
	 Paint mPaintToolTip = new Paint(Paint.ANTI_ALIAS_FLAG);
	 
	 //private int mSelectedID = -1;
	
	 public PieChart02View(Context context) {
		super(context);
		initView();
	 }	
	
	 public PieChart02View(Context context, AttributeSet attrs){   
        super(context, attrs);   
        initView();
	 }
	 
	 public PieChart02View(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	 }
	 
	 private void initView()
	 {
		 chartDataSet();	
		 chartRender();
		 
		//綁定手势滑动事件
			this.bindTouch(this,chart);
		 new Thread(this).start();
	 }	 		 	
	
	@Override  
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
        super.onSizeChanged(w, h, oldw, oldh);  
       //图所占范围大小
        chart.setChartRange(w,h);
    }  	
	

	private void chartRender()
	{
		try {										
			//标签显示(隐藏，显示在中间，显示在扇区外面,折线注释方式)
			chart.setLabelStyle(XEnum.SliceLabelStyle.BROKENLINE);		
			chart.getLabelBrokenLine().setLinePointStyle(XEnum.LabelLinePoint.END);
			chart.syncLabelColor();
			chart.syncLabelPointColor();
			
			
			//图的内边距
			//注释折线较长，缩进要多些
			int [] ltrb = new int[4];
			ltrb[0] = DensityUtil.dip2px(getContext(), 60); //left	
			ltrb[1] = DensityUtil.dip2px(getContext(), 65); //top	
			ltrb[2] = DensityUtil.dip2px(getContext(), 60); //right
			ltrb[3] = DensityUtil.dip2px(getContext(), 50); //bottom	
											
			chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);
			
			//设定数据源
			//chart.setDataSource(chartData);												
		
			//标题
			chart.setTitle("擂茶配方比");
			chart.addSubtitle("(XCL-Charts Demo)");
			//chart.setTitleVerticalAlign(XEnum.VerticalAlign.MIDDLE);
				
			//隐藏渲染效果
			chart.hideGradient();
			//显示边框
			//chart.showRoundBorder();
			
			/*
			//激活点击监听
			chart.ActiveListenItemClick();
			chart.showClikedFocus();
			chart.disablePanMode();
			
			//显示图例
			PlotLegend legend = chart.getPlotLegend();	
			legend.show();
			legend.setHorizontalAlign(XEnum.HorizontalAlign.CENTER);
			legend.setVerticalAlign(XEnum.VerticalAlign.BOTTOM);
			legend.showBox();
			*/
			chart.disablePanMode();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
	}

	private void chartDataSet()
	{				
		// 因为Java中Float和double的计算误差问题，所以建议
		//用图库中的MathHelper.getInstance()来做运算,以保证总值为100%
		
		//设置图表数据源				
		PieData pieData = new PieData("芝麻","芝麻:15%",15,Color.rgb(77, 83, 97)) ;
		pieData.setCustLabelStyle(XEnum.SliceLabelStyle.INSIDE,Color.WHITE);		
		
		//pieData.setItemLabelRotateAngle(rotateAngle)
		
		chartData.add(pieData);
				
		chartData.add(new PieData("白糖","白糖(5%)",5,Color.rgb(75, 132, 1)));
		
		//将此比例块突出显示		
		PieData pd = new PieData("花生","花生:35%",35,Color.rgb(180, 205, 230));
		pd.setItemLabelRotateAngle(45.f);
		chartData.add(pd);
		
		PieData pdOther = new PieData("其它","其它(炒米，炒花生之类)",15,Color.rgb(148, 159, 181));
		pdOther.setCustLabelStyle(XEnum.SliceLabelStyle.INSIDE,Color.BLACK);		
		chartData.add(pdOther);
		
		PieData pdTea = new PieData("茶叶","茶叶(30%)",30,Color.rgb(253, 180, 90),true);		
		pdTea.setCustLabelStyle(XEnum.SliceLabelStyle.OUTSIDE,Color.rgb(253, 180, 90));
		chartData.add(pdTea);			
	}
	@Override
	public void render(Canvas canvas) {
		// TODO Auto-generated method stub
		 try{
	            chart.render(canvas);
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
		if(!chart.getListenItemClickStatus())return;
		ArcPosition record = chart.getPositionRecord(x,y);			
		if( null == record) return;
		
		PieData pData = chartData.get(record.getDataID());		
		
	//	boolean isInvaldate = true;		
		for(int i=0;i < chartData.size();i++)
		{	
			PieData cData = chartData.get(i);
			if(i == record.getDataID())
			{
				if(cData.getSelected()) 
				{
					//isInvaldate = false;
					break;
				}else{
					cData.setSelected(true);	
				}
			}else
				cData.setSelected(false);			
		}
		
		
		//显示选中框
		chart.showFocusArc(record,pData.getSelected());
		chart.getFocusPaint().setStyle(Style.STROKE);
		chart.getFocusPaint().setStrokeWidth(5);		
		chart.getFocusPaint().setColor(Color.GREEN);	
		chart.getFocusPaint().setAlpha(100);
		
		
		//在点击处显示tooltip
		mPaintToolTip.setColor(Color.RED);			
		chart.getToolTip().setCurrentXY(x,y);		
		chart.getToolTip().addToolTip(" key:" + pData.getKey() +
							" Label:" + pData.getLabel(),mPaintToolTip);	
											
		this.refreshChart();						
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {          
         	chartAnimation();         	
         }
         catch(Exception e) {
             Thread.currentThread().interrupt();
         }  
	}
	private void chartAnimation()
	{
		  try {       
			 
			  	float sum = 0.0f;
			  	int count = chartData.size();
	          	for(int i=0;i< count ;i++)
	          	{
	          		Thread.sleep(150);
	          	
	          		ArrayList<PieData> animationData = new ArrayList<PieData>();
	        
	          		sum = 0.0f;
	          			          		
	          		for(int j=0;j<=i;j++)
	          		{            			            			
	          			animationData.add(chartData.get(j));
	          			sum = (float) MathHelper.getInstance().add(
	          									sum , chartData.get(j).getPercentage());	          			
	          		}   		          		
	          			          			          				          				          	
	          		animationData.add(new PieData("","",  MathHelper.getInstance().sub(100.0f , sum),
	          											  Color.argb(1, 0, 0, 0)));		          		
	          		chart.setDataSource(animationData);
	          	
	          		//激活点击监听
	    			if(count - 1 == i)
	    			{
	    				//chart.ActiveListenItemClick();
	    				//显示边框线，并设置其颜色
	    				//chart.getArcBorderPaint().setColor(Color.YELLOW);
	    				//chart.getArcBorderPaint().setStrokeWidth(3);
	    				
	    			
	    				
	    				//激活点击监听
	    				chart.ActiveListenItemClick();
	    				chart.showClikedFocus();
	    				chart.disablePanMode();
	    				
	    				//显示图例
	    				PlotLegend legend = chart.getPlotLegend();	
	    				legend.show();
	    				legend.setHorizontalAlign(XEnum.HorizontalAlign.CENTER);
	    				legend.setVerticalAlign(XEnum.VerticalAlign.BOTTOM);
	    				legend.showBox();
	    				
	    			}
	    			
	          		postInvalidate();            				          	          	
	          }
			  
          }
          catch(Exception e) {
              Thread.currentThread().interrupt();
          }       
		  
	}
	
	 
}
