package com.crowley.p2pnote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.crowley.p2pnote.db.DBOpenHelper;
import com.crowley.p2pnote.ui.HorizontalScrollViewAdapter;
import com.crowley.p2pnote.ui.MyHorizontalScrollView;
import com.crowley.p2pnote.ui.MyHorizontalScrollView.CurrentImageChangeListener;
import com.crowley.p2pnote.ui.MyHorizontalScrollView.OnItemClickListener;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PlatformFragment extends Fragment{
	
	private MyHorizontalScrollView mHorizontalScrollView;  
    private HorizontalScrollViewAdapter mAdapter;  
    private ImageView mImg; 
    private List<Integer> mDatas = new ArrayList<Integer>(Arrays.asList(  
    		R.drawable.company_icon01, R.drawable.company_icon02, R.drawable.company_icon03,  
            R.drawable.company_icon04, R.drawable.company_icon05, R.drawable.company_icon06, R.drawable.company_icon07,
            R.drawable.company_icon01, R.drawable.company_icon02, R.drawable.company_icon03,  
            R.drawable.company_icon04, R.drawable.company_icon05, R.drawable.company_icon06, R.drawable.company_icon07));
    private List<Integer> mDatas2 = new ArrayList<Integer>(Arrays.asList(  
    		R.string.company_name01,
    		R.string.company_name02,
    		R.string.company_name03,
    		R.string.company_name04,
    		R.string.company_name05,
    		R.string.company_name06,
    		R.string.company_name07,
    		R.string.company_name01,
    		R.string.company_name02,
    		R.string.company_name03,
    		R.string.company_name04,
    		R.string.company_name05,
    		R.string.company_name06,
    		R.string.company_name07));
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.platform_fragment, container, false);
		
		mImg = (ImageView) view.findViewById(R.id.id_content);
		mHorizontalScrollView = (MyHorizontalScrollView) view.findViewById(R.id.scroll_view);
		mAdapter = new HorizontalScrollViewAdapter(this.getActivity(), mDatas, mDatas2);
		
		/*mHorizontalScrollView  
		        .setCurrentImageChangeListener(new CurrentImageChangeListener()  
		        {  
		            @Override  
		            public void onCurrentImgChanged(int position,  
		                    View viewIndicator)  
		            {  
		                mImg.setImageResource(mDatas.get(position));  
		                //viewIndicator.setBackgroundColor(Color.parseColor("#AA024DA4"));
				        ((ImageView)(((RelativeLayout) viewIndicator).getChildAt(1))).setImageResource(R.drawable.platform_arrow_grey);
		            }  
		        }); */ 
		//添加点击回调  
		mHorizontalScrollView.setOnItemClickListener(new OnItemClickListener()  
		{  
		
		    @Override  
		    public void onClick(View view, int position)  
		    {  
		        mImg.setImageResource(mDatas.get(position));  
		        //view.setBackgroundColor(Color.parseColor("#AA024DA4")); 
		        ((ImageView)(((RelativeLayout) view).getChildAt(1))).setImageResource(R.drawable.platform_arrow_grey);
		    }  
		});  
		
		mHorizontalScrollView.initDatas(mAdapter); 
		return view;
	}

	

}
