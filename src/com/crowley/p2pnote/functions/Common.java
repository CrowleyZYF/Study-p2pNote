package com.crowley.p2pnote.functions;

import android.content.Context;
import android.net.ConnectivityManager;
import android.app.Activity;

import com.crowley.p2pnote.R;

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

}
