package com.crowley.p2pnote;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.crowley.p2pnote.functions.Platform;
import com.crowley.p2pnote.ui.recordAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class RecordActivity extends Activity implements OnClickListener {

	private ImageButton backButton;
	
	private ListView listView;
	private recordAdapter list_adapter;
	private List<Map<String, Object>> dataList;
	//private ReturnList returnList;
	private Platform platform;
	
	private String platformString;
	private TextView main_tab_banner_title;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.record);
				
		initView();
		initData();
		initEvent();		
		
	}
	
	private void initView(){	
		backButton=(ImageButton) findViewById(R.id.back);
        listView=(ListView) findViewById(R.id.record_list_view);	
        main_tab_banner_title=(TextView) findViewById(R.id.main_tab_banner_title);
	}

	public void initEvent() {
		backButton.setOnClickListener(this);
	}

	public void initData() {
		Intent intent =getIntent();
        platformString = intent.getStringExtra("platform");
        
		dataList=new ArrayList<Map<String,Object>>();
		platform=new Platform(this);     
        getData(platformString);
        list_adapter=new recordAdapter(this, dataList, R.layout.record_listview_item, new String[]{"record_name","record_type","record_time","record_money"}, new int[]{R.id.record_name1,R.id.record_type,R.id.record_time1,R.id.record_money});
        listView.setAdapter(list_adapter);
        main_tab_banner_title.setText(platformString+"的成交记录");
    	
		//platform.consoleLog();
	}
	
	private void getData(String platformString){
		dataList.clear();
		if(platformString=="暂无数据"||platformString=="平台"){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("record_name", "暂无数据");  
			map.put("record_type", "收益");  
			map.put("record_time", "暂无数据");  
			map.put("record_money", "0");
			dataList.add(map);
		}else{
			List<Map<String, Object>> tempList=platform.getRecordList(platformString);
			for(int i=tempList.size()-1;i>=0;i--){
				dataList.add(tempList.get(i));
			}
		}
    }
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:{
			finish();
			break;
		}
		default:
			break;
		}
		
	}

}
