package com.crowley.p2pnote.functions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.integer;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.crowley.p2pnote.db.DBOpenHelper;
import com.crowley.p2pnote.db.ProductModel;
import com.crowley.p2pnote.db.RecordModel;
import com.crowley.p2pnote.db.RestModel;

import java.text.SimpleDateFormat;

public class Platform {
	
	private Context nowContext;
	
	public Platform(Context context) {
		// TODO Auto-generated constructor stub
		nowContext=context;
	}
	
	/**
	 * ��ȡƽ̨���
	 * 1��ʾ������2��ʾȡ����3��ʾ���棬4��ʾͶ��,5����ؿ�
	 * */	
	public float getRest(String platform){
		DBOpenHelper helper = new DBOpenHelper(nowContext, "record.db");
		SQLiteDatabase db = helper.getWritableDatabase();
		Float rest=0.0f;
		
		Cursor tempCursor=db.rawQuery("select * from rest WHERE userName='"+Common.updateLogin(nowContext)+"' AND platform = '"+platform+"'", null);
		if(tempCursor.getCount()!=0){
			while(tempCursor.moveToNext()){
				RestModel tempRecordModel=new RestModel(tempCursor);
				switch (tempRecordModel.getType()) {
				case 1:{
					rest+=tempRecordModel.getMoney();
					break;
				}
				case 2:{
					rest-=tempRecordModel.getMoney();
					break;
				}
				case 3:{
					rest+=tempRecordModel.getMoney();
					break;
				}
				case 4:{
					rest-=tempRecordModel.getMoney();
					break;
				}
				case 5:{
					rest+=tempRecordModel.getMoney();
					break;
				}
				default:
					break;
				}
			}
		}
		tempCursor.close();
		db.close();
		helper.close();
		return rest;
	}
	
	/**
	 * 
	 * @return ƽ̨ͼ��
	 */
	public List<Integer> getPlatformsIcon(List<String> platformsName){
		List<Integer> mDatas=new ArrayList<Integer>();		
		if(platformsName.size()==0){
			mDatas.add(DBOpenHelper.PLATFORM_ICONS_BIG[(DBOpenHelper.PLATFORM_ICONS_BIG.length)-1]);
			return mDatas;
		}else{
			for(int k=0;k<platformsName.size();k++){
				for(int i=0;i<DBOpenHelper.PLATFORM_NAMES.length;i++){
					if(platformsName.get(k).equals(nowContext.getResources().getString(DBOpenHelper.PLATFORM_NAMES[i]))){
						if(!mDatas.contains(DBOpenHelper.PLATFORM_ICONS_BIG[i])){
							mDatas.add(DBOpenHelper.PLATFORM_ICONS_BIG[i]);
							i=100;
						}else{
							i=100;
						}
					}
					//˵�������һ��ѭ���� ��һ��i�ǲ��ǵ���100 �����ڵĻ���˵����û���ҵ� Ҳû����ӹ�
					if (i==DBOpenHelper.PLATFORM_NAMES.length-1) {
						mDatas.add(DBOpenHelper.PLATFORM_ICONS_BIG[DBOpenHelper.PLATFORM_NAMES.length-1]);
					}
				}			
			}
			return mDatas;
		}
	}
	
	/**
	 * 
	 * @return ƽ̨����
	 */
	public List<String> getPlatformsName(){
		List<String> mDatas=new ArrayList<String>();
		DBOpenHelper helper = new DBOpenHelper(nowContext, "record.db");
		SQLiteDatabase db = helper.getWritableDatabase();
		
		Cursor tempCursor=db.rawQuery("select * from rest WHERE userName='"+Common.updateLogin(nowContext)+"'", null);
		if(tempCursor.getCount()!=0){
			while (tempCursor.moveToNext()){
				RestModel tempRecordModel=new RestModel(tempCursor);
				if(!mDatas.contains(tempRecordModel.getPlatform())){
					mDatas.add(tempRecordModel.getPlatform());
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
			mDatas.add("ƽ̨");
			return mDatas;
		}		
	}
	
	/**
	 * 
	 * @param platform ƽ̨����
	 * @return ��ȡĳƽ̨������
	 */
	public float getEarningAll(String platform){
		DBOpenHelper helper = new DBOpenHelper(nowContext, "record.db");
		SQLiteDatabase db = helper.getWritableDatabase();
		float earningAll=0.0f;
		
		Cursor tempCursor=db.rawQuery("select * from rest WHERE type=3 AND userName='"+Common.updateLogin(nowContext)+"' AND platform = '"+platform+"'", null);
		if(tempCursor.getCount()!=0){
			while (tempCursor.moveToNext()) {
				RestModel tempRecordModel=new RestModel(tempCursor);
				earningAll+=tempRecordModel.getMoney();				
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
	 * @param platform ƽ̨����
	 * @return ��ȡԤ���껯������
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
	 * @param platform ƽ̨����
	 * @return ��ȡ��Ͷ�ܶ�
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
	 * @param platform ƽ̨����
	 * @return ���һ�ʵĳɽ���¼��ʱ��
	 */
	public String getNewestDate(String platform){
		DBOpenHelper helper = new DBOpenHelper(nowContext, "record.db");
		SQLiteDatabase db = helper.getWritableDatabase();
		
		Cursor tempCursor=db.rawQuery("select * from rest WHERE userName='"+Common.updateLogin(nowContext)+"' AND platform='"+platform+"' ORDER BY createTime DESC", null);
		if(tempCursor.getCount()!=0){
			tempCursor.moveToFirst();
			RestModel tempRecordModel=new RestModel(tempCursor);
			helper.close();
			db.close();
			tempCursor.close();
			SimpleDateFormat sf = null;
			Date d = new Date(Long.valueOf(tempRecordModel.getCreateTime()));
	        sf = new SimpleDateFormat("yyyy-MM-dd");
	        return sf.format(d);
		}else{
			helper.close();
			db.close();
			tempCursor.close();
			return "��������";
		}
	}
	
	/**
	 * 
	 * @param platform ƽ̨����
	 * @return ���һ�ʵĳɽ���¼������ 1��ʾ������2��ʾȡ����3��ʾ���棬4��ʾͶ��,5����ؿ�
	 */
	public int getNewestBool(String platform){
		DBOpenHelper helper = new DBOpenHelper(nowContext, "record.db");
		SQLiteDatabase db = helper.getWritableDatabase();
		
		Cursor tempCursor=db.rawQuery("select * from rest WHERE userName='"+Common.updateLogin(nowContext)+"' AND platform='"+platform+"' ORDER BY createTime DESC", null);
		if(tempCursor.getCount()!=0){
			tempCursor.moveToFirst();
			RestModel tempRecordModel=new RestModel(tempCursor);
			return tempRecordModel.getType();
		}else{
			helper.close();
			db.close();
			tempCursor.close();
			return 3;
		}
	}
	
	/**
	 * 
	 * @param platform ƽ̨����
	 * @return ���һ�ʵĳɽ���¼�ľ�������
	 */
	public String getNewestMoney(String platform){
		DBOpenHelper helper = new DBOpenHelper(nowContext, "record.db");
		SQLiteDatabase db = helper.getWritableDatabase();
		
		Cursor tempCursor=db.rawQuery("select * from rest WHERE userName='"+Common.updateLogin(nowContext)+"' AND platform='"+platform+"' ORDER BY createTime DESC", null);
		if(tempCursor.getCount()!=0){
			tempCursor.moveToFirst();
			RestModel tempRecordModel=new RestModel(tempCursor);
			helper.close();
			db.close();
			tempCursor.close();
			return tempRecordModel.getMoney().toString();
		}else{
			helper.close();
			db.close();
			tempCursor.close();
			return "��������";
		}		
	}
	
	/**
	 * 
	 * @return ���صĳɽ���¼
	 */
	public List<Map<String, Object>> getRecordList(String platform){
		DBOpenHelper helper = new DBOpenHelper(nowContext, "record.db");
		SQLiteDatabase db = helper.getWritableDatabase();
		
		List<Map<String, Object>> dataList=new ArrayList<Map<String,Object>>();
		Cursor tempCursor=db.rawQuery("select * from rest WHERE userName='"+Common.updateLogin(nowContext)+"' AND platform='"+platform+"' ORDER BY createTime", null);
		if(tempCursor.getCount()!=0){
			while(tempCursor.moveToNext()){
				RestModel tempRecordModel=new RestModel(tempCursor);
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("record_name", tempRecordModel.getNameString());
				switch (tempRecordModel.getType()) {
				case 1:{
					map.put("record_type", "����");
					break;
				}
				case 2:{
					map.put("record_type", "ȡ��");
					break;
				}
				case 3:{
					map.put("record_type", "�ؿ�:����");
					break;
				}
				case 4:{
					map.put("record_type", "Ͷ��");
					break;
				}
				case 5:{
					map.put("record_type", "�ؿ�:����");
					break;
				}
				default:
					break;
				}
				SimpleDateFormat sf = null;
				Date d = new Date(Long.valueOf(tempRecordModel.getCreateTime()));
		        sf = new SimpleDateFormat("yyyy-MM-dd");
				map.put("record_time", sf.format(d));  
				map.put("record_money", Common.dealFloat(tempRecordModel.getMoney()));
				dataList.add(map);
			}
			return dataList;			
		}else{
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("record_type", "����");  
			map.put("record_time", "��������");
			map.put("record_money", "0");
			dataList.add(map);
			return dataList;
		}		
	}
	
	public void consoleLog(){
		DBOpenHelper helper = new DBOpenHelper(nowContext, "record.db");
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor tempCursor=db.rawQuery("select * from rest WHERE userName='"+Common.updateLogin(nowContext)+"' ORDER BY createTime", null);
		if(tempCursor.getCount()!=0){
			while(tempCursor.moveToNext()){
				RestModel tempRecordModel=new RestModel(tempCursor);
				Log.i("m_info","--------------------------------------------------");
				Log.i("m_info",Integer.valueOf(tempRecordModel.getID()).toString());
				Log.i("m_info",tempRecordModel.getCreateTime());
				Log.i("m_info",tempRecordModel.getTimeStampString());
				Log.i("m_info",tempRecordModel.getNameString());
				Log.i("m_info",tempRecordModel.getPlatform());
				Log.i("m_info",Integer.valueOf(tempRecordModel.getType()).toString());
				Log.i("m_info",Float.valueOf(tempRecordModel.getMoney()).toString());
				Log.i("m_info","--------------------------------------------------");
			}
		}
	}

}
