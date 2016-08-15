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
 * @version 1.0
 */
package com.demo.xclcharts.view;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import org.xclcharts.chart.BarChart;
import org.xclcharts.chart.BarData;
import org.xclcharts.chart.CustomLineData;
import org.xclcharts.common.IFormatterDoubleCallBack;
import org.xclcharts.common.IFormatterTextCallBack;
import org.xclcharts.renderer.XEnum;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.util.Log;

/**
 * @ClassName BarChart03View
 * @Description 用于展示定制线与明细刻度线
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * MODIFIED    YYYY-MM-DD   REASON
 */
public class BarChart03View extends DemoView implements Runnable{
	
	private String TAG = "BarChart03View";
	private BarChart chart = new BarChart();
	//轴数据源
	private List<String> chartLabels = new LinkedList<String>();
	private List<BarData> chartData = new LinkedList<BarData>();
	private List<CustomLineData> mCustomLineDataset = new LinkedList<CustomLineData>();
	
	public BarChart03View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
		
	}
	
	public BarChart03View(Context context, AttributeSet attrs){   
        super(context, attrs);   
        initView();
	 }
	 
	 public BarChart03View(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			initView();
	 }
	 
	 private void initView()
	 {
		 	chartLabels();
			chartDataSet();
			chartCustomLines();
			chartRender();
			
			//綁定手势滑动事件
			this.bindTouch(this,chart);
			
			new Thread(this).start();
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
			
					
			//标题
			chart.setTitle("小小熊 - 期末考试成绩");
			chart.addSubtitle("(XCL-Charts Demo)");	
			//数据源
			//chart.setDataSource(chartData);
			chart.setCategories(chartLabels);	
			chart.setCustomLines(mCustomLineDataset);
			
			//图例
			chart.getAxisTitle().setTitleStyle(XEnum.AxisTitleStyle.ENDPOINT);
			chart.getAxisTitle().setLeftTitle("分数");
			chart.getAxisTitle().setLowerTitle("科目");
			chart.getAxisTitle().setCrossPointTitle("(一班)");
			
			//数据轴
			chart.getDataAxis().setAxisMax(105);
			chart.getDataAxis().setAxisMin(0);
			chart.getDataAxis().setAxisSteps(5);	
			
			
			//指隔多少个轴刻度(即细刻度)后为主刻度
			chart.getDataAxis().setDetailModeSteps(5);
			
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
			
			//在柱形顶部显示值
			chart.getBar().setItemLabelVisible(true);
			chart.getBar().setBarStyle(XEnum.BarStyle.OUTLINE);
			chart.getBar().setBorderWidth(5);
			chart.getBar().setOutlineAlpha(150);
			
			//chart.getBar().setBarRoundRadius(5);
			//chart.getBar().setBarStyle(XEnum.BarStyle.ROUNDBAR);
		
			//chart.setPlotPanMode(XEnum.PanMode.FREE);	
			chart.disablePanMode();
			
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
		dataSeriesA.add(98d); 
		dataSeriesA.add(100d); 
		dataSeriesA.add(95d); 
		dataSeriesA.add(100d); 	
	
		//依数据值确定对应的柱形颜色.
		List<Integer> dataColorA= new LinkedList<Integer>();
		dataColorA.add(Color.RED);	
		dataColorA.add(Color.BLUE);	
		dataColorA.add(Color.GREEN);	
		dataColorA.add(Color.YELLOW);	
				
		//BarData BarDataA = new BarData("",dataSeriesA,dataColorA,(int)Color.rgb(53, 169, 239));
		chartData.clear();
		chartData.add(new BarData("",dataSeriesA,dataColorA,Color.rgb(53, 169, 239)));
	}
	
	private void chartLabels()
	{
		chartLabels.add("语文"); 
		chartLabels.add("数学"); 
		chartLabels.add("英语"); 
		chartLabels.add("计算机"); 
	}	
	
	/**
	 * 定制线/分界线
	 */
	private void chartCustomLines()
	{				
		CustomLineData line1 = new CustomLineData("及格线",60d,Color.RED,7);
		line1.setCustomLineCap(XEnum.DotStyle.PRISMATIC);		
		line1.setLabelHorizontalPostion(Align.LEFT);
		line1.setLabelOffset(15);	
		line1.getLineLabelPaint().setColor(Color.RED);
		mCustomLineDataset.add(line1);
		
		CustomLineData line2 = new CustomLineData("没过打屁股",60d,Color.RED,7);
		line2.setLabelHorizontalPostion(Align.CENTER);
		line2.hideLine();
		mCustomLineDataset.add(line2);
		
		CustomLineData line3 = new CustomLineData("良好",80d,Color.rgb(35, 172, 57),5);	
		line3.setCustomLineCap(XEnum.DotStyle.RECT);		
		line3.setLabelHorizontalPostion(Align.LEFT);	
		line3.setLineStyle(XEnum.LineStyle.DOT);
		mCustomLineDataset.add(line3);
		
		CustomLineData line4 = new CustomLineData("优秀",90d,Color.rgb(53, 169, 239),5);	
		line4.setCustomLineCap(XEnum.DotStyle.TRIANGLE);
		line4.setLabelOffset(15);
		line4.getLineLabelPaint().setColor(Color.rgb(216, 44, 41));		
		line4.setLineStyle(XEnum.LineStyle.DASH);
		mCustomLineDataset.add(line4);
		
		int average =  calcAvg() ;
		CustomLineData line6 = new CustomLineData("本次考试平均得分:"+Integer.toString(average),
														(double)average,Color.BLUE,5);
		line6.setLabelHorizontalPostion(Align.CENTER);
		line6.setLineStyle(XEnum.LineStyle.DASH);
		line6.getLineLabelPaint().setColor(Color.RED);
		mCustomLineDataset.add(line6);							
	}
	
	private int calcAvg()
	{
		return (98+100+95+100)/4;
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
          	          	
          	for(int i=0;i< chartData.size() ;i++)
          	{           		       		          		          		          	
          		BarData barData = chartData.get(i); 
          		for(int j=0;j<barData.getDataSet().size();j++)
                {     
          			Thread.sleep(100);
          			List<BarData> animationData = new LinkedList<BarData>();
          			List<Double> dataSeries= new LinkedList<Double>();	
          			List<Integer> dataColorA= new LinkedList<Integer>();
          			for(int k=0;k<=j;k++)
          			{          				
          				dataSeries.add(barData.getDataSet().get(k));  
          				dataColorA.add(barData.getDataColor().get(k));  
          			}
          			
          			BarData animationBarData = new BarData("",dataSeries,dataColorA,Color.rgb(53, 169, 239));
          			animationData.add(animationBarData);
          			chart.setDataSource(animationData);
          			postInvalidate(); 
                }          		          		   
          	}
          	 
          }
          catch(Exception e) {
              Thread.currentThread().interrupt();
          }            
	}
}
