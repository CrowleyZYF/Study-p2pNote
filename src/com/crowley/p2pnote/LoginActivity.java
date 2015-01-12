package com.crowley.p2pnote;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
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
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {
	
	private Button loginButton;
	private Button registerButton;
	private ImageButton backButton;
	private EditText accountEditText;
	private EditText passwordEditText;
	
	private ReturnList returnList;
	
	private int error_code = 2;
	private final int LOGIN_SUCCESS = 0;
	private final int NOT_EXIST = 1;
	private final int PASS_ERROR = 2;
	
	private String accountString;
	private Context nowContext=this;
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg){
			if(msg.what==0x123){
				switch (error_code) {
				case LOGIN_SUCCESS:{
					SharedPreferences preferences=getSharedPreferences("user", MODE_PRIVATE);
					Editor editor = preferences.edit();
					editor.putBoolean("isLogined", true);
					editor.putString("account", accountString);
					editor.commit();
					if(returnList.checkNotLogin()){
						new SweetAlertDialog(nowContext, SweetAlertDialog.WARNING_TYPE)
			            .setTitleText("是否将本地记录导入？")
			            .setCancelText("取消")
			            .setConfirmText("确定")
			            .showCancelButton(true)
			            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
			                @Override
			                public void onClick(SweetAlertDialog sDialog) {
			                    sDialog.dismiss();
								finish();
			                }
			            })
			            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
			                @Override
			                public void onClick(SweetAlertDialog sDialog) {
			                	returnList.setUserName(accountString);
			                    sDialog.setTitleText("成功导入")
			                            .setConfirmText("确定")
			                            .showContentText(false)
			                            .showCancelButton(false)
			                            .setCancelClickListener(null)
			                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
			        		                @Override
			        		                public void onClick(SweetAlertDialog sDialog2) {
			        		                    sDialog2.dismiss();
			        							finish();
			        		                }
			        		            })
			                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
			                }
			            })
			            .show();
					}else{
						finish();
					}
					break;					
				}
				case NOT_EXIST:{
					new SweetAlertDialog(nowContext, SweetAlertDialog.ERROR_TYPE)
	                    .setTitleText("登陆失败")
	                    .setContentText("不存在该账号！")
	                    .setConfirmText("确定")
	                    .show();
					break;
				}
				case PASS_ERROR:{
					new SweetAlertDialog(nowContext, SweetAlertDialog.ERROR_TYPE)
	                    .setTitleText("登陆失败")
	                    .setContentText("密码错误！")
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
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);		
		initView();		
	}

	public void initView() {
		returnList = new ReturnList(this);
		
		loginButton = (Button) findViewById(R.id.login_button);
		registerButton = (Button) findViewById(R.id.register_button);
		backButton = (ImageButton) findViewById(R.id.back);
		accountEditText = (EditText) findViewById(R.id.account);
		passwordEditText = (EditText) findViewById(R.id.password);
		
		loginButton.setOnClickListener(this);
		registerButton.setOnClickListener(this);
		backButton.setOnClickListener(this);
	}
	
	public void login(String account,String password){
		if(!isOpenNetwork()){
			new SweetAlertDialog(nowContext, SweetAlertDialog.ERROR_TYPE)
	            .setTitleText("登陆失败")
	            .setContentText("网络未链接")
                .setConfirmText("确定")
	            .show();
		}else if(account==""){
			new SweetAlertDialog(nowContext, SweetAlertDialog.ERROR_TYPE)
	            .setTitleText("登陆失败")
	            .setContentText("账号不得为空")
                .setConfirmText("确定")
	            .show();
		}else if(password==""){
			new SweetAlertDialog(nowContext, SweetAlertDialog.ERROR_TYPE)
	            .setTitleText("登陆失败")
	            .setContentText("密码不得为空")
	            .setConfirmText("确定")
	            .show();
		}else if(returnList.isEmail(account)){
			final Map<String, String> params = new HashMap<String, String>();
			params.put("user_name", account);
			params.put("password", password);
			new Thread(){
				public void run(){String teString=HttpUtils.submitPostData("http://128.199.226.246/beerich/index.php/login", params, "utf-8");
					try {
						JSONObject object=new JSONObject(teString);
						error_code = (Integer) object.get("error_code");
						handler.sendEmptyMessage(0x123);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}.start();		
		}else {
			new SweetAlertDialog(nowContext, SweetAlertDialog.ERROR_TYPE)
	            .setTitleText("登陆失败")
	            .setContentText("邮箱格式有误！")
                .setConfirmText("确定")
	            .show();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_button:{
			accountString=accountEditText.getText().toString();
			String passwordString=passwordEditText.getText().toString();
			login(accountString, passwordString);
			break;
		}
		case R.id.register_button:{
			Intent intent=new Intent(this,RegisterActivity.class);
            startActivity(intent);
            break;			
		}
		case R.id.back:{
			finish();
			break;
		}
		default:
			break;
		}		
	}
	
	private boolean isOpenNetwork() {  
	    ConnectivityManager connManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);  
	    if(connManager.getActiveNetworkInfo() != null) {  
	        return connManager.getActiveNetworkInfo().isAvailable();  
	    }	  
	    return false;  
	}
}
