package com.crowley.p2pnote;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

import com.crowley.p2pnote.db.HttpUtils;
import com.crowley.p2pnote.functions.ReturnList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ModifyPasswordActivity extends Activity implements OnClickListener {
	
	private EditText accountEditText;
	private EditText passwordEditText;
	private EditText repeatEditText;
	private EditText repeat2EditText;
	
	private Button registerButton;
	private ImageButton backButton;
	
	private ReturnList returnList;
	
	private int error_code = 3;
	private final int MODIFY_SUCCESS = 0;
	private final int OLD_WRONG = 2;
	
	private String accountString;
	private Context nowContext=this;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.modify_password);
		
		initView();
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg){
			if(msg.what==0x123){
				switch (error_code) {
				case MODIFY_SUCCESS:{
					new SweetAlertDialog(nowContext, SweetAlertDialog.WARNING_TYPE)
		            .setTitleText("修改密码成功")
		            .setConfirmText("确定")
		            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
		                @Override
		                public void onClick(SweetAlertDialog sDialog) {
		                    sDialog.dismiss();
							finish();
		                }
		            })
		            .show();							
					break;					
				}
				case OLD_WRONG:{
					new SweetAlertDialog(nowContext, SweetAlertDialog.ERROR_TYPE)
	                    .setTitleText("修改密码失败")
	                    .setContentText("原密码错误！")
	                    .setConfirmText("确定")
	                    .show();
					break;
				}
				default:
					break;
				}				
			}
		}		
	};

	private void initView() {
		returnList = new ReturnList(this);
		
		registerButton=(Button) findViewById(R.id.register_button);
		backButton=(ImageButton) findViewById(R.id.back);
		accountEditText=(EditText) findViewById(R.id.account);
		passwordEditText=(EditText) findViewById(R.id.password);
		repeatEditText=(EditText) findViewById(R.id.password_repeat);
		repeat2EditText=(EditText) findViewById(R.id.password_repeat2);
		
		SharedPreferences preferences=getSharedPreferences("user", MODE_PRIVATE);
		accountEditText.setText(preferences.getString("account", "出错啦"));
		
		backButton.setOnClickListener(this);
		registerButton.setOnClickListener(this);
	}
	
	private void register(String account,String password,String password_repeat,String password_repeat2){
		if(!password_repeat.equals(password_repeat2)){
			new SweetAlertDialog(nowContext, SweetAlertDialog.ERROR_TYPE)
	            .setTitleText("修改密码失败")
	            .setContentText("两次密码不一致！")
	            .setConfirmText("确定")
	            .show();
		}else if(password.equals("")){
			new SweetAlertDialog(nowContext, SweetAlertDialog.ERROR_TYPE)
            .setTitleText("修改密码失败")
            .setContentText("必须输入旧密码！")
            .setConfirmText("确定")
            .show();
		}else{
			final Map<String, String> params = new HashMap<String, String>();
			params.put("user_name", account);
			params.put("old_password", password);
			params.put("new_password", password_repeat);
			new Thread(){
				public void run(){String teString=HttpUtils.submitPostData("http://128.199.226.246/beerich/index.php/login/changePassword", params, "utf-8");
					try {
						JSONObject object=new JSONObject(teString);
						error_code = (Integer) object.get("error_code");
						handler.sendEmptyMessage(0x123);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:{
			finish();
			break;
		}
		case R.id.register_button:{
			accountString=accountEditText.getText().toString();
			String passwordString=passwordEditText.getText().toString();
			register(accountString, passwordString,repeatEditText.getText().toString(),repeat2EditText.getText().toString());
			break;			
		}
		default:
			break;
		}
		
	}

}
