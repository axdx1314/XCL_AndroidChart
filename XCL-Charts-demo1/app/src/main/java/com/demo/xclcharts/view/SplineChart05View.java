package com.demo.xclcharts.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.xclcharts.chart.CustomLineData;
import org.xclcharts.chart.PointD;
import org.xclcharts.chart.SplineChart;
import org.xclcharts.chart.SplineData;
import org.xclcharts.common.DensityUtil;
import org.xclcharts.event.click.PointPosition;
import org.xclcharts.renderer.XEnum;
import org.xclcharts.renderer.info.AnchorDataPoint;
import org.xclcharts.renderer.plot.PlotGrid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
/**
 * @ClassName SplineChart01View
 * @Description  曲线图 的例子
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 */
public class SplineChart05View extends DemoView {

	private String TAG = "SplineChart05View";
	private SplineChart chart = new SplineChart();
	//分类轴标签集合
	private LinkedList<String> labels = new LinkedList<String>();
	private LinkedList<SplineData> chartData = new LinkedList<SplineData>();
	Paint pToolTip = new Paint(Paint.ANTI_ALIAS_FLAG);
	
	public SplineChart05View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}
	
	public SplineChart05View(Context context, AttributeSet attrs){   
        super(context, attrs);   
        initView();
	 }
	 
	 public SplineChart05View(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			initView();
	 }
	 
	 private void initView()
	 {
			chartLabels();
			chartDataSet();	
			//chartCustomeLines();
			chartAnchor();
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
			chart.setPadding(ltrb[0] + DensityUtil.dip2px(this.getContext(), 20), ltrb[1],
						ltrb[2]+DensityUtil.dip2px(this.getContext(), 30), ltrb[3]);	
			
		
			//标题
			chart.setTitle("一周状态");
			chart.addSubtitle("(XCL-Charts Demo)");	
			chart.getPlotTitle().setVerticalAlign(XEnum.VerticalAlign.BOTTOM);
			chart.getPlotTitle().getTitlePaint().setColor(Color.WHITE);	
			chart.getPlotTitle().getSubtitlePaint().setColor(Color.WHITE);	
			
			//显示边框
			chart.showRoundBorder();
			
			//数据源	
			chart.setCategories(labels);
			chart.setDataSource(chartData);
		//	chart.setCustomLines(mCustomLineDataset);
						
			//坐标系
			//数据轴最大值
			chart.getDataAxis().setAxisMax(20);
			//chart.getDataAxis().setAxisMin(0);
			//数据轴刻度间隔
			chart.getDataAxis().setAxisSteps(5);
			
			//标签轴最大值
			chart.setCategoryAxisMax(5);	
			//标签轴最小值
			chart.setCategoryAxisMin(0);	
			
			//chart.setCategoryAxisCustomLines(mXCustomLineDataset); //x轴
			//chart.setCustomLines(mYCustomLineDataset); //y轴
			
			//背景网格
			PlotGrid plot = chart.getPlotGrid();		
			plot.hideHorizontalLines();
			plot.hideVerticalLines();		
			
			
			chart.getPlotArea().setBackgroundColor(true, Color.rgb(30, 179, 143));
		
						
			chart.getCategoryAxis().getAxisPaint().setColor(Color.WHITE);
			chart.getCategoryAxis().getAxisPaint().setTextSize(6);
			chart.getCategoryAxis().hideTickMarks();					
			chart.getCategoryAxis().getTickLabelPaint().setColor(Color.WHITE);	
			chart.getCategoryAxis().getTickLabelPaint().setFakeBoldText(true);
			chart.getCategoryAxis().setTickLabelMargin(20);
			chart.getCategoryAxis().getTickLabelPaint().setTextSize(25);		
			
			//不使用精确计算，忽略Java计算误差,提高性能
			chart.disableHighPrecision();
			
			chart.disablePanMode();
			chart.hideBorder();
			chart.getPlotLegend().hide();
			chart.getDataAxis().hide();
			
			
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
				linePoint1.add(new PointD(0d, 3d));	
				linePoint1.add(new PointD(1d, 9d));					
				linePoint1.add(new PointD(2d, 8d));				
				linePoint1.add(new PointD(3d, 7d));				
				linePoint1.add(new PointD(4d, 15d));
				linePoint1.add(new PointD(5d, 16d));
				SplineData dataSeries1 = new SplineData("Go",linePoint1,
						Color.WHITE ); //Color.rgb(54, 141, 238)
				//把线弄细点
				dataSeries1.getLinePaint().setStrokeWidth(6);
				dataSeries1.setLineStyle(XEnum.LineStyle.DASH);	
				dataSeries1.setLabelVisible(false);					
				dataSeries1.setDotStyle(XEnum.DotStyle.RING);
				dataSeries1.getDotPaint().setColor(Color.rgb(30, 179, 143));				
				dataSeries1.getPlotLine().getPlotDot().setRingInnerColor(Color.WHITE);//
			
				chartData.add(dataSeries1);	
	}
	
	private void chartLabels()
	{
		labels.add("周一");
		labels.add("周二");
		labels.add("周三");
		labels.add("周四");
		labels.add("周五");
		labels.add("周六");
	}
	
	
	private void chartAnchor(){
		//激活点击监听
				chart.ActiveListenItemClick();
				//为了让触发更灵敏，可以扩大5px的点击监听范围
				chart.extPointClickRange(5);		
				chart.showClikedFocus();		
								
				//批注
				List<AnchorDataPoint> mAnchorSet = new ArrayList<AnchorDataPoint>();
				
				
				AnchorDataPoint an2 = new AnchorDataPoint(0,0,XEnum.AnchorStyle.TOBOTTOM);
				an2.setBgColor(Color.GRAY);
				//an2.setAnchorStyle(XEnum.AnchorStyle.CIRCLE);
				an2.setLineStyle(XEnum.LineStyle.DOT);
				
				AnchorDataPoint an3 = new AnchorDataPoint(0,1,XEnum.AnchorStyle.TOBOTTOM);
				an3.setBgColor(Color.RED);
				//an3.setAnchorStyle(XEnum.AnchorStyle.RECT);
				an3.setAreaStyle(XEnum.DataAreaStyle.STROKE);
				an3.setLineStyle(XEnum.LineStyle.DOT);
				
				//从点到底的标识线
				//从点到底的标识线
				AnchorDataPoint an4 = new AnchorDataPoint(0,2,XEnum.AnchorStyle.TOBOTTOM);
				an4.setBgColor(Color.WHITE);
				an4.setLineWidth(15);
				an4.setLineStyle(XEnum.LineStyle.DOT);
				
				AnchorDataPoint an5 = new AnchorDataPoint(0,3,XEnum.AnchorStyle.TOBOTTOM);
				an5.setBgColor(Color.WHITE);
				an5.setLineWidth(15);
				an5.setLineStyle(XEnum.LineStyle.DASH);
				
				AnchorDataPoint an6 = new AnchorDataPoint(0,4,XEnum.AnchorStyle.TOBOTTOM);
				an6.setBgColor(Color.RED);
				an6.setLineWidth(15);
				an6.setLineStyle(XEnum.LineStyle.DOT);
				
				AnchorDataPoint an7 = new AnchorDataPoint(0,5,XEnum.AnchorStyle.TOBOTTOM);
				an7.setBgColor(Color.WHITE);
				an7.setLineWidth(15);
				an7.setLineStyle(XEnum.LineStyle.DASH);
			
				mAnchorSet.add(an2);
				mAnchorSet.add(an3);
				mAnchorSet.add(an4);
				mAnchorSet.add(an5);
				mAnchorSet.add(an6);
				mAnchorSet.add(an7);
				chart.setAnchorDataPoint(mAnchorSet);		
	}


	@Override
    public void render(Canvas canvas) {
        try{
        	canvas.drawColor( Color.rgb(30, 179, 143));
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
				    // Double xValue = entry.x;
				    // Double yValue = entry.y;	
				  
				        float r = record.getRadius();
							
						chart.getFocusPaint().setStyle(Style.FILL);
						chart.getFocusPaint().setStrokeWidth(6);		
						chart.getFocusPaint().setColor(Color.rgb(237, 92, 92));
						chart.showFocusPointF(record.getPosition(),r*2);	
						
						/*
						//在点击处显示tooltip
						pToolTip.setColor(Color.RED);	
						pToolTip.setStrokeWidth(3);
						chart.getToolTip().setCurrentXY(x,y);
						//chart.getToolTip().setRoundRadius(x, y);
						chart.getToolTip().setStyle(XEnum.DyInfoStyle.CIRCLE);
						chart.getToolTip().addToolTip("",pToolTip);
						*/											
						
						this.invalidate();
						
				     break;
				}
		        i++;
			}//end while
		}
	}
	
	
}
