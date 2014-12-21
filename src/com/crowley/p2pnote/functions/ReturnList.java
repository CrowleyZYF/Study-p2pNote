package com.crowley.p2pnote.functions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.spec.IvParameterSpec;

import android.R.integer;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.crowley.p2pnote.R;
import com.crowley.p2pnote.db.DBOpenHelper;

public class ReturnList {
	
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

	private int parseDay(String date){
		String[] time=date.split("-");
		int year=Integer.parseInt(time[0]);
		int month=Integer.parseInt(time[1]);
		int day=Integer.parseInt(time[2]);	
		return year*365+month*30+day;		
	}
	
	public List<Map<String, Object>> waterSort(Context context,int type,boolean des){
		DBOpenHelper helper = new DBOpenHelper(context, "record.db");
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from record", null);
		List<Map<String, Object>> dataList=new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> temp=new ArrayList<Map<String,Object>>();
		if(cursor!=null){
			while (cursor.moveToNext()) {
				temp.clear();
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("time", cursor.getString(cursor.getColumnIndex("timeEnd")));
				int icon = PLATFORM_ICONS[0];
				for(int i=0;i<9;i++){
					if(cursor.getString(cursor.getColumnIndex("platform")).equals(context.getResources().getString(PLATFORM_NAMES[i]))){
						icon=PLATFORM_ICONS[i];						
					}
				}
				map.put("item_icon", icon);
				map.put("item_name", cursor.getString(cursor.getColumnIndex("platform"))+"-"+cursor.getString(cursor.getColumnIndex("type")));
				map.put("item_money", cursor.getFloat(cursor.getColumnIndex("money")));
				if (cursor.getFloat(cursor.getColumnIndex("earningMin"))==0.0) {
					map.put("item_profit", (cursor.getFloat(cursor.getColumnIndex("earningMax"))*100)+"%");					
				}else{
					map.put("item_profit", (cursor.getFloat(cursor.getColumnIndex("earningMin"))*100)+"%~"+(cursor.getFloat(cursor.getColumnIndex("earningMax"))*100)+"%");
				}
				//dataList.add(map);
				if(dataList.size()==0){
					dataList.add(map);
				}else{
					boolean added=false;
					for(int i=0;i<dataList.size()&&!added;i++){
						switch (type) {
						case 0:{
							if(des){
								//�Ӵ�С					
								if(parseDay((String) map.get("time"))>(parseDay((String) (dataList.get(i)).get("time")))){
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
							}else{
								//��С����
								if(parseDay((String) map.get("time"))<(parseDay((String) (dataList.get(i)).get("time")))){
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
							break;							
						}
						case 1:{							
							if(des){
								//�Ӵ�С					
								if(((Float) map.get("item_money"))>((Float) (dataList.get(i)).get("item_money"))){
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
							}else{
								//��С����
								if(((Float) map.get("item_money"))<((Float) (dataList.get(i)).get("item_money"))){
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
							break;							
						}
						case 2:{
							String now = (String) map.get("item_profit");
							String[] nowArray=now.split("~");
							Float nowRate = Float.parseFloat(nowArray[nowArray.length-1].split("%")[0]);
							String compare = (String) (dataList.get(i)).get("item_profit");
							String[] compareArray=compare.split("~");
							Float compareRate = Float.parseFloat(compareArray[compareArray.length-1].split("%")[0]);
							if(des){
								//�Ӵ�С								
								if(nowRate>compareRate){
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
							}else{
								//��С����
								if(nowRate<compareRate){
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
							break;							
						}
						default:
							break;
						}											
					}
					if (!added) {
						dataList.add(map);						
					}
				}				
			}
			cursor.close();
		}
		db.close();
		return dataList;		
	}
}
