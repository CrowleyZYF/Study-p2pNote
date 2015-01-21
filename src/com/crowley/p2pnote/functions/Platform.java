package com.crowley.p2pnote.functions;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.crowley.p2pnote.db.DBOpenHelper;
import com.crowley.p2pnote.db.RecordModel;

public class Platform {
	
	private Context nowContext;
	
	public Platform(Context context) {
		// TODO Auto-generated constructor stub
		nowContext=context;
	}
	
	/**
	 * 获取平台余额
	 * 
	 * */	
	public float getRest(String platform){
		DBOpenHelper helper = new DBOpenHelper(nowContext, "record.db");
		SQLiteDatabase db = helper.getWritableDatabase();
		
		Cursor tempCursor=db.rawQuery("select * from record WHERE state=0 AND isDeleted=0 AND userName='"+Common.updateLogin(nowContext)+"' AND platform = '"+platform+"' ORDER BY timeStamp DESC", null);
		if(tempCursor.getCount()!=0){
			if(tempCursor.moveToFirst()){
				RecordModel tempRecordModel=new RecordModel(tempCursor);
				tempCursor.close();
				db.close();
				return tempRecordModel.getRest();
			}else{
				tempCursor.close();
				db.close();
				return 0.0f;
			}
		}else{
			Cursor tempCursor2=db.rawQuery("select * from record WHERE state=1 AND isDeleted=0 AND userName='"+Common.updateLogin(nowContext)+"' AND platform = '"+platform+"' ORDER BY timeStampEnd DESC", null);
			if(tempCursor2.getCount()!=0){
				if(tempCursor2.moveToFirst()){
					RecordModel tempRecordModel=new RecordModel(tempCursor2);
					tempCursor.close();
					tempCursor2.close();
					db.close();
					return tempRecordModel.getRest();
				}else{
					tempCursor.close();
					tempCursor2.close();
					db.close();
					return 0.0f;
				}
			}else{
				tempCursor.close();
				tempCursor2.close();
				db.close();
				return 0.0f;
			}
		}		
	}

}
