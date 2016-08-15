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
import java.util.ArrayList;
import java.util.List;

import org.xclcharts.chart.BarChart;
import org.xclcharts.chart.BarData;
import org.xclcharts.chart.CustomLineData;
import org.xclcharts.chart.PieChart;
import org.xclcharts.chart.PieData;
import org.xclcharts.common.DensityUtil;
import org.xclcharts.common.IFormatterDoubleCallBack;
import org.xclcharts.common.IFormatterTextCallBack;
import org.xclcharts.event.click.BarPosition;
import org.xclcharts.renderer.XEnum;
import org.xclcharts.renderer.info.AnchorDataPoint;
import org.xclcharts.renderer.info.DyLine;
import org.xclcharts.renderer.info.Legend;
import org.xclcharts.renderer.line.PlotDot;

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
 * @ClassName BarChart01View
 * @Description  柱形图例子(竖向) 
 *  
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 */

public class BarChart01View extends DemoView implements Runnable{ //DemoView
	
	private String TAG = "BarChart01View";
	private BarChart chart = new BarChart();
	
	//标签轴
	private List<String> chartLabels = new ArrayList<String>();
	private List<BarData> chartData = new ArrayList<BarData>();
	
	Paint mPaintToolTip = new Paint(Paint.ANTI_ALIAS_FLAG);
	PlotDot mDotToolTip = new PlotDot();
	
	private List<CustomLineData> mCustomLineDataset = new ArrayList<CustomLineData>();

	private int colorORACLE = Color.rgb(186, 20, 26);
	private int colorMSSQL = Color.rgb(1, 188, 242);
	private int colorMYSQL = Color.rgb(0, 75, 106);
	private int colorOTHER = Color.rgb(27, 188, 155);		
	
	private int colorTitalAxes = Color.rgb(244, 109, 67);
	private int colorPlotArea = Color.rgb(254, 224, 144); 
	
	
	private PieChart chartPie = new PieChart();	
	private ArrayList<PieData> chartDataPie = new ArrayList<PieData>();
	
	
	public BarChart01View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();				
	}
	
	public BarChart01View(Context context, AttributeSet attrs){   
        super(context, attrs);   
        initView();
	 }
	 
	 public BarChart01View(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			initView();
	 }
	 
	 private void initView()
	 {
		 	chartLabels();
			chartDataSet();
			chartRender();
			
			//饼图
			chartPieDataSet();
			chartPieRender();	
			
			//綁定手势滑动事件
			this.bindTouch(this,chart);
			this.bindTouch(this,chartPie);
						
			new Thread(this).start();
	 }
	
	@Override  
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
        super.onSizeChanged(w, h, oldw, oldh);  
       //图所占范围大小
        chart.setChartRange(w,h); // + w * 0.5f                        
    }  
	
		 	
	private void chartRender()
	{
		try {								
			//设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....		
			int [] ltrb = getBarLnDefaultSpadding();
			chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);		
						
			//数据源
			chart.setDataSource(chartData);
			chart.setCategories(chartLabels);	
												
			//轴标题
			chart.getAxisTitle().setLeftTitle("数据库");
			chart.getAxisTitle().setLowerTitle("分布位置");
			
			//数据轴
			chart.getDataAxis().setAxisMax(100);
			chart.getDataAxis().setAxisMin(0);
			chart.getDataAxis().setAxisSteps(5);						
			
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
			
			 //让柱子间没空白
			 //chart.getBar().setBarInnerMargin(0d);
		
			
			
			//轴颜色			
			chart.getDataAxis().getAxisPaint().setColor(colorTitalAxes);
			chart.getCategoryAxis().getAxisPaint().setColor(colorTitalAxes);			
			chart.getDataAxis().getTickMarksPaint().setColor(colorTitalAxes);
			chart.getCategoryAxis().getTickMarksPaint().setColor(colorTitalAxes);
			
			chart.getDataAxis().getTickLabelPaint().setColor(colorTitalAxes);
			chart.getCategoryAxis().getTickLabelPaint().setColor(colorTitalAxes);
			
			chart.getAxisTitle().getLeftTitlePaint().setColor(colorTitalAxes);	
			chart.getAxisTitle().getLowerTitlePaint().setColor(colorTitalAxes);	
			
			chart.getBar().getItemLabelPaint().setColor(Color.rgb(246, 133, 39));	
			chart.getBar().getItemLabelPaint().setTextSize(15);
		
								
			//指隔多少个轴刻度(即细刻度)后为主刻度
			chart.getDataAxis().setDetailModeSteps(5);						  			
  			
			//显示十字交叉线
			chart.showDyLine();
			DyLine dyl = chart.getDyLine();
			if( null != dyl)
			{
				dyl.setDyLineStyle(XEnum.DyLineStyle.Horizontal);
				dyl.setLineDrawStyle(XEnum.LineStyle.DASH);
			}
			
			//数据轴居中显示
			//chart.setDataAxisLocation(XEnum.AxisLocation.VERTICAL_CENTER);
			
			//忽略Java的float计算误差，提高性能
			//chart.disableHighPrecision();
			
			//柱形和标签居中方式
			// chart.setBarCenterStyle(XEnum.BarCenterStyle.TICKMARKS);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "chartRender():"+e.toString());
		}
	}
	
			
	private void chartDataSet()
	{
		//标签对应的柱形数据集
		List<Double> dataSeriesA= new ArrayList<Double>();	
		dataSeriesA.add(66d); 
		dataSeriesA.add(33d); 
		dataSeriesA.add(50d);
		BarData BarDataA = new BarData("Oracle",dataSeriesA,colorORACLE);
		
		
		List<Double> dataSeriesB= new ArrayList<Double>();	
		dataSeriesB.add(0.d); //32
		dataSeriesB.add(25d);
		dataSeriesB.add(18d);
		BarData BarDataB = new BarData("SQL Server",dataSeriesB,colorMSSQL);
		
		List<Double> dataSeriesC= new ArrayList<Double>();	
		dataSeriesC.add(79d);
		dataSeriesC.add(91d);
		dataSeriesC.add(65d);
		BarData BarDataC = new BarData("MySQL",dataSeriesC,colorMYSQL); 
		
		List<Double> dataSeriesD= new ArrayList<Double>();	
		dataSeriesD.add(52d);
		dataSeriesD.add(45d);
		dataSeriesD.add(35d);
		BarData BarDataD = new BarData("其它类型",dataSeriesD,colorOTHER);
				
		chartData.add(BarDataA);
		chartData.add(BarDataB);
		chartData.add(BarDataC);
		chartData.add(BarDataD);
	}
	
	private void chartLabels()
	{
		chartLabels.add("福田数据中心"); 
		chartLabels.add("西丽数据中心"); 
		chartLabels.add("观澜数据中心"); 
	}	
		
	@Override
    public void render(Canvas canvas) {
        try{        	
           chart.render(canvas);    
           
           chartPie.render(canvas); 
           
        } catch (Exception e){
        	Log.e(TAG, e.toString());
        }
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
			chart.getDataAxis().hide();
			chart.getPlotLegend().hide();
          
          	int [] ltrb = getBarLnDefaultSpadding();          	
          	for(int i=8; i> 0 ;i--)
          	{
          		Thread.sleep(100);
          		chart.setPadding(ltrb[0],i *  ltrb[1], ltrb[2], ltrb[3]);	    	
          		
          		if(1 == i)
          		{          			
          			drawLast();
          			drawDyLegend();
          		}
          		postInvalidate();    
          	} 
          	                 
          }
          catch(Exception e) {
              Thread.currentThread().interrupt();
          }            
	}
	
	private void drawLast()
	{		
		//标题
		chart.setTitle("数据库统计");
		chart.addSubtitle("(XCL-Charts Demo)");	
		chart.getPlotTitle().getTitlePaint().setColor(colorTitalAxes);
		chart.getPlotTitle().getSubtitlePaint().setColor(colorTitalAxes); 
		
		//激活点击监听
		chart.ActiveListenItemClick();
		chart.showClikedFocus();
		
		//扩展横向显示范围,当数据太多时可用这个扩展实际绘图面积
		//chart.getPlotArea().extWidth(200f);		
		
		//禁用平移模式
		chart.disablePanMode();
		//限制只能左右滑动
		//chart.setPlotPanMode(XEnum.PanMode.HORIZONTAL);	
				
		//禁用双指缩放
		//chart.disableScale();
		
		chart.getDataAxis().show();		 
		chart.getPlotLegend().show();	
		
		//当值与轴最小值相等时，不显示轴
		chart.hideBarEqualAxisMin();
		
		//批注
		List<AnchorDataPoint> mAnchorSet = new ArrayList<AnchorDataPoint>();
		
		AnchorDataPoint an1 = new AnchorDataPoint(2,0,XEnum.AnchorStyle.CIRCLE);
		an1.setAlpha(200);
		an1.setBgColor(Color.rgb(203, 189, 231));
		//an1.setAreaStyle(XEnum.DataAreaStyle.FILL);
		
		AnchorDataPoint an2 = new AnchorDataPoint(1,1,XEnum.AnchorStyle.CIRCLE); //CIRCLE
		an2.setBgColor(Color.GRAY);
		
		AnchorDataPoint an3 = new AnchorDataPoint(0,2,XEnum.AnchorStyle.RECT); //CAPROUNDRECT  RECT
		an3.setBgColor(Color.rgb(255, 145, 126));
		
		AnchorDataPoint an4 = new AnchorDataPoint(0,1,XEnum.AnchorStyle.CAPRECT);
		an4.setBgColor(Color.rgb(255, 145, 126));
		an4.setAnchor("我是批注");
				
		mAnchorSet.add(an1);
		mAnchorSet.add(an2);
		mAnchorSet.add(an3);
		mAnchorSet.add(an4);
		chart.setAnchorDataPoint(mAnchorSet);	
				
		chart.setApplyBackgroundColor(true); 
		chart.setBackgroundColor(XEnum.Direction.VERTICAL,Color.rgb(69, 117, 180),Color.rgb(224, 243, 248));  //Color.rgb(17, 162, 255),Color.rgb(163, 219, 254));//Color.WHITE);				
		chart.getBorder().setBorderLineColor(Color.rgb(181, 64, 1));
		chart.getBorder().getLinePaint().setStrokeWidth(3);
								
		chart.getPlotArea().setBackgroundColor(true, colorPlotArea);
		
		//chart.getPlotArea().setApplayGradient(true);
		//chart.getPlotArea().setGradientDirection(XEnum.Direction.VERTICAL);
		//chart.getPlotArea().setBeginColor(Color.rgb(116, 174, 210)); 		
		//chart.getPlotArea().setEndColor(Color.WHITE);
		chart.showRoundBorder();
		
		CustomLineData line1 = new CustomLineData("分界",60d,Color.rgb(218, 198, 61),7);
		line1.setCustomLineCap(XEnum.DotStyle.HIDE);		
		line1.setLabelHorizontalPostion(Align.RIGHT);
		//line1.setLabelOffset(15);	
		line1.getLineLabelPaint().setColor(Color.RED);
		mCustomLineDataset.add(line1);
		chart.setCustomLines(mCustomLineDataset);
		
		
		//饼图 
		float pieWH = DensityUtil.dip2px(getContext(), 70);	
		float pieX = chart.getPlotArea().getRight() - pieWH * 3;	
		chartPie.setChartRange(pieX,pieWH, pieWH,pieWH);			
	}
	
	private void drawDyLegend()
	{
		
		Legend dyLegend = chart.getDyLegend();		
		if(null == dyLegend) return;
		dyLegend.setPosition(0.8f,0.5f);
		if(chart.getPlotArea().getHeight() > chart.getPlotArea().getWidth())
		{
			dyLegend.setPosition(0.6f,0.5f);
		}
		//dyLegend.setColSpan(30.f);
		dyLegend.getBackgroundPaint().setColor(Color.BLACK);
		dyLegend.getBackgroundPaint().setAlpha(100);
		dyLegend.setRowSpan(20.f);
		dyLegend.setMargin(15.f);
		dyLegend.setStyle(XEnum.DyInfoStyle.ROUNDRECT);
		
		Paint pDyLegend = new Paint(Paint.ANTI_ALIAS_FLAG);
		pDyLegend.setColor(Color.GREEN);			
		PlotDot dotDyLegend = new PlotDot();
		dotDyLegend.setDotStyle(XEnum.DotStyle.RECT);		
		dyLegend.addLegend(dotDyLegend, "库可用xxx(PB)", pDyLegend);
		
		Paint pDyLegend2 = new Paint(Paint.ANTI_ALIAS_FLAG);
		pDyLegend2.setColor(Color.RED);
		dyLegend.addLegend(dotDyLegend, "库已用xxx(PB)", pDyLegend2);
		
		Paint pDyLegend3 = new Paint(Paint.ANTI_ALIAS_FLAG);
		pDyLegend3.setColor(Color.CYAN);	
		dyLegend.addLegend(dotDyLegend,"未分配xxx(PB)", pDyLegend3);
		
		Paint pDyLegend4 = new Paint(Paint.ANTI_ALIAS_FLAG);
		pDyLegend4.setColor(Color.YELLOW);		
		dyLegend.addLegend("总计:xxx(PB)", pDyLegend4);
			
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
			//交叉线
			if(chart.getDyLineVisible()&&chart.getDyLine().isInvalidate())this.invalidate();
		}else{							
			BarPosition record = chart.getPositionRecord(x,y);			
			if( null == record)
			{
				if(chart.getDyLineVisible())this.invalidate();
				return;
			}
			
			if(record.getDataID() >= chartData.size()) return;
			BarData bData = chartData.get(record.getDataID());	
			
			if(record.getDataChildID() >= bData.getDataSet().size())return;
			Double bValue = bData.getDataSet().get(record.getDataChildID());			
	
			//显示选中框
			chart.showFocusRectF(record.getRectF());		
			chart.getFocusPaint().setStyle(Style.STROKE);
			chart.getFocusPaint().setStrokeWidth(3);		
			chart.getFocusPaint().setColor(Color.GREEN);							
			
			
			//在点击处显示tooltip
			mPaintToolTip.setAntiAlias(true);
			mPaintToolTip.setColor(bData.getColor());	
			
			
			mDotToolTip.setDotStyle(XEnum.DotStyle.RECT);	
			mDotToolTip.setColor(Color.BLUE); //bData.getColor());
			
			//位置显示方法一:
			//用下列方法可以让tooltip显示在柱形顶部
			//chart.getToolTip().setCurrentXY(record.getRectF().centerX(),record.getRectF().top);
			//位置显示方法二:
			//用下列方法可以让tooltip在所点击位置显示
			chart.getToolTip().setCurrentXY(x,y);
			chart.getToolTip().setStyle(XEnum.DyInfoStyle.ROUNDRECT);		
			chart.getToolTip().addToolTip(mDotToolTip,bData.getKey(), mPaintToolTip);
			chart.getToolTip().addToolTip(
						"数量:" +Double.toString(bValue),mPaintToolTip);
			chart.getToolTip().getBackgroundPaint().setAlpha(100);
			chart.getToolTip().setAlign(Align.CENTER);
			
			chart.getToolTip().setInfoStyle(XEnum.DyInfoStyle.CIRCLE);
			//chart.getToolTip().getBackgroundPaint().setColor(Color.rgb(30, 30, 30));
			this.invalidate();
		}
		
	}
	
	private void chartPieRender()
	{
		try {				
			//标签显示(隐藏，显示在中间，显示在扇区外面)
			chartPie.setLabelStyle(XEnum.SliceLabelStyle.INSIDE);
			chartPie.getLabelPaint().setColor(Color.WHITE);
			
			chartPie.setDataSource(chartDataPie);
			
			chartPie.getPlotLegend().hide();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "chartPieRender():"+e.toString());
		}
	}
	

	private void chartPieDataSet()
	{
		//演示用，不精准计算，生产环境应使用MathHelper相关类来时行计算				
		//随便弄的
		chartDataPie.clear();
		chartDataPie.add(new PieData("ORACLE","27%" , 27,colorORACLE));
		chartDataPie.add(new PieData("SQL Server","8%" ,8,colorMSSQL));
		chartDataPie.add(new PieData("MySQL","42%" ,42,colorMYSQL));
		chartDataPie.add(new PieData("其它","23%" ,23,colorOTHER));
	}
	
}
