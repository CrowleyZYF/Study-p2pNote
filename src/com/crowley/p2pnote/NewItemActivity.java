package com.crowley.p2pnote;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import com.crowley.p2pnote.db.DBOpenHelper;
import com.crowley.p2pnote.functions.ReturnList;

import android.R.integer;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class NewItemActivity extends Activity implements OnItemSelectedListener,android.view.View.OnClickListener{
	
	private ReturnList returnList;
		
	private boolean init = false;
	
	//平台下拉框
	private Spinner platformSpinner;
	private SimpleAdapter platform_adapter;
	private List<Map<String, Object>> platformList;
	
	//投资类型下拉框（如果选择已经录入的平台）
	private Spinner typeSpinner;
	private SimpleAdapter type_adapter;
	private List<Map<String, Object>> typeList;
	private Map<String, List<Map<String, Object>>> typeMap;
	
	//投资类型输入框（如果其他产品）
	private LinearLayout customLinearLayout;
	private EditText custom_platform;
	private EditText custom_type;		
	
	//本金
	private EditText money;
	
	//收益率类型
	private Spinner rateSpinner;
	private SimpleAdapter rate_Adapter;
	private List<Map<String, Object>> rateList;
	
	//收益率
	private EditText minEditText;
	private EditText maxEditText;
	private TextView middle2TextView;
	
	//计息方式
	private Spinner methodSpinner;
	private SimpleAdapter method_Adapter;
	private List<Map<String, Object>> methodList;	
	
	//日期
	private Calendar cal;
	private int year;
	private int month;
	private int day;
	private TextView begin_time;
	private TextView end_time;
	private boolean begin = true;
	
	//按钮
	private LinearLayout new_item_sure;
	private LinearLayout new_item_cancel;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.new_item);
		
		//初始化		
		initView();		
		initData();		
		initEvent();		
	}

	private void initEvent() {
		platformSpinner.setOnItemSelectedListener(this);
		rateSpinner.setOnItemSelectedListener(this);
		begin_time.setOnClickListener(this);
		end_time.setOnClickListener(this);
		new_item_sure.setOnClickListener(this);
		new_item_cancel.setOnClickListener(this);
	}

	private void initData() {
		returnList = new ReturnList(this);
		
		platformList = new ArrayList<Map<String,Object>>();
		rateList = new ArrayList<Map<String,Object>>();
		methodList = new ArrayList<Map<String,Object>>();
		typeList = new ArrayList<Map<String, Object>>();
		typeMap = new HashMap<String, List<Map<String, Object>>>();
		
		getData();
		
		rate_Adapter = new SimpleAdapter(this, rateList, R.layout.select_earning_item, new String[]{"earning_rate_name"}, new int[]{R.id.earning_rate_name});
		rateSpinner.setAdapter(rate_Adapter);
		
		method_Adapter = new SimpleAdapter(this, methodList, R.layout.select_earning_item, new String[]{"earning_rate_name"}, new int[]{R.id.earning_rate_name});
		methodSpinner.setAdapter(method_Adapter);
		
		platform_adapter=new SimpleAdapter(this, platformList, R.layout.select_platform_item, new String[]{"company_icon","company_name"}, new int[]{R.id.company_icon,R.id.company_name});
		platformSpinner.setAdapter(platform_adapter);
	}

	private void initView() {
		platformSpinner = (Spinner) findViewById(R.id.platform);
		
		typeSpinner = (Spinner) findViewById(R.id.type);		
		customLinearLayout=(LinearLayout) findViewById(R.id.custom);
		
		money=(EditText) findViewById(R.id.money);
		
		rateSpinner = (Spinner) findViewById(R.id.earning_rate);		

		minEditText = (EditText) findViewById(R.id.earning_min);
		maxEditText = (EditText) findViewById(R.id.earning_max);		
		middle2TextView = (TextView) findViewById(R.id.middle2);
		
		methodSpinner = (Spinner) findViewById(R.id.earning_method);
		
		begin_time = (TextView) findViewById(R.id.begin_time);
		
		end_time = (TextView) findViewById(R.id.end_time);
		
		new_item_sure = (LinearLayout) findViewById(R.id.new_item_sure_button);
		new_item_cancel = (LinearLayout) findViewById(R.id.new_item_cancel_button);
	}
	
	private void getData(){	
		//初始化时间
		cal=Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH)+1;
        day = cal.get(Calendar.DAY_OF_MONTH);
        
        if(month<10){
        	if(day<10){
        		begin_time.setText(year+"-0"+month+"-0"+day);
                end_time.setText(year+"-0"+month+"-0"+day); 
        	}else{
        		begin_time.setText(year+"-0"+month+"-"+day);
                end_time.setText(year+"-0"+month+"-"+day);
        	}        	       	
        }else if(day<10){
        	begin_time.setText(year+"-"+month+"-0"+day);
            end_time.setText(year+"-"+month+"-0"+day);        	
        }      
        
        //初始化收益率类型
        rateList.clear();
        Map<String, Object> map3=new HashMap<String, Object>();
		map3.put("earning_rate_name", getResources().getString(R.string.select_earning_item01));
		rateList.add(map3);		
		Map<String, Object> map4=new HashMap<String, Object>();
		map4.put("earning_rate_name", getResources().getString(R.string.select_earning_item02));
		rateList.add(map4);
		
		//初始化计息方式
		methodList.clear();
        Map<String, Object> map5=new HashMap<String, Object>();
		map5.put("earning_rate_name", getResources().getString(R.string.select_method_item01));
		methodList.add(map5);
		Map<String, Object> map6=new HashMap<String, Object>();
		map6.put("earning_rate_name", getResources().getString(R.string.select_method_item02));
		methodList.add(map6);
		Map<String, Object> map7=new HashMap<String, Object>();
		map7.put("earning_rate_name", getResources().getString(R.string.select_method_item03));
		methodList.add(map7);
		
		//初始化投资平台和类型
		platformList.clear();
    	for(int i=0;i<DBOpenHelper.PLATFORM_ICONS.length;i++){
    		Map<String, Object> map=new HashMap<String, Object>();
    		map.put("company_icon", DBOpenHelper.PLATFORM_ICONS[i]);
    		map.put("company_name", getResources().getString(DBOpenHelper.PLATFORM_NAMES[i]));
    		List<Map<String, Object>> typesList = new ArrayList<Map<String, Object>>();
    		for(int j=0;j<DBOpenHelper.PLATFORM_PRODUCT[i].length;j++){
    			if(i==8){
    				j=100;
    			}else{
    				Map<String, Object> map2=new HashMap<String, Object>();
    				//这里偷懒了 用了和收益率什么的一样的样式 所以字段是这个
        			map2.put("earning_rate_name", getResources().getString(DBOpenHelper.PLATFORM_PRODUCT[i][j]));
        			typesList.add(map2);
    			}    			    			
    		}
    		platformList.add(map);
    		typeMap.put(getResources().getString(DBOpenHelper.PLATFORM_NAMES[i]), typesList);    		
    	}		
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		//这个好像默认会调用一次 所以来了个初始化？
		if(init==false){
			Map<String, Object> map = platformList.get(position);
			String company_name = (String) map.get("company_name");
			init=true;
			for(int i=0;i<typeMap.get(company_name).size();i++){
				typeList.add(typeMap.get(company_name).get(i));
			}
			type_adapter=new SimpleAdapter(this, typeList, R.layout.select_earning_item, new String[]{"earning_rate_name"}, new int[]{R.id.earning_rate_name});
			typeSpinner.setAdapter(type_adapter);
		}else{			
			switch (parent.getId()) {
			//如果是平台被点击了 检测是否是其他平台 如果是 则隐藏下拉框 改成编辑框 否则更换下拉框内容
			case R.id.platform:{
				Map<String, Object> map = platformList.get(position);
				String company_name = (String) map.get("company_name");
				//其他
				if(company_name.equals(getResources().getString(R.string.company_name09))){
					customLinearLayout.setVisibility(View.VISIBLE);	
					typeSpinner.setVisibility(View.GONE);
				//不是其他
				}else{
					customLinearLayout.setVisibility(View.GONE);	
					typeSpinner.setVisibility(View.VISIBLE);
					typeList.clear();
					for(int i=0;i<typeMap.get(company_name).size();i++){
						typeList.add(typeMap.get(company_name).get(i));
					}
					type_adapter.notifyDataSetChanged();
				}								
				break;			
			}			
			//如果是收益率类型被点击 则根据类型对收益率进行变化
			case R.id.earning_rate:{
				Map<String, Object> map = rateList.get(position);
				String earning_rate_name = (String) map.get("earning_rate_name");
				if (earning_rate_name.equals(getResources().getString(R.string.select_earning_item02))) {
					minEditText.setVisibility(View.GONE);
					middle2TextView.setVisibility(View.GONE);
					maxEditText.setHint("收益率");
				}else{
					minEditText.setVisibility(View.VISIBLE);
					middle2TextView.setVisibility(View.VISIBLE);
					maxEditText.setHint("上限");
				}				
				break;				
			}
			default:
				break;
			}
		}				
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		//计息时间
		case R.id.begin_time:{
			new DatePickerDialog(this, new OnDateSetListener() {				
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
					// TODO Auto-generated method stub
					String divide1="-";
					String divide2="-";
					if((monthOfYear+1)<10){
						divide1="-0";
					}
					if((dayOfMonth)<10){
						divide2="-0";
					}
					begin_time.setText(year+divide1+(monthOfYear+1)+divide2+dayOfMonth);
				}
			}, year, cal.get(Calendar.MONTH), day).show();
			break;
		}
		//结束时间
		case R.id.end_time:{
			begin=false;
			new DatePickerDialog(this, new OnDateSetListener() {				
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
					// TODO Auto-generated method stub
					String divide1="-";
					String divide2="-";
					if((monthOfYear+1)<10){
						divide1="-0";
					}
					if((dayOfMonth)<10){
						divide2="-0";
					}
					end_time.setText(year+divide1+(monthOfYear+1)+divide2+dayOfMonth);
				}
			}, year, cal.get(Calendar.MONTH), day).show();
			break;
		}
		//新建投资
		case R.id.new_item_sure_button:{
			String errorString="";
			Boolean erroredBoolean=false;
			String company= ((Map<String, Object>) (platformSpinner.getSelectedItem())).get("company_name").toString();
			String type = "";
			String rate = ((Map<String, Object>) (rateSpinner.getSelectedItem())).get("earning_rate_name").toString();
			Float moneys=0.0f;
			
			//判断company是否为其他
			if(company.equals(getResources().getString(DBOpenHelper.PLATFORM_NAMES[DBOpenHelper.PLATFORM_NAMES.length-1]))){
				if(TextUtils.isEmpty(custom_platform.getText())||TextUtils.isEmpty(custom_type.getText())){
					erroredBoolean=true;
					errorString="收益率不得为空";	
				}else{
					company=custom_platform.getText().toString();
					type=custom_type.getText().toString();
				}				
			}else{
				type = ((Map<String, Object>) (typeSpinner.getSelectedItem())).get("earning_rate_name").toString();
			}
			
			if(TextUtils.isEmpty(money.getText())){
				erroredBoolean=true;
				errorString="本金数目不得为空";	
			}else{
				moneys = Float.parseFloat(money.getText().toString());
				if(moneys<=0){
					erroredBoolean=true;
					errorString="请输入大于0的本金数目";
				}
			}
			
			Float min = 0.0f;
			Float max = 0.0f;
			if(rate.equals("浮动收益率")){
				if(TextUtils.isEmpty(minEditText.getText())||TextUtils.isEmpty(maxEditText.getText())){
					erroredBoolean=true;
					errorString="收益率不得为空";	
				}else{
					min = Float.parseFloat(minEditText.getText().toString())/100;
					if(min<0){
						erroredBoolean=true;
						errorString="请输入大于0的下限数目";				
					}
					max = Float.parseFloat(maxEditText.getText().toString())/100;
					if(max<min){
						erroredBoolean=true;
						errorString="收益率上限应大于收益率下限";				
					}
				}				
			}else{
				if(TextUtils.isEmpty(maxEditText.getText())){
					erroredBoolean=true;
					errorString="收益率不得为空";	
				}else{
					max = Float.parseFloat(maxEditText.getText().toString())/100;
					if(max<0){
						erroredBoolean=true;
						errorString="收益率不得为空";				
					}
				}
			}			
			String test = ((Map<String, Object>) (methodSpinner.getSelectedItem())).get("earning_rate_name").toString();
			int method=0;
			if (test.equals("到期还本息")) {
				method=0;
			}else if(test.equals("按月还本息")){
				method=1;
			}else{
				method=2;
			}
			String begin_dayString=begin_time.getText().toString();
			String end_dayString=end_time.getText().toString();
			if(returnList.parseDay(begin_dayString)>returnList.parseDay(end_dayString)){
				erroredBoolean=true;
				errorString="结束时间应晚于计息时间";	
			}
			String sqlString="insert into record(platform,type,money,earningMin,earningMax,method,timeBegin,timeEnd) values('"+company+"','"+type+"',"+moneys+","+min+","+max+","+method+",'"+begin_dayString+"','"+end_dayString+"')";
			if (erroredBoolean) {
				//Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
				new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
	                .setTitleText("新建投资失败")
	                .setContentText(errorString)
	                .setConfirmText("确定")
	                .show();
			}else{
				DBOpenHelper helper = new DBOpenHelper(NewItemActivity.this, "record.db");
				SQLiteDatabase db = helper.getWritableDatabase();
				db.execSQL(sqlString);
				Intent intent=new Intent(this,MainActivity.class);
	            startActivity(intent);
			}			
			break;
		}
		//取消
		case R.id.new_item_cancel_button:{
			Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
			break;
		}
		default:
			break;
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
