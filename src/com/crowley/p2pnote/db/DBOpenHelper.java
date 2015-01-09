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
		super(context, name, null, 4);
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
		 * platform��ƽ̨����
		 * userName���û���
		 * type�����滹��ȡ�� ����Ϊ0 ȡ��Ϊ1
		 * money��֧�����ٺ�ȡ������
		 * rest�������ʼ�¼�����ж���
		 * timeStampEnd������ʱ��
		 * 
		 * 
		 */
		//db.execSQL("DROP TABLE record");
		db.execSQL("create table if not exists record (_id integer primary key autoincrement,platform text not null,type text not null,money real not null,earningMin real not null,earningMax real not null,method integer not null,timeBegin text not null,timeEnd text not null,timeStamp text not null,state integer not null,isDeleted integer not null,restBegin real not null,restEnd real not null,timeStampEnd text not null)");		
		db.execSQL("create table if not exists rest (_id integer primary key autoincrement,platform text not null,userName text not null,type integer not null,money real not null,rest real not null,timeStampEnd real not null)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		for(int i = oldVersion+1;i<=newVersion;i++){
			switch (i) {
			case 2:{
				db.execSQL("ALTER TABLE record ADD COLUMN timeStamp text not null DEFAULT ''");
				db.execSQL("ALTER TABLE record ADD COLUMN state integer not null DEFAULT 0");
				db.execSQL("ALTER TABLE record ADD COLUMN isDeleted integer not null DEFAULT 0");
				db.execSQL("ALTER TABLE record ADD COLUMN userName text not null DEFAULT ''");
				db.execSQL("ALTER TABLE record ADD COLUMN restBegin real not null DEFAULT 0.00");
				db.execSQL("ALTER TABLE record ADD COLUMN restEnd real not null DEFAULT 0.00");
				
				Cursor allRecords = db.rawQuery("select * from record", null);
				while (allRecords.moveToNext()) {
					Long tsLong = System.currentTimeMillis();
					String ts = tsLong.toString();
					int id=allRecords.getInt(allRecords.getColumnIndex("_id"));
					db.execSQL("UPDATE record SET timeStamp = "+ts+", state = 0, isDeleted = 0, userName = 'not_login', restBegin = 0.00, restEnd = 0.00 WHERE _id = "+id);
					Log.i("m_info",ts);
				}
				break;
			}
			case 3:{
				db.execSQL("ALTER TABLE record ADD COLUMN timeStampEnd text not null DEFAULT ''");
				break;
			}
			case 4:{
				db.execSQL("create table if not exists rest (_id integer primary key autoincrement,platform text not null,userName text not null,type integer not null,money real not null,rest real not null,timeStampEnd real not null)");
				break;
			}
			default:
				break;
			}
		}
	}
	
	public Cursor returALLRecords(SQLiteDatabase db){
		/*Cursor allRecords = db.rawQuery("select * from record", null);
		Long tsLong = System.currentTimeMillis();		
		while (allRecords.moveToNext()) {
			tsLong++;
			String ts = tsLong.toString();
			Long tsLong = System.currentTimeMillis();
			String ts = tsLong.toString();
			int id=allRecords.getInt(allRecords.getColumnIndex("_id"));
			db.execSQL("UPDATE record SET timeStamp = "+ts+", state = 0, isDeleted = 0, userName = 'not_login', restBegin = 0.00, restEnd = 0.00 WHERE _id = "+id);
			//int id=allRecords.getInt(allRecords.getColumnIndex("_id"));
			//db.execSQL("UPDATE record SET timeStamp = "+ts+"  WHERE _id = "+id);
			Log.i("m_info","id:"+allRecords.getInt(allRecords.getColumnIndex("_id")));
			Log.i("m_info","timeStamp:"+allRecords.getString(allRecords.getColumnIndex("timeStamp")));
			Log.i("m_info","-----------------");
		}*/
		return db.rawQuery("select * from record", null);
	}
	
	
}
