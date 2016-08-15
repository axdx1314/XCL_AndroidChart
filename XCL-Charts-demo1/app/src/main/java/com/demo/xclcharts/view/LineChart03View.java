package com.demo.xclcharts.view;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.xclcharts.chart.LineChart;
import org.xclcharts.chart.LineData;
import org.xclcharts.renderer.XEnum;
import org.xclcharts.renderer.info.AnchorDataPoint;
import org.xclcharts.view.GraphicalView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class LineChart03View  extends GraphicalView {
	
	private String TAG = "LineChart03View";
	private LineChart chart = new LineChart();
	
	//标签集合
	private LinkedList<String> labels = new LinkedList<String>();
	private LinkedList<LineData> chartData = new LinkedList<LineData>();

	public LineChart03View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
			chartLabels();
			chartDataSet();	
			chartRender();
	}
	
	 public LineChart03View(Context context, AttributeSet attrs){   
	        super(context, attrs);   
	        chartLabels();
			chartDataSet();	
			chartRender();
	 }
	 
	 public LineChart03View(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			chartLabels();
			chartDataSet();	
			chartRender();
	 }		

	@SuppressLint("NewApi")
	private void chartRender()
	{
		try {				
			setLayerType(View.LAYER_TYPE_SOFTWARE, null);
			//setLayerType(View.LAYER_TYPE_NONE, null);
				

			//设定数据源
			chart.setCategories(labels);								
			chart.setDataSource(chartData);
			
			//数据轴最大值
			chart.getDataAxis().setAxisMax(100);
			//数据轴刻度间隔
			chart.getDataAxis().setAxisSteps(10);
			
			//chart.getDataAxis().setAxisLineVisible(false);
			chart.getDataAxis().hide();
			
			chart.getCategoryAxis().getTickLabelPaint().setTextAlign(Align.LEFT);
			chart.getCategoryAxis().setTickLabelRotateAngle(90);
			
			
			chart.getAxisTitle().setLowerTitle("(年份)");	
			
			//chart.hideRightAxis();
			//chart.hideTopAxis();
			
			chart.getPlotLegend().hide();			
			
			//忽略Java的float计算误差
			chart.disableHighPrecision();
			
			//批注
			List<AnchorDataPoint> mAnchorSet = new ArrayList<AnchorDataPoint>();
			
			AnchorDataPoint an1 = new AnchorDataPoint(2,0,XEnum.AnchorStyle.RECT);
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
			
			chart.disablePanMode(); //禁掉平移，这样线上的标注框在最左和最右时才能显示全
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
	}
	private void chartDataSet()
	{
		//Line 1
		LinkedList<Double> dataSeries1= new LinkedList<Double>();	
		dataSeries1.add(20d); 
		dataSeries1.add(10d); 
		dataSeries1.add(31d); 
		dataSeries1.add(40d);
		dataSeries1.add(0d);
		LineData lineData1 = new LineData("方块",dataSeries1,Color.rgb(234, 83, 71));
		lineData1.setLabelVisible(true);		
		lineData1.setDotStyle(XEnum.DotStyle.RECT);				
		lineData1.getDotLabelPaint().setColor(Color.BLUE);
		lineData1.getDotLabelPaint().setTextSize(22);
		lineData1.getDotLabelPaint().setTextAlign(Align.LEFT);		
		//Line 2
		LinkedList<Double> dataSeries2= new LinkedList<Double>();	
		dataSeries2.add((double)30); 
		dataSeries2.add((double)42); 
		dataSeries2.add((double)50); 	
		dataSeries2.add((double)60); 
		dataSeries2.add((double)40); 
		LineData lineData2 = new LineData("圆环",dataSeries2,Color.rgb(75, 166, 51));
		lineData2.setDotStyle(XEnum.DotStyle.RING);				
		lineData2.getPlotLine().getDotPaint().setColor(Color.BLACK);
		lineData2.setLabelVisible(true);		
		//Line 3
		LinkedList<Double> dataSeries3= new LinkedList<Double>();	
		dataSeries3.add(65d);
		dataSeries3.add(75d);
		dataSeries3.add(55d);
		dataSeries3.add(65d);
		dataSeries3.add(95d);
		LineData lineData3 = new LineData("圆点",dataSeries3,Color.rgb(123, 89, 168));
		lineData3.setDotStyle(XEnum.DotStyle.DOT);
		//Line 4
		LinkedList<Double> dataSeries4= new LinkedList<Double>();	
		dataSeries4.add(50d);
		dataSeries4.add(60d);
		dataSeries4.add(80d);
		dataSeries4.add(84d);
		dataSeries4.add(90d);
		LineData lineData4 = new LineData("棱形",dataSeries4,Color.rgb(84, 206, 231));		
		lineData4.setDotStyle(XEnum.DotStyle.PRISMATIC);
		//Line 5
		LinkedList<Double> valuesE= new LinkedList<Double>();	
		valuesE.add(0d);
		valuesE.add(80d);
		valuesE.add(85d);
		valuesE.add(90d);
		LineData lineData5 = new LineData("定制",valuesE,Color.rgb(234, 142, 43));
		lineData5.setDotRadius(15);
		
		chartData.add(lineData1);
		chartData.add(lineData2);
		chartData.add(lineData3);
		chartData.add(lineData4);
		chartData.add(lineData5);
	}
	
	private void chartLabels()
	{
		labels.add("2010");
		labels.add("2011");
		labels.add("2012");
		labels.add("2013");
		labels.add("2014");
	}
	
	@Override  
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
        super.onSizeChanged(w, h, oldw, oldh);  
       //图所占范围大小
        chart.setChartRange(w,h);
    }  
	
	/*
	 * 
	 1. 右边轴view 遮住右边view视图中最左边点或线的处理办法：
	xml  FrameLayout 中 LineChart03View_left 要放到后面，放前面会盖住scrollview中的图
	HLNScrollActivity 中的horiView.setPadding()可以注释掉
	然后在 LineChart03View 中 通过设置chart.setpadding中的left来对整齐
	或通过  chart.setChartRange()中的x位置来偏移即可
	
	 2. 如果觉得左滑范围太大，可以调整 HLNScrollActivity中 的horiView.setPadding()
	 也可以调整  chart.setChartRange()中的x位置如 chart.setChartRange(60,0, ....)
	*/
	
	@Override
    public void render(Canvas canvas) {
        try{
        	        
        	//设置图表大小
	       // chart.setChartRange(0,0,
	       // chart.setChartRange(60,0,
	       // 		this.getLayoutParams().width - 10, this.getLayoutParams().height - 10);
	      //  //设置绘图区内边距	  
	      //  chart.setPadding( 0,120, 100,180 );	
	        
	        
	        // chart.setChartRange(60,0, //设置x位置为60
	        chart.setChartRange(0,0,
	        		this.getLayoutParams().width - 10, this.getLayoutParams().height - 10);
	        //设置绘图区内边距	  
	        chart.setPadding( 70,120, 100,180 );	
	    
            chart.render(canvas);
        } catch (Exception e){
        	Log.e(TAG, e.toString());
        }
    }

	@Override
	 public void onDraw(Canvas canvas){   				
	        super.onDraw(canvas);  
	        
	 }
	
	
	/*
	 @Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			
			int width = DensityUtil.dip2px(getContext(), 500);
			int height = DensityUtil.dip2px(getContext(), 500);
			
			setMeasuredDimension(width,height);		
		}
	 */
	 
	

	
	
}
