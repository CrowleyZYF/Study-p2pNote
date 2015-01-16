package com.crowley.p2pnote.db;

import android.database.Cursor;
import android.util.Log;


public class NewsModel {
	
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
	private String title;
	private String add_time;
	private String content;
	
	public NewsModel(){
		this.id=0;
		this.title="";
		this.add_time="";
		this.content="";
	}
	
	public NewsModel(Integer id,String title,String add_time,String content){
		this.id=id;
		this.title=title;
		this.add_time=add_time;
		this.content=content;
	}
	
	public NewsModel(Cursor cursor){
		this.id=cursor.getInt(cursor.getColumnIndex("_id"));;
		this.title=cursor.getString(cursor.getColumnIndex("title"));
		this.add_time=cursor.getString(cursor.getColumnIndex("add_time"));
		this.content=cursor.getString(cursor.getColumnIndex("content"));
	}
	
	//get method
	public int getID(){
		return this.id;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public String getAddTime(){
		return this.add_time;
	}
	
	public String getContent(){
		return this.content;
	}
	
	//set method	
	public void setTitle(String s){
		this.title=s;
	}
	
	public void setAddTime(String s){
		this.add_time=s;
	}
	
	public void setContent(String s){
		this.content=s;
	}
}
