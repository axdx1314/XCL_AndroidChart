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
 * @Description Android图表基类库演示
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * @license http://www.apache.org/licenses/  Apache v2 License
 * @version 1.0
 */
package com.demo.xclcharts;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.demo.xclcharts.view.ArcLineChart01View;
import com.demo.xclcharts.view.AreaChart01View;
import com.demo.xclcharts.view.AreaChart02View;
import com.demo.xclcharts.view.AreaChart03View;
import com.demo.xclcharts.view.BarChart01View;
import com.demo.xclcharts.view.BarChart02View;
import com.demo.xclcharts.view.BarChart03View;
import com.demo.xclcharts.view.BarChart04View;
import com.demo.xclcharts.view.BarChart05View;
import com.demo.xclcharts.view.BarChart06View;
import com.demo.xclcharts.view.BarChart08View;
import com.demo.xclcharts.view.BarChart09View;
import com.demo.xclcharts.view.BarChart10View;
import com.demo.xclcharts.view.BarChart11View;
import com.demo.xclcharts.view.BarChart12View;
import com.demo.xclcharts.view.BarChart13View;
import com.demo.xclcharts.view.BarChart3D01View;
import com.demo.xclcharts.view.BarChart3D02View;
import com.demo.xclcharts.view.BubbleChart01View;
import com.demo.xclcharts.view.CircleChart04View;
import com.demo.xclcharts.view.DemoView;
import com.demo.xclcharts.view.DountChart01View;
import com.demo.xclcharts.view.FunnelChart01View;
import com.demo.xclcharts.view.FunnelChart02View;
import com.demo.xclcharts.view.FunnelChart201View;
import com.demo.xclcharts.view.LineChart01View;
import com.demo.xclcharts.view.LineChart02View;
import com.demo.xclcharts.view.MultiAxisChart01View;
import com.demo.xclcharts.view.MultiAxisChart02View;
import com.demo.xclcharts.view.MultiAxisChart03View;
import com.demo.xclcharts.view.MultiBarChart01View;
import com.demo.xclcharts.view.PieChart01View;
import com.demo.xclcharts.view.PieChart02View;
import com.demo.xclcharts.view.PieChart3D01View;
import com.demo.xclcharts.view.QuadrantChart01View;
import com.demo.xclcharts.view.RadarChart01View;
import com.demo.xclcharts.view.RadarChart02View;
import com.demo.xclcharts.view.RadarChart03View;
import com.demo.xclcharts.view.RangeBarChart01View;
import com.demo.xclcharts.view.RoseChart01View;
import com.demo.xclcharts.view.ScatterChart01View;
import com.demo.xclcharts.view.SplineChart01View;
import com.demo.xclcharts.view.SplineChart02View;
import com.demo.xclcharts.view.SplineChart03View;
import com.demo.xclcharts.view.SplineChart04View;
import com.demo.xclcharts.view.SplineChart05View;
import com.demo.xclcharts.view.StackBarChart01View;
import com.demo.xclcharts.view.StackBarChart02View;


/**
 * @ClassName ChartsActivity
 * @Description  展示各类图表
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 */


/**
 * 关于整个图表缩放的说明 :
 * 	Demo中提供了两种缩放模式:
 * 		1. 通过 ZoomControls控件调用DemoView中的Zoom相关函数来缩放图表.
 * 		  通过这种方式缩放后的点击事件。
 * 		禁用方式:
 * 			可通过直接继承GraphicalView或通过覆盖onTouchEvent事件来禁用
 * 		2. 通过chart.enableScale()来激活通过双指手势对图表进行缩放。
 * 		 
 *      禁用方式:
 *      	chart.disableScale();
 * 
 * 图表区的平移  :  
 *  激活图表区的平移     
 * 		chart.enablePanMode()
 * 
 *      如果数据量比较大，感觉平移不够 顺畅，可以禁用图库的高精度计算，函数: 
 *			chart.disableHighPrecision();
 *      即，忽略掉Java的Float/Double类型的计算误差，能显著提高性能。但饼图类慎用。
 *			
 *  禁用图表区的平移     
 *  	chart.disablePanMode()
 *  
 *   平移默认是打开的。如果出现图或标识在绘图区外显示不全，可以禁掉此模式即可。
 *  
 *  
 *  如果要展示的图表数据比较长或多，可以通过调整绘图区宽度chart.getPlotArea().extWidth(增加宽度);
 *  或整个图的大小，即chart.setChartRange()的值。
 *  然后用户可以通过平移图表区的方式来展示未显示出来的数据. 
 *  注意，此方式性能会有些损失，超大量的就不用尝试这种方式了， 
 *  对于这种超大量的可以通过ScrollView控件方式来处理,具体可参考"左右滑动折线图"的例子。
 *  
 *  
 *  如还有其它问题,可访问:
 *    http://blog.csdn.net/xcl168/article/details/42318669
 *  有一份简单的教程与常见问题解答。
 *    也可加群直接问.
 *  
 * @author XCL
 *
 */
public class ChartsActivity extends Activity {
	
	//private ZoomControls mZoomControls;
	private int mSelected = 0;
	
	private DemoView[] mCharts ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE); //设置没标题
		
		mCharts = new DemoView[]{
			new BarChart01View(this) ,	//竖向柱形图
			new BarChart02View(this),	//横向柱形图
			new BarChart05View(this),	//横向定制线柱形图
			new BarChart03View(this),	//竖向定制线柱形图
			new BarChart04View(this),	//高密度柱形图
			
			new BarChart3D01View(this),	//竖向3D柱形图
			new BarChart3D02View(this),	//横向3D柱形图
			
			new BarChart08View(this),	//正负背向式图
			new BarChart09View(this),	//正负背向式图(横向)
			
			new BarChart10View(this),   // 双轴柱形图
			new BarChart11View(this),   // 顶轴横向柱形图
			new BarChart12View(this),   // 圆角柱形图
			new BarChart13View(this),   // 圆角柱形图(横向)
			
			
			new StackBarChart01View(this),	//竖向堆积柱形图
			new StackBarChart02View(this),	//横向堆积柱形图
			new LineChart01View(this),	//折线图(封闭式)
			new LineChart02View(this),	//折线图(开放式)
			new SplineChart03View(this),	//平滑曲线图
			new SplineChart01View(this),	//普通曲线图
			new SplineChart02View(this),	//三角函数曲线图
			new SplineChart04View(this),  // github
			new SplineChart05View(this),
			new AreaChart01View(this),	//区域图
			new AreaChart02View(this),	//平滑区域图
			new AreaChart03View(this),	//
			
			new MultiAxisChart01View(this),
			new MultiAxisChart02View(this),
			new MultiBarChart01View(this),	//多柱形堆积效果图
			new MultiAxisChart03View(this),	//面积折线混合图
			
			new PieChart01View(this),	//饼图
			new PieChart02View(this),
			new PieChart3D01View(this),	//3D饼图
			new DountChart01View(this),	//环形图
			new RoseChart01View(this),	//南丁格尔玫瑰图
			new RadarChart01View(this),	//蜘蛛网雷达图
			new RadarChart02View(this),	//圆形雷达图
			new RadarChart03View(this),	//玫瑰风向图
			new BarChart06View(this),
			new ArcLineChart01View(this),	//弧线比较图
			new ScatterChart01View(this),	//散点图
			new BubbleChart01View(this),	//气泡图
			new RangeBarChart01View(this), 	//范围条形图			
			new QuadrantChart01View(this),	//象限图
			
			new FunnelChart01View(this), //漏斗图(Desc)
			new FunnelChart02View(this), //漏斗图(Asc)
			new CircleChart04View(this), //圆形图
			new FunnelChart201View(this) //漏斗图(另一种风格)
		}; 
				
		Bundle bunde = this.getIntent().getExtras();  
		mSelected = bunde.getInt("selected");  
		String title = bunde.getString("title"); 			
		
		if(mSelected > mCharts.length - 1){									
			setContentView(R.layout.activity_charts);
			this.setTitle(Integer.toString(mSelected));
		}else{			
	        initActivity();
			this.setTitle(title);
		}
						
	}
	
	private void initActivity()
	{
		   //图表的使用方法:
		   //使用方式一:
		   // 1.新增一个Activity
		   // 2.新增一个View,继承Demo中的GraphicalView或DemoView都可，依Demo中View目录下例子绘制图表.
		   // 3.将自定义的图表View放置入Activity对应的XML中，将指明其layout_width与layout_height大小.
		   // 运行即可看到效果. 可参考非ChartsActivity的那几个图的例子，都是这种方式。
		
 		  //使用方式二:
		   //代码调用 方式有下面二种方法:
		   //方法一:
		   //在xml中的FrameLayout下增加图表和ZoomControls,这是利用了现有的xml文件.
   		   // 1. 新增一个View，绘制图表.
 		   // 2. 通过下面的代码得到控件，addview即可
	       //LayoutInflater factory = LayoutInflater.from(this);
	       //View content = (View) factory.inflate(R.layout.activity_multi_touch, null);     
	     
			
			//方法二:
			//完全动态创建,无须XML文件.
	       FrameLayout content = new FrameLayout(this);    
	       
	       //缩放控件放置在FrameLayout的上层，用于放大缩小图表
		   FrameLayout.LayoutParams frameParm = new FrameLayout.LayoutParams(
		   LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);  
		   frameParm.gravity = Gravity.BOTTOM|Gravity.RIGHT;  
		
		   /*
		  //缩放控件放置在FrameLayout的上层，用于放大缩小图表
	       mZoomControls = new ZoomControls(this);
	       mZoomControls.setIsZoomInEnabled(true);
	       mZoomControls.setIsZoomOutEnabled(true);	  
		   mZoomControls.setLayoutParams(frameParm);  
		   */
		   
		   //图表显示范围在占屏幕大小的90%的区域内
		   DisplayMetrics dm = getResources().getDisplayMetrics();		   
		   int scrWidth = (int) (dm.widthPixels * 0.9); 	
		   int scrHeight = (int) (dm.heightPixels * 0.9); 			   		
	       RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
	    		   													scrWidth, scrHeight);
	       
	       //居中显示
           layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);   
           //图表view放入布局中，也可直接将图表view放入Activity对应的xml文件中
           final RelativeLayout chartLayout = new RelativeLayout(this);  
      
           chartLayout.addView( mCharts[mSelected], layoutParams);
  
	        //增加控件
		   ((ViewGroup) content).addView(chartLayout);		   
		   //((ViewGroup) content).addView(mZoomControls);
		    setContentView(content);		   	       
		    //放大监听
		  //  mZoomControls.setOnZoomInClickListener(new OnZoomInClickListenerImpl());
		    //缩小监听
		  //  mZoomControls.setOnZoomOutClickListener(new OnZoomOutClickListenerImpl());  		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, Menu.FIRST + 1, 0, "帮助");  
        menu.add(Menu.NONE, Menu.FIRST + 2, 0, "关于XCL-Charts"); 
		return true;
	}

	 @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        super.onOptionsItemSelected(item);
	        switch(item.getItemId())
	        {
	        case Menu.FIRST+1: 
	        	//String chartsHelp[] = getResources().getStringArray(R.array.chartsHelp);	        
	        	//String URL = chartsHelp[mSelected]; 	        	
	        	String URL =getResources().getString(R.string.helpurl);	        		        
		        Uri uri = Uri.parse(URL);  
		        Intent intent2 = new Intent(Intent.ACTION_VIEW, uri);  
		        startActivity(intent2);  
		        finish();
	            break;
	        case Menu.FIRST+2:
		        Intent intent = new Intent();  
	    		intent.setClass(ChartsActivity.this,AboutActivity.class);    				
	    		startActivity(intent); 	        
	            break;
	        }
	        return true;
	    }
	 
	 
	 /*
	 private class OnZoomInClickListenerImpl implements OnClickListener {

	        @Override
	        public void onClick(View view) {	        	
	        	mCharts[mSelected].zoomIn();
	        }
	    }

	    private class OnZoomOutClickListenerImpl implements OnClickListener {

	        @Override
	        public void onClick(View view) {
	        	mCharts[mSelected].zoomOut();
	        }
	    }
	    
	 */
	 
}
