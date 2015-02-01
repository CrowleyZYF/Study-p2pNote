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
		super(context, name, null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		/*
		 * record表
		 * 
		 * _id:id号
		 * platform:平台名 如陆金所
		 * product:具体的投资项目 如富赢人生
		 * moneyFromPlatform:来自余额的本金
		 * moneyFromNew:来自新增的本金
		 * earningMin:浮动收益率下限 如果是固定收益率 该值为0 格式为小数 即15%的话该值为0.15
		 * earningMax:浮动收益率上限 如果是固定收益率 该值为其收益率
		 * method:计息方式 0为到期还本息 1为 按月还本息 2为按月只还息
		 * timeBegin:计息时间 格式为2014-12-26的字符串
		 * timeEnd:到期时间 格式为2014-12-26的字符串	 
		 * timeStamp:创建记录时的时间戳
		 * state:到期后是否处理,0为未处理，1为已处理
		 * isDeleted:是否已被删除，0为未删除，1为已删除
		 * userName:用户名，登陆后用登陆的用户名，未登录的时候默认为not_login
		 * timeStampEnd:结算记录时的时间戳 new
		 * 
		 * 
		 * rest表
		 * 
		 * _id:id号
		 * timeStamp：对应记录的创建记录时的时间戳
		 * platform：平台名称
		 * name：平台-项目名称/取出操作/充值操作
		 * userName：用户名
		 * type：1表示新增，2表示取出，3表示收益，4表示投资,5代表回款
		 * money：具体多少数额
		 * 
		 * 
		 * product表
		 * 
		 * _id:id号
		 * platform:平台
		 * product:项目
		 * money:本金
		 * method:计息方式 0为到期还本息 1为 按月还本息 2为按月只还息
		 * earningMin:浮动收益率下限 如果是固定收益率 该值为0 格式为小数 即15%的话该值为0.15
		 * earningMax:浮动收益率上限 如果是固定收益率 该值为其收益率
		 * period:投资时间
		 * 
		 * 
		 * 
		 */
		//db.execSQL("DROP TABLE record");
		
		db.execSQL("create table if not exists record (_id integer primary key autoincrement,platform text not null,type text not null,moneyFromPlatform real not null,moneyFromNew real not null,earningMin real not null,earningMax real not null,method integer not null,timeBegin text not null,timeEnd text not null,timeStamp text not null,state integer not null,isDeleted integer not null,userName text not null)");		
		db.execSQL("create table if not exists rest (_id integer primary key autoincrement,platform text not null,name text not null,type text not null,money real not null,timeStamp text not null,userName text not null)");
		db.execSQL("create table if not exists product (_id integer primary key autoincrement,platform text not null,product text not null,money real not null,earningMin real not null,earningMax real not null,method integer not null,period integer not null)");
		
		
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('爱投资','爱担保',-1,-1,-1,-1,-1)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('爱投资','爱保理',-1,-1,-1,-1,-1)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('爱投资','爱融租',-1,-1,-1,-1,-1)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('爱投资','爱收藏',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('点融网','新手投资团',100,0.07,0.16,2,12)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('点融网','稳健投资团',1000,0,0.09,2,12)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('点融网','高手投资团',500,0,0.07,2,12)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('点融网','点融VIP团',300000,0,0.12,2,12)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('点融网','散标',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('积木盒子','散标',-1,0,0.09,0,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('陆金所','安盈票据',3000,0,0.06,0,3)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('陆金所','富盈人生',1000,0,0.056,0,1)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('陆金所','稳盈安e',10000,0,0.0861,1,36)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('陆金所','稳盈安业-3个月',250000,0,0.075,2,3)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('陆金所','稳盈安业-6个月',250000,0,0.078,2,6)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('陆金所','专享理财',100000,0.1,0.15,1,1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('人人贷','U计划A',1000,0.07,0.14,0,3)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('人人贷','U计划B',10000,0,0.09,0,6)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('人人贷','U计划C',10000,0,0.11,0,12)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('人人贷','散标',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('盛融在线','散标',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('鑫合汇','日生益',-1,-1,-1,-1,-1)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('鑫合汇','聚优宝',-1,-1,-1,-1,-1)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('鑫合汇','企益融',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('有利网','定存宝-12个月',1000,0,0.11,0,12)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('有利网','定存宝-6个月',1000,0,0.09,0,6)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('有利网','定存宝-3个月',1000,0,0.07,0,3)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('有利网','月息通',50,0,0.12,1,12)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('合拍在线','散标',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('红岭创投','散标',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('钱爸爸','散标',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('人人聚财','定投宝-1个月',-1,0.07,0.08,-1,1)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('人人聚财','定投宝-3个月',-1,0.08,0.09,-1,3)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('人人聚财','定投宝-6个月',-1,0.09,0.10,-1,6)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('人人聚财','定投宝-12个月',-1,0.11,0.13,-1,12)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('人人聚财','散标',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('融资城','散标',-1,-1,-1,-1,-1)");

		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('微贷网','散标',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('温州贷','散标',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('91旺财','散标',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('PPmoney','加多保',-1,-1,-1,-1,-1)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('PPmoney','安稳盈',-1,-1,-1,-1,-1)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('PPmoney','直投宝',-1,-1,-1,-1,-1)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('PPmoney','小贷宝',-1,-1,-1,-1,-1)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('PPmoney','典当宝',-1,-1,-1,-1,-1)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('PPmoney','散标',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('爱钱进','整存宝-3个月',-1,-1,-1,-1,3)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('爱钱进','整存宝-12个月',-1,-1,-1,-1,12)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('爱钱进','零钱通',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('金信网','月满金',-1,0,0.058,-1,1)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('金信网','季度金',-1,0,0.09,-1,3)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('金信网','双季金',-1,0,0.1,-1,6)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('金信网','金元宝',-1,0.12,0.14,-1,12)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('金信网','金信宝',-1,0,0.13,-1,18)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('金信网','金信盈',-1,0,0.15,-1,24)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('开鑫贷','散标',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('你我贷','散标',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('拍拍贷','散标',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('投哪网','散标',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('向上金服','散标',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('小牛在线','安心牛-1个月',-1,0.102,0.105,-1,1)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('小牛在线','安心牛-3个月',-1,0.118,0.121,-1,3)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('小牛在线','安心牛-4个月',-1,0.136,0.139,-1,6)");
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('小牛在线','散标',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('信融财富','散标',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('宜人贷','散标',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('易贷网','散标',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('翼龙贷','散标',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('银湖网','散标',-1,-1,-1,-1,-1)");
		
		db.execSQL("insert into product(platform,product,money,earningMin,earningMax,method,period) values('招财宝','散标',-1,-1,-1,-1,-1)");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}	
	
	public Cursor returALLRecords(SQLiteDatabase db){
		return db.rawQuery("select * from record", null);
	}
}
