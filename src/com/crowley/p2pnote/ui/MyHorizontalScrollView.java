package com.crowley.p2pnote.ui;

import java.util.HashMap;
import java.util.Map;

import com.crowley.p2pnote.R;

import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;

public class MyHorizontalScrollView extends HorizontalScrollView implements OnClickListener {  

	/** 
	* 条目点击时的回调 
	*  
	* @author Crowley 
	*  
	*/  
	public interface OnItemClickListener  
	{  
		void onClick(View view, int pos);  
	}
	
	private OnItemClickListener mOnClickListener;
		
	/** 
	* HorizontalListView中的LinearLayout 
	*/  
	private LinearLayout mContainer;  
	  
	/** 
	* 数据适配器 
	*/  
	private HorizontalScrollViewAdapter mAdapter;  
		
	
	/** 
	* 保存View与位置的键值对 
	*/  
	private Map<View, Integer> mViewPos = new HashMap<View, Integer>();   
	
	public MyHorizontalScrollView(Context context, AttributeSet attrs)  
	{  
		super(context, attrs);  
	}  
	
	@Override  
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)  
	{  
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);  
		mContainer = (LinearLayout) getChildAt(0);  
	}  
	
			
	/** 
	* 初始化数据，设置数据适配器 
	*  
	* @param mAdapter 
	*/  
	public void initDatas(HorizontalScrollViewAdapter mAdapter)  
	{  
		this.mAdapter = mAdapter;  
		mContainer = (LinearLayout) getChildAt(0);  
		// 获得适配器中第一个View  
		final View view = mAdapter.getView(0, null, mContainer);  
		mContainer.addView(view);  		
		
		//初始化第一屏幕的元素  
		initFirstScreenChildren();  
	}  
	
	/** 
	* 加载第一屏的View 
	*  
	* @param mCountOneScreen 
	*/  
	public void initFirstScreenChildren()  
	{  
		mContainer = (LinearLayout) getChildAt(0);  
		mContainer.removeAllViews();  
		mViewPos.clear();  
		for (int i = 0; i < mAdapter.getCount(); i++)
		{  
		    View view = mAdapter.getView(i, null, mContainer);  
		    view.setOnClickListener(this);
		    if (i==0) {
		    	((ImageView)((RelativeLayout) view).getChildAt(1)).setImageResource(R.drawable.platform_arrow_grey);
			}
		    mContainer.addView(view);  
		    mViewPos.put(view, i); 
		}	
	}
	
	public void setOnItemClickListener(OnItemClickListener mOnClickListener)  
	{  
		this.mOnClickListener = mOnClickListener;  
	}  

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (mOnClickListener != null)  
		{  
		    for (int i = 0; i < mContainer.getChildCount(); i++)  
		    {  
		        RelativeLayout tempLayout = (RelativeLayout) mContainer.getChildAt(i);
		        ((ImageView)tempLayout.getChildAt(1)).setImageResource(R.drawable.platform_arrow_white);
		    }  
		    mOnClickListener.onClick(arg0, mViewPos.get(arg0));
		}
	}  

}
