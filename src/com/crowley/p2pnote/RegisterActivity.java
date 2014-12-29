package com.crowley.p2pnote;

import com.crowley.p2pnote.functions.ReturnList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener {
	
	private EditText accountEditText;
	private EditText passwordEditText;
	private EditText repeatEditText;
	
	private Button registerButton;
	private ImageButton backButton;
	
	private ReturnList returnList;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register);
		
		initView();
	}

	private void initView() {
		returnList = new ReturnList(this);
		
		registerButton=(Button) findViewById(R.id.register_button);
		backButton=(ImageButton) findViewById(R.id.back);
		accountEditText=(EditText) findViewById(R.id.account);
		passwordEditText=(EditText) findViewById(R.id.password);
		repeatEditText=(EditText) findViewById(R.id.password_repeat);
		
		backButton.setOnClickListener(this);
		registerButton.setOnClickListener(this);
	}
	
	private boolean register(String account,String password,String password_repeat){
		if(!returnList.isEmail(account)){
			Toast.makeText(getApplicationContext(),"邮箱格式有误",Toast.LENGTH_SHORT).show();
			return false;			
		}else if(!password.equals(password_repeat)){
			Toast.makeText(getApplicationContext(),"两次密码不一致",Toast.LENGTH_SHORT).show();
			return false;
		}else{
			Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
			return true;
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
			String accountString=accountEditText.getText().toString();
			String passwordString=passwordEditText.getText().toString();
			if(register(accountString, passwordString,repeatEditText.getText().toString())){
				SharedPreferences preferences=getSharedPreferences("user", MODE_PRIVATE);
				Editor editor = preferences.edit();
				editor.putBoolean("isLogined", true);
				editor.putString("account", accountString);
				editor.commit();
				Intent intent=new Intent(this,MainActivity.class);
	            startActivity(intent);
			}
			break;			
		}
		default:
			break;
		}
		
	}

}
