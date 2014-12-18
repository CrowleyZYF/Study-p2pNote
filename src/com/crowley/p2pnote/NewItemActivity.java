package com.crowley.p2pnote;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

public class NewItemActivity extends Activity {
	
	public static final String TABLENAME = "record";
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.new_item);
		
		SQLiteDatabase db = openOrCreateDatabase("record.db", MODE_PRIVATE, null);
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
		db.close();
	}

}
