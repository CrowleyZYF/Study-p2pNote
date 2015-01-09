package com.crowley.p2pnote.db;

import android.database.Cursor;


public class RecordModel {
	
	/*
	 * Ŀǰ��һ����record����¼���е�Ͷ�ʼ�¼
	 * ����ֶηֱ�Ϊ
	 * _id:id��
	 * platform:ƽ̨�� ��½����
	 * type:�����Ͷ����Ŀ �縻Ӯ����
	 * money:Ͷ�ʵı���
	 * earningMin:�������������� ����ǹ̶������� ��ֵΪ0 ��ʽΪС�� ��15%�Ļ���ֵΪ0.15
	 * earningMax:�������������� ����ǹ̶������� ��ֵΪ��������
	 * method:��Ϣ��ʽ 0Ϊ���ڻ���Ϣ 1Ϊ ���»���Ϣ 2Ϊ����ֻ��Ϣ
	 * timeBegin:��Ϣʱ�� ��ʽΪ2014-12-26���ַ���
	 * timeEnd:����ʱ�� ��ʽΪ2014-12-26���ַ���
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
	}
	
	public RecordModel(Integer id,String platform,String type,Float money,Float earningMin,Float earningMax,Integer method,String timeBegin,String timeEnd){
		this.id=id;
		this.platformString=platform;
		this.typeString=type;
		this.moneyFloat=money;
		this.earningMinFloat=earningMin;
		this.earningMaxFloat=earningMax;
		this.methodInteger=method;
		this.timeBeginString=timeBegin;
		this.timeEndString=timeEnd;	
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
}
