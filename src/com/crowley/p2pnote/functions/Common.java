package com.crowley.p2pnote.functions;

import java.math.BigDecimal;
import java.util.Calendar;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.app.Activity;

import com.crowley.p2pnote.R;
import com.crowley.p2pnote.db.DBOpenHelper;
import com.crowley.p2pnote.db.RecordModel;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 常用工具类
 * @author 奕峰
 *
 */
public class Common {
	
	/**
	 * 弹出对话框，提示该功能还在开发中
	 * @param context
	 * @return 对话框
	 * 
	 */
	public static SweetAlertDialog toBeContinuedDialog(Context context){
		return new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
	        .setTitleText("功能还在开发中")
	        .setContentText("敬请期待  ∩_∩")
	        .setConfirmText("确定")
	        .setCustomImage(R.drawable.logo);		
	}
	
	
	/**
	 * 弹出错误对话框
	 * @param title为错误标题 content为错误内容 <br/>
	 * @return 对话框
	 * 
	 */
	public static SweetAlertDialog errorDialog(Context context, String title, String content){
		return new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
	        .setTitleText(title)
	        .setContentText(content)
	        .setConfirmText("确定");
	}
	
	
	/**
	 * 检测是否有网络链接
	 * @param activity
	 * @return true为有网络链接
	 * 
	 */	
	public static boolean isOpenNetwork(Activity activity) {  
	    ConnectivityManager connManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);  
	    if(connManager.getActiveNetworkInfo() != null) {  
	        return connManager.getActiveNetworkInfo().isAvailable();  
	    }	  
	    return false;  
	}
	
	/**
	 * 返回现在登录的账号名
	 * @param context
	 * @return 登录的账号名 如果没有登录 则为not_login
	 * 	
	 */
	public static String updateLogin(Context context){
		SharedPreferences preferences=context.getSharedPreferences("user", android.content.Context.MODE_PRIVATE);
		boolean isLogined = preferences.getBoolean("isLogined", false);
		if(isLogined){
			return preferences.getString("account", "出错啦");
		}else{
			return "not_login";
		}
	}
	
	/**
	 * 返回天数
	 * @param 时间字符串 如2014-12-12
	 * @return 天数
	 * 	
	 */	
	public static int parseDay(String date){
		String[] time=date.split("-");
		int year=Integer.parseInt(time[0]);
		int month=Integer.parseInt(time[1]);
		int day=Integer.parseInt(time[2]);
		int[] months={0,31,28,31,30,31,30,31,31,30,31,30,31};
		return year*365+month*months[month]+day;		
	}
	
	/**
	 * 	
	 * @return 今天的年月日
	 */
	public static String getTime(){
		String monthString;
		String dayString;
		Calendar cal = Calendar.getInstance();
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH)+1;
		int day=cal.get(Calendar.DAY_OF_MONTH);
		if (month<10) {
			monthString="-0"+month;			
		}else{
			monthString="-"+month;
		}
		if (day<10) {
			dayString="-0"+day;
		}else{
			dayString="-"+day;
		}
		return year+monthString+dayString;
	}
	
	/**
	 * 返回月数
	 * @param 时间字符串 如2014-12-12
	 * @return 月数
	 * 	
	 */	
	public static int parseMonth(String date){
		String[] time=date.split("-");
		int year=Integer.parseInt(time[0]);
		int month=Integer.parseInt(time[1]);
		return year*12+month+1;		
	}
	
	/**
	 * 保证小数点后两位
	 * @param f
	 * @return
	 */
	public static Float dealFloat(Float f){
		int scale = 2;//设置位数  
		int roundingMode = 4;//表示四舍五入，可以选择其他舍值方式，例如去尾，等等.  
		BigDecimal bd = new BigDecimal((double)f);  
		bd = bd.setScale(scale,roundingMode);  
		return bd.floatValue();
	}
	
	/**
	 * 保证小数点后两位
	 * @param d
	 * @return
	 */
	public static Double dealFloat(Double d){
		int scale = 2;//设置位数  
		int roundingMode = 4;//表示四舍五入，可以选择其他舍值方式，例如去尾，等等.  
		BigDecimal bd = new BigDecimal(d);  
		bd = bd.setScale(scale,roundingMode);  
		return bd.doubleValue();		
	}
	
	/**
	 * 删除记录
	 * @param context
	 * @param id
	 */
	public static void deleteItem(Context context,String id){
		DBOpenHelper helper = new DBOpenHelper(context, "record.db");
		SQLiteDatabase db = helper.getWritableDatabase();
		
		db.execSQL("UPDATE record SET isDeleted = 1 WHERE _id = "+id);
		
		db.close();
		helper.close();
	}
	
	/**
	 * 得到平台字符串
	 * @param id
	 * @return
	 */
	public static String getPlatformString(Context context,String id){
		DBOpenHelper helper = new DBOpenHelper(context, "record.db");
		SQLiteDatabase db = helper.getWritableDatabase();
		
		Cursor tempCursor=db.rawQuery("select * from record WHERE _id="+id, null);
		tempCursor.moveToFirst();
		String platformString=new RecordModel(tempCursor).getPlatform();
		
		db.close();
		helper.close();
		return platformString;
	}

}
