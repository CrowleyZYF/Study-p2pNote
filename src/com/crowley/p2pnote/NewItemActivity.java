package com.crowley.p2pnote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

public class NewItemActivity extends Activity {
	
	public static final String TABLENAME = "record";
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
	
	private Spinner platformSpinner;
	private SimpleAdapter platform_adapter;
	private List<Map<String, Object>> platformList;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.new_item);
		
		platformSpinner = (Spinner) findViewById(R.id.platform);
		platformList=new ArrayList<Map<String,Object>>();
		getPlatformData();
		platform_adapter=new SimpleAdapter(this, platformList, R.layout.select_platform_item, new String[]{"company_icon","company_name"}, new int[]{R.id.company_icon,R.id.company_name});
		platformSpinner.setAdapter(platform_adapter);
		
		
		
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
	
	private void getPlatformData(){
		platformList.clear();
    	for(int i=0;i<9;i++){
    		Map<String, Object> map=new HashMap<String, Object>();
    		map.put("company_icon", PLATFORM_ICONS[i]);
    		map.put("company_name", getResources().getString(PLATFORM_NAMES[i]));
    		platformList.add(map);    		
    	}
		
	}

}
