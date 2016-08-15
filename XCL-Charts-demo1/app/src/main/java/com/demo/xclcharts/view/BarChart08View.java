package com.demo.xclcharts.view;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.xclcharts.chart.BarChart;
import org.xclcharts.chart.BarData;
import org.xclcharts.chart.CustomLineData;
import org.xclcharts.common.DrawHelper;
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

public class BarChart08View extends DemoView{
	
	private String TAG = "BarChart08View";
	private BarChart chart = new BarChart();
	//轴数据源
	private List<String> chartLabels = new LinkedList<String>();
	private List<BarData> chartData = new LinkedList<BarData>();
	private List<CustomLineData> mCustomLineDataset = new LinkedList<CustomLineData>();
	
	public BarChart08View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}
	
	public BarChart08View(Context context, AttributeSet attrs){   
        super(context, attrs);   
        initView();
	 }
	 
	 public BarChart08View(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			initView();
	 }
	 
	 private void initView()
	 {
		 	chartLabels();
			chartDataSet();
			chartDesireLines();
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
			
			//显示边框
			chart.showRoundBorder();
					
			//标题
			chart.setTitle("正负背向式图");
			chart.addSubtitle("(XCL-Charts Demo)");	
			//数据源
			chart.setDataSource(chartData);
			chart.setCategories(chartLabels);	
			chart.setCustomLines(mCustomLineDataset);
			
			//图例
			//chart.getAxisTitle().setLeftAxisTitle("参考成年男性标准值");
			//chart.getAxisTitle().setLowerAxisTitle("(请不要忽视您的健康)");
			
			//数据轴
			chart.getDataAxis().setAxisMax(40);
			chart.getDataAxis().setAxisMin(-15);
			chart.getDataAxis().setAxisSteps(5);		
			//指隔多少个轴刻度(即细刻度)后为主刻度
			chart.getDataAxis().setDetailModeSteps(2);
			
			
			chart.getDataAxis().enabledAxisStd();
			chart.getDataAxis().setAxisStd(5);
			chart.getCategoryAxis().setAxisBuildStd(true);
			  			
			//背景网格
			chart.getPlotGrid().showHorizontalLines();
			
			//定义数据轴标签显示格式
			chart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack(){
	
				@Override
				public String textFormatter(String value) {
					// TODO Auto-generated method stub		
					Double tmp = Double.parseDouble(value);
					DecimalFormat df=new DecimalFormat("#0");
					String label = df.format(tmp).toString();				
					return (label);
				}
				
			});
			
			//标签旋转45度
		//	chart.getCategoryAxis().setTickLabelRotateAngle(45f);
			chart.getCategoryAxis().getTickLabelPaint().setTextSize(15);
			
			//在柱形顶部显示值
			chart.getBar().setItemLabelVisible(true);
			//设定格式
			chart.setItemLabelFormatter(new IFormatterDoubleCallBack() {
				@Override
				public String doubleFormatter(Double value) {
					// TODO Auto-generated method stub
					DecimalFormat df=new DecimalFormat("#0");					 
					String label = df.format(value).toString();
					return label;
				}});
			
			//隐藏Key
			chart.getPlotLegend().hide();
			
			 //让柱子间没空白
			 chart.getBar().setBarInnerMargin(0.1f); //可尝试0.1或0.5各有啥效果噢
			
			 
			 //禁用平移模式
			// chart.disablePanMode();
			 
			 chart.disableHighPrecision();
			 
			//限制只能左右滑动
			chart.setPlotPanMode(XEnum.PanMode.HORIZONTAL);	
			
			chart.setBarCenterStyle(XEnum.BarCenterStyle.TICKMARKS);
			 
			// chart.showRoundBorder();
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
		
		int max = 35;
	    int min = -15;
	        
		for(int i=1;i<10;i++) 
		{
			Random random = new Random();
			int v = random.nextInt(max)%(max-min+1) + min;			 
			dataSeriesA.add((double) v);
			
			if(v <= -5d ) //适中
			{
				dataColorA.add(Color.rgb(77, 184, 73));				
			}else if(v <= 5d){ //超重
				dataColorA.add(Color.rgb(252, 210, 9));
			}else if(v <= 10d){ //偏胖
				dataColorA.add(Color.rgb(171, 42, 96));	
			}else{  //肥胖
				dataColorA.add(Color.RED);
			}
		}  
		//此地的颜色为Key值颜色及柱形的默认颜色
		BarData BarDataA = new BarData("",dataSeriesA,dataColorA,
										Color.rgb(53, 169, 239));
		
		chartData.add(BarDataA);
	}
	
	private void chartLabels()
	{		
		for(int i=1;i<10;i++)
		{
			if(1 == i || i%5 == 0)
			{
				chartLabels.add(Integer.toString(i));
			}else{
				chartLabels.add("");
			}
		}
	}	
	
	/**
	 * 期望线/分界线
	 */
	private void chartDesireLines()
	{							
		mCustomLineDataset.add(new CustomLineData("损失",-5d,Color.rgb(77, 184, 73),3));		
		//mCustomLineDataset.add(new CustomLineData("超重",6d,(int)Color.rgb(252, 210, 9),4));
		mCustomLineDataset.add(new CustomLineData("平衡",10d,Color.rgb(171, 42, 96),5));	
		mCustomLineDataset.add(new CustomLineData("良好",15d,Color.RED,6));
								
	}
	
	private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	
	@Override
    public void render(Canvas canvas) {
        try{
            chart.render(canvas);
            
            
            paint.setTextSize(22);
            paint.setColor(Color.RED);
            
            float textHeight = DrawHelper.getInstance().getPaintFontHeight(paint);
            paint.setTextAlign(Align.LEFT);
            canvas.drawText("Y轴标题",chart.getPlotArea().getLeft(), chart.getPlotArea().getTop() - textHeight ,paint);
            
            paint.setTextAlign(Align.RIGHT);
            canvas.drawText("X轴标题",chart.getPlotArea().getRight(), chart.getPlotArea().getBottom() + textHeight ,paint);
            
        } catch (Exception e){
        	Log.e(TAG, e.toString());
        }
    }

}
