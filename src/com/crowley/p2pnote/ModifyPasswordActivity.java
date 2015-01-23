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
import android.content.SharedPreferences;
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
public class ModifyPasswordActivity extends Activity implements OnClickListener {
	
	private EditText accountEditText;
	private EditText passwordEditText;
	private EditText repeatEditText;
	private EditText repeat2EditText;
	
	private Button registerButton;
	private ImageButton backButton;
		
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
		            .setTitleText("–ﬁ∏ƒ√‹¬Î≥…π¶")
		            .setConfirmText("»∑∂®")
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
					Common.errorDialog(nowContext, "–ﬁ∏ƒ√‹¬Î ß∞‹", "‘≠√‹¬Î¥ÌŒÛ£°").show();
					break;
				}
				default:
					break;
				}				
			}
		}		
	};

	private void initView() {		
		registerButton=(Button) findViewById(R.id.register_button);
		backButton=(ImageButton) findViewById(R.id.back);
		accountEditText=(EditText) findViewById(R.id.account);
		passwordEditText=(EditText) findViewById(R.id.password);
		repeatEditText=(EditText) findViewById(R.id.password_repeat);
		repeat2EditText=(EditText) findViewById(R.id.password_repeat2);
		
		SharedPreferences preferences=getSharedPreferences("user", MODE_PRIVATE);
		accountEditText.setText(preferences.getString("account", "≥ˆ¥Ì¿≤"));
		
		backButton.setOnClickListener(this);
		registerButton.setOnClickListener(this);
	}
	
	private void register(String account,String password,String password_repeat,String password_repeat2){
		if(!Common.isOpenNetwork(this)){
			Common.errorDialog(nowContext, "–ﬁ∏ƒ√‹¬Î ß∞‹", "Õ¯¬ÁŒ¥¡¥Ω”").show();
		}else if(!password_repeat.equals(password_repeat2)){
			Common.errorDialog(nowContext, "–ﬁ∏ƒ√‹¬Î ß∞‹", "¡Ω¥Œ√‹¬Î≤ª“ª÷¬£°").show();
		}else if(password.equals("")){
			Common.errorDialog(nowContext, "–ﬁ∏ƒ√‹¬Î ß∞‹", "±ÿ–Î ‰»Îæ…√‹¬Î£°").show();
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
