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
 * @version 2.3
 */
package com.demo.xclcharts.view;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.xclcharts.chart.BarChart;
import org.xclcharts.chart.BarData;
import org.xclcharts.common.IFormatterDoubleCallBack;
import org.xclcharts.renderer.XEnum;
import org.xclcharts.renderer.info.AnchorDataPoint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;


/**
 * @ClassName BarChart13View
 * @Description  圆角柱形图例子(横向) 
 *  
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 */

public class BarChart13View  extends DemoView {
	private static final String TAG = "BarChart13View";
	private BarChart chart = new BarChart();
	
	//标签轴
	private List<String> chartLabels = new LinkedList<String>();
	private List<BarData> chartData = new LinkedList<BarData>();
	
	public BarChart13View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}
	
	public BarChart13View(Context context, AttributeSet attrs){   
        super(context, attrs);           
        initView();
	 }
	 
	 public BarChart13View(Context context, AttributeSet attrs, int defStyle) {
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
			chart.setPadding(ltrb[0],ltrb[1], ltrb[2],  ltrb[3]);
				
			chart.setTitle("附近广场舞调查 (共23支舞队)");
			chart.addSubtitle("(XCL-Charts Demo)");		
			chart.setTitleVerticalAlign(XEnum.VerticalAlign.BOTTOM);
			chart.setTitleAlign(XEnum.HorizontalAlign.RIGHT);
			chart.getPlotTitle().getTitlePaint().setColor(Color.rgb(255, 9, 52));
		
			//数据源
			chart.setDataSource(chartData);
			chart.setCategories(chartLabels);	
			//数据轴
			chart.getDataAxis().setAxisMax(500);
			chart.getDataAxis().setAxisMin(100);
			chart.getDataAxis().setAxisSteps(100);
											
			chart.getDataAxis().getTickLabelPaint().
									setColor(Color.rgb(199, 88, 122));
									
			//网格
			chart.getPlotGrid().hideHorizontalLines();
			chart.getPlotGrid().hideVerticalLines();					
			
			 chart.getBar().setItemLabelStyle(XEnum.ItemLabelStyle.BOTTOM);
			 chart.getBar().setBarStyle(XEnum.BarStyle.OUTLINE);			 
			 chart.getBar().setBarTickSpacePercent(0.6f);
						
			//横向显示柱形
			chart.setChartDirection(XEnum.Direction.HORIZONTAL);
			//将轴置于右边
			chart.setCategoryAxisLocation(XEnum.AxisLocation.RIGHT);
			
			chart.getCategoryAxis().hideAxisLine();
			chart.getCategoryAxis().hideTickMarks();
			chart.getDataAxis().hide();
			chart.getCategoryAxis().getTickLabelPaint().setColor(Color.rgb(166, 84, 254));
			
			//标签轴文字旋转-45度
			//chart.getCategoryAxis().setTickLabelRotateAngle(-45f);
			
			//在柱形顶部显示值
			chart.getBar().setItemLabelVisible(true);
			chart.getBar().getItemLabelPaint().setTextSize(22);
			chart.getBar().setBarStyle(XEnum.BarStyle.ROUNDBAR);
			
			
			chart.setItemLabelFormatter(new IFormatterDoubleCallBack() {
				@Override
				public String doubleFormatter(Double value) {
					// TODO Auto-generated method stub
					
					if(Double.compare(value, 200d) == 0) {
						return "1) 小苹果";
					}else if (Double.compare(value, 250d) == 0) {
						return "2) 今夜舞起来";
					}else if (Double.compare(value, 300d) == 0) {
						return "3) 最炫民族风";
					}else if (Double.compare(value, 350d) == 0) {
						return "4) 火火的姑娘";
					}else if (Double.compare(value, 400d) == 0) {
						return "5) 我的玫瑰卓玛拉";
					}else{
						DecimalFormat df=new DecimalFormat("[#0]");					 
						String label = df.format(value).toString();
						return label;
					}
				}});	
			
				  
			chart.disablePanMode();
			//chart.showBorder();
			
			//批注
			List<AnchorDataPoint> mAnchorSet = new ArrayList<AnchorDataPoint>();
			
			AnchorDataPoint an0 = new AnchorDataPoint(0,0,XEnum.AnchorStyle.ROUNDRECT);
			an0.setBgColor(Color.rgb(255, 145, 126));
			an0.setAnchor("64%");
			
			AnchorDataPoint an1 = new AnchorDataPoint(0,1,XEnum.AnchorStyle.ROUNDRECT); 
			an1.setBgColor(Color.rgb(255, 145, 126));
			an1.setAnchor("53%");
			
			AnchorDataPoint an2 = new AnchorDataPoint(0,2,XEnum.AnchorStyle.ROUNDRECT);
			an2.setBgColor(Color.rgb(255, 145, 126));
			an2.setAnchor("42%");
			
			AnchorDataPoint an3 = new AnchorDataPoint(0,3,XEnum.AnchorStyle.CIRCLE); 
			an3.setBgColor(Color.rgb(56, 127, 255));
			an3.setAreaStyle(XEnum.DataAreaStyle.FILL);
			an3.setAnchor("二");
			an3.setTextColor(Color.WHITE);
			
			AnchorDataPoint an4 = new AnchorDataPoint(0,4,XEnum.AnchorStyle.CIRCLE);
			an4.setBgColor(Color.rgb(56, 127, 255));
			an4.setAreaStyle(XEnum.DataAreaStyle.FILL);
			an4.setAnchor("一");
			an4.setTextColor(Color.WHITE);
			
			mAnchorSet.add(an0);
			mAnchorSet.add(an1);
			mAnchorSet.add(an2);
			mAnchorSet.add(an3);
			mAnchorSet.add(an4);
			chart.setAnchorDataPoint(mAnchorSet);	

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
		
			
	}
	private void chartDataSet()
	{

		//标签对应的柱形数据集
		List<Double> dataSeriesA= new LinkedList<Double>();	
		//依数据值确定对应的柱形颜色.
		List<Integer> dataColorA= new LinkedList<Integer>();	
		dataSeriesA.add((double)400); 
		dataSeriesA.add((double)350); 
		dataSeriesA.add((double)300);
		dataSeriesA.add((double)250); 
		dataSeriesA.add((double)200); 
		
		dataColorA.add(Color.rgb(152, 211, 19)); 
		dataColorA.add(Color.rgb(252, 212, 290)); 
		dataColorA.add(Color.rgb(212, 213, 19));
		dataColorA.add(Color.rgb(222, 214, 290)); 
		dataColorA.add(Color.rgb(232, 215, 39)); 
		
		//此地的颜色为Key值颜色及柱形的默认颜色
		BarData BarDataA = new BarData("",dataSeriesA,dataColorA,
										Color.rgb(53, 169, 239));
		
		chartData.add(BarDataA);
				
	}
	
	private void chartLabels()
	{		
		chartLabels.add("**楼下广场/16人"); 
		chartLabels.add("**小镇广场/35人"); 
		chartLabels.add("**公园/3支队伍/平均60人"); 
		chartLabels.add("**文体中心/5支队伍/平均50人/7点半-9点"); 
		chartLabels.add("天虹**/6队伍/最大300人分6方队/(已成大气候)"); 
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

