package com.crowley.p2pnote.db;

import android.database.Cursor;
import android.util.Log;


public class RestModel {
	
	/*
	 * product��
	 * 
	 * _id:id��
	 * timeStamp����Ӧ��¼�Ĵ�����¼ʱ��ʱ���
	 * platform��ƽ̨����
	 * name��ƽ̨-��Ŀ����/ȡ������/��ֵ����
	 * userName���û���
	 * type��1��ʾ������2��ʾȡ����3��ʾ���棬4��ʾͶ��
	 * money�������������
	 * 
	 * 
	 * 
	 */
	
	private int id;
	private String timeStampString;
	private String platformString;
	private String nameString;
	private String userNameString;
	private Float moneyFloat;
	private Integer typeInteger;
	private String createTimeString;
	
	public RestModel(){
		this.id=0;
		this.timeStampString="";
		this.platformString="";
		this.nameString="";
		this.userNameString="";
		this.moneyFloat=0.0f;
		this.typeInteger=0;
		this.createTimeString="";
	}
	
	public RestModel(Integer id,String timeStamp,String platform,String name,String userNameString,Float money,Integer type,String createTime){
		this.id=id;
		this.timeStampString=timeStamp;
		this.platformString=platform;
		this.nameString=name;
		this.userNameString=userNameString;
		this.moneyFloat=money;
		this.typeInteger=type;
		this.createTimeString=createTime;
	}
	
	public RestModel(Cursor cursor){
		this.id=cursor.getInt(cursor.getColumnIndex("_id"));
		this.timeStampString=cursor.getString(cursor.getColumnIndex("timeStamp"));
		this.platformString=cursor.getString(cursor.getColumnIndex("platform"));
		this.nameString=cursor.getString(cursor.getColumnIndex("name"));;
		this.userNameString=cursor.getString(cursor.getColumnIndex("userName"));;
		this.moneyFloat=cursor.getFloat(cursor.getColumnIndex("money"));
		this.typeInteger=cursor.getInt(cursor.getColumnIndex("type"));
		this.createTimeString=cursor.getString(cursor.getColumnIndex("createTime"));
	}
	
	//get method
	public int getID(){
		return this.id;
	}
	
	public String getTimeStampString(){
		return this.timeStampString;
	}
	
	public String getPlatform(){
		return this.platformString;
	}
	
	public String getNameString(){
		return this.nameString;
	}
	
	public String getUserNameString(){
		return this.userNameString;
	}
	
	public Float getMoney(){
		return this.moneyFloat;
	}
	
	public int getType(){
		return this.typeInteger;
	}
	
	public String getCreateTime(){
		return this.createTimeString;
	}
	
	//set method	
	public void setTimeStamp(String s){
		this.timeStampString=s;
	}
	
	public void setPlatform(String s){
		this.platformString=s;
	}
	
	public void setNameString(String s){
		this.nameString=s;
	}
	
	public void setUserNameString(String s){
		this.userNameString=s;
	}
	
	public void setMoney(Float f){
		this.moneyFloat=f;
	}
	
	public void setType(int i){
		this.typeInteger=i;
	}
	
	public void setCreateTime(String s){
		this.createTimeString=s;
	}
}
