package com.crowley.p2pnote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.crowley.p2pnote.db.DBOpenHelper;
import com.crowley.p2pnote.ui.HorizontalScrollViewAdapter;
import com.crowley.p2pnote.ui.MyHorizontalScrollView;

import android.app.Fragment;
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
import android.widget.TextView;

public class PlatformFragment extends Fragment{
	
	private MyHorizontalScrollView mHorizontalScrollView;  
    private HorizontalScrollViewAdapter mAdapter;  
    private ImageView mImg; 
    private List<Integer> mDatas = new ArrayList<Integer>(Arrays.asList(  
    		R.drawable.company_icon01, R.drawable.company_icon02, R.drawable.company_icon03,  
            R.drawable.company_icon04, R.drawable.company_icon05, R.drawable.company_icon06, R.drawable.company_icon07));
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.platform_fragment, container, false);
		mHorizontalScrollView = (MyHorizontalScrollView) view.findViewById(R.id.scroll_view);
		mAdapter = new HorizontalScrollViewAdapter(this.getActivity(), mDatas);
		mHorizontalScrollView.initDatas(mAdapter); 
		return view;
	}

	/*private void initView() {
		// TODO Auto-generated method stub		  
        for (int i = 0; i < mImgIds.length; i++)  
        {  
              
            View view = mInflater.inflate(R.layout.platform_tab,  
                    mGallery, false);  
            ImageView img = (ImageView) view  
                    .findViewById(R.id.platform_tab_img);  
            img.setImageResource(mImgIds[i]);
            ImageView img2 = (ImageView) view  
                    .findViewById(R.id.platform_tab_arrow);  
            if(i==0){
            	img2.setImageResource(R.drawable.platform_arrow_grey);
            }else{
            	img2.setImageResource(R.drawable.platform_arrow_white);
            }            
            TextView txt = (TextView) view  
                    .findViewById(R.id.platform_tab_text);  
            txt.setText(DBOpenHelper.PLATFORM_NAMES[0]);  
            mGallery.addView(view);  
        } 
        scrollView.setOnTouchListener(this);
	}

	private void initData() {
		// TODO Auto-generated method stub
		mImgIds = new int[] { R.drawable.company_icon01, R.drawable.company_icon02, R.drawable.company_icon03,  
                R.drawable.company_icon04, R.drawable.company_icon05, R.drawable.company_icon06, R.drawable.company_icon07 };
		
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		if(arg0.getId()==R.id.scroll_view){
			Log.i("m_info", "test");
			int action = arg1.getAction();
			switch (action) {
			case MotionEvent.ACTION_UP:
				return true;
			default:
				return false;
			}			
		}else{
			return false;
		}		
	}*/

}
