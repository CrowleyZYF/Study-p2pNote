package com.crowley.p2pnote.ui;

import java.util.HashMap;
import java.util.Map;

import com.crowley.p2pnote.R;

import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
	* ��Ŀ���ʱ�Ļص� 
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
	* HorizontalListView�е�LinearLayout 
	*/  
	private LinearLayout mContainer;  
	  
	/** 
	* ���������� 
	*/  
	private HorizontalScrollViewAdapter mAdapter;  
		
	
	/** 
	* ����View��λ�õļ�ֵ�� 
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
	* ��ʼ�����ݣ��������������� 
	*  
	* @param mAdapter 
	*/  
	public void initDatas(HorizontalScrollViewAdapter mAdapter)  
	{  
		this.mAdapter = mAdapter;  
		mContainer = (LinearLayout) getChildAt(0);  
		// ����������е�һ��View  
		final View view = mAdapter.getView(0, null, mContainer);  
		mContainer.addView(view);  		
		
		//��ʼ����һ��Ļ��Ԫ��  
		initFirstScreenChildren(null);  
	}  
	
	public void updateDate(HorizontalScrollViewAdapter mAdapter,String platformString)
	{
		this.mAdapter = mAdapter;  
		mContainer = (LinearLayout) getChildAt(0);  
		mContainer.removeAllViews();
		// ����������е�һ��View  
		final View view = mAdapter.getView(0, null, mContainer); 
		mContainer.addView(view);  		
		
		//��ʼ����һ��Ļ��Ԫ��  
		initFirstScreenChildren(platformString);  
	}
	
	/** 
	* ���ص�һ����View 
	*  
	* @param mCountOneScreen 
	*/  
	public void initFirstScreenChildren(String platformString)  
	{  
		mContainer = (LinearLayout) getChildAt(0);  
		mContainer.removeAllViews();  
		mViewPos.clear();  
		for (int i = 0; i < mAdapter.getCount(); i++)
		{  
		    View view = mAdapter.getView(i, null, mContainer);  
		    view.setOnClickListener(this);
		    if(platformString!=null){
		    	if(platformString.equals(((TextView)(((RelativeLayout) view).getChildAt(0))).getText().toString())){
		    		((ImageView)((RelativeLayout) view).getChildAt(2)).setImageResource(R.drawable.platform_arrow_grey);
		    	}
		    }else{
		    	if (i==0) {
			    	((ImageView)((RelativeLayout) view).getChildAt(2)).setImageResource(R.drawable.platform_arrow_grey);
				}
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
		        ((ImageView)tempLayout.getChildAt(2)).setImageResource(R.drawable.platform_arrow_white);
		    }  
		    mOnClickListener.onClick(arg0, mViewPos.get(arg0));
		}
	}  
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub		
		return super.onTouchEvent(ev);
	}

}
