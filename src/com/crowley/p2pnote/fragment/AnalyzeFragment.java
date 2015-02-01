package com.crowley.p2pnote.fragment;

import java.util.ArrayList;

import com.crowley.p2pnote.R;
import com.crowley.p2pnote.R.color;
import com.crowley.p2pnote.R.id;
import com.crowley.p2pnote.R.layout;
import com.crowley.p2pnote.db.DBOpenHelper;
import com.crowley.p2pnote.functions.Analyze;
import com.crowley.p2pnote.ui.MyHorizontalScrollView;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendForm;
import com.github.mikephil.charting.utils.Legend.LegendPosition;

import android.app.Fragment;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AnalyzeFragment extends Fragment implements OnClickListener,OnTouchListener{

	private int status;
	private Analyze analyze;
	private MyHorizontalScrollView mHorizontalScrollView;
	
	private LinearLayout analyzeLinearLayout;
	private PieChart mChart;	
	private TextView analyze_name;
	private ImageView prev;
	private ImageView next;
	
	final int RIGHT = 0;  
    final int LEFT = 1;  
    private GestureDetector gestureDetector;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.analyze_fragment, container, false);
		
		initView(v);		
		initData();        
        initEvent();
		
		return v;
	}

	public void initEvent() {
		prev.setOnClickListener(this);
        next.setOnClickListener(this);
        analyzeLinearLayout.setOnTouchListener(this);
        mHorizontalScrollView.setOnTouchListener(this);
	}

	public void initData() {
		status=0;
		analyze=new Analyze(this.getActivity());
		
		mChart.setDescription("");
        mChart.setUsePercentValues(true);        
        mChart.setCenterTextSize(22f);
        mChart.setHoleRadius(45f); 
        mChart.setTransparentCircleRadius(50f);       
        
        //设置文字颜色
      	mChart.setValueTextColor(getResources().getColor(R.color.light_black));
        //设置圆圈内的标题文字
        mChart.setCenterText(this.getActivity().getResources().getString(DBOpenHelper.ANALYZE_TITLE_SMALL[status]));
        //获取数据
        mChart.setData(generatePieData(status));  
        
        Legend l = mChart.getLegend();
        l.setPosition(LegendPosition.BELOW_CHART_CENTER);
        l.setForm(LegendForm.SQUARE);
	}

	public void initView(View v) {		
		mHorizontalScrollView = (MyHorizontalScrollView) v.findViewById(R.id.scroll_view);
		analyzeLinearLayout = (LinearLayout) v.findViewById(R.id.analyze);
		mChart = (PieChart) v.findViewById(R.id.pieChart1);
		analyze_name=(TextView) v.findViewById(R.id.analyze_name);
		prev=(ImageView) v.findViewById(R.id.prev);
		next=(ImageView) v.findViewById(R.id.next);
		gestureDetector = new GestureDetector(this.getActivity(),onGestureListener); 
	}
	
	protected PieData generatePieData(int type) {
		ArrayList<String> xVals=null;
		ArrayList<Entry> entries1 =null;
		
		//饼状图分类 如果是0和4 需要去数据库查询 其他的有固定分类
		if (type==0||type==4) {
			xVals = analyze.analyzexVals(type);
		}else {
			switch (type) {
			case 1:{
				//收益率
				xVals = new ArrayList<String>();
	        	xVals.add("0% ~ 6%");
	        	xVals.add("6% ~ 8%");
	        	xVals.add("8% ~ 10%");
	        	xVals.add("10% ~ 12%");
	        	xVals.add("12% ~ 15%");
	        	xVals.add("20% ~ 20%");
	        	xVals.add("20% ~ 25%");
	        	xVals.add("> 25%");
	        	break;
			}
			case 2:{
				//期限结构
				xVals = new ArrayList<String>();
	        	xVals.add("小于一个月");
	        	xVals.add("小于三个月");
	        	xVals.add("小于半年");
	        	xVals.add("小于九个月");
	        	xVals.add("小于一年");
	        	xVals.add("小于一年半");
	        	xVals.add("小于两年");
	        	xVals.add("两年以上");
	        	break;
			}
			case 3:{
				//回款时间
				xVals = new ArrayList<String>();
	        	xVals.add("小于一周");
	        	xVals.add("小于一个月");
	        	xVals.add("小于三个月");
	        	xVals.add("小于半年");
	        	xVals.add("小于九个月");
	        	xVals.add("小于一年");
	        	xVals.add("小于一年以上");
	        	break;
			}
			default:
				break;
			}
		}        
        
        int count = xVals.size();
        PieData d;
        PieDataSet ds1=null;
        entries1 = analyze.analyzeEntries(type,xVals);
        boolean checkEmpty = true;
        for(int i=0;i<entries1.size();i++){
        	if(entries1.get(i).getVal()!=0){
        		checkEmpty=false;
        		i=entries1.size()+1;        		
        	}
        }
        if(count==0||checkEmpty){
        	xVals = new ArrayList<String>();
        	xVals.add("暂无相应分析");
        	entries1 = new ArrayList<Entry>();
        	entries1.add(new Entry(100f, 0));
        	ds1 = new PieDataSet(entries1, "");
        	ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
            ds1.setSliceSpace(2f);
            d = new PieData(xVals, ds1);
        }else{        	
        	ArrayList<Entry> result = new ArrayList<Entry>();
        	ArrayList<String> xValsResult = new ArrayList<String>();
        	for(int i=0;i<entries1.size();i++){
        		if(entries1.get(i).getVal()!=0){
            		result.add(entries1.get(i));
            		xValsResult.add(xVals.get(i));
            	}
        	}
            for(int i = 0; i < result.size(); i++) {
            	xValsResult.add("entry" + (i+1));                
            }
            ds1 = new PieDataSet(result, "");
            ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
            ds1.setSliceSpace(2f);
            d = new PieData(xValsResult, ds1);
        }        
        return d;
        
    }

	@Override
	public void onClick(View v) {		
		switch (v.getId()) {
		case R.id.prev:{
			if(status==0){
				status=4;
			}else{
				status--;
			}
			analyze_name.setText(DBOpenHelper.ANALYZE_TITLE[status]);
			mChart.setData(generatePieData(status));
			mChart.setCenterText(this.getActivity().getResources().getString(DBOpenHelper.ANALYZE_TITLE_SMALL[status]));
			mChart.invalidate();
			break;
		}
		case R.id.next:{
			if(status==4){
				status=0;
			}else{
				status++;
			}
			analyze_name.setText(DBOpenHelper.ANALYZE_TITLE[status]);
			mChart.setData(generatePieData(status));
			mChart.setCenterText(this.getActivity().getResources().getString(DBOpenHelper.ANALYZE_TITLE_SMALL[status]));
			mChart.invalidate();
			break;
		}
		default:
			break;
		}		
	}
	
	public void reflash(){
		analyze_name.setText(DBOpenHelper.ANALYZE_TITLE[status]);
		mChart.setData(generatePieData(status));
		mChart.setCenterText(this.getActivity().getResources().getString(DBOpenHelper.ANALYZE_TITLE_SMALL[status]));
		mChart.invalidate();
	}
	
	private GestureDetector.OnGestureListener onGestureListener =   
	        new GestureDetector.SimpleOnGestureListener() {  
	        @Override  
	        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,  
	                float velocityY) {  
	            float x = e2.getX() - e1.getX();  
	            float y = e2.getY() - e1.getY();  
	            if (x > 0) {  
	                doResult(RIGHT);  
	            } else if (x < 0) {  
	                doResult(LEFT);  
	            }  
	            return true;  
	        }  
	    }; 

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.analyze:{
			return gestureDetector.onTouchEvent(arg1); 			
		}
		default:
			break;
		}
		return false;
	}
	
	public void doResult(int action) { 		  
        switch (action) {  
        case RIGHT:  
        	if(status==4){
				status=0;
			}else{
				status++;
			}
			analyze_name.setText(DBOpenHelper.ANALYZE_TITLE[status]);
			mChart.setData(generatePieData(status));
			mChart.setCenterText(this.getActivity().getResources().getString(DBOpenHelper.ANALYZE_TITLE_SMALL[status]));
			mChart.invalidate();
            break;    
        case LEFT:          	
			if(status==0){
				status=4;
			}else{
				status--;
			}
			analyze_name.setText(DBOpenHelper.ANALYZE_TITLE[status]);
			mChart.setData(generatePieData(status));
			mChart.setCenterText(this.getActivity().getResources().getString(DBOpenHelper.ANALYZE_TITLE_SMALL[status]));
			mChart.invalidate();
            break;   
        }  
    }

}
