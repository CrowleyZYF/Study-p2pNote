package com.crowley.p2pnote.db;

import android.database.Cursor;
import android.util.Log;


public class NewsModel {
	
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
