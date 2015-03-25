package com.crowley.p2pnote;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import com.crowley.p2pnote.db.DBOpenHelper;
import com.crowley.p2pnote.db.ProductModel;
import com.crowley.p2pnote.db.RecordModel;
import com.crowley.p2pnote.functions.Common;
import com.crowley.p2pnote.functions.Platform;
import com.github.mikephil.charting.utils.ColorTemplate;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class NewItemActivity extends Activity implements OnItemSelectedListener,android.view.View.OnClickListener{	
	
	private Platform platform;
	private boolean init = false;
	private ImageButton backButton;
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
	//来自余额的本金
	private EditText rest_money;
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
	//模式
	private String modelString;
	private String idString;
	private String platformString;
	//title
	private TextView titleTextView;
	//fake_rest
	private Float fake_restFloat;
	//fake_platform
	private String fake_platformString;
	//prodcut
	private int product;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.new_item);	
		
		Intent intent =getIntent();
		modelString = intent.getStringExtra("model");
        idString = intent.getStringExtra("id");
        platformString = intent.getStringExtra("platform");
		
		//初始化		
		initView();		
		initData();		
		initEvent();	
		

		if(modelString.equals("1")){
    		RecordModel itemModel=Common.getRecordModel(this,idString);
    		//检测平台是否为其他
    		boolean isOther=true;
    		for(int i=0;i<DBOpenHelper.PLATFORM_NAMES.length;i++){
    			if(getResources().getString(DBOpenHelper.PLATFORM_NAMES[i]).equals(itemModel.getPlatform())){
    				isOther=false;
    				platformSpinner.setSelection(i);
    				for(int j=0;j<typeMap.get(itemModel.getPlatform()).size();j++){
    					if(itemModel.getType().equals(((Map<String, Object>) (typeMap.get(itemModel.getPlatform()).get(j))).get("earning_rate_name").toString())){
    						typeSpinner.setSelection(j);
    						product=j;
    					}
    				}
    			}
    		}
    		if(isOther){
    			platformSpinner.setSelection(DBOpenHelper.PLATFORM_NAMES.length-1);
    			customLinearLayout.setVisibility(View.VISIBLE);	
				typeSpinner.setVisibility(View.GONE);
				custom_platform.setText(itemModel.getPlatform());
				custom_type.setText(itemModel.getType());
    		}
    		//本金
    		money.setText(itemModel.getMoney().toString());
    		//rest
    		fake_restFloat=itemModel.getMoneyFromPlatform();
    		rest_money.setText(fake_restFloat.toString()); 
    		rest_money.setHint("上限为"+Float.valueOf(platform.getRest(itemModel.getPlatform())+fake_restFloat).toString());
    		fake_platformString=itemModel.getPlatform();
    		//收益率类型
    		if(itemModel.getEarningMin()==0.0){
    			//固定收益率
    			rateSpinner.setSelection(1);
    			maxEditText.setText(Float.valueOf(Common.dealFloat(itemModel.getEarningMax() * 100)).toString());
    		}else {
				//浮动收益率
    			rateSpinner.setSelection(0);
    			minEditText.setText(Float.valueOf(Common.dealFloat(itemModel.getEarningMin() * 100)).toString());
    			maxEditText.setText(Float.valueOf(Common.dealFloat(itemModel.getEarningMax() * 100)).toString());
			}
    		//计息方式
    		methodSpinner.setSelection(itemModel.getMethod());
    		//时间
    		begin_time.setText(itemModel.getTimeBegin());
    		end_time.setText(itemModel.getTimeEnd()); 
    		//按钮
    		((TextView) new_item_sure.findViewById(R.id.new_item_sure)).setText("确定修改");
    		((TextView) new_item_cancel.findViewById(R.id.new_item_cancel)).setText("取消修改");
    		//title
    		titleTextView.setText("修改记录");
    		//rateSpinner.setSelection(1);
    	}else if(modelString.equals("2")){
    		boolean isOther=true;
    		for(int i=0;i<DBOpenHelper.PLATFORM_NAMES.length;i++){
    			if(getResources().getString(DBOpenHelper.PLATFORM_NAMES[i]).equals(platformString)){
    				isOther=false;
    				platformSpinner.setSelection(i);
    			}
    		}
    		if(isOther){
    			platformSpinner.setSelection(DBOpenHelper.PLATFORM_NAMES.length-1);
    			customLinearLayout.setVisibility(View.VISIBLE);	
				typeSpinner.setVisibility(View.GONE);
				custom_platform.setText(platformString);
				custom_type.setText("");
    		}
    		rateSpinner.setSelection(1);
    	}else{    		
    		rateSpinner.setSelection(1);
    	}
	}

	private void initEvent() {
		backButton.setOnClickListener(this);
		platformSpinner.setOnItemSelectedListener(this);
		typeSpinner.setOnItemSelectedListener(this);
		rateSpinner.setOnItemSelectedListener(this);
		begin_time.setOnClickListener(this);
		end_time.setOnClickListener(this);
		new_item_sure.setOnClickListener(this);
		new_item_cancel.setOnClickListener(this);
	}

	private void initData() {
		platform=new Platform(this);
		//数据源
		platformList = new ArrayList<Map<String,Object>>();
		rateList = new ArrayList<Map<String,Object>>();
		methodList = new ArrayList<Map<String,Object>>();
		typeList = new ArrayList<Map<String, Object>>();
		typeMap = new HashMap<String, List<Map<String, Object>>>();
		//得到数据
		getData();
		//设置适配器
		
		//收益率类型
		rate_Adapter = new SimpleAdapter(this, rateList, R.layout.select_earning_item, new String[]{"earning_rate_name"}, new int[]{R.id.earning_rate_name});
		rateSpinner.setAdapter(rate_Adapter);
		//计息方式
		method_Adapter = new SimpleAdapter(this, methodList, R.layout.select_earning_item, new String[]{"earning_rate_name"}, new int[]{R.id.earning_rate_name});
		methodSpinner.setAdapter(method_Adapter);
		//投资平台
		platform_adapter=new SimpleAdapter(this, platformList, R.layout.select_platform_item, new String[]{"company_icon","company_name"}, new int[]{R.id.company_icon,R.id.company_name});
		platformSpinner.setAdapter(platform_adapter);
		rest_money.setHint("上限为"+platform.getRest(((Map<String, Object>) (platformSpinner.getSelectedItem())).get("company_name").toString()));
	}
	
	private void setPre(){
		String company= ((Map<String, Object>) (platformSpinner.getSelectedItem())).get("company_name").toString();
		String product= ((Map<String, Object>) (typeSpinner.getSelectedItem())).get("earning_rate_name").toString();
		
		DBOpenHelper helper = new DBOpenHelper(NewItemActivity.this, "record.db");
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor tempCursor=db.rawQuery("select * from product WHERE platform='"+company+"' AND product='"+product+"'", null);
		if(tempCursor.getCount()!=0){
			tempCursor.moveToFirst();
			ProductModel productModel=new ProductModel(tempCursor);
			/*if(productModel.getMoney()>0){
				money.setText(productModel.getMoney().toString());
			}*/
			if(productModel.getEarningMin()>=0){
				minEditText.setText(Float.valueOf(Common.dealFloat(productModel.getEarningMin()*100)).toString());
				maxEditText.setText(Float.valueOf(Common.dealFloat(productModel.getEarningMax()*100)).toString());
			}else{
				minEditText.setText("");
				maxEditText.setText("");
			}
			if(productModel.getEarningMin()>0){
				rateSpinner.setSelection(0);
			}else{
				rateSpinner.setSelection(1);
			}
			if(productModel.getMethod()>0){
				methodSpinner.setSelection(productModel.getMethod());
			}else{
				methodSpinner.setSelection(0);
			}
			if(productModel.getPeriod()>0){
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.MONTH, productModel.getPeriod());				
				String monthString;
				String dayString;
				int year=cal.get(Calendar.YEAR);
				int month=cal.get(Calendar.MONTH)+1;
				int day=cal.get(Calendar.DAY_OF_MONTH);				
				if (month<10) {
					monthString="-0"+month;			
				}else{
					monthString="-"+month;
				}
				if (day<10) {
					dayString="-0"+day;
				}else{
					dayString="-"+day;
				}
				end_time.setText(year+monthString+dayString);				
			}
		}		
		tempCursor.close();
	}

	private void initView() {
		backButton = (ImageButton) findViewById(R.id.back);
		//平台
		platformSpinner = (Spinner) findViewById(R.id.platform);
		//类型
		typeSpinner = (Spinner) findViewById(R.id.type);		
		customLinearLayout=(LinearLayout) findViewById(R.id.custom);
		custom_platform=(EditText) findViewById(R.id.custom_platform);
		custom_type=(EditText) findViewById(R.id.custom_type);
		//本金
		money=(EditText) findViewById(R.id.money);
		//
		rest_money=(EditText) findViewById(R.id.money_rest);
		//收益率类型
		rateSpinner = (Spinner) findViewById(R.id.earning_rate);		
		//收益率
		minEditText = (EditText) findViewById(R.id.earning_min);
		maxEditText = (EditText) findViewById(R.id.earning_max);		
		middle2TextView = (TextView) findViewById(R.id.middle2);
		//计息方式
		methodSpinner = (Spinner) findViewById(R.id.earning_method);
		//计息时间
		begin_time = (TextView) findViewById(R.id.begin_time);
		//结束时间
		end_time = (TextView) findViewById(R.id.end_time);
		//确认和取消
		new_item_sure = (LinearLayout) findViewById(R.id.new_item_sure_button);
		new_item_cancel = (LinearLayout) findViewById(R.id.new_item_cancel_button);
		//标题
		titleTextView = (TextView) findViewById(R.id.main_tab_banner_title);
	}
	
	private ArrayList<String> getProductStrings(String platform){
		DBOpenHelper helper = new DBOpenHelper(this, "record.db");		
		SQLiteDatabase db = helper.getWritableDatabase();
		
		ArrayList<String> products = new ArrayList<String>();
		Cursor tempCursor=db.rawQuery("select * from product WHERE platform='"+platform+"'", null);
		if(tempCursor.getCount()!=0){
			while(tempCursor.moveToNext()){
				ProductModel tempRecordModel=new ProductModel(tempCursor);
				products.add(tempRecordModel.getProduct());
			}
			tempCursor.close();
			db.close();
			helper.close();
			return products;
		}else{
			return null;
		}	
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
    		ArrayList<String> productsArrayList=getProductStrings(getResources().getString(DBOpenHelper.PLATFORM_NAMES[i]));
    		Log.i("m_info",getResources().getString(DBOpenHelper.PLATFORM_NAMES[i]));
    		if (productsArrayList!=null) {
    			for(int j=0;j<productsArrayList.size();j++){
    				Map<String, Object> map2=new HashMap<String, Object>();
    				//这里偷懒了 用了和收益率什么的一样的样式 所以字段是这个
        			map2.put("earning_rate_name", productsArrayList.get(j));
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
			init=true;
			Map<String, Object> map = platformList.get(position);
			String company_name = (String) map.get("company_name");			
			for(int i=0;i<typeMap.get(company_name).size();i++){
				typeList.add(typeMap.get(company_name).get(i));
			}
			type_adapter=new SimpleAdapter(this, typeList, R.layout.select_earning_item, new String[]{"earning_rate_name"}, new int[]{R.id.earning_rate_name});
			typeSpinner.setAdapter(type_adapter);
			
			if(modelString.equals("1")){
				typeSpinner.setSelection(product);
			}else if (modelString.equals("0")) {
				setPre();
			}
		}else{			
			switch (parent.getId()) {
			//如果是平台被点击了 检测是否是其他平台 如果是 则隐藏下拉框 改成编辑框 否则更换下拉框内容
			case R.id.platform:{
				Map<String, Object> map = platformList.get(position);
				String company_name = (String) map.get("company_name");
				rest_money.setHint("上限为"+platform.getRest(company_name));
				//其他
				if(company_name.equals(getResources().getString(R.string.company_name32))){
					customLinearLayout.setVisibility(View.VISIBLE);	
					typeSpinner.setVisibility(View.GONE);
					money.setText("");
					minEditText.setText("");
					maxEditText.setText("");
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
				if (modelString.equals("0")) {
					setPre();
				}
				break;			
			}
			case R.id.type:{
				if (modelString.equals("0")) {
					setPre();
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
		switch (v.getId()) {
		case R.id.back:{
			finish();
			break;
		}
		//计息时间
		case R.id.begin_time:{
			new DatePickerDialog(this, new OnDateSetListener() {				
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
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
			//判断company是否为其他
			String company= ((Map<String, Object>) (platformSpinner.getSelectedItem())).get("company_name").toString();
			String type = "";
			if(typeSpinner.getVisibility()==View.GONE){
				if(TextUtils.isEmpty(custom_platform.getText())||TextUtils.isEmpty(custom_type.getText())){
					erroredBoolean=true;
					errorString="自定义平台和其投资类型不得为空";
				}else if((custom_platform.getText().toString()).equals("平台")){
					erroredBoolean=true;
					errorString="自定义平台名称不得为“平台”";
				}else{
					company=custom_platform.getText().toString();
					type=custom_type.getText().toString();
				}
			}else{
				type = ((Map<String, Object>) (typeSpinner.getSelectedItem())).get("earning_rate_name").toString();
			}			
			//判断本金
			Float moneys=0.0f;
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
			//判断来自余额本金
			Float money_rest=0.0f;
			if(TextUtils.isEmpty(rest_money.getText())){
				money_rest=0.0f;
			}else{
				money_rest = Float.parseFloat(rest_money.getText().toString());
				Float monet_rest_max=0.0f;
				//如果是修改模式并且平台没有修改，那么余额上限是现在的平台余额加上未修改前的来自余额
				if(modelString.equals("1")&&fake_platformString.equals(company)){
					monet_rest_max=platform.getRest(company)+fake_restFloat;
				}else{
					monet_rest_max=platform.getRest(company);
				}
				if(money_rest<0){
					erroredBoolean=true;
					errorString="来自余额不得为负数";
				}else if(money_rest>moneys){
					erroredBoolean=true;
					errorString="来自余额不得大于总本金";
				}else if(money_rest>monet_rest_max){
					erroredBoolean=true;
					errorString="来自余额不得大于余额上限";
				}
			}
			//判断收益率
			Float min = 0.0f;
			Float max = 0.0f;
			String rate = ((Map<String, Object>) (rateSpinner.getSelectedItem())).get("earning_rate_name").toString();
			if(rate.equals("浮动收益率")){
				if(TextUtils.isEmpty(minEditText.getText())||TextUtils.isEmpty(maxEditText.getText())){
					erroredBoolean=true;
					errorString="收益率不得为空";
				}else{
					min = Float.parseFloat(minEditText.getText().toString())/100;
					if(min<=0){
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
					if(max<=0){
						erroredBoolean=true;
						errorString="收益率必须大于0";				
					}
				}
			}			
			//判断计息方式
			String test = ((Map<String, Object>) (methodSpinner.getSelectedItem())).get("earning_rate_name").toString();
			int method=0;
			if (test.equals("到期还本息")) {
				method=0;
			}else if(test.equals("按月还本息")){
				method=1;
			}else{
				method=2;
			}			
			//判断时间
			String begin_dayString=begin_time.getText().toString();
			String end_dayString=end_time.getText().toString();
			
			String[] time1=begin_dayString.split("-");
			String[] time2=end_dayString.split("-");
			int day1=Integer.parseInt(time1[2]);
			int day2=Integer.parseInt(time2[2]);			
			
			if(Common.parseDay(begin_dayString)>=Common.parseDay(end_dayString)){
				erroredBoolean=true;
				errorString="结束时间应晚于计息时间";	
			}
			//创建时间戳
			Long tsLong = System.currentTimeMillis();
			String ts = tsLong.toString();			
			//用户名
			String userNameString="";			
			SharedPreferences preferences=getSharedPreferences("user", MODE_PRIVATE);
			boolean isLogined = preferences.getBoolean("isLogined", false);
			if(isLogined){
				userNameString=preferences.getString("account", "出错啦");
			}else{
				userNameString="not_login";
			}
			DBOpenHelper helper = new DBOpenHelper(NewItemActivity.this, "record.db");
			SQLiteDatabase db = helper.getWritableDatabase();
			String recordSqlString="";
			String restSqlString1="";
			String restSqlString4="";
			if(modelString.equals("1")){
				recordSqlString="UPDATE record SET platform= '"+company+"', type= '"+type+"', moneyFromPlatform= "+money_rest+" , moneyFromNew= "+(moneys-money_rest)+" , earningMin= "+min+" ,earningMax= "+max+" ,method= "+method+" ,timeBegin= '"+begin_dayString+"' ,timeEnd='"+end_dayString+"' WHERE _id="+idString+"";
				Cursor tempCursor=db.rawQuery("select * from record WHERE _id="+idString, null);
				tempCursor.moveToFirst();
				String timeStamp=new RecordModel(tempCursor).getTimeStamp();
				tempCursor.close();
				if(moneys-money_rest>0){
					restSqlString1="UPDATE rest SET platform='"+company+"' , name='"+(company+"-"+type)+"' , money="+(moneys-money_rest)+" WHERE type=1 AND timeStamp='"+timeStamp+"'";
				}else if(moneys-money_rest==0){
					//没有新增的记录，则需要删除记录
					restSqlString1="DELETE FROM rest WHERE timeStamp = '"+timeStamp+"' AND type=1 ";
				}
				restSqlString4="UPDATE rest SET platform='"+company+"' , name='"+(company+"-"+type)+"' , money="+moneys+" WHERE type=4 AND timeStamp='"+timeStamp+"'";
			}else{
				recordSqlString="insert into record(platform,type,moneyFromPlatform,moneyFromNew,earningMin,earningMax,method,timeBegin,timeEnd,timeStamp,state,isDeleted,userName) values('"+company+"','"+type+"',"+money_rest+","+(moneys-money_rest)+","+min+","+max+","+method+",'"+begin_dayString+"','"+end_dayString+"','"+ts+"',0,0,'"+userNameString+"')";
				if(moneys-money_rest>0){
					restSqlString1="insert into rest(platform,name,type,money,timeStamp,userName,createTime) values('"+company+"','"+(company+"-"+type)+"',1,"+(moneys-money_rest)+",'"+ts+"','"+userNameString+"','"+ts+"')";
				}
				restSqlString4="insert into rest(platform,name,type,money,timeStamp,userName,createTime) values('"+company+"','"+(company+"-"+type)+"',4,"+moneys+",'"+ts+"','"+userNameString+"','"+ts+"')";
			}
			//如果出错则弹出提示
			if (erroredBoolean) {
				new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
	                .setTitleText("新建投资失败")
	                .setContentText(errorString)
	                .setConfirmText("确定")
	                .show();
			//如果成功则插入数据并返回
			}else{
				db.execSQL(recordSqlString);
				if(!restSqlString1.equals("")){
					db.execSQL(restSqlString1);
				}
				db.execSQL(restSqlString4);
				db.close();
				helper.close();				
				finish();
			}			
			break;
		}
		//取消
		case R.id.new_item_cancel_button:{
			finish();
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
