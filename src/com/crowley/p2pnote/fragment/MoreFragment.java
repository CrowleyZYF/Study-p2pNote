package com.crowley.p2pnote.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import com.crowley.p2pnote.R;
import com.crowley.p2pnote.R.drawable;
import com.crowley.p2pnote.R.id;
import com.crowley.p2pnote.R.layout;
import com.crowley.p2pnote.db.HttpUtils;
import com.crowley.p2pnote.db.NewsModel;
import com.crowley.p2pnote.functions.Common;
import com.crowley.p2pnote.ui.RefreshListView;
import com.crowley.p2pnote.ui.RefreshListView.IReflashListener;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

@SuppressLint("HandlerLeak") 
public class MoreFragment extends Fragment implements IReflashListener{
	
	private RefreshListView listView;
	private SimpleAdapter list_adapter;
	private List<Map<String, Object>> dataList;
	
	private JSONArray array;
	
	private String timeString;
	
	
	private SharedPreferences preferences;
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg){
			if(msg.what==0x123){
				if(array.length()!=0){
					for (int i = 0; i < array.length(); i++) {
						try {
							String titleString=((JSONObject)array.get(i)).get("title").toString();
							String addString=((JSONObject)array.get(i)).get("add_time").toString();
							String content=((JSONObject)array.get(i)).get("content").toString();
							NewsModel temp=new NewsModel(0,titleString,addString,content);
							Long tsLong = System.currentTimeMillis();
							timeString = tsLong.toString();							
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					getData(1);
					//通知界面显示
					list_adapter.notifyDataSetChanged();
					//通知listview 刷新数据完毕；
					listView.reflashComplete();
				}else{
					//通知界面显示
					list_adapter.notifyDataSetChanged();
					//通知listview 刷新数据完毕；
					listView.reflashComplete();
				}
			}
		}		
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.news_fragment, container, false);
		
		dataList=new ArrayList<Map<String,Object>>();
        listView=(RefreshListView) view.findViewById(R.id.listview);
        listView.setInterface(this);
        preferences=this.getActivity().getSharedPreferences("user", android.content.Context.MODE_PRIVATE);
		//boolean isLogined = preferences.getBoolean("isLogined", false);
        String last_timeString=preferences.getString("last_time", "出错啦");
		if(last_timeString.equals("出错啦")){
			timeString="0";
		}else{
			timeString=last_timeString;
		}
		if(timeString.equals("0")){
			//说明没有数据 提醒用户刷新
			getData(0);
		}else{
			//有数据 加载所有
			getData(1);
		}
        list_adapter=new SimpleAdapter(this.getActivity(), dataList, R.layout.news_listview_item, new String[]{"news_pic","news_title","news_intro","news_time"}, new int[]{R.id.news_pic,R.id.news_title,R.id.news_intro,R.id.news_time});
        listView.setAdapter(list_adapter);
		return view;
	}

	private void getData(int type) {
		// TODO Auto-generated method stub
		dataList.clear();
		if(type==0){
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("news_pic", R.drawable.news_temp);
			map.put("news_title", "暂无数据，快下拉刷新试试~"); 
			map.put("news_intro", "");
			map.put("news_time", "暂无数据");
			dataList.add(map);
		}else if(type==1){
			List<Map<String, Object>> temp = new ArrayList<Map<String,Object>>();
			for (int i = 0; i < temp.size(); i++) {
				//dataList.add(temp.get(i));			
				Map<String, Object> map=new HashMap<String, Object>();  
				map.put("news_pic", R.drawable.news_temp);
				map.put("news_title", temp.get(i).get("news_title"));  
				map.put("news_intro", temp.get(i).get("news_intro").toString().substring(0, 20)+"...");
				map.put("news_time", temp.get(i).get("news_time"));
				dataList.add(map);
			}
			Editor editor = preferences.edit();
			editor.putString("last_time", timeString);
			editor.commit();
		}else if (type==2) {
			
		}
	}
	
	private void setReflashData() {
		if(!Common.isOpenNetwork(this.getActivity())){
			Common.errorDialog(this.getActivity(), "登陆失败", "网络未链接").show();
			listView.reflashComplete();
		}else{
			final Map<String, String> params = new HashMap<String, String>();
			params.put("last_timestamp", timeString);
			new Thread(){
				public void run(){String teString=HttpUtils.submitPostData("http://128.199.226.246/beerich/index.php/news", params, "utf-8");
					try {
						array=new JSONArray(teString);
						//JSONObject object=new JSONObject(teString);
						//error_code = (Integer) array.get(0);
						handler.sendEmptyMessage(0x123);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}.start();
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
				//list_adapter.notifyDataSetChanged();
				//通知listview 刷新数据完毕；
				//listView.reflashComplete();
			}
		}, 0);
		
	}

}
