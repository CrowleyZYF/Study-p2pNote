package com.crowley.p2pnote.db;


import com.crowley.p2pnote.R;

import android.R.integer;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBOpenHelper extends SQLiteOpenHelper{
	
	/**
	 * ƽ̨����
	 * 
	 * */
	public static final int[] PLATFORM_NAMES = {
		R.string.company_name01,
		R.string.company_name02,
		R.string.company_name03,
		R.string.company_name04,
		R.string.company_name05,
		R.string.company_name06,
		R.string.company_name07,
		R.string.company_name08,
		R.string.company_name09
		};
	
	/**
	 * ƽ̨��Ʒ
	 * 
	 * */	
	public static final int[][] PLATFORM_PRODUCT = {
		{R.string.company_name01_product01,R.string.company_name01_product02,R.string.company_name01_product03,R.string.company_name01_product04,R.string.company_name01_product05},
		{R.string.company_name02_product01,R.string.company_name02_product02,R.string.company_name02_product03,R.string.company_name02_product04,R.string.company_name02_product05},
		{R.string.company_name03_product01,R.string.company_name03_product02,R.string.company_name03_product03,R.string.company_name03_product04,R.string.company_name03_product05},
		{R.string.company_name04_product01,R.string.company_name04_product02,R.string.company_name04_product03,R.string.company_name04_product04,R.string.company_name04_product05},
		{R.string.company_name05_product01,R.string.company_name05_product02,R.string.company_name05_product03,R.string.company_name05_product04,R.string.company_name05_product05},
		{R.string.company_name06_product01,R.string.company_name06_product02,R.string.company_name06_product03,R.string.company_name06_product04,R.string.company_name06_product05},
		{R.string.company_name07_product01,R.string.company_name07_product02,R.string.company_name07_product03,R.string.company_name07_product04,R.string.company_name07_product05},
		{R.string.company_name08_product01,R.string.company_name08_product02,R.string.company_name08_product03,R.string.company_name08_product04,R.string.company_name08_product05},
		{}
	};
	
	/**
	 * ƽ̨ͼ��
	 * 
	 * */	
	public static final int[] PLATFORM_ICONS = {
		R.drawable.company_icon01,
		R.drawable.company_icon02,
		R.drawable.company_icon03,
		R.drawable.company_icon04,
		R.drawable.company_icon05,
		R.drawable.company_icon06,
		R.drawable.company_icon07,
		R.drawable.company_icon08,
		R.drawable.company_icon09
		};	
	
	/**
	 * ƽ̨��ͼ��
	 * 
	 * */	
	public static final int[] PLATFORM_ICONS_BIG = {
		R.drawable.company_icon01_big,
		R.drawable.company_icon02_big,
		R.drawable.company_icon03_big,
		R.drawable.company_icon04_big,
		R.drawable.company_icon05_big,
		R.drawable.company_icon06_big,
		R.drawable.company_icon07_big,
		R.drawable.company_icon08_big,
		R.drawable.company_icon09_big
		};
	
	/**
	 * ƽ̨��ͼ��
	 * 
	 * */	
	public static final int[] PLATFORM_ICONS_GRAY = {
		R.drawable.company_icon01_gray,
		R.drawable.company_icon02_gray,
		R.drawable.company_icon03_gray,
		R.drawable.company_icon04_gray,
		R.drawable.company_icon05_gray,
		R.drawable.company_icon06_gray,
		R.drawable.company_icon07_gray,
		R.drawable.company_icon08_gray,
		R.drawable.company_icon09_gray
		};
	
	/**
	 * ƽ̨��ɫ
	 * 
	 * */	
	public static final int[] COLOR = {
		R.color.company01,
		R.color.company02,
		R.color.company03,
		R.color.company04,
		R.color.company05,
		R.color.company06,
		R.color.company07,
		R.color.company08,
		R.color.company09
		};
	
	public static final int gray=R.color.font_gray;
	public static final int platform_in=R.color.platform_in;
	public static final int platform_out=R.color.platform_out;
	
	/**
	 * ��������
	 * 
	 * */
	public static final int[] ANALYZE_TITLE = {
		R.string.analyze_piechart01,
		R.string.analyze_piechart02,
		R.string.analyze_piechart03,
		R.string.analyze_piechart04,
		R.string.analyze_piechart05
		};
	
	/**
	 * ����С����
	 * 
	 * */	
	public static final int[] ANALYZE_TITLE_SMALL = {
		R.string.analyze_piechart01_small,
		R.string.analyze_piechart02_small,
		R.string.analyze_piechart03_small,
		R.string.analyze_piechart04_small,
		R.string.analyze_piechart05_small
		};

	public DBOpenHelper(Context context, String name) {
		super(context, name, null, 2);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		/*
		 * Ŀǰ��һ����record����¼���е�Ͷ�ʼ�¼
		 * ����ֶηֱ�Ϊ
		 * _id:id��
		 * plateform:ƽ̨�� ��½����
		 * type:�����Ͷ����Ŀ �縻Ӯ����
		 * money:Ͷ�ʵı���
		 * earningMin:�������������� ����ǹ̶������� ��ֵΪ0 ��ʽΪС�� ��15%�Ļ���ֵΪ0.15
		 * earningMax:�������������� ����ǹ̶������� ��ֵΪ��������
		 * method:��Ϣ��ʽ 0Ϊ���ڻ���Ϣ 1Ϊ ���»���Ϣ 2Ϊ����ֻ��Ϣ
		 * timeBegin:��Ϣʱ�� ��ʽΪ2014-12-26���ַ���
		 * timeEnd:����ʱ�� ��ʽΪ2014-12-26���ַ���	 
		 * 
		 * 
		 * version 2:
		 * �����ֶ�
		 * 
		 * timeStamp:������¼ʱ��ʱ���
		 * state:���ں��Ƿ���,0Ϊδ����1Ϊ�Ѵ���
		 * isDeleted:�Ƿ��ѱ�ɾ����0Ϊδɾ����1Ϊ��ɾ��
		 * userName:�û�������½���õ�½���û�����δ��¼��ʱ��Ĭ��Ϊnot_login
		 * restBegin:��Ϣ��ƽ̨����ʣ����� new
		 * restEnd:ȡ����ƽ̨����ʣ����� new
		 * 
		 * 
		 * version 3:
		 * �����ֶ�
		 * 
		 * timeStampEnd:�����¼ʱ��ʱ��� new
		 * 
		 * 
		 * version 4:
		 * ���ӱ�rest
		 * 
		 * _id:id��
		 * platform��ƽ̨-��Ŀ����
		 * userName���û���
		 * type����Ϣ��ʽ�� 0Ϊ���ڻ���Ϣ 1Ϊ ���»���Ϣ 2Ϊ����ֻ��Ϣ
		 * money��֧�����ٺ�ȡ������
		 * rest�������ʼ�¼�����ж���
		 * timeStampEnd������ʱ��
		 * 
		 * 
		 * version 5:
		 * ����rest record����һ���ֶ�rest
		 * rest:����� ��ƽ̨�����ж���
		 * 
		 * 
		 * ���϶�Ϊһ���汾�� ��汾2
		 * version2
		 * ����news��
		 * 
		 * 
		 * 
		 * 
		 */
		//db.execSQL("DROP TABLE record");
		
		db.execSQL("create table if not exists record (_id integer primary key autoincrement,platform text not null,type text not null,money real not null,earningMin real not null,earningMax real not null,method integer not null,timeBegin text not null,timeEnd text not null,timeStamp text not null DEFAULT '',state integer not null DEFAULT 0,isDeleted integer not null DEFAULT 0,userName text not null DEFAULT '',restBegin real not null DEFAULT 0.00,restEnd real not null DEFAULT 0.00,timeStampEnd text not null DEFAULT '',rest real not null DEFAULT 0.00)");		
		db.execSQL("create table if not exists news (_id integer primary key autoincrement,title text not null,add_time text not null,content text not null)");
		//db.execSQL("create table if not exists rest (_id integer primary key autoincrement,platform text not null,userName text not null,type integer not null,money real not null,rest real not null,timeStampEnd real not null)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		for(int i = oldVersion+1;i<=newVersion;i++){
			switch (i) {
			case 2:{
				db.execSQL("create table if not exists news (_id integer primary key autoincrement,title text not null,add_time text not null,content text not null)");
				break;
			}
			default:
				break;
			}
		}
	}
	
	public Cursor returALLRecords(SQLiteDatabase db){
		return db.rawQuery("select * from record", null);
	}
	
	public Cursor returALLNews(SQLiteDatabase db){
		return db.rawQuery("select * from news", null);
	}
	
	
}
