package com.crowley.p2pnote;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.crowley.p2pnote.db.DBOpenHelper;

import android.R.integer;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class NewItemActivity extends Activity implements OnItemSelectedListener,android.view.View.OnClickListener{
	
	public static final String TABLENAME = "record";
	
	//平台图标
	public static final int[] PLATFORM_ICONS = {
		R.drawable.company_icon01,
		R.drawable.company_icon02,
		R.drawable.company_icon03,
		R.drawable.company_icon04,
		R.drawable.company_icon05,
		R.drawable.company_icon06,
		R.drawable.company_icon07,
		R.drawable.company_icon08,
		R.drawable.company_icon09
		};
	
	//平台名称
	public static final int[] PLATFORM_NAMES = {
		R.string.company_name01,
		R.string.company_name02,
		R.string.company_name03,
		R.string.company_name04,
		R.string.company_name05,
		R.string.company_name06,
		R.string.company_name07,
		R.string.company_name08,
		R.string.company_name09
		};
	
	private boolean init = false;
	
	//平台下拉框
	private Spinner platformSpinner;
	private SimpleAdapter platform_adapter;
	private List<Map<String, Object>> platformList;
	
	//投资类型下拉框
	private Spinner typeSpinner;
	private SimpleAdapter type_adapter;
	private List<Map<String, Object>> typeList;
	private Map<String, List<Map<String, Object>>> typeMap;
	
	//收益率类型
	private Spinner rateSpinner;
	private SimpleAdapter rate_Adapter;
	private List<Map<String, Object>> rateList;
	
	//计息方式
	private Spinner methodSpinner;
	private SimpleAdapter method_Adapter;
	private List<Map<String, Object>> methodList;
	
	//收益率
	private EditText minEditText;
	private EditText maxEditText;
	private TextView middle2TextView;
	
	//日期
	private Calendar cal;
	private int year;
	private int month;
	private int day;
	private TextView begin_time;
	private TextView end_time;
	private boolean begin = true;
	
	//数据库
	
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.new_item);
		
		//初始化
		DBOpenHelper helper = new DBOpenHelper(NewItemActivity.this, "record.db");
		SQLiteDatabase db = helper.getWritableDatabase();		
		platformSpinner = (Spinner) findViewById(R.id.platform);
		typeSpinner = (Spinner) findViewById(R.id.type);
		rateSpinner = (Spinner) findViewById(R.id.earning_rate);
		methodSpinner = (Spinner) findViewById(R.id.earning_method);
		minEditText = (EditText) findViewById(R.id.earning_min);
		maxEditText = (EditText) findViewById(R.id.earning_max);
		middle2TextView = (TextView) findViewById(R.id.middle2);
		begin_time = (TextView) findViewById(R.id.begin_time);
		end_time = (TextView) findViewById(R.id.end_time);
		platformList = new ArrayList<Map<String,Object>>();
		rateList = new ArrayList<Map<String,Object>>();
		methodList = new ArrayList<Map<String,Object>>();
		typeList = new ArrayList<Map<String, Object>>();
		typeMap = new HashMap<String, List<Map<String, Object>>>();
		initData();
		
		rate_Adapter = new SimpleAdapter(this, rateList, R.layout.select_earning_item, new String[]{"earning_rate_name"}, new int[]{R.id.earning_rate_name});
		rateSpinner.setAdapter(rate_Adapter);
		
		method_Adapter = new SimpleAdapter(this, methodList, R.layout.select_earning_item, new String[]{"earning_rate_name"}, new int[]{R.id.earning_rate_name});
		methodSpinner.setAdapter(method_Adapter);
		
		platform_adapter=new SimpleAdapter(this, platformList, R.layout.select_platform_item, new String[]{"company_icon","company_name"}, new int[]{R.id.company_icon,R.id.company_name});
		platformSpinner.setAdapter(platform_adapter);
		
		
		
		platformSpinner.setOnItemSelectedListener(this);
		rateSpinner.setOnItemSelectedListener(this);
		begin_time.setOnClickListener(this);
		end_time.setOnClickListener(this);
		
		
		
		/*SQLiteDatabase db = openOrCreateDatabase("record.db", MODE_PRIVATE, null);
		db.execSQL("create table if not exists record (_id integer primary key autoincrement,platform text not null,type text not null,money real not null,earningMin real not null,earningMax real not null,method integer not null,timeBegin text not null,timeEnd text not null)");
		//db.execSQL("insert into record(platform,type,money,earningMin,earningMax,method,timeBegin,timeEnd) values('陆金所','富赢人生',10000.00,0.05,0.08,0,'2014-12-16','2015-12-16')");
		//db.execSQL("insert into record(platform,type,money,earningMin,earningMax,method,timeBegin,timeEnd) values('陆金所','富赢人生',10000.00,0.05,0.08,0,'2014-12-17','2015-12-17')");
		//db.execSQL("insert into record(platform,type,money,earningMin,earningMax,method,timeBegin,timeEnd) values('陆金所','富赢人生',10000.00,0.05,0.08,0,'2014-12-18','2015-12-18')");
		ContentValues values = new ContentValues();
		values.put("platform", "陆金所");
		values.put("type", "富赢人生");
		values.put("money", 10000.00);
		values.put("earningMin", 0.01);
		values.put("earningMax", 0.09);
		values.put("method", 0);
		values.put("timeBegin", "2014-12-16");
		values.put("timeEnd", "2015-12-16");
		long rowId = db.insert(TABLENAME, null, values);
		values.clear();
		values.put("type", "富赢人生123");
		db.update(TABLENAME, values, "_id>?", new String[]{"3"});
		//db.delete(TABLENAME, "_id>?", new String[]{"4"});
		Cursor cursor = db.rawQuery("select * from record", null);
		
		if(cursor!=null){
			while (cursor.moveToNext()) {
				Log.i("m_info", "_id:"+cursor.getInt(cursor.getColumnIndex("_id")));
				Log.i("m_info", "platform:"+cursor.getString(cursor.getColumnIndex("platform")));
				Log.i("m_info", "type:"+cursor.getString(cursor.getColumnIndex("type")));
				Log.i("m_info", "money:"+cursor.getFloat(cursor.getColumnIndex("money")));
				Log.i("m_info", "earningMin:"+cursor.getFloat(cursor.getColumnIndex("earningMin")));
				Log.i("m_info", "earningMax:"+cursor.getFloat(cursor.getColumnIndex("earningMax")));
				Log.i("m_info", "method:"+cursor.getInt(cursor.getColumnIndex("method")));
				Log.i("m_info", "timeBegin:"+cursor.getString(cursor.getColumnIndex("timeBegin")));
				Log.i("m_info", "timeEnd:"+cursor.getString(cursor.getColumnIndex("timeEnd")));
				Log.i("m_info", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			}
			cursor.close();
		}
		db.close();*/
	}
	
	private void initData(){
		
		cal=Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH)+1;
        day = cal.get(Calendar.DAY_OF_MONTH);
        
        begin_time.setText(year+"-"+month+"-"+day);
        end_time.setText(year+"-"+month+"-"+day);
        
        rateList.clear();
        Map<String, Object> map3=new HashMap<String, Object>();
		map3.put("earning_rate_name", getResources().getString(R.string.select_earning_item01));
		rateList.add(map3);
		Map<String, Object> map4=new HashMap<String, Object>();
		map4.put("earning_rate_name", getResources().getString(R.string.select_earning_item02));
		rateList.add(map4);
		
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
		
		platformList.clear();
    	for(int i=0;i<9;i++){
    		Map<String, Object> map=new HashMap<String, Object>();
    		map.put("company_icon", PLATFORM_ICONS[i]);
    		map.put("company_name", getResources().getString(PLATFORM_NAMES[i]));
    		List<Map<String, Object>> typesList = new ArrayList<Map<String, Object>>();
    		for(int j=0;j<8;j++){
    			Map<String, Object> map2=new HashMap<String, Object>();
        		map2.put("company_icon", PLATFORM_ICONS[i]);
        		map2.put("type_name", getResources().getString(PLATFORM_NAMES[i])+":投资项目"+j);
    			typesList.add(map2);
    			if(i==8){
    				j=100;
    			}
    		}
    		platformList.add(map);
    		typeMap.put(getResources().getString(PLATFORM_NAMES[i]), typesList);    		
    	}		
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		//Toast.makeText(getApplicationContext(), "id:"+parent.getId(),Toast.LENGTH_SHORT).show();
		//Log.i("m_info", "id:"+id);
		if(init==false){
			Map<String, Object> map = platformList.get(position);
			String company_name = (String) map.get("company_name");
			init=true;
			for(int i=0;i<typeMap.get(company_name).size();i++){
				typeList.add(typeMap.get(company_name).get(i));
			}
			type_adapter=new SimpleAdapter(this, typeList, R.layout.select_type_item, new String[]{"company_icon","type_name"}, new int[]{R.id.company_icon,R.id.type_name});
			typeSpinner.setAdapter(type_adapter);
		}else{			
			switch (parent.getId()) {
			case R.id.platform:{
				Map<String, Object> map = platformList.get(position);
				String company_name = (String) map.get("company_name");
				typeList.clear();
				for(int i=0;i<typeMap.get(company_name).size();i++){
					typeList.add(typeMap.get(company_name).get(i));
				}
				type_adapter.notifyDataSetChanged();				
				break;			
			}
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
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.begin_time:{
			new DatePickerDialog(this, new OnDateSetListener() {				
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
					// TODO Auto-generated method stub
					begin_time.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
				}
			}, year, cal.get(Calendar.MONTH), day).show();
			break;
		}
		case R.id.end_time:{
			begin=false;
			new DatePickerDialog(this, new OnDateSetListener() {				
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
					// TODO Auto-generated method stub
					end_time.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
				}
			}, year, cal.get(Calendar.MONTH), day).show();
			break;
		}
		default:
			break;
		}
		
	}

}
