package com.crowley.p2pnote.functions;

import android.content.Context;
import android.net.ConnectivityManager;
import android.app.Activity;

import com.crowley.p2pnote.R;

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

}
