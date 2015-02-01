package com.crowley.p2pnote.functions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.crowley.p2pnote.db.DBOpenHelper;
import com.crowley.p2pnote.db.RecordModel;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Water {
	
	private Context nowContext;
	
	public Water(Context context){
		nowContext=context;
	}
	
	public String getEarning(String idString){
		DBOpenHelper helper = new DBOpenHelper(nowContext, "record.db");
		SQLiteDatabase db = helper.getWritableDatabase();
		
		Cursor tempCursor=db.rawQuery("select * from record WHERE _id = "+idString, null);
		tempCursor.moveToFirst();
		RecordModel tempRecordModel=new RecordModel(tempCursor);
		tempCursor.close();
		db.close();
		helper.close();
		return "";
	}
	
	public String getOut(String idString){
		DBOpenHelper helper = new DBOpenHelper(nowContext, "record.db");
		SQLiteDatabase db = helper.getWritableDatabase();
		
		Cursor tempCursor=db.rawQuery("select * from record WHERE _id = "+idString, null);
		tempCursor.moveToFirst();
		RecordModel tempRecordModel=new RecordModel(tempCursor);
		tempCursor.close();
		db.close();
		helper.close();
		return "";
	}
	
	public List<Map<String, Object>> waterSort(int type,boolean des){
		DBOpenHelper helper = new DBOpenHelper(nowContext, "record.db");
		SQLiteDatabase db = helper.getWritableDatabase();
		
		List<Map<String, Object>> dataList=new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> temp=new ArrayList<Map<String,Object>>();
		Cursor allRecords = helper.returALLRecords(db);
		if(allRecords.getCount()!=0){
			while (allRecords.moveToNext()) {
				temp.clear();
				Map<String, Object> map=new HashMap<String, Object>();
				RecordModel record=new RecordModel(allRecords);
				//如果记录已经被删除  或者不属于该账户 跳出本次循环
				if(record.getIsDeleted()==1||!record.getUserName().equals(Common.updateLogin(nowContext))||(record.getEarningMin()==0&&record.getEarningMax()==0)){
					continue;
				}
				map.put("item_id", record.getID());
				map.put("item_state", record.getState());
				map.put("timeBegin", record.getTimeBegin());
				map.put("timeEnd", "至 "+record.getTimeEnd());
				int icon = DBOpenHelper.PLATFORM_ICONS[0];
				boolean findIcon=false;
				for(int i=0;i<DBOpenHelper.PLATFORM_NAMES.length-1&&(findIcon==false);i++){
					if(record.getPlatform().equals(nowContext.getResources().getString(DBOpenHelper.PLATFORM_NAMES[i]))){
						icon=DBOpenHelper.PLATFORM_ICONS[i];
						findIcon=true;
					}						
				}
				if (!findIcon) {
					icon=DBOpenHelper.PLATFORM_ICONS[DBOpenHelper.PLATFORM_NAMES.length-1];
					findIcon=true;
				}
				map.put("item_icon", icon);
				map.put("item_name", record.getPlatform()+"-"+record.getType());
				map.put("item_money", record.getMoney());
				if (record.getEarningMin()==0.0) {
					map.put("item_profit", Common.dealFloat(record.getEarningMax()*100)+"%");					
				}else{
					map.put("item_profit", Common.dealFloat(record.getEarningMin()*100)+"%~"+Common.dealFloat(record.getEarningMax()*100)+"%");
				}
				//如果为0，直接添加元素
				if(dataList.size()==0){
					dataList.add(map);
				}else{
					//是否已经添加了，如果没有添加过，则说明该元素应该直接插入到表尾，所以在最后的时候直接add即可
					boolean added=false;
					for(int i=0;i<dataList.size()&&!added;i++){
						boolean judge=false;
						switch (type) {
							case 0:{
								String mapString=record.getTimeEnd();
								String compareString=(((String) (dataList.get(i)).get("timeEnd")).split(" "))[(((String) (dataList.get(i)).get("timeEnd")).split(" ")).length-1];
								if(Common.parseDay(mapString)>(Common.parseDay(compareString))){
									judge=true;
								}							
								break;							
							}
							case 1:{							
								if(((Float) map.get("item_money"))>((Float) (dataList.get(i)).get("item_money"))){
									judge=true;
								}				
								break;							
							}
							case 2:{
								String now = (String) map.get("item_profit");
								String[] nowArray=now.split("~");
								Float nowRate = Float.parseFloat(nowArray[nowArray.length-1].split("%")[0]);
								String compare = (String) (dataList.get(i)).get("item_profit");
								String[] compareArray=compare.split("~");
								Float compareRate = Float.parseFloat(compareArray[compareArray.length-1].split("%")[0]);
								if(nowRate>compareRate){
									judge=true;
								}						
								break;							
							}
							case 3:{
								String mapString=record.getTimeBegin();;
								String compareString=((String) (dataList.get(i)).get("timeBegin"));
								if(Common.parseDay(mapString)>(Common.parseDay(compareString))){
									judge=true;
								}							
								break;
							}
							default:
								break;
						}
						if (judge) {
							for(int j=0;j+i<dataList.size();j++){
								temp.add(dataList.get(j+i));
							}
							dataList.set(i, map);
							for(int k=0;k<temp.size();k++){
								if(i+1+k<dataList.size()){
									dataList.set(i+1+k,temp.get(k));
								}else{
									dataList.add(temp.get(k));
								}									
							}
							added=true;
						}
					}
					if (!added) {
						dataList.add(map);						
					}
				}				
			}
		}
		//如果不是降序，则反转dataList
		if (!des) {
			temp.clear();
			for(int i=0;i<dataList.size();i++){
				temp.add(dataList.get(i));
			}
			for(int i=0;i<dataList.size();i++){
				dataList.set(i, temp.get(dataList.size()-1-i));
			}					
		}
		allRecords.close();
		db.close();
		helper.close();
		return dataList;		
	}

}
