package com.crowley.p2pnote.functions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.crowley.p2pnote.db.DBOpenHelper;
import com.crowley.p2pnote.db.RecordModel;
import com.crowley.p2pnote.db.RestModel;

public class Index {
	//剩余几天判定为即将到期
	private int LEFT_DAY=100;
	
	private Calendar cal = Calendar.getInstance();
	private int days=Common.parseDay(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH));
	private Context nowContext;
	private Platform platform;
	
	public Index(Context context) {
		// TODO Auto-generated constructor stub
		nowContext=context;
		platform=new Platform(context);
	}
	
	/**
	 * @return 今天的年月日 字符串
	 */
	public String getTime(){
		String monthString;
		String dayString;
		String weekString;
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH)+1;
		int day=cal.get(Calendar.DAY_OF_MONTH);
		int weekday=cal.get(Calendar.DAY_OF_WEEK);
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
		switch (weekday) {
		case Calendar.MONDAY:{
			weekString="星期一";
			break;
		}
		case Calendar.TUESDAY:{
			weekString="星期二";
			break;
		}
		case Calendar.WEDNESDAY:{
			weekString="星期三";
			break;
		}
		case Calendar.THURSDAY:{
			weekString="星期四";
			break;
		}
		case Calendar.FRIDAY:{
			weekString="星期五";
			break;
		}
		case Calendar.SATURDAY:{
			weekString="星期六";
			break;
		}
		case Calendar.SUNDAY:{
			weekString="星期天";
			break;
		}
		default:
			weekString="星期天";
			break;
		}
		return year+monthString+dayString+" "+weekString;
	}
	
	/**
	 * 
	 * @return 当日收益均值
	 */
	public String getBaseInfo01Number01(){
		float amount=0.0f;
		DBOpenHelper helper = new DBOpenHelper(nowContext, "record.db");
		SQLiteDatabase db = helper.getWritableDatabase();
		
		Cursor tempCursor=db.rawQuery("select * from record WHERE state=0 AND isDeleted=0 AND userName='"+Common.updateLogin(nowContext)+"'", null);
		if(tempCursor.getCount()!=0){
			while(tempCursor.moveToNext()){
				RecordModel tempRecordModel=new RecordModel(tempCursor);
				if(Common.parseDay(tempRecordModel.getTimeEnd())<days){
					continue;
				}else{
					if (tempRecordModel.getEarningMin()==0) {
						amount+=tempRecordModel.getMoney()*tempRecordModel.getEarningMax()/365;						
					}else{
						amount+=tempRecordModel.getMoney()*(tempRecordModel.getEarningMax()+tempRecordModel.getEarningMin())/2/365;
					}
				}
			}
			tempCursor.close();
			db.close();
			helper.close();
			return Float.valueOf(Common.dealFloat(amount)).toString();
		}else{
			tempCursor.close();
			db.close();
			helper.close();
			return Float.valueOf(Common.dealFloat(0.0f)).toString();
		}
	}
	
	/**
	 * 
	 * @return 当日收益浮动
	 */
	public String getBaseInfo01Number02(){
		float high=0.0f;
		float amount=0.0f;
		DBOpenHelper helper = new DBOpenHelper(nowContext, "record.db");
		SQLiteDatabase db = helper.getWritableDatabase();
		
		Cursor tempCursor=db.rawQuery("select * from record WHERE state=0 AND isDeleted=0 AND userName='"+Common.updateLogin(nowContext)+"'", null);
		if(tempCursor.getCount()!=0){
			while(tempCursor.moveToNext()){
				RecordModel tempRecordModel=new RecordModel(tempCursor);
				if(Common.parseDay(tempRecordModel.getTimeEnd())<days){
					continue;
				}else{
					if (tempRecordModel.getEarningMin()==0) {							
						amount+=tempRecordModel.getMoney()*tempRecordModel.getEarningMax()/365;						
					}else{
						amount+=tempRecordModel.getMoney()*(tempRecordModel.getEarningMax()+tempRecordModel.getEarningMin())/2/365;
					}
					high+=tempRecordModel.getMoney()*tempRecordModel.getEarningMax()/365;
				}
			}
			tempCursor.close();
			db.close();
			helper.close();
			return Float.valueOf(Common.dealFloat(high-amount)).toString();
		}else{
			tempCursor.close();
			db.close();
			helper.close();
			return Float.valueOf(Common.dealFloat(0.0f)).toString();
		}
	}
	
	/**
	 * 
	 * @return 均年化收益均值
	 */
	public String getBaseInfo02Number01(){
		float total=0.0f;
		float amount=0.0f;
		DBOpenHelper helper = new DBOpenHelper(nowContext, "record.db");
		SQLiteDatabase db = helper.getWritableDatabase();
		
		Cursor tempCursor=db.rawQuery("select * from record WHERE state=0 AND isDeleted=0 AND userName='"+Common.updateLogin(nowContext)+"'", null);
		if(tempCursor.getCount()!=0){
			while(tempCursor.moveToNext()){
				RecordModel tempRecordModel=new RecordModel(tempCursor);
				if(Common.parseDay(tempRecordModel.getTimeEnd())<days){
					continue;
				}else{
					if (tempRecordModel.getEarningMin()==0) {
						total+=tempRecordModel.getMoney()*tempRecordModel.getEarningMax();
						amount+=tempRecordModel.getMoney();					
					}else{
						total+=tempRecordModel.getMoney()*(tempRecordModel.getEarningMax()+tempRecordModel.getEarningMin())/2;
						amount+=tempRecordModel.getMoney();
					}
				}
			}
			if(amount==0){
				tempCursor.close();
				db.close();
				helper.close();
				return Float.valueOf(Common.dealFloat(0.0f)).toString();
			}else{
				tempCursor.close();
				db.close();
				helper.close();
				return Float.valueOf(Common.dealFloat(total/amount*100)).toString();
			}
			
		}else{
			tempCursor.close();
			db.close();
			helper.close();
			return Float.valueOf(Common.dealFloat(0.0f)).toString();
		}
	}
	
	/**
	 * 
	 * @return 均年化收益浮动
	 */
	public String getBaseInfo02Number02(){
		float total=0.0f;
		float total2=0.0f;
		float amount=0.0f;
		DBOpenHelper helper = new DBOpenHelper(nowContext, "record.db");
		SQLiteDatabase db = helper.getWritableDatabase();
		
		Cursor tempCursor=db.rawQuery("select * from record WHERE state=0 AND isDeleted=0 AND userName='"+Common.updateLogin(nowContext)+"'", null);
		if(tempCursor.getCount()!=0){
			while(tempCursor.moveToNext()){
				RecordModel tempRecordModel=new RecordModel(tempCursor);
				if(Common.parseDay(tempRecordModel.getTimeEnd())<days){
					continue;
				}else{
					if (tempRecordModel.getEarningMin()==0) {						
						total+=tempRecordModel.getMoney()*tempRecordModel.getEarningMax();
						amount+=tempRecordModel.getMoney();					
					}else{
						total+=tempRecordModel.getMoney()*(tempRecordModel.getEarningMax()+tempRecordModel.getEarningMin())/2;
						amount+=tempRecordModel.getMoney();
					}
					total2+=tempRecordModel.getMoney()*tempRecordModel.getEarningMax();
				}
			}
			if(amount==0){
				tempCursor.close();
				db.close();
				helper.close();
				return Float.valueOf(Common.dealFloat(0.0f)).toString()+" %";
			}else{
				tempCursor.close();
				db.close();
				helper.close();
				return Float.valueOf(Common.dealFloat(total2/amount*100-total/amount*100)).toString()+" %";
			}			
		}else{
			tempCursor.close();
			db.close();
			helper.close();
			return Float.valueOf(Common.dealFloat(0.0f)).toString()+" %";
		}
	}
	
	/**
	 * 
	 * @return 在投总额
	 */
	public String getBaseInfo03(){
		float amount=0.0f;
		DBOpenHelper helper = new DBOpenHelper(nowContext, "record.db");
		SQLiteDatabase db = helper.getWritableDatabase();
		
		Cursor tempCursor=db.rawQuery("select * from record WHERE state=0 AND isDeleted=0 AND userName='"+Common.updateLogin(nowContext)+"'", null);
		if(tempCursor.getCount()!=0){
			while(tempCursor.moveToNext()){
				RecordModel tempRecordModel=new RecordModel(tempCursor);
				amount+=tempRecordModel.getMoney();	
			}
			tempCursor.close();
			db.close();
			helper.close();
			return Float.valueOf(Common.dealFloat(amount)).toString();
		}else{
			tempCursor.close();
			db.close();
			helper.close();
			return Float.valueOf(0.0f).toString();
		}	
	}
	
	/**
	 * 
	 * @return 累计收益
	 */
	public String getBaseInfo04(){
		float amount=0.0f;
		DBOpenHelper helper = new DBOpenHelper(nowContext, "record.db");
		SQLiteDatabase db = helper.getWritableDatabase();
		
		Cursor tempCursor=db.rawQuery("select * from rest WHERE type=3 AND userName='"+Common.updateLogin(nowContext)+"'", null);
		if(tempCursor.getCount()!=0){
			while(tempCursor.moveToNext()){
				RestModel tempRecordModel=new RestModel(tempCursor);
				amount+=tempRecordModel.getMoney();	
			}
			tempCursor.close();
			db.close();
			helper.close();
			return Float.valueOf(Common.dealFloat(amount)).toString();
		}else{
			tempCursor.close();
			db.close();
			helper.close();
			return Float.valueOf(0.0f).toString();
		}	
	}
	
	/**
	 * 返回已经到期和即将到期的数量
	 * @param context
	 * @param type 为0表示已经到期，1表示即将到期
	 * @return
	 */
	public int indexCount(int type){		
		int count=0;	
		
		DBOpenHelper helper=new DBOpenHelper(nowContext, "record.db");
		Cursor allRecords = helper.returALLRecords(helper.getWritableDatabase());
		
		if(allRecords.getCount()!=0){
			while (allRecords.moveToNext()) {
				RecordModel record=new RecordModel(allRecords);
				//如果记录已经被删除 跳出本次循环
				if(record.getIsDeleted()==1||!record.getUserName().equals(Common.updateLogin(nowContext))||record.getState()==1||(record.getEarningMin()==0&&record.getEarningMax()==0)){
					continue;
				}
				switch (type) {
				case 0:{
					if(Common.parseDay(record.getTimeEnd())<=days){
						count++;						
					}
					break;
				}
				case 1:{
					int daysLeft=Common.parseDay(record.getTimeEnd())-days;
					if(daysLeft<LEFT_DAY&&daysLeft>0){
						count++;						
					}
					break;
				}
				default:
					break;
				}
				
			}
		}
		allRecords.close();
		helper.close();
		return count;		
	}
	
	/**
	 * 返回已经到期和即将到期的list
	 * @param type 为0表示已经到期，1表示即将到期
	 * @return
	 */
	public List<Map<String, Object>> indexList(int type){
		List<Map<String, Object>> dataList=new ArrayList<Map<String,Object>>();
		
		DBOpenHelper helper=new DBOpenHelper(nowContext, "record.db");
		Cursor allRecords = helper.returALLRecords(helper.getWritableDatabase());
		
		if(allRecords.getCount()!=0){
			while (allRecords.moveToNext()) {
				Map<String, Object> map=new HashMap<String, Object>();
				RecordModel record=new RecordModel(allRecords);
				//如果记录已经被删除 不属于该用户 已经处理过 跳出本次循环
				if(record.getIsDeleted()==1||!record.getUserName().equals(Common.updateLogin(nowContext))||record.getState()==1||(record.getEarningMin()==0&&record.getEarningMax()==0)){
					continue;
				}
				boolean judge=false;
				switch (type) {
					case 0:{
						if(Common.parseDay(record.getTimeEnd())<=days){
							judge=true;	
						}
						break;
					}
					case 1:{
						int daysLeft=Common.parseDay(record.getTimeEnd())-days;
						if(daysLeft<LEFT_DAY&&daysLeft>0){
							judge=true;							
						}
						break;
					}
					default:
						break;
				}
				if (judge) {
					map.put("item_id", record.getID());
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
						icon=DBOpenHelper.PLATFORM_ICONS[DBOpenHelper.PLATFORM_ICONS.length-1];
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
					dataList.add(map);
				}				
			}
		}
		
		allRecords.close();
		helper.close();
		return dataList;		
	}
	
	/**
	 * 到期处理
	 * 
	 * */
	public void dealRecord(String idString,Float earning,Float get_out){
		Long tsLong = System.currentTimeMillis();
		String ts1 = tsLong.toString();		
		String ts2 = Long.valueOf(tsLong+1).toString();		
		DBOpenHelper helper = new DBOpenHelper(nowContext, "record.db");
		SQLiteDatabase db = helper.getWritableDatabase();		
		Cursor tempCursor=db.rawQuery("select * from record WHERE _id = "+idString, null);
		tempCursor.moveToFirst();
		RecordModel tempRecordModel=new RecordModel(tempCursor);
		String platformString=tempRecordModel.getPlatform();
		db.execSQL("UPDATE record SET userName='"+Common.updateLogin(nowContext)+"', state = 1 WHERE _id = "+idString);
		db.execSQL("insert into rest(platform,name,type,money,timeStamp,userName,createTime) values('"+platformString+"','"+platformString+"-"+tempRecordModel.getType()+"',5,"+tempRecordModel.getMoney()+",'"+tempRecordModel.getTimeStamp()+"','"+Common.updateLogin(nowContext)+"','"+ts1+"')");
		db.execSQL("insert into rest(platform,name,type,money,timeStamp,userName,createTime) values('"+platformString+"','"+platformString+"-"+tempRecordModel.getType()+"',3,"+earning+",'"+tempRecordModel.getTimeStamp()+"','"+Common.updateLogin(nowContext)+"','"+ts2+"')");
	}
	
	/**
	 * 返回利息
	 * 
	 * */
	public String getEarning(String idString) throws ParseException{
		DBOpenHelper helper = new DBOpenHelper(nowContext, "record.db");
		SQLiteDatabase db = helper.getWritableDatabase();
		
		Cursor tempCursor=db.rawQuery("select * from record WHERE _id = "+idString, null);
		tempCursor.moveToFirst();
		RecordModel tempRecordModel=new RecordModel(tempCursor);
		switch (tempRecordModel.getMethod()) {
		//到期还本息
		case 0:{
			int days=Common.parseDay(tempRecordModel.getTimeEnd())-Common.parseDay(tempRecordModel.getTimeBegin());
			if(tempRecordModel.getEarningMin()==0.0){
				tempCursor.close();
				db.close();
				helper.close();
				//return Float.valueOf(Common.dealFloat(tempRecordModel.getMoney()*tempRecordModel.getEarningMax()*days/365)).toString();			
				//总回款
				return Float.valueOf(tempRecordModel.getMoney()+Common.dealFloat(tempRecordModel.getMoney()*tempRecordModel.getEarningMax()*days/365)).toString();
			}else{
				tempCursor.close();
				db.close();
				helper.close();
				//return Common.dealFloat(tempRecordModel.getMoney()*tempRecordModel.getEarningMin()*days/365)+"~"+Common.dealFloat(tempRecordModel.getMoney()*tempRecordModel.getEarningMax()*days/365);
				return Common.dealFloat(tempRecordModel.getMoney()+tempRecordModel.getMoney()*tempRecordModel.getEarningMin()*days/365)+"~"+Common.dealFloat(tempRecordModel.getMoney()+tempRecordModel.getMoney()*tempRecordModel.getEarningMax()*days/365);
			}
		}
		case 1:{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c1.setTime(sdf.parse(tempRecordModel.getTimeBegin()));
			c2.setTime(sdf.parse(tempRecordModel.getTimeEnd()));
			int months = (c2.get(Calendar.YEAR)*12+c2.get(Calendar.MONTH)) - (c1.get(Calendar.YEAR)*12+c1.get(Calendar.MONTH));
			months = (months==0) ? 1 : Math.abs(months);
			if(tempRecordModel.getEarningMin()==0.0){
				Float rate = tempRecordModel.getEarningMax() / 12;	
				double payMonth = (tempRecordModel.getMoney() * rate * Math.pow((1 + rate), months) / (Math.pow((1 + rate), months) - 1));
				double earning = payMonth*months-tempRecordModel.getMoney();
				tempCursor.close();
				db.close();
				helper.close();
				//return Double.valueOf(Common.dealFloat(earning)).toString();
				return Double.valueOf(Common.dealFloat(tempRecordModel.getMoney()+earning)).toString();
			}else{
				Float rate1 = tempRecordModel.getEarningMin() / 12;				
				double earning1 = (tempRecordModel.getMoney() * months * Math.pow((1 + rate1), months) / (Math.pow((1 + rate1), months) - 1))*months-tempRecordModel.getMoney();
				Float rate2 = tempRecordModel.getEarningMax() / 12;				
				double earning2 = (tempRecordModel.getMoney() * months * Math.pow((1 + rate2), months) / (Math.pow((1 + rate2), months) - 1))*months-tempRecordModel.getMoney();
				tempCursor.close();
				db.close();
				helper.close();
				//return Double.valueOf(Common.dealFloat(earning1)).toString()+"~"+Double.valueOf(Common.dealFloat(earning2)).toString();
				return Double.valueOf(Common.dealFloat(tempRecordModel.getMoney()+earning1)).toString()+"~"+Double.valueOf(Common.dealFloat(tempRecordModel.getMoney()+earning2)).toString();
			}
		}
		case 2:{
			int days=Common.parseDay(tempRecordModel.getTimeEnd())-Common.parseDay(tempRecordModel.getTimeBegin());
			if(tempRecordModel.getEarningMin()==0.0){
				tempCursor.close();
				db.close();
				helper.close();
				//return Float.valueOf(Common.dealFloat(tempRecordModel.getMoney()*tempRecordModel.getEarningMax()*days/365)).toString();
				return Float.valueOf(Common.dealFloat(tempRecordModel.getMoney()+tempRecordModel.getMoney()*tempRecordModel.getEarningMax()*days/365)).toString();
			}else{
				tempCursor.close();
				db.close();
				helper.close();
				//return Common.dealFloat(tempRecordModel.getMoney()*tempRecordModel.getEarningMin()*days/365)+"~"+Common.dealFloat(tempRecordModel.getMoney()*tempRecordModel.getEarningMax()*days/365);
				return Common.dealFloat(tempRecordModel.getMoney()+tempRecordModel.getMoney()*tempRecordModel.getEarningMin()*days/365)+"~"+Common.dealFloat(tempRecordModel.getMoney()+tempRecordModel.getMoney()*tempRecordModel.getEarningMax()*days/365);
			}			
		}
		default:
			return "123";
		}		
	}

}
