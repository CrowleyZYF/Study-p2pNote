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
import android.R.string;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.crowley.p2pnote.R;
import com.crowley.p2pnote.db.DBOpenHelper;
import com.crowley.p2pnote.db.NewsModel;
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
	private Cursor allNews;
	
	private String loginString;
	
	public ReturnList(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		
		this.helper = new DBOpenHelper(context, "record.db");
		this.db = this.helper.getWritableDatabase();
		this.allRecords = this.helper.returALLRecords(this.db);
		//this.allNews = this.helper.returALLNews(this.db);
		
		this.cal=Calendar.getInstance();
		this.year = this.cal.get(Calendar.YEAR);
		this.month = this.cal.get(Calendar.MONTH)+1;
		this.day = this.cal.get(Calendar.DAY_OF_MONTH);
		this.days=this.year*365+this.month*this.months[this.month]+this.day;
		
		updateLogin();
		logInfo();
	}
	
	public List<Map<String, Object>> getAllNews(){
		//this.allNews = this.helper.returALLNews(this.db);
		List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
		if(allNews.getCount()!=0){
			while (allNews.moveToNext()) {
				NewsModel tempModel=new NewsModel(allNews);
				Map<String, Object> map=new HashMap<String, Object>();  
				map.put("news_pic", R.drawable.logo);  
				map.put("news_title", tempModel.getTitle());  
				map.put("news_intro", tempModel.getContent().substring(0, 30));
				map.put("news_time", tempModel.getAddTime());
				dataList.add(map);				
			}
			return dataList;
		}
		return null;
		
	}
	
	public int daysNumber(){
		return this.days;
	}
	
	public void updateLogin(){
		SharedPreferences preferences=context.getSharedPreferences("user", android.content.Context.MODE_PRIVATE);
		boolean isLogined = preferences.getBoolean("isLogined", false);
		if(isLogined){
			loginString=preferences.getString("account", "出错啦");
		}else{
			loginString="not_login";
		}
	}
	
	public boolean checkNotLogin(){
		Cursor tempCursor=this.db.rawQuery("select * from record WHERE isDeleted=0 AND userName='not_login'", null);
		return !(tempCursor.getCount()==0);
	}
	
	public void setUserName(String userString){
		this.db.execSQL("UPDATE record SET userName = '"+userString+"' WHERE userName = 'not_login'");
	}
	
	public void logInfo(){
		/*this.allRecords = this.helper.returALLRecords(this.db);
		if(allRecords.getCount()!=0){
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
				Log.i("m_info","rest:"+allRecords.getFloat(allRecords.getColumnIndex("rest")));
				Log.i("m_info","-----------------");				
			}
		}		*/
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
		updateLogin();
		float amount=0.0f;
		Cursor tempCursor=this.db.rawQuery("select * from record WHERE state=0 AND isDeleted=0 AND userName='"+loginString+"'", null);
		if(tempCursor.getCount()!=0){
			while(tempCursor.moveToNext()){
				RecordModel tempRecordModel=new RecordModel(tempCursor);
				if(parseDay(tempRecordModel.getTimeEnd())<this.days){
					continue;
				}else{
					if (tempRecordModel.getEarningMin()==0) {
						amount+=tempRecordModel.getMoney()*tempRecordModel.getEarningMax()/365;						
					}else{
						amount+=tempRecordModel.getMoney()*(tempRecordModel.getEarningMax()+tempRecordModel.getEarningMin())/2/365;
					}
				}
			}
			return Float.valueOf(dealFloat(amount)).toString();
		}else{
			return Float.valueOf(dealFloat(0.0f)).toString();
		}
	}
	
	public String getBaseInfo01Number02(){
		updateLogin();
		float high=0.0f;
		float amount=0.0f;
		Cursor tempCursor=this.db.rawQuery("select * from record WHERE state=0 AND isDeleted=0 AND userName='"+loginString+"'", null);
		if(tempCursor.getCount()!=0){
			while(tempCursor.moveToNext()){
				RecordModel tempRecordModel=new RecordModel(tempCursor);
				if(parseDay(tempRecordModel.getTimeEnd())<this.days){
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
			return Float.valueOf(dealFloat(high-amount)).toString();
		}else{
			return Float.valueOf(dealFloat(0.0f)).toString();
		}
	}
	
	public String getBaseInfo02Number01(){
		updateLogin();
		float total=0.0f;
		float amount=0.0f;
		Cursor tempCursor=this.db.rawQuery("select * from record WHERE state=0 AND isDeleted=0 AND userName='"+loginString+"'", null);
		if(tempCursor.getCount()!=0){
			while(tempCursor.moveToNext()){
				RecordModel tempRecordModel=new RecordModel(tempCursor);
				if(parseDay(tempRecordModel.getTimeEnd())<this.days){
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
				return Float.valueOf(dealFloat(0.0f)).toString();
			}else{
				return Float.valueOf(dealFloat(total/amount*100)).toString();
			}
			
		}else{
			return Float.valueOf(dealFloat(0.0f)).toString();
		}
	}
	
	public String getBaseInfo02Number02(){
		updateLogin();
		float total=0.0f;
		float total2=0.0f;
		float amount=0.0f;
		Cursor tempCursor=this.db.rawQuery("select * from record WHERE state=0 AND isDeleted=0 AND userName='"+loginString+"'", null);
		if(tempCursor.getCount()!=0){
			while(tempCursor.moveToNext()){
				RecordModel tempRecordModel=new RecordModel(tempCursor);
				if(parseDay(tempRecordModel.getTimeEnd())<this.days){
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
				return Float.valueOf(dealFloat(0.0f)).toString()+" %";
			}else{
				return Float.valueOf(dealFloat(total2/amount*100-total/amount*100)).toString()+" %";
			}			
		}else{
			return Float.valueOf(dealFloat(0.0f)).toString()+" %";
		}
	}
	
	public String getBaseInfo03(){
		updateLogin();
		float amount=0.0f;
		Cursor tempCursor=this.db.rawQuery("select * from record WHERE state=0 AND isDeleted=0 AND userName='"+loginString+"'", null);
		if(tempCursor.getCount()!=0){
			while(tempCursor.moveToNext()){
				RecordModel tempRecordModel=new RecordModel(tempCursor);
				amount+=tempRecordModel.getMoney();	
			}
			return Float.valueOf(dealFloat(amount)).toString();
		}else{
			return Float.valueOf(0.0f).toString();
		}	
	}
	
	
	
	public String getNewestDate(String platform){
		updateLogin();
		Cursor tempCursor=this.db.rawQuery("select * from record WHERE state=1 AND isDeleted=0 AND userName='"+loginString+"' AND platform='"+platform+"' ORDER BY timeStampEnd DESC", null);
		if(tempCursor.getCount()!=0){
			tempCursor.moveToFirst();
			RecordModel tempRecordModel=new RecordModel(tempCursor);
			return tempRecordModel.getTimeEnd();
		}else{
			return "暂无数据";
		}		
	}
	
	public boolean getNewestBool(String platform){
		updateLogin();
		Cursor tempCursor=this.db.rawQuery("select * from record WHERE state=1 AND isDeleted=0 AND userName='"+loginString+"' AND platform='"+platform+"' ORDER BY timeStampEnd DESC", null);
		if(tempCursor.getCount()!=0){
			tempCursor.moveToFirst();
			RecordModel tempRecordModel=new RecordModel(tempCursor);
			if(tempRecordModel.getEarningMin()==0&&tempRecordModel.getEarningMax()==0){
				return false;
			}else{
				return true;
			}			
		}else{
			return true;
		}
	}
	
	
	
	public String getPlatformString(String id){
		updateLogin();
		Cursor tempCursor=this.db.rawQuery("select * from record WHERE _id="+id, null);
		tempCursor.moveToFirst();
		return (new RecordModel(tempCursor).getPlatform());
	}

	public int parseDay(String date){
		String[] time=date.split("-");
		int year=Integer.parseInt(time[0]);
		int month=Integer.parseInt(time[1]);
		int day=Integer.parseInt(time[2]);	
		return year*365+month*this.months[month]+day;		
	}
	
	public int parseMonth(String date){
		String[] time=date.split("-");
		int year=Integer.parseInt(time[0]);
		int month=Integer.parseInt(time[1]);
		return year*12+month+1;		
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
	
	/**
	 * 获取在投总额
	 * 
	 * */	
	public float getAllAmount(String platform){
		updateLogin();
		float amount=0.0f;
		Cursor tempCursor=this.db.rawQuery("select * from record WHERE state=0 AND isDeleted=0 AND userName='"+loginString+"' AND platform = '"+platform+"'", null);
		if(tempCursor.getCount()!=0){
			while(tempCursor.moveToNext()){
				RecordModel tempRecordModel=new RecordModel(tempCursor);
				amount+=tempRecordModel.getMoney();	
			}
			return amount;
		}else{
			return 0.0f;
		}		
	}
	
	/**
	 * 获取预期年化收益率
	 * 
	 * */	
	public float getEarningRateAll(String platform){
		updateLogin();
		float amount=0.0f;
		float total=0.0f;
		Cursor tempCursor=this.db.rawQuery("select * from record WHERE state=0 AND isDeleted=0 AND userName='"+loginString+"' AND platform = '"+platform+"'", null);
		if(tempCursor.getCount()!=0){
			while(tempCursor.moveToNext()){
				RecordModel tempRecordModel=new RecordModel(tempCursor);
				float test=tempRecordModel.getMoney();
				test=tempRecordModel.getEarningMax();
				amount+=tempRecordModel.getMoney();				
				total+=tempRecordModel.getMoney()*tempRecordModel.getEarningMax();
			}
			return dealFloat(total/amount*100);
		}else{
			return 0.0f;
		}		
	}
	
	
	
	
	
	/**
	 * 获取平台图标
	 * 
	 * */	
	public List<Integer> getPlatformsIcon(){
		updateLogin();
		List<Integer> mDatas=new ArrayList<Integer>();
		logInfo();
		Cursor tempCursor=this.db.rawQuery("select * from record WHERE isDeleted=0 AND userName='"+loginString+"'", null);
		if(tempCursor.getCount()!=0){
			while (tempCursor.moveToNext()){
				RecordModel tempRecordModel=new RecordModel(tempCursor);
				for(int i=0;i<DBOpenHelper.PLATFORM_NAMES.length;i++){
					if(tempRecordModel.getPlatform().equals(context.getResources().getString(DBOpenHelper.PLATFORM_NAMES[i]))){
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
			return mDatas;
		}else{
			mDatas.add(DBOpenHelper.PLATFORM_ICONS_BIG[(DBOpenHelper.PLATFORM_ICONS_BIG.length)-1]);
			return mDatas;
		}		
	}
	
	/**
	 * 获取平台名称
	 * 
	 * */	
	public List<String> getPlatformsName(){
		updateLogin();
		List<String> mDatas=new ArrayList<String>();
		logInfo();
		Cursor tempCursor=this.db.rawQuery("select * from record WHERE isDeleted=0 AND userName='"+loginString+"'", null);
		if(tempCursor.getCount()!=0){
			while (tempCursor.moveToNext()){
				RecordModel tempRecordModel=new RecordModel(tempCursor);
				for(int i=0;i<DBOpenHelper.PLATFORM_NAMES.length;i++){
					if(tempRecordModel.getPlatform().equals(context.getResources().getString(DBOpenHelper.PLATFORM_NAMES[i]))){
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
			return mDatas;
		}else{
			mDatas.add("平台");
			return mDatas;
		}		
	}
	
	/**
	 * 到期处理
	 * 
	 * */
	
	
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
		updateLogin();
		List<Map<String, Object>> dataList=new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> temp=new ArrayList<Map<String,Object>>();
		this.allRecords = this.helper.returALLRecords(this.db);
		if(allRecords.getCount()!=0){
			while (allRecords.moveToNext()) {
				temp.clear();
				Map<String, Object> map=new HashMap<String, Object>();
				RecordModel record=new RecordModel(allRecords);
				//如果记录已经被删除  或者不属于该账户 跳出本次循环
				int tempInt=record.getID();
				if(record.getIsDeleted()==1||!record.getUserName().equals(loginString)||(record.getEarningMin()==0&&record.getEarningMax()==0)){
					continue;
				}
				map.put("item_id", record.getID());
				map.put("item_state", record.getState());
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
					map.put("item_profit", dealFloat(record.getEarningMax()*100)+"%");					
				}else{
					map.put("item_profit", dealFloat(record.getEarningMin()*100)+"%~"+dealFloat(record.getEarningMax()*100)+"%");
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
		updateLogin();
		List<Map<String, Object>> dataList=new ArrayList<Map<String,Object>>();
		this.allRecords = this.helper.returALLRecords(this.db);
		if(allRecords.getCount()!=0){
			while (allRecords.moveToNext()) {
				Map<String, Object> map=new HashMap<String, Object>();
				RecordModel record=new RecordModel(allRecords);
				//如果记录已经被删除 不属于该用户 已经处理过 跳出本次循环
				if(record.getIsDeleted()==1||!record.getUserName().equals(loginString)||record.getState()==1||(record.getEarningMin()==0&&record.getEarningMax()==0)){
					continue;
				}
				boolean judge=false;
				switch (type) {
					case 0:{
						//int nowDays=year*365+month*30+day;
						if(parseDay(record.getTimeEnd())<=this.days){
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
						icon=DBOpenHelper.PLATFORM_ICONS[DBOpenHelper.PLATFORM_ICONS.length-1];
						findIcon=true;
					}
					map.put("item_icon", icon);
					map.put("item_name", record.getPlatform()+"-"+record.getType());
					map.put("item_money", record.getMoney());
					if (record.getEarningMin()==0.0) {
						map.put("item_profit", dealFloat(record.getEarningMax()*100)+"%");					
					}else{
						map.put("item_profit", dealFloat(record.getEarningMin()*100)+"%~"+dealFloat(record.getEarningMax()*100)+"%");
					}
					dataList.add(map);
				}				
			}
		}
		return dataList;		
	}
	
	//type为0表示已经到期，1表示即将到期
	public int indexCount(int type){
		updateLogin();
		int count=0;
		this.allRecords = this.helper.returALLRecords(this.db);
		if(allRecords.getCount()!=0){
			while (allRecords.moveToNext()) {
				RecordModel record=new RecordModel(allRecords);
				//如果记录已经被删除 跳出本次循环
				if(record.getIsDeleted()==1||!record.getUserName().equals(loginString)||record.getState()==1||(record.getEarningMin()==0&&record.getEarningMax()==0)){
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
		updateLogin();
        ArrayList<String> xVals = new ArrayList<String>();
		this.allRecords = this.helper.returALLRecords(this.db);
		if(allRecords.getCount()!=0){
			while (allRecords.moveToNext()) {
				RecordModel record=new RecordModel(allRecords);
				//如果记录已经被删除 跳出本次循环
				if(record.getIsDeleted()==1||!record.getUserName().equals(loginString)){
					continue;
				}
				switch (type) {
					case 0:{
						if(!xVals.contains(record.getPlatform())&&record.getState()==0){
							xVals.add(record.getPlatform());
						}
						break;
					}
					case 4:{
						if(!xVals.contains(record.getPlatform())&&record.getState()==1){
							xVals.add(record.getPlatform());
						}
						break;
					}
					default:
						break;
				}
				
			}
		}
		return xVals;
	}
		
	

	public void saveNews(NewsModel temp) {
		// TODO Auto-generated method stub
		 String sqlString="insert into news(title,add_time,content) values('"+temp.getTitle()+"','"+temp.getAddTime()+"','"+temp.getContent()+"')";
		 Log.i("m_info",sqlString);
		 this.db.execSQL(sqlString);		
	}
}
