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
		R.string.company_name09,
		R.string.company_name10,
		R.string.company_name11,
		R.string.company_name12,
		R.string.company_name13,
		R.string.company_name14,
		R.string.company_name15,
		R.string.company_name16,
		R.string.company_name17,
		R.string.company_name18,
		R.string.company_name19,
		R.string.company_name20,
		R.string.company_name21,
		R.string.company_name22,
		R.string.company_name23,
		R.string.company_name24,
		R.string.company_name25,
		R.string.company_name26,
		R.string.company_name27,
		R.string.company_name28,
		R.string.company_name29,
		R.string.company_name30,
		R.string.company_name31,
		R.string.company_name32
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
		R.drawable.company_icon09,
		R.drawable.company_icon10,
		R.drawable.company_icon11,
		R.drawable.company_icon12,
		R.drawable.company_icon13,
		R.drawable.company_icon14,
		R.drawable.company_icon15,
		R.drawable.company_icon16,
		R.drawable.company_icon17,
		R.drawable.company_icon18,
		R.drawable.company_icon19,
		R.drawable.company_icon20,
		R.drawable.company_icon21,
		R.drawable.company_icon22,
		R.drawable.company_icon23,
		R.drawable.company_icon24,
		R.drawable.company_icon25,
		R.drawable.company_icon26,
		R.drawable.company_icon27,
		R.drawable.company_icon28,
		R.drawable.company_icon29,
		R.drawable.company_icon30,
		R.drawable.company_icon31,
		R.drawable.company_icon32
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
		R.drawable.company_icon09_big,
		R.drawable.company_icon10_big,
		R.drawable.company_icon11_big,
		R.drawable.company_icon12_big,
		R.drawable.company_icon13_big,
		R.drawable.company_icon14_big,
		R.drawable.company_icon15_big,
		R.drawable.company_icon16_big,
		R.drawable.company_icon17_big,
		R.drawable.company_icon18_big,
		R.drawable.company_icon19_big,
		R.drawable.company_icon20_big,
		R.drawable.company_icon21_big,
		R.drawable.company_icon22_big,
		R.drawable.company_icon23_big,
		R.drawable.company_icon24_big,
		R.drawable.company_icon25_big,
		R.drawable.company_icon26_big,
		R.drawable.company_icon27_big,
		R.drawable.company_icon28_big,
		R.drawable.company_icon29_big,
		R.drawable.company_icon30_big,
		R.drawable.company_icon31_big,
		R.drawable.company_icon32_big
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
		R.drawable.company_icon09_gray,
		R.drawable.company_icon10_gray,
		R.drawable.company_icon11_gray,
		R.drawable.company_icon12_gray,
		R.drawable.company_icon13_gray,
		R.drawable.company_icon14_gray,
		R.drawable.company_icon15_gray,
		R.drawable.company_icon16_gray,
		R.drawable.company_icon17_gray,
		R.drawable.company_icon18_gray,
		R.drawable.company_icon19_gray,
		R.drawable.company_icon20_gray,
		R.drawable.company_icon21_gray,
		R.drawable.company_icon22_gray,
		R.drawable.company_icon23_gray,
		R.drawable.company_icon24_gray,
		R.drawable.company_icon25_gray,
		R.drawable.company_icon26_gray,
		R.drawable.company_icon27_gray,
		R.drawable.company_icon28_gray,
		R.drawable.company_icon29_gray,
		R.drawable.company_icon30_gray,
		R.drawable.company_icon31_gray,
		R.drawable.company_icon32_gray
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
		R.color.company09,
		R.color.company10,
		R.color.company11,
		R.color.company12,
		R.color.company13,
		R.color.company14,
		R.color.company15,
		R.color.company16,
		R.color.company17,
		R.color.company18,
		R.color.company19,
		R.color.company20,
		R.color.company21,
		R.color.company22,
		R.color.company23,
		R.color.company24,
		R.color.company25,
		R.color.company26,
		R.color.company27,
		R.color.company28,
		R.color.company29,
		R.color.company30,
		R.color.company31,
		R.color.company32
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
		super(context, name, null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		/*
		 * record��
		 * 
		 * _id:id��
		 * platform:ƽ̨�� ��½����
		 * product:�����Ͷ����Ŀ �縻Ӯ����
		 * moneyFromPlatform:�������ı���
		 * moneyFromNew:���������ı���
		 * earningMin:�������������� ����ǹ̶������� ��ֵΪ0 ��ʽΪС�� ��15%�Ļ���ֵΪ0.15
		 * earningMax:�������������� ����ǹ̶������� ��ֵΪ��������
		 * method:��Ϣ��ʽ 0Ϊ���ڻ���Ϣ 1Ϊ ���»���Ϣ 2Ϊ����ֻ��Ϣ
		 * timeBegin:��Ϣʱ�� ��ʽΪ2014-12-26���ַ���
		 * timeEnd:����ʱ�� ��ʽΪ2014-12-26���ַ���	 
		 * timeStamp:������¼ʱ��ʱ���
		 * state:���ں��Ƿ���,0Ϊδ����1Ϊ�Ѵ���
		 * isDeleted:�Ƿ��ѱ�ɾ����0Ϊδɾ����1Ϊ��ɾ��
		 * userName:�û�������½���õ�½���û�����δ��¼��ʱ��Ĭ��Ϊnot_login
		 * timeStampEnd:�����¼ʱ��ʱ��� new
		 * 
		 * 
		 * rest��
		 * 
		 * _id:id��
		 * timeStamp����Ӧ��¼�Ĵ�����¼ʱ��ʱ���
		 * platform��ƽ̨����
		 * name��ƽ̨-��Ŀ����/ȡ������/��ֵ����
		 * userName���û���
		 * type��1��ʾ������2��ʾȡ����3��ʾ���棬4��ʾͶ��,5����ؿ�
		 * money�������������
		 * 
		 * 
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
		//db.execSQL("DROP TABLE record");
		
		db.execSQL("create table if not exists record (_id integer primary key autoincrement,platform text not null,type text not null,moneyFromPlatform real not null,moneyFromNew real not null,earningMin real not null,earningMax real not null,method integer not null,timeBegin text not null,timeEnd text not null,timeStamp text not null,state integer not null,isDeleted integer not null,userName text not null)");		
		db.execSQL("create table if not exists rest (_id integer primary key autoincrement,platform text not null,name text not null,type text not null,money real not null,timeStamp text not null,userName text not null)");
		db.execSQL("create table if not exists product (_id integer primary key autoincrement,platform text not null,product text not null,money real not null,earningMin real not null,earningMax real not null,method integer not null,period integer not null)");
		
		
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('��Ͷ��','������',-1,-1,-1,-1,-1)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('��Ͷ��','������',-1,-1,-1,-1,-1)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('��Ͷ��','������',-1,-1,-1,-1,-1)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('��Ͷ��','���ղ�',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('������','����Ͷ����',100,0.07,0.16,2,12)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('������','�Ƚ�Ͷ����',1000,0,0.09,2,12)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('������','����Ͷ����',500,0,0.07,2,12)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('������','����VIP��',300000,0,0.12,2,12)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('������','ɢ��',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('��ľ����','ɢ��',-1,0,0.09,0,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('½����','��ӯƱ��',3000,0,0.06,0,3)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('½����','��ӯ����',1000,0,0.056,0,1)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('½����','��ӯ��e',10000,0,0.0861,1,36)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('½����','��ӯ��ҵ-3����',250000,0,0.075,2,3)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('½����','��ӯ��ҵ-6����',250000,0,0.078,2,6)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('½����','ר�����',100000,0.1,0.15,1,1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('���˴�','U�ƻ�A',1000,0.07,0.14,0,3)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('���˴�','U�ƻ�B',10000,0,0.09,0,6)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('���˴�','U�ƻ�C',10000,0,0.11,0,12)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('���˴�','ɢ��',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('ʢ������','ɢ��',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('�κϻ�','������',-1,-1,-1,-1,-1)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('�κϻ�','���ű�',-1,-1,-1,-1,-1)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('�κϻ�','������',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('������','���汦-12����',1000,0,0.11,0,12)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('������','���汦-6����',1000,0,0.09,0,6)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('������','���汦-3����',1000,0,0.07,0,3)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('������','��Ϣͨ',50,0,0.12,1,12)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('��������','ɢ��',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('���봴Ͷ','ɢ��',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('Ǯ�ְ�','ɢ��',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('���˾۲�','��Ͷ��-1����',-1,0.07,0.08,-1,1)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('���˾۲�','��Ͷ��-3����',-1,0.08,0.09,-1,3)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('���˾۲�','��Ͷ��-6����',-1,0.09,0.10,-1,6)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('���˾۲�','��Ͷ��-12����',-1,0.11,0.13,-1,12)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('���˾۲�','ɢ��',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('���ʳ�','ɢ��',-1,-1,-1,-1,-1)");

		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('΢����','ɢ��',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('���ݴ�','ɢ��',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('91����','ɢ��',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('PPmoney','�Ӷౣ',-1,-1,-1,-1,-1)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('PPmoney','����ӯ',-1,-1,-1,-1,-1)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('PPmoney','ֱͶ��',-1,-1,-1,-1,-1)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('PPmoney','С����',-1,-1,-1,-1,-1)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('PPmoney','�䵱��',-1,-1,-1,-1,-1)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('PPmoney','ɢ��',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('��Ǯ��','���汦-3����',-1,-1,-1,-1,3)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('��Ǯ��','���汦-12����',-1,-1,-1,-1,12)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('��Ǯ��','��Ǯͨ',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('������','������',-1,0,0.058,-1,1)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('������','���Ƚ�',-1,0,0.09,-1,3)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('������','˫����',-1,0,0.1,-1,6)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('������','��Ԫ��',-1,0.12,0.14,-1,12)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('������','���ű�',-1,0,0.13,-1,18)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('������','����ӯ',-1,0,0.15,-1,24)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('���δ�','ɢ��',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('���Ҵ�','ɢ��',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('���Ĵ�','ɢ��',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('Ͷ����','ɢ��',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('���Ͻ��','ɢ��',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('Сţ����','����ţ-1����',-1,0.102,0.105,-1,1)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('Сţ����','����ţ-3����',-1,0.118,0.121,-1,3)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('Сţ����','����ţ-4����',-1,0.136,0.139,-1,6)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('Сţ����','ɢ��',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('���ڲƸ�','ɢ��',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('���˴�','ɢ��',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('�״���','ɢ��',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('������','ɢ��',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('������','ɢ��',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('�вƱ�','ɢ��',-1,-1,-1,-1,-1)");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}	
	
	public Cursor returALLRecords(SQLiteDatabase db){
		return db.rawQuery("select * from record", null);
	}
}
