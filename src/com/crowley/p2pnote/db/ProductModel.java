package com.crowley.p2pnote.db;

import android.database.Cursor;
import android.util.Log;


public class ProductModel {
	
	/*
	 * product��
	 * 
	 * _id:id��
	 * platform:ƽ̨
	 * product:��Ŀ
	 * money:����
	 * method:��Ϣ��ʽ 0Ϊ���ڻ���Ϣ 1Ϊ ���»���Ϣ 2Ϊ����ֻ��Ϣ
	 * earningMin:�������������� ����ǹ̶������� ��ֵΪ0 ��ʽΪС�� ��15%�Ļ���ֵΪ0.15
	 * earningMax:�������������� ����ǹ̶������� ��ֵΪ��������
	 * period:Ͷ��ʱ��
	 * 
	 * 
	 * 
	 */
	
	private int id;
	private String platformString;
	private String productString;
	private Float moneyFloat;
	private Float earningMinFloat;
	private Float earningMaxFloat;
	private Integer methodInteger;
	private Integer periodInteger;
	
	public ProductModel(){
		this.id=0;
		this.platformString="";
		this.productString="";
		this.moneyFloat=0.0f;
		this.earningMinFloat=0.0f;
		this.earningMaxFloat=0.0f;
		this.methodInteger=0;
		this.periodInteger=0;
	}
	
	public ProductModel(Integer id,String platform,String product,Float money,Float earningMin,Float earningMax,Integer method,Integer time){
		this.id=id;
		this.platformString=platform;
		this.productString=product;
		this.moneyFloat=money;
		this.earningMinFloat=earningMin;
		this.earningMaxFloat=earningMax;
		this.methodInteger=method;
		this.periodInteger=time;
	}
	
	public ProductModel(Cursor cursor){
		this.id=cursor.getInt(cursor.getColumnIndex("_id"));
		this.platformString=cursor.getString(cursor.getColumnIndex("platform"));
		this.productString=cursor.getString(cursor.getColumnIndex("product"));
		this.moneyFloat=cursor.getFloat(cursor.getColumnIndex("money"));
		this.earningMinFloat=cursor.getFloat(cursor.getColumnIndex("earningMin"));
		this.earningMaxFloat=cursor.getFloat(cursor.getColumnIndex("earningMax"));
		this.methodInteger=cursor.getInt(cursor.getColumnIndex("method"));
		this.periodInteger=cursor.getInt(cursor.getColumnIndex("period"));
	}
	
	//get method
	public int getID(){
		return this.id;
	}
	
	public String getPlatform(){
		return this.platformString;
	}
	
	public String getProduct(){
		return this.productString;
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
	
	public Integer getPeriod(){
		return this.periodInteger;
	}
	
	//set method	
	public void setPlatform(String s){
		this.platformString=s;
	}
	
	public void setProduct(String s){
		this.productString=s;
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

	public void setPeroid(Integer i){
		this.periodInteger=i;
	}
}
