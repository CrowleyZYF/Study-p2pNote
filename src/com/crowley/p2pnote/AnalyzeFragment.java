package com.crowley.p2pnote;

import java.util.ArrayList;

import com.crowley.p2pnote.functions.ReturnList;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendForm;
import com.github.mikephil.charting.utils.Legend.LegendPosition;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AnalyzeFragment extends Fragment{
	
	private PieChart mChart;
	private ReturnList returnList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.analyze_fragment, container, false);
		returnList=new ReturnList();
		mChart = (PieChart) v.findViewById(R.id.pieChart1);
        mChart.setDescription("");
        
        //Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        
        //mChart.setValueTypeface(tf);
        //mChart.setCenterTextTypeface(Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf"));
        mChart.setUsePercentValues(true);
        mChart.setCenterText("投资平台\n占比图");
        mChart.setCenterTextSize(22f);
         
        // radius of the center hole in percent of maximum radius
        mChart.setHoleRadius(45f); 
        mChart.setTransparentCircleRadius(50f);
        
        // enable / disable drawing of x- and y-values
//        mChart.setDrawYValues(false);
//        mChart.setDrawXValues(false);
        
        mChart.setData(generatePieData());
        
        Legend l = mChart.getLegend();
        l.setPosition(LegendPosition.BELOW_CHART_CENTER);
        l.setForm(LegendForm.SQUARE);
		
		return v;
	}
	
	protected PieData generatePieData() {
        

        ArrayList<String> xVals = returnList.analyzexVals(this.getActivity(), 0, "2015-12-01", "2015-12-31");
        int count = xVals.size();
        
        ArrayList<Entry> entries1 = returnList.analyzeEntries(this.getActivity(), 0, "2015-12-01", "2015-12-31", xVals);       
        
        for(int i = 0; i < count; i++) {
            xVals.add("entry" + (i+1));    
            //entries1.add(new Entry((float) (Math.random() * 60) + 40, i));
        }
        
        PieDataSet ds1 = new PieDataSet(entries1, "");
        //ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setSliceSpace(2f);
        
        PieData d = new PieData(xVals, ds1);
        return d;
		/*int count = 1;
        
        ArrayList<Entry> entries1 = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();
        
        xVals.add("该月没有投资记录");
        
        for(int i = 0; i < count; i++) {
            xVals.add("entry" + (i+1));    
            entries1.add(new Entry(100f, i));
        }
        
        PieDataSet ds1 = new PieDataSet(entries1, "");
        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setSliceSpace(2f);
        
        PieData d = new PieData(xVals, ds1);
        return d;*/
    }

}
