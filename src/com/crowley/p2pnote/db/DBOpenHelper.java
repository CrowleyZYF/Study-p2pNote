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
	 * 平台名称
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
	 * 平台产品
	 * 
	 * */	
	public static final int[][] PLATFORM_PRODUCT = {
		{R.string.company_name01_product01,R.string.company_name01_product02,R.string.company_name01_product03,R.string.company_name01_product04,R.string.company_name01_product05},
		{R.string.company_name02_product01,R.string.company_name02_product02,R.string.company_name02_product03,R.string.company_name02_product04},
		{R.string.company_name03_product01},
		{R.string.company_name04_product01,R.string.company_name04_product02,R.string.company_name04_product03,R.string.company_name04_product04,R.string.company_name04_product05},
		{R.string.company_name05_product01,R.string.company_name05_product02,R.string.company_name05_product03},
		{R.string.company_name06_product01},
		{R.string.company_name07_product01,R.string.company_name07_product02},
		{R.string.company_name08_product01,R.string.company_name08_product02,R.string.company_name08_product03,R.string.company_name08_product04},
		{R.string.company_name09_product01},
		{R.string.company_name10_product01},
		{R.string.company_name11_product01},
		{R.string.company_name12_product01,R.string.company_name12_product02},
		{R.string.company_name13_product01},
		{R.string.company_name14_product01},
		{R.string.company_name15_product01},
		{R.string.company_name16_product01},
		{R.string.company_name17_product01,R.string.company_name17_product02,R.string.company_name17_product03,R.string.company_name17_product04,R.string.company_name17_product05,R.string.company_name17_product06},
		{R.string.company_name18_product01,R.string.company_name18_product02},
		{R.string.company_name19_product01,R.string.company_name19_product02,R.string.company_name19_product03,R.string.company_name19_product04,R.string.company_name19_product05,R.string.company_name19_product06},
		{R.string.company_name20_product01},
		{R.string.company_name21_product01},
		{R.string.company_name22_product01},
		{R.string.company_name23_product01},
		{R.string.company_name24_product01},
		{R.string.company_name25_product01,R.string.company_name25_product02},
		{R.string.company_name26_product01},
		{R.string.company_name27_product01},
		{R.string.company_name28_product01},
		{R.string.company_name29_product01},
		{R.string.company_name30_product01},
		{R.string.company_name31_product01},
		{}
	};
	
	/**
	 * 平台图标
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
	 * 平台大图标
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
	 * 平台灰图标
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
	 * 平台颜色
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
	 * 分析标题
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
	 * 分析小标题
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
		 * 目前就一个表，record，记录所有的投资记录
		 * 表的字段分别为
		 * _id:id号
		 * plateform:平台名 如陆金所
		 * type:具体的投资项目 如富赢人生
		 * money:投资的本金
		 * earningMin:浮动收益率下限 如果是固定收益率 该值为0 格式为小数 即15%的话该值为0.15
		 * earningMax:浮动收益率上限 如果是固定收益率 该值为其收益率
		 * method:计息方式 0为到期还本息 1为 按月还本息 2为按月只还息
		 * timeBegin:计息时间 格式为2014-12-26的字符串
		 * timeEnd:到期时间 格式为2014-12-26的字符串	 
		 * 
		 * 
		 * version 2:
		 * 增加字段
		 * 
		 * timeStamp:创建记录时的时间戳
		 * state:到期后是否处理,0为未处理，1为已处理
		 * isDeleted:是否已被删除，0为未删除，1为已删除
		 * userName:用户名，登陆后用登陆的用户名，未登录的时候默认为not_login
		 * restBegin:利息，平台的所剩的余额 new
		 * restEnd:取出，平台的所剩的余额 new
		 * 
		 * 
		 * version 3:
		 * 增加字段
		 * 
		 * timeStampEnd:结算记录时的时间戳 new
		 * 
		 * 
		 * version 4:
		 * 增加表rest
		 * 
		 * _id:id号
		 * platform：平台-项目名称
		 * userName：用户名
		 * type：计息方式， 0为到期还本息 1为 按月还本息 2为按月只还息
		 * money：支出多少和取出多少
		 * rest：完成这笔记录后余额还有多少
		 * timeStampEnd：结算时间
		 * 
		 * 
		 * version 5:
		 * 弃用rest record增加一个字段rest
		 * rest:结算后 该平台的余额还有多少
		 * 
		 * 
		 * 以上都为一个版本， 真版本2
		 * version2
		 * 增加news表
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
