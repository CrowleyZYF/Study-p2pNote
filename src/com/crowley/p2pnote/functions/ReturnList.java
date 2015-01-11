package com.crowley.p2pnote.functions;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.spec.IvParameterSpec;

import android.R.integer;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.crowley.p2pnote.R;
import com.crowley.p2pnote.db.DBOpenHelper;
import com.crowley.p2pnote.db.RecordModel;
import com.github.mikephil.charting.data.Entry;

public class ReturnList {
	
	private Context context;
	
	private Calendar cal;
	private int year;
	private int month;
	private int[] months={0,31,28,31,30,31,30,31,31,30,31,30,31};
	private int day;
	private int days;
	
	private DBOpenHelper helper;
	private SQLiteDatabase db;
	private Cursor allRecords;
	
	private String loginString;
	
	public ReturnList(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		
		this.helper = new DBOpenHelper(context, "record.db");
		this.db = this.helper.getWritableDatabase();
		this.allRecords = this.helper.returALLRecords(this.db);
		
		this.cal=Calendar.getInstance();
		this.year = this.cal.get(Calendar.YEAR);
		this.month = this.cal.get(Calendar.MONTH)+1;
		this.day = this.cal.get(Calendar.DAY_OF_MONTH);
		this.days=this.year*365+this.month*this.months[this.month]+this.day;
		
		SharedPreferences preferences=context.getSharedPreferences("user", android.content.Context.MODE_PRIVATE);
		boolean isLogined = preferences.getBoolean("isLogined", false);
		if(isLogined){
			loginString=preferences.getString("account", "出错啦");
		}else{
			loginString="not_login";
		}
	}
	
	public int daysNumber(){
		return this.days;
	}
	
	public void logInfo(){
		if(allRecords.moveToFirst()){
			while (allRecords.moveToNext()) {
				Log.i("m_info","id:"+allRecords.getInt(allRecords.getColumnIndex("_id")));
				Log.i("m_info","platform:"+allRecords.getString(allRecords.getColumnIndex("platform")));
				Log.i("m_info","type:"+allRecords.getString(allRecords.getColumnIndex("type")));
				Log.i("m_info","money:"+allRecords.getFloat(allRecords.getColumnIndex("money")));
				Log.i("m_info","earningMin:"+allRecords.getFloat(allRecords.getColumnIndex("earningMin")));
				Log.i("m_info","earningMax:"+allRecords.getFloat(allRecords.getColumnIndex("earningMax")));
				Log.i("m_info","method:"+allRecords.getInt(allRecords.getColumnIndex("method")));
				Log.i("m_info","timeBegin:"+allRecords.getString(allRecords.getColumnIndex("timeBegin")));
				Log.i("m_info","timeEnd:"+allRecords.getString(allRecords.getColumnIndex("timeEnd")));		
				Log.i("m_info","timeStamp:"+allRecords.getString(allRecords.getColumnIndex("timeStamp")));
				Log.i("m_info","state:"+allRecords.getInt(allRecords.getColumnIndex("state")));
				Log.i("m_info","isDeleted:"+allRecords.getInt(allRecords.getColumnIndex("isDeleted")));
				Log.i("m_info","userName:"+allRecords.getString(allRecords.getColumnIndex("userName")));
				Log.i("m_info","restBegin:"+allRecords.getFloat(allRecords.getColumnIndex("restBegin")));
				Log.i("m_info","restEnd:"+allRecords.getFloat(allRecords.getColumnIndex("restEnd")));		
				Log.i("m_info","timeStampEnd:"+allRecords.getString(allRecords.getColumnIndex("timeStampEnd")));
				Log.i("m_info","-----------------");				
			}
		}		
	}
	
	public String getTime(){
		String monthString;
		String dayString;
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
		return year+monthString+dayString;
	}
	
	public boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);

		return m.matches();
	}
	
	public String getBaseInfo01Number01(){
		return "20.95";
	}
	
	public String getBaseInfo01Number02(){
		return "1";
	}
	
	public String getBaseInfo02Number01(){
		return "8.5";
	}
	
	public String getBaseInfo02Number02(){
		return "1.2 %";
	}
	
	public String getBaseInfo03(){
		return "135600.58";
	}

	public int parseDay(String date){
		String[] time=date.split("-");
		int year=Integer.parseInt(time[0]);
		int month=Integer.parseInt(time[1]);
		int day=Integer.parseInt(time[2]);	
		return year*365+month*this.months[month]+day;		
	}
	
	public void deleteItem(String id){
		this.db.execSQL("UPDATE record SET isDeleted = 1 WHERE _id = "+id);
	}
	
	public RecordModel getRecordModel(String id){
		Cursor tempCursor=this.db.rawQuery("select * from record WHERE _id = "+id, null);
		tempCursor.moveToFirst();
		return new RecordModel(tempCursor);
	}
	
	public Float dealFloat(Float f){
		int scale = 2;//设置位数  
		int roundingMode = 4;//表示四舍五入，可以选择其他舍值方式，例如去尾，等等.  
		BigDecimal bd = new BigDecimal((double)f);  
		bd = bd.setScale(scale,roundingMode);  
		return bd.floatValue();
	}
	
	public Double dealFloat(Double d){
		int scale = 2;//设置位数  
		int roundingMode = 4;//表示四舍五入，可以选择其他舍值方式，例如去尾，等等.  
		BigDecimal bd = new BigDecimal(d);  
		bd = bd.setScale(scale,roundingMode);  
		return bd.doubleValue();
	}
	
	public float getRest(String platform){
		return 0.0f;
		
	}
	
	/**
	 * 到期处理
	 * 
	 * */
	public void dealRecord(String idString,Float earning,Float get_out){
		Long tsLong = System.currentTimeMillis();
		String ts = tsLong.toString();
		Cursor tempCursor=this.db.rawQuery("select * from record WHERE _id = "+idString, null);
		tempCursor.moveToFirst();
		RecordModel tempRecordModel=new RecordModel(tempCursor);
		float rest = getRest(tempRecordModel.getPlatform());
		rest=rest+earning-get_out;
		this.db.execSQL("UPDATE record SET restBegin = "+earning+", state = 1, rest = "+rest+", timeStampEnd = '"+ts+"', restEnd = "+get_out+" WHERE _id = "+idString);
	}
	
	/**
	 * 返回利息
	 * 
	 * */
	public String getEarning(String idString) throws ParseException{
		Cursor tempCursor=this.db.rawQuery("select * from record WHERE _id = "+idString, null);
		tempCursor.moveToFirst();
		RecordModel tempRecordModel=new RecordModel(tempCursor);
		switch (tempRecordModel.getMethod()) {
		//到期还本息
		case 0:{
			int days=parseDay(tempRecordModel.getTimeEnd())-parseDay(tempRecordModel.getTimeBegin());
			if(tempRecordModel.getEarningMin()==0.0){
				return Float.valueOf(dealFloat(tempRecordModel.getMoney()*tempRecordModel.getEarningMax()*days/365)).toString();				
			}else{
				return dealFloat(tempRecordModel.getMoney()*tempRecordModel.getEarningMin()*days/365)+"~"+dealFloat(tempRecordModel.getMoney()*tempRecordModel.getEarningMax()*days/365);
			}
		}
		case 1:{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c1.setTime(sdf.parse(tempRecordModel.getTimeBegin()));
			c2.setTime(sdf.parse(tempRecordModel.getTimeEnd()));
			int months = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
			months = (months==0) ? 1 : Math.abs(months);
			if(tempRecordModel.getEarningMin()==0.0){
				Float rate = tempRecordModel.getEarningMax() / 12;				
				double earning = (tempRecordModel.getMoney() * months * Math.pow((1 + rate), months) / (Math.pow((1 + rate), months) - 1))*months-tempRecordModel.getMoney();
				return Double.valueOf(dealFloat(earning)).toString();				
			}else{
				Float rate1 = tempRecordModel.getEarningMin() / 12;				
				double earning1 = (tempRecordModel.getMoney() * months * Math.pow((1 + rate1), months) / (Math.pow((1 + rate1), months) - 1))*months-tempRecordModel.getMoney();
				Float rate2 = tempRecordModel.getEarningMax() / 12;				
				double earning2 = (tempRecordModel.getMoney() * months * Math.pow((1 + rate2), months) / (Math.pow((1 + rate2), months) - 1))*months-tempRecordModel.getMoney();
				return Double.valueOf(dealFloat(earning1)).toString()+"~"+Double.valueOf(dealFloat(earning2)).toString();
			}
		}
		case 2:{
			int days=parseDay(tempRecordModel.getTimeEnd())-parseDay(tempRecordModel.getTimeBegin());
			if(tempRecordModel.getEarningMin()==0.0){
				return Float.valueOf(dealFloat(tempRecordModel.getMoney()*tempRecordModel.getEarningMax()*days/365)).toString();				
			}else{
				return dealFloat(tempRecordModel.getMoney()*tempRecordModel.getEarningMin()*days/365)+"~"+dealFloat(tempRecordModel.getMoney()*tempRecordModel.getEarningMax()*days/365);
			}			
		}
		default:
			return "123";
		}		
	}
	
	//type为0表示到期时间，1表示金额，2表示收益率
	public List<Map<String, Object>> waterSort(int type,boolean des){
		List<Map<String, Object>> dataList=new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> temp=new ArrayList<Map<String,Object>>();
		this.allRecords = this.helper.returALLRecords(this.db);
		if(allRecords.moveToFirst()){
			while (allRecords.moveToNext()) {
				temp.clear();
				Map<String, Object> map=new HashMap<String, Object>();
				RecordModel record=new RecordModel(allRecords);
				//如果记录已经被删除  或者不属于该账户 跳出本次循环
				if(record.getIsDeleted()==1||record.getUserName().equals(loginString)){
					continue;
				}
				map.put("item_id", record.getID());
				map.put("timeBegin", record.getTimeBegin());
				map.put("timeEnd", "至 "+record.getTimeEnd());
				int icon = DBOpenHelper.PLATFORM_ICONS[0];
				boolean findIcon=false;
				for(int i=0;i<DBOpenHelper.PLATFORM_NAMES.length-1&&(findIcon==false);i++){
					if(record.getPlatform().equals(context.getResources().getString(DBOpenHelper.PLATFORM_NAMES[i]))){
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
					map.put("item_profit", (record.getEarningMax()*100)+"%");					
				}else{
					map.put("item_profit", (record.getEarningMin()*100)+"%~"+(record.getEarningMax()*100)+"%");
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
								if(parseDay(mapString)>(parseDay(compareString))){
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
								if(parseDay(mapString)>(parseDay(compareString))){
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
		return dataList;		
	}
	
	//type为0表示已经到期，1表示即将到期
	public List<Map<String, Object>> indexList(int type){
		List<Map<String, Object>> dataList=new ArrayList<Map<String,Object>>();
		this.allRecords = this.helper.returALLRecords(this.db);
		if(allRecords.moveToFirst()){
			while (allRecords.moveToNext()) {
				Map<String, Object> map=new HashMap<String, Object>();
				RecordModel record=new RecordModel(allRecords);
				//如果记录已经被删除 不属于该用户 已经处理过 跳出本次循环
				if(record.getIsDeleted()==1||record.getUserName().equals(loginString)||record.getState()==1){
					continue;
				}
				boolean judge=false;
				switch (type) {
					case 0:{
						int nowDays=year*365+month*30+day;
						if(parseDay(record.getTimeEnd())<=nowDays){
							judge=true;												
						}
						break;
					}
					case 1:{
						int daysLeft=parseDay(record.getTimeEnd())-this.days;
						if(daysLeft<100&&daysLeft>0){
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
						if(record.getPlatform().equals(context.getResources().getString(DBOpenHelper.PLATFORM_NAMES[i]))){
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
						map.put("item_profit", (record.getEarningMax()*100)+"%");					
					}else{
						map.put("item_profit", (record.getEarningMin()*100)+"%~"+(record.getEarningMax()*100)+"%");
					}
					dataList.add(map);
				}				
			}
		}
		return dataList;		
	}
	
	//type为0表示已经到期，1表示即将到期
	public int indexCount(int type){
		int count=0;
		this.allRecords = this.helper.returALLRecords(this.db);
		if(allRecords.moveToFirst()){
			while (allRecords.moveToNext()) {
				RecordModel record=new RecordModel(allRecords);
				//如果记录已经被删除 跳出本次循环
				if(record.getIsDeleted()==1||record.getUserName().equals(loginString)||record.getState()==1){
					continue;
				}
				switch (type) {
				case 0:{
					if(parseDay(record.getTimeEnd())<=this.days){
						count++;						
					}
					break;
				}
				case 1:{
					int daysLeft=parseDay(record.getTimeEnd())-this.days;
					if(daysLeft<100&&daysLeft>0){
						count++;						
					}
					break;
				}
				default:
					break;
				}
				
			}
		}
		return count;		
	}

	//type为0表示平台在投金额分析，4为平台余额分析
	public ArrayList<String> analyzexVals(int type){
        ArrayList<String> xVals = new ArrayList<String>();
		this.allRecords = this.helper.returALLRecords(this.db);
		if(allRecords.moveToFirst()){
			while (allRecords.moveToNext()) {
				RecordModel record=new RecordModel(allRecords);
				//如果记录已经被删除 跳出本次循环
				if(record.getIsDeleted()==1||record.getUserName().equals(loginString)||record.getState()==1){
					continue;
				}
				switch (type) {
					case 0:{
						int time=parseDay(record.getTimeEnd());
						if(time>this.days){
							if(xVals.size()==0){
								xVals.add(record.getPlatform());
							}else{
								int count=0;
								for(int i=0;i<xVals.size();i++){
									if(!(record.getPlatform()).equals(xVals.get(i))){
										count++;																		
									}
								}
								if(count==xVals.size()){
									xVals.add(record.getPlatform());
								}
							}
						}
						break;
					}
					case 4:{
						break;
					}
					default:
						break;
				}
				
			}
		}
		return xVals;
	}
		
	//type为0表示平台在投金额分析，1为收益率，2为期限结构，3为回款时间，4为平台余额分析
	public ArrayList<Entry> analyzeEntries(int type,ArrayList<String> xVals){
		ArrayList<Entry> entries1 = new ArrayList<Entry>();
		ArrayList<Float> counts = new ArrayList<Float>();
		Float[] analyze01={0.06f,0.08f,0.1f,0.12f,0.15f,0.2f,0.25f};
		Integer[] analyze02={30,90,182,272,365,547,730};
		Integer[] analyze03={7,30,90,182,272,365};
		float amount=0.0f;
		for(int i=0;i<xVals.size();i++){
			counts.add(0.0f);
		}
		this.allRecords = this.helper.returALLRecords(this.db);
		if(allRecords.moveToFirst()){
			while (allRecords.moveToNext()) {
				RecordModel record=new RecordModel(allRecords);
				//如果记录已经被删除 跳出本次循环
				if(record.getIsDeleted()==1||record.getUserName().equals(loginString)||record.getState()==1){
					continue;
				}
				int time=parseDay(record.getTimeEnd());
				//如果没有到期
				if(time>this.days){
					switch (type) {
					case 0:{
						for(int i=0;i<xVals.size();i++){
							String platformString=record.getPlatform();
							if(platformString.equals(xVals.get(i))){
								counts.set(i, counts.get(i)+record.getMoney());
								amount+=record.getMoney();
							}
						}
						break;
					}
					case 1:{
						Float profit=record.getEarningMax();
						boolean added=false;
						for(int i=0;i<analyze01.length&&added==false;i++){
							if(profit<analyze01[i]){
								added=true;
								counts.set(i, counts.get(i)+1.0f);
								amount+=1.0f;
							}
						}
						if (added==false) {
							counts.set(xVals.size()-1, counts.get(xVals.size()-1)+1.0f);
							amount+=1.0f;
						}						
					}
					case 2:{
						int duration=parseDay(record.getTimeEnd())-parseDay(record.getTimeBegin());
						boolean added=false;
						for(int i=0;i<analyze02.length&&added==false;i++){
							if(duration<analyze02[i]){
								added=true;
								counts.set(i, counts.get(i)+1.0f);
								amount+=1.0f;
							}
						}
						if (added==false) {
							counts.set(xVals.size()-1, counts.get(xVals.size()-1)+1.0f);
							amount+=1.0f;
						}						
					}
					case 3:{
						int left=parseDay(record.getTimeEnd())-days;
						boolean added=false;
						for(int i=0;i<analyze03.length&&added==false;i++){
							if(left<analyze03[i]){
								added=true;
								counts.set(i, counts.get(i)+1.0f);
								amount+=1.0f;
							}
						}
						if (added==false) {
							counts.set(xVals.size()-1, counts.get(xVals.size()-1)+1.0f);
							amount+=1.0f;
						}						
					}
					default:
						break;
				}
				}
									
			}
		}
		for(int i = 0; i < xVals.size(); i++) {
			float result=100f*counts.get(i)/amount;
            entries1.add(new Entry(result, i));
        }
		return entries1;
	}
}
