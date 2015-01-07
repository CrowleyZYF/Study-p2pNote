package com.crowley.p2pnote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.crowley.p2pnote.ui.RefreshListView;
import com.crowley.p2pnote.ui.RefreshListView.IReflashListener;
import com.crowley.p2pnote.ui.listAdapter;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MoreFragment extends Fragment implements IReflashListener{
	
	private RefreshListView listView;
	private SimpleAdapter list_adapter;
	private List<Map<String, Object>> dataList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.news_fragment, container, false);
		
		dataList=new ArrayList<Map<String,Object>>();
        listView=(RefreshListView) view.findViewById(R.id.listview);
        listView.setInterface(this);
        getData();
        list_adapter=new SimpleAdapter(this.getActivity(), dataList, R.layout.news_listview_item, new String[]{"news_pic","news_title","news_intro","news_time"}, new int[]{R.id.news_pic,R.id.news_title,R.id.news_intro,R.id.news_time});
        listView.setAdapter(list_adapter);
		return view;
	}

	private void getData() {
		// TODO Auto-generated method stub
		dataList.clear();
		int count=9;
		for(int i=0;i<count;i++){
			if (i==(count-1)) {
				Map<String, Object> map=new HashMap<String, Object>();  
				map.put("news_pic", R.drawable.logo);  
				map.put("news_title", "欢迎使用网贷篮子");  
				map.put("news_intro", "为您提供随时随地，最专业的P2P记账服务，通过统计服务、平台服务等功能，让您一手掌握属于自己的投资现状");
				map.put("news_time", "2015-01-07");
				dataList.add(map);
			}else{
				Map<String, Object> map=new HashMap<String, Object>();  
				map.put("news_pic", R.drawable.news_temp);  
				map.put("news_title", "陆金所—中国平安集团成员"+i);  
				map.put("news_intro", "Lufax.com专业网络投融资平台，为个人提供可信赖的投融资服务"+i);
				map.put("news_time", "2015-01-0"+i);
				dataList.add(map);
			}			
		}
	}
	
	private void setReflashData() {
		List<Map<String, Object>> temp = new ArrayList<Map<String,Object>>();
		for(int i=0;i<dataList.size();i++){
			temp.add(dataList.get(i));
		}
		dataList.clear();
		for (int i = 0; i < 2; i++) {
			Map<String, Object> map=new HashMap<String, Object>();  
			map.put("news_pic", R.drawable.news_temp);  
			map.put("news_title", "刷新陆金所—中国平安集团成员"+i);  
			map.put("news_intro", "刷新Lufax.com专业网络投融资平台，为个人提供可信赖的投融资服务"+i);
			map.put("news_time", "刷新2015-01-0"+i);
			dataList.add(map);
		}
		for(int i=0;i<temp.size();i++){
			dataList.add(temp.get(i));
		}
	}
	
	public void onReflash() {
		// TODO Auto-generated method stub\
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//获取最新数据
				setReflashData();
				//通知界面显示
				list_adapter.notifyDataSetChanged();
				//通知listview 刷新数据完毕；
				listView.reflashComplete();
			}
		}, 2000);
		
	}

}
