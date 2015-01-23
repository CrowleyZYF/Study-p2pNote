package com.crowley.p2pnote.functions;

import java.util.ArrayList;
import java.util.List;

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
	
	/**
	 * 
	 * @return 平台图标
	 */
	public List<Integer> getPlatformsIcon(){
		List<Integer> mDatas=new ArrayList<Integer>();
		DBOpenHelper helper = new DBOpenHelper(nowContext, "record.db");
		SQLiteDatabase db = helper.getWritableDatabase();
		
		Cursor tempCursor=db.rawQuery("select * from record WHERE isDeleted=0 AND userName='"+Common.updateLogin(nowContext)+"'", null);
		if(tempCursor.getCount()!=0){
			while (tempCursor.moveToNext()){
				RecordModel tempRecordModel=new RecordModel(tempCursor);
				for(int i=0;i<DBOpenHelper.PLATFORM_NAMES.length;i++){
					if(tempRecordModel.getPlatform().equals(nowContext.getResources().getString(DBOpenHelper.PLATFORM_NAMES[i]))){
						if(!mDatas.contains(DBOpenHelper.PLATFORM_ICONS_BIG[i])){
							mDatas.add(DBOpenHelper.PLATFORM_ICONS_BIG[i]);
							i=100;
						}else{
							i=100;
						}
					}
					//说明是最后一次循环了 看一下i是不是等于100 不等于的话就说明即没有找到 也没有添加过
					if (i==DBOpenHelper.PLATFORM_NAMES.length-1) {
						mDatas.add(DBOpenHelper.PLATFORM_ICONS_BIG[DBOpenHelper.PLATFORM_NAMES.length-1]);
					}
				}
			}
			helper.close();
			db.close();
			tempCursor.close();
			return mDatas;
		}else{
			mDatas.add(DBOpenHelper.PLATFORM_ICONS_BIG[(DBOpenHelper.PLATFORM_ICONS_BIG.length)-1]);
			helper.close();
			db.close();
			tempCursor.close();
			return mDatas;
		}		
	}
	
	/**
	 * 
	 * @return 平台名称
	 */
	public List<String> getPlatformsName(){
		List<String> mDatas=new ArrayList<String>();
		DBOpenHelper helper = new DBOpenHelper(nowContext, "record.db");
		SQLiteDatabase db = helper.getWritableDatabase();
		
		Cursor tempCursor=db.rawQuery("select * from record WHERE isDeleted=0 AND userName='"+Common.updateLogin(nowContext)+"'", null);
		if(tempCursor.getCount()!=0){
			while (tempCursor.moveToNext()){
				RecordModel tempRecordModel=new RecordModel(tempCursor);
				for(int i=0;i<DBOpenHelper.PLATFORM_NAMES.length;i++){
					if(tempRecordModel.getPlatform().equals(nowContext.getResources().getString(DBOpenHelper.PLATFORM_NAMES[i]))){
						if(!mDatas.contains(tempRecordModel.getPlatform())){
							mDatas.add(tempRecordModel.getPlatform());
							i=100;
						}else{
							i=100;
						}						
					}
					//说明是最后一次循环了 看一下i是不是等于100 不等于的话就说明即没有找到 也没有添加过
					if (i==DBOpenHelper.PLATFORM_NAMES.length-1) {
						mDatas.add(tempRecordModel.getPlatform());
					}
				}
			}
			helper.close();
			db.close();
			tempCursor.close();
			return mDatas;
		}else{
			helper.close();
			db.close();
			tempCursor.close();
			mDatas.add("平台");
			return mDatas;
		}		
	}
	
	/**
	 * 
	 * @param platform 平台名称
	 * @return 获取某平台总收益
	 */
	public float getEarningAll(String platform){
		DBOpenHelper helper = new DBOpenHelper(nowContext, "record.db");
		SQLiteDatabase db = helper.getWritableDatabase();
		float earningAll=0.0f;
		
		Cursor tempCursor=db.rawQuery("select * from record WHERE state=1 AND isDeleted=0 AND userName='"+Common.updateLogin(nowContext)+"' AND platform = '"+platform+"'", null);
		if(tempCursor.getCount()!=0){
			while (tempCursor.moveToNext()) {
				RecordModel tempRecordModel=new RecordModel(tempCursor);
				earningAll+=tempRecordModel.getRestBegin();				
			}
			helper.close();
			db.close();
			tempCursor.close();
			return earningAll;
		}else{
			helper.close();
			db.close();
			tempCursor.close();
			return 0.0f;
		}		
	}
	
	/**
	 * 	
	 * @param platform 平台名称
	 * @return 获取预期年化收益率
	 */
	public float getEarningRateAll(String platform){
		DBOpenHelper helper = new DBOpenHelper(nowContext, "record.db");
		SQLiteDatabase db = helper.getWritableDatabase();
		float amount=0.0f;
		float total=0.0f;
		
		Cursor tempCursor=db.rawQuery("select * from record WHERE state=0 AND isDeleted=0 AND userName='"+Common.updateLogin(nowContext)+"' AND platform = '"+platform+"'", null);
		if(tempCursor.getCount()!=0){
			while(tempCursor.moveToNext()){
				RecordModel tempRecordModel=new RecordModel(tempCursor);
				amount+=tempRecordModel.getMoney();				
				total+=tempRecordModel.getMoney()*tempRecordModel.getEarningMax();
			}
			helper.close();
			db.close();
			tempCursor.close();
			return Common.dealFloat(total/amount*100);
		}else{
			helper.close();
			db.close();
			tempCursor.close();
			return 0.0f;
		}		
	}
	
	/**
	 * 
	 * @param platform 平台名称
	 * @return 获取在投总额
	 */
	public float getAllAmount(String platform){
		DBOpenHelper helper = new DBOpenHelper(nowContext, "record.db");
		SQLiteDatabase db = helper.getWritableDatabase();
		float amount=0.0f;
		
		Cursor tempCursor=db.rawQuery("select * from record WHERE state=0 AND isDeleted=0 AND userName='"+Common.updateLogin(nowContext)+"' AND platform = '"+platform+"'", null);
		if(tempCursor.getCount()!=0){
			while(tempCursor.moveToNext()){
				RecordModel tempRecordModel=new RecordModel(tempCursor);
				amount+=tempRecordModel.getMoney();	
			}
			helper.close();
			db.close();
			tempCursor.close();
			return amount;
		}else{
			helper.close();
			db.close();
			tempCursor.close();
			return 0.0f;
		}		
	}
	
	/**
	 * 	
	 * @param platform 平台名称
	 * @return 最近一笔的成交记录的时间
	 */
	public String getNewestDate(String platform){
		DBOpenHelper helper = new DBOpenHelper(nowContext, "record.db");
		SQLiteDatabase db = helper.getWritableDatabase();
		
		Cursor tempCursor=db.rawQuery("select * from record WHERE state=1 AND isDeleted=0 AND userName='"+Common.updateLogin(nowContext)+"' AND platform='"+platform+"' ORDER BY timeStampEnd DESC", null);
		if(tempCursor.getCount()!=0){
			tempCursor.moveToFirst();
			RecordModel tempRecordModel=new RecordModel(tempCursor);
			helper.close();
			db.close();
			tempCursor.close();
			return tempRecordModel.getTimeEnd();
		}else{
			helper.close();
			db.close();
			tempCursor.close();
			return "暂无数据";
		}		
	}
	
	/**
	 * 
	 * @param platform 平台名称
	 * @return 最近一笔的成交记录的类型 是支出还是收取
	 */
	public boolean getNewestBool(String platform){
		DBOpenHelper helper = new DBOpenHelper(nowContext, "record.db");
		SQLiteDatabase db = helper.getWritableDatabase();
		
		Cursor tempCursor=db.rawQuery("select * from record WHERE state=1 AND isDeleted=0 AND userName='"+Common.updateLogin(nowContext)+"' AND platform='"+platform+"' ORDER BY timeStampEnd DESC", null);
		if(tempCursor.getCount()!=0){
			tempCursor.moveToFirst();
			RecordModel tempRecordModel=new RecordModel(tempCursor);
			if(tempRecordModel.getEarningMin()==0&&tempRecordModel.getEarningMax()==0){
				helper.close();
				db.close();
				tempCursor.close();
				return false;
			}else{
				helper.close();
				db.close();
				tempCursor.close();
				return true;
			}			
		}else{
			helper.close();
			db.close();
			tempCursor.close();
			return true;
		}
	}
	
	/**
	 * 
	 * @param platform 平台名称
	 * @param in 判断是支出还是收益
	 * @return 最近一笔的成交记录的具体数额
	 */
	public String getNewestMoney(String platform,boolean in){
		DBOpenHelper helper = new DBOpenHelper(nowContext, "record.db");
		SQLiteDatabase db = helper.getWritableDatabase();
		
		Cursor tempCursor=db.rawQuery("select * from record WHERE state=1 AND isDeleted=0 AND userName='"+Common.updateLogin(nowContext)+"' AND platform='"+platform+"' ORDER BY timeStampEnd DESC", null);
		if(tempCursor.getCount()!=0){
			tempCursor.moveToFirst();
			RecordModel tempRecordModel=new RecordModel(tempCursor);
			if(in){
				helper.close();
				db.close();
				tempCursor.close();
				return Common.dealFloat(tempRecordModel.getRestBegin())+"";
			}else{
				helper.close();
				db.close();
				tempCursor.close();
				return Common.dealFloat(tempRecordModel.getRestEnd())+"";
			}
		}else{
			helper.close();
			db.close();
			tempCursor.close();
			return "暂无数据";
		}		
	}

}
