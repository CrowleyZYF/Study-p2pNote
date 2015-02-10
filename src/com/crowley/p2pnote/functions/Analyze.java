package com.crowley.p2pnote.functions;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.database.Cursor;

import com.crowley.p2pnote.db.DBOpenHelper;
import com.crowley.p2pnote.db.RecordModel;
import com.crowley.p2pnote.db.RestModel;
import com.github.mikephil.charting.data.Entry;
import com.crowley.p2pnote.functions.Common;

public class Analyze {
	
	private Context nowContext;
	private Platform platform;
	public Analyze(Context context) {
		// TODO Auto-generated constructor stub
		nowContext=context;
		platform=new Platform(context);		
	}
	
	
	/**
	 * 返回饼状图的分类
	 * @param context
	 * @param type 为0表示平台在投金额分析，4为平台余额分析
	 * @return
	 */
	public ArrayList<String> analyzexVals(int type){
        ArrayList<String> xVals = new ArrayList<String>();
        
        DBOpenHelper helper=new DBOpenHelper(nowContext, "record.db");
        
        switch (type) {
			case 0:{
				Cursor allRecords = helper.returALLRecords(helper.getWritableDatabase());
				if(allRecords.getCount()!=0){
					while (allRecords.moveToNext()) {
						RecordModel record=new RecordModel(allRecords);
						//如果记录已经被删除 跳出本次循环
						if(record.getIsDeleted()==1||!record.getUserName().equals(Common.updateLogin(nowContext))){
							continue;
						}
						if(!xVals.contains(record.getPlatform())&&record.getState()==0){
							xVals.add(record.getPlatform());
						}
					}
				}
				allRecords.close();
				break;
			}
			case 4:{
				Cursor allRecords = helper.returALLRests(helper.getWritableDatabase());
				if(allRecords.getCount()!=0){
					while (allRecords.moveToNext()) {
						RestModel record=new RestModel(allRecords);
						if(!xVals.contains(record.getPlatform())){
							xVals.add(record.getPlatform());
						}
					}
				}
				allRecords.close();
				break;
			}
			default:
				break;
		}
		helper.close();
		return xVals;
	}
	
	
	/**
	 * 返回饼状图所需数据 
	 * @param context
	 * @param type 为0表示平台在投金额分析，1为收益率，3为期限结构，2为回款时间，4为平台余额分析
	 * @param xVals 饼状图分类
	 * @return
	 */
	public ArrayList<Entry> analyzeEntries(int type,ArrayList<String> xVals){		
		ArrayList<Entry> entries1 = new ArrayList<Entry>();
		ArrayList<Float> counts = new ArrayList<Float>();
		//收益率计算节点
		Float[] analyze01={0.06f,0.08f,0.1f,0.12f,0.15f,0.2f,0.25f};
		//期限结构计算节点
		//Integer[] analyze02={1,3,6,9,12,18,24};
		Integer[] analyze02={40,100,190,280,380,570,760};
		//回款时间计算节点 第一个数字为天数 其他的为月数
		Integer[] analyze03={7,1,3,6,9,12};
		
		float amount=0.0f;
		for(int i=0;i<xVals.size();i++){
			counts.add(0.0f);
		}
		DBOpenHelper helper=new DBOpenHelper(nowContext, "record.db");
		Cursor allRecords = helper.returALLRecords(helper.getWritableDatabase());
		
		if(allRecords.getCount()!=0){
			while (allRecords.moveToNext()) {
				RecordModel record=new RecordModel(allRecords);
				//如果记录已经被删除 或者不属于该用户 跳出本次循环
				if(record.getIsDeleted()==1||!record.getUserName().equals(Common.updateLogin(nowContext))){
					continue;
				}
				switch (type) {
				case 0:{
					if(record.getState()==1){
						continue;
					}
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
					if(record.getState()==1){
						continue;
					}
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
					break;						
				}
				case 3:{
					if(record.getState()==1){
						continue;
					}
					//int duration=Common.parseMonth(record.getTimeEnd())-Common.parseMonth(record.getTimeBegin());
					int duration=Common.parseDay(record.getTimeEnd())-Common.parseDay(record.getTimeBegin());
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
					break;
				}
				case 2:{
					if(record.getState()==1){
						continue;
					}
					Calendar cal = Calendar.getInstance();
					int leftMonth=Common.parseMonth(record.getTimeEnd())-(cal.get(Calendar.YEAR)*12+cal.get(Calendar.MONTH)+1);
					int leftDay=Common.parseDay(record.getTimeEnd())-Common.parseDay(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH));
					boolean added=false;
					for(int i=0;i<analyze03.length&&added==false;i++){
						if(i==0){
							if(leftDay<analyze03[i]){
								added=true;
								counts.set(i, counts.get(i)+1.0f);
								amount+=1.0f;
							}
						}else{
							if(leftMonth<analyze03[i]){
								added=true;
								counts.set(i, counts.get(i)+1.0f);
								amount+=1.0f;
							}
						}
						
					}
					if (added==false) {
						counts.set(xVals.size()-1, counts.get(xVals.size()-1)+1.0f);
						amount+=1.0f;
					}
					break;
				}
				case 4:{
					for(int i=0;i<xVals.size();i++){
						String platformString=xVals.get(i);
						counts.set(i, counts.get(i)+platform.getRest(platformString));
						amount+=platform.getRest(platformString);
					}
				}
				default:
					break;
				}									
			}
		}
		for(int i = 0; i < xVals.size(); i++) {
			float result;
			if (amount==0) {
				result=0.0f;
			}else{
				result=100f*counts.get(i)/amount;
			}			
	        entries1.add(new Entry(result, i));
	    }
		
		allRecords.close();
		helper.close();		
		return entries1;
	}
	

}
