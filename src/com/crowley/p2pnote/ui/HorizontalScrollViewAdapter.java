package com.crowley.p2pnote.ui;

import java.util.List;

import com.crowley.p2pnote.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class HorizontalScrollViewAdapter {
	private Context mContext;  
    private LayoutInflater mInflater;  
    private List<Integer> mDatas;
    private List<Integer> mDatas2;
  
    public HorizontalScrollViewAdapter(Context context, List<Integer> mDatas, List<Integer> mDatas2)  
    {  
        this.mContext = context;  
        mInflater = LayoutInflater.from(context);  
        this.mDatas = mDatas; 
        this.mDatas2 = mDatas2; 
    }  
  
    public int getCount()  
    {  
        return mDatas.size();  
    }  
  
    public Object getItem(int position)  
    {  
        return mDatas.get(position);  
    }  
  
    public long getItemId(int position)  
    {  
        return position;  
    }  
  
    public View getView(int position, View convertView, ViewGroup parent)  
    {  
        ViewHolder viewHolder = null;  
        if (convertView == null)  
        {  
            viewHolder = new ViewHolder();  
            convertView = mInflater.inflate(  
                    R.layout.platform_tab, parent, false);  
            viewHolder.mImg = (ImageView) convertView  
                    .findViewById(R.id.platform_tab_img);  
            viewHolder.mText = (TextView) convertView  
                    .findViewById(R.id.platform_tab_text);  
            convertView.setTag(viewHolder);  
        } else  
        {  
            viewHolder = (ViewHolder) convertView.getTag();  
        }  
        viewHolder.mImg.setImageResource(mDatas.get(position));  
        viewHolder.mText.setText(this.mContext.getResources().getString(mDatas2.get(position)));
  
        return convertView;  
    }  
  
    private class ViewHolder  
    {  
        ImageView mImg;  
        TextView mText;  
    } 
}
