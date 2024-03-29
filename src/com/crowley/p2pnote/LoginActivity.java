package com.crowley.p2pnote;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import cn.pedant.SweetAlert.SweetAlertDialog;
import com.crowley.p2pnote.db.HttpUtils;
import com.crowley.p2pnote.functions.Common;
import android.annotation.SuppressLint;
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

@SuppressLint("HandlerLeak") 
public class LoginActivity extends Activity implements OnClickListener {
	
	private Button loginButton;
	private Button registerButton;
	private ImageButton backButton;
	private EditText accountEditText;
	private EditText passwordEditText;
		
	private int error_code = 2;
	private final int LOGIN_SUCCESS = 0;
	private final int NOT_EXIST = 1;
	private final int PASS_ERROR = 2;
	
	private String accountString;
	private Context nowContext;
	
	private String urlString="http://120.27.44.42/p2pbeerich/index.php/login";
	
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
					if(Common.checkNotLogin(nowContext)){
						new SweetAlertDialog(nowContext, SweetAlertDialog.WARNING_TYPE)
			            .setTitleText("�Ƿ񽫱��ؼ�¼���룿")
			            .setCancelText("ȡ��")
			            .setConfirmText("ȷ��")
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
			                	Common.setUserName(nowContext,accountString);
			                    sDialog.setTitleText("�ɹ�����")
			                            .setConfirmText("ȷ��")
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
					Common.errorDialog(nowContext, "��½ʧ��", "�����ڸ��˺ţ�").show();
					break;
				}
				case PASS_ERROR:{
					Common.errorDialog(nowContext, "��½ʧ��", "�������").show();
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
		nowContext=this;
	}

	public void initView() {
		
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
		if(!Common.isOpenNetwork(this)){
			Common.errorDialog(nowContext, "��½ʧ��", "����δ����").show();
		}else if(account==""){
			Common.errorDialog(nowContext, "��½ʧ��", "�˺Ų���Ϊ��").show();
		}else if(password==""){
			Common.errorDialog(nowContext, "��½ʧ��", "���벻��Ϊ��").show();
		}else if(!Common.isEmail(account)){
			Common.errorDialog(nowContext, "��½ʧ��", "�����ʽ����").show();					
		}else {
			final Map<String, String> params = new HashMap<String, String>();
			params.put("user_name", account);
			params.put("password", password);
			new Thread(){
				public void run(){String teString=HttpUtils.submitPostData(urlString, params, "utf-8");
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
}
