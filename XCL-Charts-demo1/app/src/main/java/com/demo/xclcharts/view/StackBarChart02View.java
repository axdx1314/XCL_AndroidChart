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

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import org.xclcharts.chart.BarData;
import org.xclcharts.chart.StackBarChart;
import org.xclcharts.common.DensityUtil;
import org.xclcharts.common.IFormatterDoubleCallBack;
import org.xclcharts.common.IFormatterTextCallBack;
import org.xclcharts.event.click.BarPosition;
import org.xclcharts.renderer.XEnum;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
/**
 * @ClassName StackBarChart02View
 * @Description  堆叠图 的例子(横向)
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 */
public class StackBarChart02View extends DemoView {

	private String TAG = "StackBarChart02View";
	private StackBarChart chart = new StackBarChart();
	
	//标签轴
	List<String> chartLabels = new LinkedList<String>();
	List<BarData> BarDataSet = new LinkedList<BarData>();
	
	Paint pToolTip = new Paint(Paint.ANTI_ALIAS_FLAG);
	
	public StackBarChart02View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}
	
	public StackBarChart02View(Context context, AttributeSet attrs){   
        super(context, attrs);   
        initView();
	 }
	 
	 public StackBarChart02View(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			initView();
	 }
	 
	 private void initView()
	 {
		 chartLabels();
		 chartDataSet();	
		 chartRender();
		//綁定手势滑动事件
			this.bindTouch(this,chart);
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
			
			//设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....		
			int [] ltrb = getBarLnDefaultSpadding();
			chart.setPadding(DensityUtil.dip2px(getContext(), 50),ltrb[1], ltrb[2],  ltrb[3]);	
			
			//显示边框
			chart.showRoundBorder();
			
			//指定显示为横向柱形
			chart.setChartDirection(XEnum.Direction.HORIZONTAL);
			//数据源		
			chart.setCategories(chartLabels);	
			chart.setDataSource(BarDataSet);
			
			//坐标系
			chart.getDataAxis().setAxisMax(1200);
			chart.getDataAxis().setAxisMin(100);
			chart.getDataAxis().setAxisSteps(100);
			//指定数据轴标签旋转-45度显示
			chart.getCategoryAxis().setTickLabelRotateAngle(-45f);			
			
			//标题
			chart.setTitle("费用预算与实际发生对比");
			chart.addSubtitle("(XCL-Charts Demo)");
			chart.setTitleAlign(XEnum.HorizontalAlign.CENTER);
			
			//图例
			chart.getAxisTitle().setLowerTitle("单位为(W)");
			
			//背景网格			
			chart.getPlotGrid().showVerticalLines();
			chart.getPlotGrid().setVerticalLineStyle(XEnum.LineStyle.DOT);
					
			
			//chart.getPlotGrid().setVerticalLinesVisible(true);
			//chart.getPlotGrid().setEvenRowsFillVisible(true);
			//chart.getPlotGrid().getEvenFillPaint().setColor((int)Color.rgb(225, 230, 246)); 
			
			//定义数据轴标签显示格式
			chart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack(){
	
				@Override
				public String textFormatter(String value) {
					// TODO Auto-generated method stub	
					
					DecimalFormat df=new DecimalFormat("#0");
					Double tmp = Double.parseDouble(value);
					String label = df.format(tmp).toString();					
					return label;
				}
				
			});
			
			//定义标签轴标签显示颜色
			chart.getCategoryAxis().getTickLabelPaint().
				setColor(Color.rgb(1, 188, 242));
					
			//定义柱形上标签显示格式
			chart.getBar().setItemLabelVisible(true);
			chart.setItemLabelFormatter(new IFormatterDoubleCallBack() {
				@Override
				public String doubleFormatter(Double value) {
					// TODO Auto-generated method stub
					DecimalFormat df=new DecimalFormat("#0.00");					 
					String label = df.format(value).toString();
					return label;
				}});	 
			//定义柱形上标签显示颜色
			chart.getBar().getItemLabelPaint().setColor(Color.rgb(225, 43, 44));
			//柱形形标签字体大小
			chart.getBar().getItemLabelPaint().setTextSize(18);
			
			//激活点击监听
			chart.ActiveListenItemClick();
			chart.showClikedFocus();
			chart.setPlotPanMode(XEnum.PanMode.VERTICAL);
			
			//设置柱子的最大高度范围，不然有些数据太少时，柱子太高不太好看。 
			chart.getBar().setBarMaxPxHeight(100.f);
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
	}
	private void chartDataSet()
	{
		//标签对应的柱形数据集
		List<Double> dataSeriesA= new LinkedList<Double>();	
		dataSeriesA.add(200d);
		dataSeriesA.add(550d);
		dataSeriesA.add(400d); 

		List<Double> dataSeriesB= new LinkedList<Double>();	
		dataSeriesB.add(380d);
		dataSeriesB.add(452.57d); 
		dataSeriesB.add(657.65d); 

		
		BarDataSet.add(new BarData("预算(Budget)",dataSeriesA,Color.rgb(64, 175, 240)));
		BarDataSet.add(new BarData("实际(Actual)",dataSeriesB,Color.rgb(247, 156, 27)));
	}
	private void chartLabels()
	{
		chartLabels.add("一季度(Q1)"); 
		chartLabels.add("二季度(Q2)"); 
		chartLabels.add("三季度(Q3)"); 
	}
	
	@Override
    public void render(Canvas canvas) {
        try{
        	//chart.setChartRange(this.getMeasuredWidth(), this.getMeasuredHeight());
        	
        	//chart.setChartRange(this.getWidth(), this.getHeight());
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
	
		BarPosition record = chart.getPositionRecord(x,y);			
		if( null == record) return;
		
		if(record.getDataID() >= BarDataSet.size()) return;
		BarData bData = BarDataSet.get(record.getDataID());					
		Double bValue = bData.getDataSet().get(record.getDataChildID());			
		
		chart.showFocusRectF(record.getRectF());		
		chart.getFocusPaint().setStyle(Style.FILL);
		chart.getFocusPaint().setStrokeWidth(3);		
		chart.getFocusPaint().setColor(Color.GRAY);	
		chart.getFocusPaint().setAlpha(100);
		
		//在点击处显示tooltip
		pToolTip.setColor(Color.WHITE);		
		chart.getToolTip().setAlign(Align.CENTER);
		chart.getToolTip().getBackgroundPaint().setColor(Color.BLUE);
		chart.getToolTip().setInfoStyle(XEnum.DyInfoStyle.CAPROUNDRECT);
		//chart.getToolTip().setCurrentXY(x,y);	
		chart.getToolTip().setCurrentXY(record.getRectF().centerX(),record.getRectF().top);				
		chart.getToolTip().addToolTip(" Current Value:" +Double.toString(bValue),pToolTip);
		
		this.invalidate();
	}

}
