package com.demo.xclcharts.view;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import org.xclcharts.chart.BarChart;
import org.xclcharts.chart.BarData;
import org.xclcharts.common.DensityUtil;
import org.xclcharts.common.IFormatterDoubleCallBack;
import org.xclcharts.common.IFormatterTextCallBack;
import org.xclcharts.event.click.BarPosition;
import org.xclcharts.renderer.XEnum;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

public class BarChart09View extends DemoView{

	private static final String TAG = "BarChart09View";
	private BarChart chart = new BarChart();
	
	//标签轴
	private List<String> chartLabels = new LinkedList<String>();
	private List<BarData> chartData = new LinkedList<BarData>();

	public BarChart09View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}
	
	public BarChart09View(Context context, AttributeSet attrs){   
        super(context, attrs);           
        initView();
	 }
	 
	 public BarChart09View(Context context, AttributeSet attrs, int defStyle) {
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
        if(null !=chart)chart.setChartRange(w,h);
    }  
	
	
	private void chartRender()
	{
		try {
			//设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....	
			int [] ltrb = getBarLnDefaultSpadding();
			chart.setPadding(DensityUtil.dip2px(getContext(), 50),ltrb[1], ltrb[2],  ltrb[3]);
			
	
			chart.setTitle("正负背向式图(横向)");
			chart.addSubtitle("(XCL-Charts Demo)");		
			chart.setTitleVerticalAlign(XEnum.VerticalAlign.MIDDLE);
			//chart.setTitleAlign(XEnum.HorizontalAlign.LEFT);
		
			//数据源
			chart.setDataSource(chartData);
			chart.setCategories(chartLabels);	
			
			//轴标题
			chart.getAxisTitle().setLeftTitle("小于230");
			chart.getAxisTitle().setLowerTitle("营收");
			chart.getAxisTitle().setRightTitle("超出230");
			
			//数据轴
			chart.getDataAxis().setAxisMax(500);
			chart.getDataAxis().setAxisMin(100);
			chart.getDataAxis().setAxisSteps(100);
						
			chart.getDataAxis().enabledAxisStd();
			chart.getDataAxis().setAxisStd(230);
			chart.getCategoryAxis().setAxisBuildStd(true);
			
			chart.getDataAxis().hideTickMarks();
			
									
			chart.getDataAxis().getTickLabelPaint().
									setColor(Color.rgb(199, 88, 122));
			chart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack(){

				@Override
				public String textFormatter(String value) {
					// TODO Auto-generated method stub				
					String tmp = value+"万元";
					return tmp;
				}
				
			});
			
			//网格
			chart.getPlotGrid().showHorizontalLines();
			chart.getPlotGrid().hideVerticalLines();
			chart.getPlotGrid().hideEvenRowBgColor();
			
			//标签轴文字旋转-45度
			chart.getCategoryAxis().setTickLabelRotateAngle(-45f);
			//横向显示柱形
			chart.setChartDirection(XEnum.Direction.HORIZONTAL);
			//在柱形顶部显示值
			chart.getBar().setItemLabelVisible(true);
			chart.getBar().getItemLabelPaint().setTextSize(22);
			
			chart.setItemLabelFormatter(new IFormatterDoubleCallBack() {
				@Override
				public String doubleFormatter(Double value) {
					// TODO Auto-generated method stub
					DecimalFormat df=new DecimalFormat("[#0]");					 
					String label = df.format(value).toString();
					return label;
				}});	 
				  
			//激活点击监听
			chart.ActiveListenItemClick();
			chart.showClikedFocus();
			
			chart.disablePanMode();
			//chart.getDataAxis().setVerticalTickPosition(XEnum.VerticalAlign.TOP);			
			chart.setDataAxisLocation(XEnum.AxisLocation.TOP);
			
			chart.getPlotLegend().hide();			
			chart.getBar().setBarStyle(XEnum.BarStyle.FILL);
			
			/*
			chart.setDataAxisPosition(XEnum.DataAxisPosition.BOTTOM);
			chart.getDataAxis().setVerticalTickPosition(XEnum.VerticalAlign.BOTTOM);
			
			chart.setCategoryAxisPosition(XEnum.CategoryAxisPosition.LEFT);
			chart.getCategoryAxis().setHorizontalTickAlign(Align.LEFT);
			*/
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
		
			
	}
	private void chartDataSet()
	{
		//标签对应的柱形数据集
		List<Double> dataSeriesA= new LinkedList<Double>();	
		dataSeriesA.add((double)200); 
		dataSeriesA.add((double)250); 
		dataSeriesA.add((double)400);
		BarData BarDataA = new BarData("小熊",dataSeriesA,Color.rgb(0, 0,255));
		
		
		List<Double> dataSeriesB= new LinkedList<Double>();	
		dataSeriesB.add((double)300);
		dataSeriesB.add((double)150);
		dataSeriesB.add((double)450);
		BarData BarDataB = new BarData("小周",dataSeriesB,Color.rgb(255, 0, 0));
		
		
		chartData.add(BarDataA);
		chartData.add(BarDataB);
	}
	
	private void chartLabels()
	{		
		chartLabels.add("擂茶"); 
		chartLabels.add("槟榔"); 				
		chartLabels.add("纯净水"); 
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
		BarPosition record = chart.getPositionRecord(x,y);			
		if( null == record) return;
		
		BarData bData = chartData.get(record.getDataID());					
		Double bValue = bData.getDataSet().get(record.getDataChildID());			

		Toast.makeText(this.getContext(),
				"info:" + record.getRectInfo() +
				" Key:" + bData.getKey() + 							
				" Current Value:" + Double.toString(bValue), 
				Toast.LENGTH_SHORT).show();		
			
		chart.showFocusRectF(record.getRectF());		
		chart.getFocusPaint().setStyle(Style.STROKE);
		chart.getFocusPaint().setStrokeWidth(3);		
		chart.getFocusPaint().setColor(Color.GREEN);	
		this.invalidate();
	}
	
}
