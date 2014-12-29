package com.crowley.p2pnote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class AdviceActivity extends Activity implements OnClickListener {

	private ImageButton backButton;
	private Button sendButton;
	private TextView accountTextView;
	private EditText adviceEditText;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.advice);
		
		initView();
		
		
	}
	
	private void initView(){
		
		backButton=(ImageButton) findViewById(R.id.back);
		sendButton=(Button) findViewById(R.id.send_button);
		accountTextView=(TextView) findViewById(R.id.account);
		adviceEditText=(EditText) findViewById(R.id.advice);
		
		backButton.setOnClickListener(this);
		sendButton.setOnClickListener(this);
		
		SharedPreferences preferences=getSharedPreferences("user", MODE_PRIVATE);
		boolean isLogined = preferences.getBoolean("isLogined", false);
		if(isLogined){
			accountTextView.setText(preferences.getString("account", "出错啦"));
		}else{
			accountTextView.setText("未登录用户");
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
		case R.id.send_button:{
			if(sendAdvice()){
				finish();
			}
			break;
		}
		default:
			break;
		}
		
	}

	private boolean sendAdvice() {
		// TODO Auto-generated method stub
		String advice=adviceEditText.getText().toString();
		if(advice.equals("")){
			Toast.makeText(getApplicationContext(),"意见不可为空哦 ∩_∩ ",Toast.LENGTH_SHORT).show();
			return false;
		}else{
			Toast.makeText(getApplicationContext(),"感谢您的意见",Toast.LENGTH_SHORT).show();
			return true;
		}
		
	}

}
