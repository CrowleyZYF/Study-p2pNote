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
 * ���ù�����
 * @author �ȷ�
 *
 */
public class Common {
	
	/**
	 * �����Ի�����ʾ�ù��ܻ��ڿ�����
	 * @param context
	 * @return �Ի���
	 * 
	 */
	public static SweetAlertDialog toBeContinuedDialog(Context context){
		return new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
	        .setTitleText("���ܻ��ڿ�����")
	        .setContentText("�����ڴ�  ��_��")
	        .setConfirmText("ȷ��")
	        .setCustomImage(R.drawable.logo);		
	}
	
	
	/**
	 * ��������Ի���
	 * @param titleΪ������� contentΪ�������� <br/>
	 * @return �Ի���
	 * 
	 */
	public static SweetAlertDialog errorDialog(Context context, String title, String content){
		return new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
	        .setTitleText(title)
	        .setContentText(content)
	        .setConfirmText("ȷ��");
	}
	
	
	/**
	 * ����Ƿ�����������
	 * @param activity
	 * @return trueΪ����������
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
	 * �������ڵ�¼���˺���
	 * @param context
	 * @return ��¼���˺��� ���û�е�¼ ��Ϊnot_login
	 * 	
	 */
	public static String updateLogin(Context context){
		SharedPreferences preferences=context.getSharedPreferences("user", android.content.Context.MODE_PRIVATE);
		boolean isLogined = preferences.getBoolean("isLogined", false);
		if(isLogined){
			return preferences.getString("account", "������");
		}else{
			return "not_login";
		}
	}
	
	/**
	 * ��������
	 * @param ʱ���ַ��� ��2014-12-12
	 * @return ����
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
	 * @return �����������
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
	 * ��������
	 * @param ʱ���ַ��� ��2014-12-12
	 * @return ����
	 * 	
	 */	
	public static int parseMonth(String date){
		String[] time=date.split("-");
		int year=Integer.parseInt(time[0]);
		int month=Integer.parseInt(time[1]);
		return year*12+month+1;		
	}
	
	/**
	 * ��֤С�������λ
	 * @param f
	 * @return
	 */
	public static Float dealFloat(Float f){
		int scale = 2;//����λ��  
		int roundingMode = 4;//��ʾ�������룬����ѡ��������ֵ��ʽ������ȥβ���ȵ�.  
		BigDecimal bd = new BigDecimal((double)f);  
		bd = bd.setScale(scale,roundingMode);  
		return bd.floatValue();
	}
	
	/**
	 * ��֤С�������λ
	 * @param d
	 * @return
	 */
	public static Double dealFloat(Double d){
		int scale = 2;//����λ��  
		int roundingMode = 4;//��ʾ�������룬����ѡ��������ֵ��ʽ������ȥβ���ȵ�.  
		BigDecimal bd = new BigDecimal(d);  
		bd = bd.setScale(scale,roundingMode);  
		return bd.doubleValue();		
	}
	
	/**
	 * ɾ����¼
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
	 * �õ�ƽ̨�ַ���
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
