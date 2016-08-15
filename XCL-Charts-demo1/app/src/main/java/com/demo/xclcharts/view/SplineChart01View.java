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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.xclcharts.chart.PointD;
import org.xclcharts.chart.SplineChart;
import org.xclcharts.chart.SplineData;
import org.xclcharts.common.IFormatterTextCallBack;
import org.xclcharts.event.click.PointPosition;
import org.xclcharts.renderer.XEnum;
import org.xclcharts.renderer.info.AnchorDataPoint;
import org.xclcharts.renderer.plot.PlotGrid;

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
 * @ClassName SplineChart01View
 * @Description  曲线图 的例子
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 */
public class SplineChart01View extends DemoView {

	private String TAG = "SplineChart01View";
	private SplineChart chart = new SplineChart();
	//分类轴标签集合
	private LinkedList<String> labels = new LinkedList<String>();
	private LinkedList<SplineData> chartData = new LinkedList<SplineData>();
	Paint pToolTip = new Paint(Paint.ANTI_ALIAS_FLAG);
	
	
	public SplineChart01View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}
	
	public SplineChart01View(Context context, AttributeSet attrs){   
        super(context, attrs);   
        initView();
	 }
	 
	 public SplineChart01View(Context context, AttributeSet attrs, int defStyle) {
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
			chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);	
			
			//平移时收缩下
			//float margin = DensityUtil.dip2px(getContext(), 20);
			//chart.setXTickMarksOffsetMargin(margin);
			
			//显示边框
			chart.showRoundBorder();
			
			//数据源	
			chart.setCategories(labels);
			chart.setDataSource(chartData);
						
			//坐标系
			//数据轴最大值
			chart.getDataAxis().setAxisMax(100);
			//chart.getDataAxis().setAxisMin(0);
			//数据轴刻度间隔
			chart.getDataAxis().setAxisSteps(10);
			
			//标签轴最大值
			chart.setCategoryAxisMax(10);	
			//标签轴最小值
			chart.setCategoryAxisMin(0);	
			
			//设置图的背景色
			//chart.setBackgroupColor(true,Color.BLACK);
			//设置绘图区的背景色
			//chart.getPlotArea().setBackgroupColor(true, Color.WHITE);
			
			//背景网格
			PlotGrid plot = chart.getPlotGrid();			
			plot.showHorizontalLines();
			plot.showVerticalLines();			
			plot.getHorizontalLinePaint().setStrokeWidth(3);
			plot.getHorizontalLinePaint().setColor(Color.rgb(127, 204, 204));			
			plot.setHorizontalLineStyle(XEnum.LineStyle.DOT);
		
			
			//把轴线设成和横向网络线一样和大小和颜色,演示下定制性，这块问得人较多
			//chart.getDataAxis().getAxisPaint().setStrokeWidth(
			//		plot.getHorizontalLinePaint().getStrokeWidth());
			//chart.getCategoryAxis().getAxisPaint().setStrokeWidth(
			//		plot.getHorizontalLinePaint().getStrokeWidth());
			
			chart.getDataAxis().getAxisPaint().setColor(Color.rgb(127, 204, 204));
			chart.getCategoryAxis().getAxisPaint().setColor(Color.rgb(127, 204, 204));
			
			chart.getDataAxis().getTickMarksPaint().setColor(Color.rgb(127, 204, 204));
			chart.getCategoryAxis().getTickMarksPaint().setColor(Color.rgb(127, 204, 204));
			
			//居中
			chart.getDataAxis().setHorizontalTickAlign(Align.CENTER);
			chart.getDataAxis().getTickLabelPaint().setTextAlign(Align.CENTER);
			
			//居中显示轴 
			//plot.hideHorizontalLines();
			//plot.hideVerticalLines();	
			//chart.setDataAxisLocation(XEnum.AxisLocation.VERTICAL_CENTER);
			//chart.setCategoryAxisLocation(XEnum.AxisLocation.HORIZONTAL_CENTER);
			
			
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
			chart.setTitle("Spline Chart");
			chart.addSubtitle("(XCL-Charts Demo)");
			
			//激活点击监听
			chart.ActiveListenItemClick();
			//为了让触发更灵敏，可以扩大5px的点击监听范围
			chart.extPointClickRange(5);
			chart.showClikedFocus();
			
			//显示十字交叉线
			chart.showDyLine();
			chart.getDyLine().setDyLineStyle(XEnum.DyLineStyle.Vertical);
			//扩大实际绘制宽度
			//chart.getPlotArea().extWidth(500.f);
			
			//封闭轴
			chart.setAxesClosed(true);
			
			//将线显示为直线，而不是平滑的
			chart.setCrurveLineStyle(XEnum.CrurveLineStyle.BEELINE);
			
			//不使用精确计算，忽略Java计算误差,提高性能
			chart.disableHighPrecision();
			
			//仅能横向移动
			chart.setPlotPanMode(XEnum.PanMode.HORIZONTAL);
									
			//批注
			List<AnchorDataPoint> mAnchorSet = new ArrayList<AnchorDataPoint>();
			
			AnchorDataPoint an1 = new AnchorDataPoint(2,0,XEnum.AnchorStyle.CAPROUNDRECT);
			an1.setAlpha(200);
			an1.setBgColor(Color.RED);
			an1.setAreaStyle(XEnum.DataAreaStyle.FILL);
			
			AnchorDataPoint an2 = new AnchorDataPoint(1,1,XEnum.AnchorStyle.CIRCLE);
			an2.setBgColor(Color.GRAY);
			
			AnchorDataPoint an3 = new AnchorDataPoint(0,2,XEnum.AnchorStyle.RECT);
			an3.setBgColor(Color.BLUE);
					
			mAnchorSet.add(an1);
			mAnchorSet.add(an2);
			mAnchorSet.add(an3);
			chart.setAnchorDataPoint(mAnchorSet);	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, e.toString());
		}
	}
	private void chartDataSet()
	{
		/*
		//线1的数据集
		List<PointD> linePoint1 = new ArrayList<PointD>();
		linePoint1.add(new PointD(5d, 8d));
		
		linePoint1.add(new PointD(12d, 12d));
		linePoint1.add(new PointD(25d, 15d));
		linePoint1.add(new PointD(30d, 30d));
		linePoint1.add(new PointD(45d, 25d));
		
		linePoint1.add(new PointD(55d, 33d));
		linePoint1.add(new PointD(62d, 45d));
		SplineData dataSeries1 = new SplineData("青菜萝卜够吃",linePoint1,
				Color.rgb(54, 141, 238) );	
		//把线弄细点
		dataSeries1.getLinePaint().setStrokeWidth(2);
		
		//线2的数据集
		List<PointD> linePoint2 = new ArrayList<PointD>();
		linePoint2.add(new PointD(40d, 50d));
		linePoint2.add(new PointD(55d, 55d));
		linePoint2.add(new PointD(60d, 65d));
		linePoint2.add(new PointD(65d, 85d));		
		
		linePoint2.add(new PointD(72d, 70d));	
		linePoint2.add(new PointD(85d, 68d));	
		SplineData dataSeries2 = new SplineData("饭管够",linePoint2,
				Color.rgb(255, 165, 132) );
		
				
		dataSeries2.setLabelVisible(true);		
		dataSeries2.setDotStyle(XEnum.DotStyle.RECT);				
		dataSeries2.getDotLabelPaint().setColor(Color.RED);	
			
		//设定数据源		
		chartData.add(dataSeries1);				
		chartData.add(dataSeries2);	
		*/
		
		//线1的数据集
				List<PointD> linePoint1 = new ArrayList<PointD>();
				
				linePoint1.add(new PointD(2d, 8d));
				
				linePoint1.add(new PointD(5d, 8d));
				
				linePoint1.add(new PointD(10d, 12d));
				//linePoint1.add(new PointD(25d, 15d));
				//linePoint1.add(new PointD(30d, 30d));
				//linePoint1.add(new PointD(45d, 25d));
				
				//linePoint1.add(new PointD(55d, 33d));
				//linePoint1.add(new PointD(62d, 45d));
				SplineData dataSeries1 = new SplineData("青菜萝卜够吃",linePoint1,
						Color.rgb(54, 141, 238) );
				//把线弄细点
				dataSeries1.getLinePaint().setStrokeWidth(2);
								

				//线2的数据集
				List<PointD> linePoint2 = new ArrayList<PointD>();
				linePoint2.add(new PointD(1d, 50d));
				linePoint2.add(new PointD(2d, 52d));
				linePoint2.add(new PointD(3d, 53d));
				linePoint2.add(new PointD(8d, 55d));
				SplineData dataSeries2 = new SplineData("饭管够",linePoint2,
						Color.rgb(255, 165, 132) );
				
						
				dataSeries2.setLabelVisible(true);		
				dataSeries2.setDotStyle(XEnum.DotStyle.RECT);				
				dataSeries2.getDotLabelPaint().setColor(Color.RED);	
				
				//设置round风格的标签
				//dataSeries2.getLabelOptions().showBackground();
				dataSeries2.getLabelOptions().getBox().getBackgroundPaint().setColor(Color.GREEN);
				dataSeries2.getLabelOptions().getBox().setRoundRadius(8);
				dataSeries2.getLabelOptions().setLabelBoxStyle(XEnum.LabelBoxStyle.CAPROUNDRECT);
				
				chartData.add(dataSeries1);	
				chartData.add(dataSeries2);	
	}
	
	private void chartLabels()
	{
		labels.add("0");
		labels.add("5");
		labels.add("10");
		
		//labels.add("5:52:33");
		//labels.add("5:52:35");
		//labels.add("5:52:37");
		//labels.add("5:52:39");
		//labels.add("5:52:41");
		//labels.add("5:52:43");
		//labels.add("5:52:45");
	}

	@Override
    public void render(Canvas canvas) {
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
		//交叉线
		if(chart.getDyLineVisible())chart.getDyLine().setCurrentXY(x,y);		
		if(!chart.getListenItemClickStatus())
		{
			if(chart.getDyLineVisible()&&chart.getDyLine().isInvalidate())this.invalidate();
		}else{	
			PointPosition record = chart.getPositionRecord(x,y);			
			if( null == record) return;
	
			if(record.getDataID() >= chartData.size()) return;
			SplineData lData = chartData.get(record.getDataID());
			List<PointD> linePoint =  lData.getLineDataSet();	
			int pos = record.getDataChildID();
			int i = 0;
			Iterator it = linePoint.iterator();
			while(it.hasNext())
			{
				PointD  entry=(PointD)it.next();	
				
				if(pos == i)
				{							 						
				     Double xValue = entry.x;
				     Double yValue = entry.y;	
				  
				        float r = record.getRadius();
						chart.showFocusPointF(record.getPosition(),r * 2);		
						chart.getFocusPaint().setStyle(Style.STROKE);
						chart.getFocusPaint().setStrokeWidth(3);		
						if(record.getDataID() >= 2)
						{
							chart.getFocusPaint().setColor(Color.BLUE);
						}else{
							chart.getFocusPaint().setColor(Color.RED);
						}	
						
						//在点击处显示tooltip
						pToolTip.setColor(Color.RED);				
						chart.getToolTip().setCurrentXY(x,y);
						chart.getToolTip().addToolTip(" Key:"+lData.getLineKey(),pToolTip);
						chart.getToolTip().addToolTip(" Label:"+lData.getLabel(),pToolTip);		
						chart.getToolTip().addToolTip(" Current Value:" +Double.toString(xValue)+","+Double.toString(yValue),pToolTip);
						chart.getToolTip().getBackgroundPaint().setAlpha(100);
						this.invalidate();
						
				     break;
				}
		        i++;
			}//end while
		}
	}
	
	
}
