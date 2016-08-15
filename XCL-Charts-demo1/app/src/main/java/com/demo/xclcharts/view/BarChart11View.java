
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

/**
 * @ClassName BarChart11View
 * @Description  柱形图例子(横向)
 *    来自CSDN: http://www.csdn.net/article/2015-09-15/2825707
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 */
public class BarChart11View extends DemoView {
	
	private String TAG = "BarChart11View";
	private BarChart chart = new BarChart();
	//轴数据源
	private List<String> chartLabels = new LinkedList<String>();
	private List<BarData> chartData = new LinkedList<BarData>();

	public BarChart11View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}
	
	public BarChart11View(Context context, AttributeSet attrs){   
        super(context, attrs);           
        initView();
	 }
	 
	 public BarChart11View(Context context, AttributeSet attrs, int defStyle) {
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
			chart.setPadding(ltrb[0] + DensityUtil.dip2px(this.getContext(), 40),
							ltrb[1] +  DensityUtil.dip2px(this.getContext(), 10), 
							ltrb[2], ltrb[3]);				
					
			//标题
			chart.setTitle("Cloud Computing Services\n(SOHO/SMB) - Overall Scores");
			//chart.addSubtitle("(XCL-Charts Demo)");	
			chart.setTitleAlign(XEnum.HorizontalAlign.CENTER);
			
			//数据源
			chart.setDataSource(chartData);
			chart.setCategories(chartLabels);	
			
			//数据轴
			chart.getDataAxis().setAxisMax(8);
			chart.getDataAxis().setAxisMin(6);
			chart.getDataAxis().setAxisSteps(1);		
			
			chart.getDataAxis().hideAxisLine();
			chart.getDataAxis().hideTickMarks();	
			chart.getCategoryAxis().hideTickMarks();
			
			chart.getDataAxis().setVerticalTickPosition(XEnum.VerticalAlign.TOP);
			chart.getCategoryAxis().setVerticalTickPosition(XEnum.VerticalAlign.TOP);
			
			chart.setChartDirection(XEnum.Direction.HORIZONTAL);			
			chart.setDataAxisLocation(XEnum.AxisLocation.TOP);
									
			//定义数据轴标签显示格式
			chart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack(){
	
				@Override
				public String textFormatter(String value) {
					// TODO Auto-generated method stub		
					Double tmp = Double.parseDouble(value);
					DecimalFormat df=new DecimalFormat("#0.0");
					String label = df.format(tmp).toString();				
					return (label);
				}
				
			});
			
			
			//设定格式
			chart.setItemLabelFormatter(new IFormatterDoubleCallBack() {
				@Override
				public String doubleFormatter(Double value) {
					// TODO Auto-generated method stub
					DecimalFormat df=new DecimalFormat("#0.0");					 
					String label = df.format(value).toString();
					return label;
				}});
			
			//隐藏Key
			chart.getPlotLegend().hide();
	
			
			 //提高性能
			 chart.disableHighPrecision();
			 
			 chart.setApplyBackgroundColor(true);
			 chart.setBackgroundColor(Color.BLACK);
			 
			 chart.getDataAxis().getTickLabelPaint().setColor(Color.WHITE);
			 chart.getCategoryAxis().getTickLabelPaint().setColor(Color.WHITE);
			 chart.getPlotTitle().getTitlePaint().setColor(Color.WHITE);
			 chart.getPlotTitle().getSubtitlePaint().setColor(Color.WHITE);
			 chart.getBar().getItemLabelPaint().setColor(Color.WHITE);
			 
			 chart.getPlotGrid().getVerticalLinePaint().setColor(Color.rgb(106, 194, 57));
			
			 chart.getPlotGrid().getHorizontalLinePaint().setColor(Color.rgb(106, 194, 57));
			// chart.getPlotGrid().showHorizontalLines();
			 chart.getPlotGrid().showVerticalLines();
			 
			 chart.getBar().setBarStyle(XEnum.BarStyle.FILL);
			 chart.getBar().setItemLabelVisible(true);
			 chart.getBar().getItemLabelPaint().setTextSize(22);
			 chart.getBar().setItemLabelStyle(XEnum.ItemLabelStyle.INNER);
			 chart.getCategoryAxis().getAxisPaint().setColor(Color.rgb(106, 194, 57));
			 
			 chart.getDataAxis().setVerticalTickPosition(XEnum.VerticalAlign.TOP);
			 
			 chart.ActiveListenItemClick();
			 					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void chartDataSet()
	{
		//标签对应的柱形数据集
		List<Double> dataSeriesA= new LinkedList<Double>();	
		//依数据值确定对应的柱形颜色.
		List<Integer> dataColorA= new LinkedList<Integer>();	
	
		dataSeriesA.add(7.9d);
		dataColorA.add(Color.rgb(205, 5, 5));	
		
		dataSeriesA.add(7.8d);
		dataColorA.add(Color.rgb(205, 5, 5));
		
		dataSeriesA.add(7.3d);
		dataColorA.add(Color.rgb(203, 203, 203));	
		
		dataSeriesA.add(6.9d);
		dataColorA.add(Color.rgb(203, 203, 203));	
		dataSeriesA.add(6.9d);
		dataColorA.add(Color.rgb(203, 203, 203));	
		
		dataSeriesA.add(6.4d);
		dataColorA.add(Color.rgb(203, 203, 203));	
		
		dataSeriesA.add(7.2d);
		dataColorA.add(Color.rgb(137, 137, 137));	
	
		
		//此地的颜色为Key值颜色及柱形的默认颜色
		BarData BarDataA = new BarData("",dataSeriesA,dataColorA,
										Color.rgb(53, 169, 239));
		
		chartData.add(BarDataA);
	}
	
	private void chartLabels()
	{				
		chartLabels.add("Carbonite");
		chartLabels.add("Dropbox");
		chartLabels.add("Google Drive");
		chartLabels.add("Microsoft OneDrive");
		chartLabels.add("Box");
		chartLabels.add("SugarSync");
		chartLabels.add("AVERAGE");
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