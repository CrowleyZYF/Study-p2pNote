package com.crowley.p2pnote.db;

import android.database.Cursor;


public class RecordModel {
	
	/*
	 * 目前就一个表，record，记录所有的投资记录
	 * 表的字段分别为
	 * _id:id号
	 * platform:平台名 如陆金所
	 * type:具体的投资项目 如富赢人生
	 * money:投资的本金
	 * earningMin:浮动收益率下限 如果是固定收益率 该值为0 格式为小数 即15%的话该值为0.15
	 * earningMax:浮动收益率上限 如果是固定收益率 该值为其收益率
	 * method:计息方式 0为到期还本息 1为 按月还本息 2为按月只还息
	 * timeBegin:计息时间 格式为2014-12-26的字符串
	 * timeEnd:到期时间 格式为2014-12-26的字符串
	 * 
	 */
	
	private int id;
	private String platformString;
	private String typeString;
	private Float moneyFloat;
	private Float earningMinFloat;
	private Float earningMaxFloat;
	private Integer methodInteger;
	private String timeBeginString;
	private String timeEndString;
	
	private String timeStamp;
	private int state;
	private int isDeleted;
	private String userName;
	private Float restBegin;
	private Float restEnd;
 
	private String timeStampEnd;
	
	public RecordModel(){
		this.id=0;
		this.platformString="";
		this.typeString="";
		this.moneyFloat=0.0f;
		this.earningMinFloat=0.0f;
		this.earningMaxFloat=0.0f;
		this.methodInteger=0;
		this.timeBeginString="";
		this.timeEndString="";	
		
		this.timeStamp="";
		this.state=0;
		this.isDeleted=0;
		this.userName="";
		this.restBegin=0.0f;
		this.restEnd=0.0f;
		
		this.timeStampEnd="";
	}
	
	public RecordModel(Integer id,String platform,String type,Float money,Float earningMin,Float earningMax,Integer method,String timeBegin,String timeEnd,
			String timeStamp,int state,int isDeleted,String userName,Float restBegin,Float restEnd,
			String timeStampEnd){
		this.id=id;
		this.platformString=platform;
		this.typeString=type;
		this.moneyFloat=money;
		this.earningMinFloat=earningMin;
		this.earningMaxFloat=earningMax;
		this.methodInteger=method;
		this.timeBeginString=timeBegin;
		this.timeEndString=timeEnd;
		
		this.timeStamp=timeStamp;
		this.state=state;
		this.isDeleted=isDeleted;
		this.userName=userName;
		this.restBegin=restBegin;
		this.restEnd=restEnd;
		
		this.timeStampEnd=timeStampEnd;
	}
	
	public RecordModel(Cursor cursor){
		this.id=cursor.getInt(cursor.getColumnIndex("_id"));
		this.platformString=cursor.getString(cursor.getColumnIndex("platform"));
		this.typeString=cursor.getString(cursor.getColumnIndex("type"));
		this.moneyFloat=cursor.getFloat(cursor.getColumnIndex("money"));
		this.earningMinFloat=cursor.getFloat(cursor.getColumnIndex("earningMin"));
		this.earningMaxFloat=cursor.getFloat(cursor.getColumnIndex("earningMax"));
		this.methodInteger=cursor.getInt(cursor.getColumnIndex("method"));
		this.timeBeginString=cursor.getString(cursor.getColumnIndex("timeBegin"));
		this.timeEndString=cursor.getString(cursor.getColumnIndex("timeEnd"));
		
		this.timeStamp=cursor.getString(cursor.getColumnIndex("timeStamp"));
		this.state=cursor.getInt(cursor.getColumnIndex("state"));
		this.isDeleted=cursor.getInt(cursor.getColumnIndex("isDeleted"));
		this.userName=cursor.getString(cursor.getColumnIndex("userName"));
		this.restBegin=cursor.getFloat(cursor.getColumnIndex("restBegin"));
		this.restEnd=cursor.getFloat(cursor.getColumnIndex("restEnd"));
		
		this.timeStampEnd=cursor.getString(cursor.getColumnIndex("timeStampEnd"));
	}
	
	//get method
	public int getID(){
		return this.id;
	}
	
	public String getPlatform(){
		return this.platformString;
	}
	
	public String getType(){
		return this.typeString;
	}
	
	public Float getMoney(){
		return this.moneyFloat;
	}
	
	public Float getEarningMin(){
		return this.earningMinFloat;
	}
	
	public Float getEarningMax(){
		return this.earningMaxFloat;
	}
	
	public Integer getMethod(){
		return this.methodInteger;
	}
	
	public String getTimeBegin(){
		return this.timeBeginString;
	}
	
	public String getTimeEnd(){
		return this.timeEndString;
	}
	
	public String getTimeStamp(){
		return this.timeStamp;
	}
	
	public int getState(){
		return this.state;
	}
	
	public int getIsDeleted(){
		return this.isDeleted;
	}
	
	public String getUserName(){
		return this.userName;
	}
	
	public Float getRestBegin(){
		return this.restBegin;
	}
	
	public Float getRestEnd(){
		return this.restEnd;
	}
	
	public String getTimeStampEnd(){
		return this.timeStampEnd;
	}
	
	//set method	
	public void setPlatform(String s){
		this.platformString=s;
	}
	
	public void setType(String s){
		this.typeString=s;
	}
	
	public void setMoney(Float f){
		this.moneyFloat=f;
	}
	
	public void setEarningMin(Float f){
		this.earningMinFloat=f;
	}
	
	public void setEarningMax(Float f){
		this.earningMaxFloat=f;
	}
	
	public void setMethod(Integer i){
		this.methodInteger=i;
	}
	
	public void setTimeBegin(String s){
		this.timeBeginString=s;
	}
	
	public void setTimeEnd(String s){
		this.timeEndString=s;
	}
	
	public void setTimeStamp(String s){
		this.timeStamp=s;
	}
	
	public void setState(int i){
		this.state=i;
	}
	
	public void setIsDeleted(int i){
		this.isDeleted=i;
	}
	
	public void setUserName(String s){
		this.userName=s;
	}
	
	public void setRestBegin(Float f){
		this.restBegin=f;
	}
	
	public void setRestEnd(Float f){
		this.restEnd=f;
	}
	
	public void setTimeStampEnd(String s){
		this.timeStampEnd=s;
	}
}
