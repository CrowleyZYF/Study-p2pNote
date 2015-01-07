package com.crowley.p2pnote;

import java.util.HashMap;
import java.util.Map;

import com.crowley.p2pnote.db.HttpUtils;
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

public class LoginActivity extends Activity implements OnClickListener {
	
	private Button loginButton;
	private Button registerButton;
	private ImageButton backButton;
	private EditText accountEditText;
	private EditText passwordEditText;
	
	private ReturnList returnList;
	
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
	
	public boolean login(String account,String password){
		if(returnList.isEmail(account)){
			Map<String, String> params = new HashMap<String, String>();
			params.put("user_name", account);
			params.put("password", password);
			//mTextView_result.setText(HttpUtils.submitPostData("http://128.199.226.246/beerich/index.php/login", params, "utf-8"));
			Toast.makeText(getApplicationContext(),HttpUtils.submitPostData("http://128.199.226.246/beerich/index.php/login", params, "utf-8"),Toast.LENGTH_SHORT).show();
			return false;			
		}else {
			Toast.makeText(getApplicationContext(),"” œ‰∏Ò Ω”–ŒÛ",Toast.LENGTH_SHORT).show();
			return false;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_button:{
			String accountString=accountEditText.getText().toString();
			String passwordString=passwordEditText.getText().toString();
			if(login(accountString, passwordString)){
				SharedPreferences preferences=getSharedPreferences("user", MODE_PRIVATE);
				Editor editor = preferences.edit();
				editor.putBoolean("isLogined", true);
				editor.putString("account", accountString);
				editor.commit();
				finish();
			}
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
