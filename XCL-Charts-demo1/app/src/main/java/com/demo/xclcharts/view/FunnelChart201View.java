package com.demo.xclcharts.view;

import java.util.ArrayList;
import java.util.List;

import org.xclcharts.chart.Funnel2Data;
import org.xclcharts.chart.FunnelChart2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;


/**
 * @ClassName FunnelChart201View
 * @Description  漏斗图例子 
 *  
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 */
public class FunnelChart201View extends DemoView {
	
	private String TAG = "FunnelChart201View";
	private FunnelChart2 chart = new FunnelChart2();
	private List<Funnel2Data> chartData = new ArrayList<Funnel2Data>();
	
	public FunnelChart201View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}
	
	public FunnelChart201View(Context context, AttributeSet attrs){   
        super(context, attrs);   
        initView();
	 }
	 
	 public FunnelChart201View(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			initView();
	 }
	 
	 private void initView()
	 {
		 	chartDataSet();	
			chartRender();
			//綁定手势滑动事件
			this.bindTouch(this,chart);
	 }
	
	 private void chartRender()
		{
			try {
				
				//设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....		
				int [] ltrb = getBarLnDefaultSpadding();
				chart.setPadding(ltrb[0],ltrb[1], ltrb[2],  ltrb[3]);	
			
						
				//标题
				chart.setTitle("漏斗图(另一种)");
				chart.addSubtitle("(XCL-Charts Demo)");	
				
				//数据源
				chart.setDataSource(chartData);				
				chart.getPaintLabel().setTextSize(22);				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	 
	 
		private void chartDataSet()
		{	
			 chartData.add(new Funnel2Data("底层"  ,0.4f, 0.1f,Color.rgb(163, 73, 164)));
			 chartData.add(new Funnel2Data("中层"  ,0.2f, 0.2f,Color.rgb(34, 177, 76)));
			 chartData.add(new Funnel2Data("高层"  ,0.3f, 0.3f,Color.rgb(255, 242, 0)));
			 chartData.add(new Funnel2Data("顶层 "  ,0.1f, 0.4f,Color.rgb(237, 28, 36)));		 		 
		}
		
		
	 
	 @Override  
	    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
	        super.onSizeChanged(w, h, oldw, oldh);  
	       //图所占范围大小
	       chart.setChartRange(w,h);
	    }  
		
		@Override
	    public void render(Canvas canvas) {
	        try{
	            chart.render(canvas);
	            
	        } catch (Exception e){
	        	Log.e(TAG, e.toString());
	        }
	    }
		

}
