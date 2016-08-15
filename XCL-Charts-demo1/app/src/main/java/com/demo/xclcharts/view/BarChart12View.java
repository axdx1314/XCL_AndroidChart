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
 * @license http://www.apache.org/licenses/  Apache v2 License
 * @version 2.4
 */
package com.demo.xclcharts.view;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import org.xclcharts.chart.BarChart;
import org.xclcharts.chart.BarData;
import org.xclcharts.common.DensityUtil;
import org.xclcharts.common.IFormatterDoubleCallBack;
import org.xclcharts.common.IFormatterTextCallBack;
import org.xclcharts.renderer.XEnum;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;

/**
 * @ClassName BarChart03View
 * @Description 用于展示定制线与明细刻度线
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * MODIFIED    YYYY-MM-DD   REASON
 */
public class BarChart12View extends DemoView{
	
	private String TAG = "BarChart12View";
	private BarChart chart = new BarChart();
	//轴数据源
	private List<String> chartLabels = new LinkedList<String>();
	private List<BarData> chartData = new LinkedList<BarData>();
	
	private BarChart chart2 = new BarChart();
	private List<String> chartLabels2 = new LinkedList<String>();
	private List<BarData> chartData2 = new LinkedList<BarData>();
	
	public BarChart12View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
		
	}
	
	public BarChart12View(Context context, AttributeSet attrs){   
        super(context, attrs);   
        initView();
	 }
	 
	 public BarChart12View(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			initView();
	 }
	 
	 private void initView()
	 {
		 	chartLabels();
			chartDataSet();
			
			
			chartLabels2();
			chartDataSet2();
			
			chartRender();
			chartRender2();
			
			
			//綁定手势滑动事件
			this.bindTouch(this,chart);
			this.bindTouch(this,chart2);
	 }
	 
	
	@Override  
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
        super.onSizeChanged(w, h, oldw, oldh);  
       //图所占范围大小
        chart.setChartRange(w,h);
        chart2.setChartRange(w,h);
    }  
	
	
	private void chartRender()
	{
		try {
			
			//设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....		
			int [] ltrb = getBarLnDefaultSpadding();
			chart.setPadding(ltrb[0], ltrb[1], 
					ltrb[2], ltrb[3]+  DensityUtil.dip2px(this.getContext(), 30));	
			
					
			//标题
			chart.setTitle("身份认证方案");
			chart.addSubtitle("(XCL-Charts Demo)");	
			//数据源
			chart.setCategories(chartLabels);	
			chart.setDataSource(chartData);
			
			//数据轴
			chart.getDataAxis().setAxisMax(105);
			chart.getDataAxis().setAxisMin(0);
			chart.getDataAxis().setAxisSteps(5);	
			
			
			//指隔多少个轴刻度(即细刻度)后为主刻度
			chart.getDataAxis().setDetailModeSteps(5);
			
			//背景网格
			chart.getPlotGrid().hideHorizontalLines();
			chart.getPlotGrid().hideVerticalLines();
			chart.getPlotGrid().hideEvenRowBgColor();
			chart.getPlotGrid().hideOddRowBgColor();
									
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
			chart.getBar().setItemLabelStyle(XEnum.ItemLabelStyle.INNER);
			chart.getBar().getItemLabelPaint().setColor(Color.rgb(162, 53, 46));
			chart.getBar().getItemLabelPaint().setTextSize(20);
		
			chart.getBar().setBarRoundRadius(15);
			chart.getBar().setBarStyle(XEnum.BarStyle.ROUNDBAR);
			
			chart.getCategoryAxis().hideAxisLine();
			chart.getCategoryAxis().hideTickMarks();	
			chart.getCategoryAxis().hideAxisLabels();
			chart.getBar().setBarTickSpacePercent(0.9f);
			chart.getDataAxis().hide();
		
			chart.disablePanMode();
			chart.disableHighPrecision();
			
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
			
			chart.getClipExt().setExtTop(150.f);
			
			//柱形和标签居中方式
			chart.setBarCenterStyle(XEnum.BarCenterStyle.TICKMARKS);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void chartDataSet()
	{
		//标签对应的柱形数据集
		List<Double> dataSeriesA= new LinkedList<Double>();	
		dataSeriesA.add(100d); 
		dataSeriesA.add(100d); 
		dataSeriesA.add(0.d); 
		dataSeriesA.add(0.d); 	
	
		//依数据值确定对应的柱形颜色.
		List<Integer> dataColorA= new LinkedList<Integer>();
		dataColorA.add(Color.rgb(255, 187, 120));	
		dataColorA.add(Color.rgb(152, 223, 138));	
		dataColorA.add(Color.GREEN);	
		dataColorA.add(Color.YELLOW);	
				
		chartData.clear();
		chartData.add(new BarData("",dataSeriesA,dataColorA,Color.rgb(53, 169, 239)));
	}
	
	private void chartLabels()
	{
		chartLabels.add("Kerberos"); 
		chartLabels.add("PKI"); 
		chartLabels.add("OpenStack Keystone"); 
		chartLabels.add("CoreOS Dex"); 
	}	
	
	
	private void chartRender2()
	{
		try {
			
			//设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....		
			int [] ltrb = getBarLnDefaultSpadding();
			chart2.setPadding(ltrb[0], ltrb[1], 
					ltrb[2], ltrb[3] +  DensityUtil.dip2px(this.getContext(), 30));	
			
					
			//标题
			//chart2.setTitle("小小熊 - 期末考试成绩");
			//chart2.addSubtitle("(XCL-Charts Demo)");	
			//数据源
			chart2.setCategories(chartLabels2);	
			chart2.setDataSource(chartData2);
			
			//数据轴
			chart2.getDataAxis().setAxisMax(105);
			chart2.getDataAxis().setAxisMin(0);
			chart2.getDataAxis().setAxisSteps(5);	
			
			
			//指隔多少个轴刻度(即细刻度)后为主刻度
			chart2.getDataAxis().setDetailModeSteps(5);
			
			//背景网格
			chart2.getPlotGrid().hideHorizontalLines();
			chart2.getPlotGrid().hideVerticalLines();
			chart2.getPlotGrid().hideEvenRowBgColor();
			chart2.getPlotGrid().hideOddRowBgColor();
									
			//定义数据轴标签显示格式
			chart2.getDataAxis().setLabelFormatter(new IFormatterTextCallBack(){
	
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
			chart2.getBar().setItemLabelVisible(true);
			chart2.getBar().setItemLabelStyle(XEnum.ItemLabelStyle.INNER);
			chart2.getBar().getItemLabelPaint().setColor(Color.WHITE);
			chart2.getBar().getItemLabelPaint().setTextSize(20);
		
			chart2.getBar().setBarRoundRadius(15);
			chart2.getBar().setBarStyle(XEnum.BarStyle.ROUNDBAR);
			
			chart2.getCategoryAxis().hideAxisLine();
			chart2.getCategoryAxis().hideTickMarks();			
			
			chart2.getDataAxis().hide();
			//柱形比设大点
			chart2.getBar().setBarTickSpacePercent(0.9f);
			chart2.getCategoryAxis().setTickLabelRotateAngle(45);
			chart2.getCategoryAxis().getTickLabelPaint().setColor(Color.rgb(83, 194, 232));
			chart2.getCategoryAxis().getTickLabelPaint().setTextSize(20);
			
		
			chart2.disablePanMode();
			chart2.disableHighPrecision();
			
			//设定格式
			chart2.setItemLabelFormatter(new IFormatterDoubleCallBack() {
				@Override
				public String doubleFormatter(Double value) {
					// TODO Auto-generated method stub
					DecimalFormat df=new DecimalFormat("#0");					 
					String label = df.format(value).toString();
					return label;
				}});
			
			//隐藏Key
			chart2.getPlotLegend().hide();
			
		//	chart2.getClipExt().setExtTop(150.f);
			
			//柱形和标签居中方式
			chart2.setBarCenterStyle(XEnum.BarCenterStyle.TICKMARKS);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void chartDataSet2()
	{
		//标签对应的柱形数据集
		List<Double> dataSeriesA= new LinkedList<Double>();	
		dataSeriesA.add(80d); 
		dataSeriesA.add(85d); 
		dataSeriesA.add(90d); 
		dataSeriesA.add(95d); 	
	
		//依数据值确定对应的柱形颜色.
		List<Integer> dataColorA= new LinkedList<Integer>();
		dataColorA.add(Color.rgb(255, 95, 6));	
		dataColorA.add(Color.rgb(55, 61, 65));	
		dataColorA.add(Color.rgb(162, 144, 98));	
		dataColorA.add(Color.rgb(66, 115, 156));	
				
		chartData2.clear();
		chartData2.add(new BarData("",dataSeriesA,dataColorA,Color.rgb(53, 169, 239)));
	}
	
	private void chartLabels2()
	{
		chartLabels2.add("Kerberos"); 
		chartLabels2.add("PKI"); 
		chartLabels2.add("OpenStack Keystone"); 
		chartLabels2.add("CoreOS Dex"); 
	}	
	
	
	
	@Override
    public void render(Canvas canvas) {
        try{
            chart.render(canvas);
            chart2.render(canvas);
        } catch (Exception e){
        	Log.e(TAG, e.toString());
        }
    }



}
