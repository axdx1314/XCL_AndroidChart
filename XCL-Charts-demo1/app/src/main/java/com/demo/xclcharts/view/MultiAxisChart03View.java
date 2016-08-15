package com.demo.xclcharts.view;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.xclcharts.chart.AreaChart;
import org.xclcharts.chart.AreaData;
import org.xclcharts.chart.LineChart;
import org.xclcharts.chart.LineData;
import org.xclcharts.chart.PieChart;
import org.xclcharts.chart.PieData;
import org.xclcharts.chart.PointD;
import org.xclcharts.chart.SplineChart;
import org.xclcharts.chart.SplineData;
import org.xclcharts.common.DensityUtil;
import org.xclcharts.event.click.PointPosition;
import org.xclcharts.renderer.XEnum;
import org.xclcharts.renderer.plot.PlotGrid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class MultiAxisChart03View extends DemoView {
	private String TAG = "MultiAxisChart03View";
	
	//用来显示面积图，左边及底部的轴
	private AreaChart chart = new AreaChart();	
	//标签集合
	private LinkedList<String> mLabels = new LinkedList<String>();
	//数据集合
	private LinkedList<AreaData> mDataset = new LinkedList<AreaData>();
	
	//用来显示折线,右边及顶部的轴
	private LineChart chartLn = new LineChart();
	private LinkedList<LineData> chartData = new LinkedList<LineData>();
	
	//曲线图，用来显示最两边的两条竖轴
	private SplineChart chartLnAxes = new SplineChart();
	private LinkedList<SplineData> chartDataAxes = new LinkedList<SplineData>();
	private LinkedList<String> mLabelsAxes = new LinkedList<String>();
	
	//饼图
	private PieChart chartPie = new PieChart();	
	private LinkedList<PieData> chartDataPie = new LinkedList<PieData>();
	
	private Paint mPaintTooltips = new Paint(Paint.ANTI_ALIAS_FLAG);
		
	public MultiAxisChart03View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}	 
	
	public MultiAxisChart03View(Context context, AttributeSet attrs){   
        super(context, attrs);   
        initView();
	 }
	 
	 public MultiAxisChart03View(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			initView();
	 }
	 
	 private void initView()
	 {
		chartLabels();
		chartLabelsAxes();
		chartDataSetPie();
		
		chartDataSet();		
		chartDataSetLn();
		chartDataSetAxes();		
		
		chartRender();
		chartRenderLn();
		chartRenderLnAxes();
		chartRenderPie();
		
		//綁定手势滑动事件
		this.bindTouch(this,chart);
		this.bindTouch(this,chartLn);
		// this.bindTouch(this,chartLnAxes);
	 }
	
	@Override  
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
        super.onSizeChanged(w, h, oldw, oldh);  
       //图所占范围大小
        chart.setChartRange(w ,h);
        chartLn.setChartRange(w ,h);
        chartLnAxes.setChartRange(w ,h);
        
        float left = DensityUtil.dip2px(getContext(), 42); 
        float top = DensityUtil.dip2px(getContext(),62); 
        
        float piewidth = Math.min(w, h) / 4;//1.5f;
        
        chartPie.setChartRange(left, top, piewidth, piewidth);
    }  
			 
	private void chartRender()
	{
		try{												 
				//设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....		
				int [] ltrb = getBarLnDefaultSpadding();
				//chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);
				float left = DensityUtil.dip2px(getContext(), 40); //left 40	
				float right = DensityUtil.dip2px(getContext(), 40); //right	20			
				chart.setPadding(left, ltrb[1],right, ltrb[3]);	//ltrb[2]
											
				//轴数据源						
				//标签轴
				chart.setCategories(mLabels);
				//数据轴
				chart.setDataSource(mDataset);
				//仅横向平移
				chart.setPlotPanMode(XEnum.PanMode.HORIZONTAL);
							
				//数据轴最大值
				chart.getDataAxis().setAxisMax(300);
				chart.getDataAxis().setAxisMin(0);
				//数据轴刻度间隔
				chart.getDataAxis().setAxisSteps(50);
				
				//网格
				chart.getPlotGrid().showHorizontalLines();
				chart.getPlotGrid().showVerticalLines();	
		
				//把轴线和刻度线给隐藏起来
				//chart.getDataAxis().hideAxisLine();
				chart.getDataAxis().hideTickMarks();		
			//	chart.getCategoryAxis().hideAxisLine();
				chart.getCategoryAxis().hideTickMarks();	
				
				chart.getDataAxis().setTickLabelRotateAngle(-90);
				chart.getDataAxis().getTickLabelPaint().setColor(Color.RED); 
				chart.getCategoryAxis().getTickLabelPaint().setColor(Color.RED);
			
				//标题
				chart.setTitle("混合图(区域、折线、饼图)");
				chart.addSubtitle("(XCL-Charts Demo)");	
				//轴标题
				//chart.getAxisTitle().setLowerAxisTitle("(时间点)");
				
				//透明度
				chart.setAreaAlpha(200);
				//显示图例
				chart.getPlotLegend().hide();
				
				//把轴线设成和横向网络线一样和大小和颜色,演示下定制性，这块问得人较多
				PlotGrid plot = chart.getPlotGrid();	
				chart.getDataAxis().getAxisPaint().setStrokeWidth(
						plot.getHorizontalLinePaint().getStrokeWidth());
				chart.getCategoryAxis().getAxisPaint().setStrokeWidth(
						plot.getHorizontalLinePaint().getStrokeWidth());
				
				chart.getDataAxis().getAxisPaint().setColor(
						plot.getHorizontalLinePaint().getColor());
				chart.getCategoryAxis().getAxisPaint().setColor(
						plot.getHorizontalLinePaint().getColor());
				
				chart.getDataAxis().getTickMarksPaint().setColor(
						plot.getHorizontalLinePaint().getColor());
				chart.getCategoryAxis().getTickMarksPaint().setColor(
						plot.getHorizontalLinePaint().getColor());
			
				plot.hideHorizontalLines();
				
				

				//激活点击监听
				chart.ActiveListenItemClick();
				//为了让触发更灵敏，可以扩大5px的点击监听范围
				chart.extPointClickRange(10);
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, e.toString());
		}
	}
	
	private void chartDataSet()
	{
	
		List<Double> dataSeries2 = new LinkedList<Double>();			
		dataSeries2.add((double)140);  //40
		dataSeries2.add((double)122); 
		dataSeries2.add((double)130); 	
		dataSeries2.add((double)135); 
		dataSeries2.add((double)115); //15
				
		AreaData line2 = new AreaData("小小熊",dataSeries2,
											Color.RED,	
											Color.YELLOW //(int)Color.rgb(254, 170, 50)
											);
		//设置线上每点对应标签的颜色
		line2.getDotLabelPaint().setColor(Color.rgb(83, 148, 235));
		//设置点标签
		line2.setLabelVisible(true);
		line2.getLabelOptions().setLabelBoxStyle(XEnum.LabelBoxStyle.CAPRECT);
		line2.getLabelOptions().setOffsetY(20.f);
		
		line2.setApplayGradient(true);
		line2.setGradientMode(Shader.TileMode.MIRROR);
		line2.setAreaBeginColor(Color.WHITE); //Color.rgb(254, 170, 50));
		line2.setAreaEndColor(Color.rgb(224, 65, 10)); 
		
	
		line2.setDotStyle(XEnum.DotStyle.RING);				
		line2.getPlotLine().getDotPaint().setColor(Color.WHITE);	
		line2.getPlotLine().getPlotDot().setRingInnerColor(Color.RED);
					
		mDataset.add(line2);			
	}
			
	private void chartRenderLn()
	{
		try {				
			
			//设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....		
			int [] ltrb = getBarLnDefaultSpadding();
			
			float left = DensityUtil.dip2px(getContext(), 40); //left 40	
			float right = DensityUtil.dip2px(getContext(), 40); //right	20			
			chartLn.setPadding(left, ltrb[1],right, ltrb[3]);	//ltrb[2]
		
		
			//设定数据源
			chartLn.setCategories(mLabels);								
			chartLn.setDataSource(chartData);
			
			//数据轴最大值
			chartLn.getDataAxis().setAxisMax(70); 
			//数据轴刻度间隔
			chartLn.getDataAxis().setAxisSteps(10);
			
			//仅横向平移
			chartLn.setPlotPanMode(XEnum.PanMode.HORIZONTAL);
			
			//背景网格
			chartLn.getPlotGrid().hideEvenRowBgColor();
			chartLn.getPlotGrid().hideHorizontalLines();
			chartLn.getPlotGrid().hideOddRowBgColor();
			chartLn.getPlotGrid().hideVerticalLines();
			
			//chartLn.getPlotGrid().showHorizontalLines();
			
			//chartLn.getDataAxis().hideAxisLine();
			chartLn.getDataAxis().hideTickMarks();		
			//chartLn.getCategoryAxis().hideAxisLine();
			chartLn.getCategoryAxis().hideTickMarks();	
			
			chartLn.getDataAxis().setTickLabelRotateAngle(-90);
			chartLn.getDataAxis().getTickLabelPaint().setColor(Color.rgb(106, 218, 92));
			chartLn.getCategoryAxis().getTickLabelPaint().setColor(Color.rgb(106, 218, 92));
			chartLn.getDataAxis().setHorizontalTickAlign(Align.RIGHT);
			chartLn.getDataAxis().getTickLabelPaint().setTextAlign(Align.LEFT);
			
			//调整轴显示位置
			chartLn.setDataAxisLocation(XEnum.AxisLocation.RIGHT);
			chartLn.setCategoryAxisLocation(XEnum.AxisLocation.TOP);
			
			
			
			//把轴线设成和横向网络线一样和大小和颜色,演示下定制性，这块问得人较多
			PlotGrid plot = chart.getPlotGrid();	
			chartLn.getDataAxis().getAxisPaint().setStrokeWidth(
					plot.getHorizontalLinePaint().getStrokeWidth());
			chartLn.getCategoryAxis().getAxisPaint().setStrokeWidth(
					plot.getHorizontalLinePaint().getStrokeWidth());
			
			chartLn.getDataAxis().getAxisPaint().setColor(
					plot.getHorizontalLinePaint().getColor());
			chartLn.getCategoryAxis().getAxisPaint().setColor(
					plot.getHorizontalLinePaint().getColor());
			
			chartLn.getDataAxis().getTickMarksPaint().setColor(
					plot.getHorizontalLinePaint().getColor());
			chartLn.getCategoryAxis().getTickMarksPaint().setColor(
					plot.getHorizontalLinePaint().getColor());
			
			//图例显示在正下方
			chartLn.getPlotLegend().setVerticalAlign(XEnum.VerticalAlign.BOTTOM);
			chartLn.getPlotLegend().setHorizontalAlign(XEnum.HorizontalAlign.CENTER);
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
	}
	
	
	private void chartDataSetLn()
	{
		
		LinkedList<Double> dataSeries0= new LinkedList<Double>();	
		dataSeries0.add(0d); 
		LineData line2 = new LineData("Area圆环",dataSeries0,Color.rgb(224, 65, 10)); //(int)Color.rgb(48, 145, 255)); 
		line2.setDotStyle(XEnum.DotStyle.RING);				
		line2.getPlotLine().getDotPaint().setColor(Color.WHITE);	
		line2.getPlotLine().getPlotDot().setRingInnerColor(Color.RED);
		//line2.getLabelOptions().hideBox();
		
		
		//Line 1
		LinkedList<Double> dataSeries1= new LinkedList<Double>();	
		dataSeries1.add(40d); 
		dataSeries1.add(35d); 
		dataSeries1.add(50d); 
		dataSeries1.add(60d);
		dataSeries1.add(55d);
		LineData lineData1 = new LineData("直线",dataSeries1,Color.rgb(106, 218, 92));
		lineData1.setLabelVisible(true);		
		lineData1.setDotStyle(XEnum.DotStyle.HIDE);		
		
		lineData1.getDotLabelPaint().setColor(Color.BLUE);
		lineData1.getDotLabelPaint().setTextSize(22);
		lineData1.getDotLabelPaint().setTextAlign(Align.LEFT);	
		lineData1.setItemLabelRotateAngle(45.f);
		lineData1.getLabelOptions().setLabelBoxStyle(XEnum.LabelBoxStyle.RECT);
		
		//Line 2
		LinkedList<Double> dataSeries2= new LinkedList<Double>();	
		dataSeries2.add((double)50); 
		dataSeries2.add((double)42); 
		dataSeries2.add((double)55); 	
		dataSeries2.add((double)65); 
		dataSeries2.add((double)58); 
		LineData lineData2 = new LineData("圆环",dataSeries2,Color.rgb(48, 145, 255));
		lineData2.setDotStyle(XEnum.DotStyle.RING);				
		lineData2.getPlotLine().getDotPaint().setColor(Color.RED);
		lineData2.setLabelVisible(true);		
		lineData2.getPlotLine().getPlotDot().setRingInnerColor(Color.GREEN);
		lineData2.getLabelOptions().setLabelBoxStyle(XEnum.LabelBoxStyle.CAPRECT);
		//lineData2.setLineStyle(XEnum.LineStyle.DASH);
		
		
		LinkedList<Double> dataSeries3= new LinkedList<Double>();	
		dataSeries3.add((double)55); 
		dataSeries3.add((double)42); 
		dataSeries3.add((double)65); 	
		dataSeries3.add((double)45);  
		LineData lineData3 = new LineData("角",dataSeries3,Color.rgb(199, 64, 219));
		lineData3.setDotStyle(XEnum.DotStyle.TRIANGLE);				
		lineData3.getLabelOptions().setLabelBoxStyle(XEnum.LabelBoxStyle.TEXT);
	
		chartData.add(line2);
		chartData.add(lineData1);
		chartData.add(lineData2);
		chartData.add(lineData3);
	}
	
	
	private void chartRenderLnAxes()
	{
		try {				
			
			//设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....		
			int [] ltrb = getBarLnDefaultSpadding();
			
			float left = DensityUtil.dip2px(getContext(), 20); //left 40	
			float right = DensityUtil.dip2px(getContext(), 20); //right	20			
			chartLnAxes.setPadding(left, ltrb[1],right, ltrb[3]);	//ltrb[2]
		
		
			//设定数据源
			chartLnAxes.setCategories(mLabelsAxes);								
			chartLnAxes.setDataSource(chartDataAxes);
			
			//数据轴最大值
			chartLnAxes.getDataAxis().setAxisMax(70); 
			//数据轴刻度间隔
			chartLnAxes.getDataAxis().setAxisSteps(10);
			
			//标签轴最大值
			chartLnAxes.setCategoryAxisMax(70);	
			//标签轴最小值
			chartLnAxes.setCategoryAxisMin(0);	
			
			chartLnAxes.getPlotLegend().hide();
			
			//背景网格
			chartLnAxes.getPlotGrid().hideEvenRowBgColor();
			chartLnAxes.getPlotGrid().hideHorizontalLines();
			chartLnAxes.getPlotGrid().hideOddRowBgColor();
			chartLnAxes.getPlotGrid().hideVerticalLines();
			
			chartLnAxes.getDataAxis().hideAxisLine();
			chartLnAxes.getDataAxis().hideTickMarks();		
			
			chartLnAxes.getCategoryAxis().hideAxisLine();
			chartLnAxes.getCategoryAxis().hideTickMarks();
			chartLnAxes.getCategoryAxis().setTickLabelRotateAngle(-90);
			
			
			chartLnAxes.getDataAxis().setTickLabelRotateAngle(-90);
			chartLnAxes.getDataAxis().getTickLabelPaint().setColor(Color.rgb(48, 145, 255));
			
			chartLnAxes.getCategoryAxis().getTickLabelPaint().setColor(Color.rgb(199, 64, 219));
			chartLnAxes.getDataAxis().setHorizontalTickAlign(Align.RIGHT);
			chartLnAxes.getDataAxis().getTickLabelPaint().setTextAlign(Align.LEFT);
					
			//调整轴显示位置
			chartLnAxes.setDataAxisLocation(XEnum.AxisLocation.RIGHT);
			chartLnAxes.setCategoryAxisLocation(XEnum.AxisLocation.LEFT);
			
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
	}
	
	private void chartLabelsAxes()
	{		
		mLabelsAxes.add("0");
		mLabelsAxes.add("10");
		mLabelsAxes.add("20");
		mLabelsAxes.add("30");
		mLabelsAxes.add("40");
		mLabelsAxes.add("50");
		mLabelsAxes.add("60");
		mLabelsAxes.add("70");
	}
	
	private void chartDataSetAxes()
	{
		//线1的数据集
		List<PointD> linePoint1 = new ArrayList<PointD>();
		linePoint1.add(new PointD(0d, 0d));
		
		SplineData dataSeries1 = new SplineData("",linePoint1,
				Color.rgb(54, 141, 238) );	
		dataSeries1.setDotStyle(XEnum.DotStyle.HIDE);
					
		//设定数据源		
		chartDataAxes.add(dataSeries1);		
	}
	
	
	private void chartLabels()
	{		
		mLabels.add("1h46'");
		mLabels.add("3h46'");
		mLabels.add("5h46'");
		mLabels.add("7h46'");
		mLabels.add("8h46'");
	}
	
	
	
	private void chartRenderPie()
	{								
		chartPie.setPadding(0,0,0,0);
		
		//标签显示(隐藏，显示在中间，显示在扇区外面)
		chartPie.setLabelStyle(XEnum.SliceLabelStyle.INSIDE);
		chartPie.getLabelPaint().setColor(Color.WHITE);
												
		chartPie.setDataSource(chartDataPie);
		
		//显示图例
		chartPie.getPlotLegend().hide();
	}
	
	private void chartDataSetPie()
	{
		/*
		chartDataPie.add(new PieData("closed","25%" ,   (0.25*100),(int)Color.rgb(155, 187, 90)));
		chartDataPie.add(new PieData("inspect","45%" ,   (0.45*100),(int)Color.rgb(191, 79, 75)));
		chartDataPie.add(new PieData("workdone","15%" , (0.15*100),(int)Color.rgb(60, 173, 213)));
		chartDataPie.add(new PieData("dispute","15%" ,  (0.15*100),(int)Color.rgb(90, 79, 88)));	
		*/
		
		
		chartDataPie.add(new PieData("closed","25%" ,   25,Color.rgb(155, 187, 90)));
		chartDataPie.add(new PieData("inspect","45%" ,   45,Color.rgb(191, 79, 75)));
		chartDataPie.add(new PieData("workdone","15%" , 15,Color.rgb(60, 173, 213)));
		chartDataPie.add(new PieData("dispute","15%" ,  15,Color.rgb(90, 79, 88)));
		
	}
	
	
	@Override
    public void render(Canvas canvas) {
        try{
            chart.render(canvas);
            chartLn.render(canvas);
            chartLnAxes.render(canvas);
            chartPie.render(canvas);
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
		PointPosition record = chart.getPositionRecord(x,y);			
		if( null == record) return;

		AreaData lData = mDataset.get(record.getDataID());
		Double lValue = lData.getLinePoint().get(record.getDataChildID());	
		
	
		
		//在点击处显示tooltip
		mPaintTooltips.setColor(Color.rgb(240, 73, 119));			
		chart.getToolTip().getBackgroundPaint().setColor(Color.GREEN);
		chart.getToolTip().setCurrentXY(x,y);
		chart.getToolTip().addToolTip(" Key:"+lData.getLineKey(),mPaintTooltips);
		chart.getToolTip().addToolTip(" Label:"+lData.getLabel(),mPaintTooltips);		
		chart.getToolTip().addToolTip(" Current Value:" +Double.toString(lValue),mPaintTooltips);
		chart.getToolTip().setAlign(Align.LEFT);
					
		this.invalidate();
		
	}


	
}

