package com.crowley.p2pnote;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.crowley.p2pnote.functions.ReturnList;
import com.crowley.p2pnote.ui.listAdapter;
import com.crowley.p2pnote.ui.recordAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class RecordActivity extends Activity implements OnClickListener {

	private ImageButton backButton;
	
	private ListView listView;
	private recordAdapter list_adapter;
	private List<Map<String, Object>> dataList;
	private ReturnList returnList;
	
	private String platformString;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.record);
		
		Intent intent =getIntent();
        platformString = intent.getStringExtra("platform");
		
		initView();
		
		
	}
	
	private void initView(){
		
		backButton=(ImageButton) findViewById(R.id.back);		
		dataList=new ArrayList<Map<String,Object>>();
        listView=(ListView) findViewById(R.id.record_list_view);
        returnList=new ReturnList(this);
        
        getData(platformString);
        list_adapter=new recordAdapter(this, dataList, R.layout.record_listview_item, new String[]{"record_type","record_time","record_money"}, new int[]{R.id.record_type,R.id.record_time,R.id.record_money});
        listView.setAdapter(list_adapter);
        
		backButton.setOnClickListener(this);
	}
	
	private void getData(String platform){
		dataList.clear();
		if(platform=="暂无数据"||platform=="平台"){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("record_type", "收益");  
			map.put("record_time", "暂无数据");  
			map.put("record_money", "0");
			dataList.add(map);
		}else{
			List<Map<String, Object>> tempList=returnList.getRecordList(platformString);
			for(int i=0;i<tempList.size();i++){
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
