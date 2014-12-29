package com.crowley.p2pnote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class UserActivity extends Activity implements OnClickListener {
	
	private TextView loginOutTextView;
	private TextView accountTextView;
	private ImageButton backButton;
	
	private ListView listView;
	private SimpleAdapter simp_adapter;
	private List<Map<String, Object>> dataList;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user);
		
		initView();
		
		
	}
	
	private void initView(){
		
		loginOutTextView=(TextView) findViewById(R.id.login_out);
		accountTextView=(TextView) findViewById(R.id.account);
		listView=(ListView) findViewById(R.id.backup_record);
		backButton=(ImageButton) findViewById(R.id.back);
		
        dataList=new ArrayList<Map<String,Object>>();
		
		simp_adapter=new SimpleAdapter(this, getData(), R.layout.backup_listview_item, new String[]{"backup_time","backup_device"}, new int[]{R.id.backup_time,R.id.backup_device});
		
		
		listView.setAdapter(simp_adapter);
		
		loginOutTextView.setOnClickListener(this);
		backButton.setOnClickListener(this);
		
		SharedPreferences preferences=getSharedPreferences("user", MODE_PRIVATE);
		accountTextView.setText(preferences.getString("account", "出错啦"));		
	}
	
	private List<Map<String, Object>> getData(){
    	for(int i=0;i<9;i++){
    		Map<String, Object> map=new HashMap<String, Object>();
    		map.put("backup_time", "2014-0"+(i+1)+"-02 11:11");
    		map.put("backup_device", "用Android与网站进行了一次云备份");
    		dataList.add(map);    		
    	}
    	return dataList;
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_out:{
			SharedPreferences preferences=getSharedPreferences("user", MODE_PRIVATE);
			Editor editor = preferences.edit();
			editor.putBoolean("isLogined", false);
			editor.commit();
			Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);			
			break;		
		}
		case R.id.back:{
			finish();
			break;
		}
		default:
			break;
		}
		
	}

}
