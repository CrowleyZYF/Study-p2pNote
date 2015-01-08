package com.crowley.p2pnote;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class RecordActivity extends Activity implements OnClickListener {

	private ImageButton backButton;
	private RelativeLayout weichat;
	private RelativeLayout weibo;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.about);
		
		initView();
		
		
	}
	
	private void initView(){
		
		backButton=(ImageButton) findViewById(R.id.back);
		weichat=(RelativeLayout) findViewById(R.id.weichat_button);
		weibo=(RelativeLayout) findViewById(R.id.weibo_button);
		
		backButton.setOnClickListener(this);
		weichat.setOnClickListener(this);
		weibo.setOnClickListener(this);
	}
	
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:{
			finish();
			break;
		}
		case R.id.weibo_button:{
			Toast.makeText(getApplicationContext(),"功能还在开发中 敬请期待  ∩_∩ ",Toast.LENGTH_SHORT).show();
			break;
		}
		case R.id.weichat_button:{
			Toast.makeText(getApplicationContext(),"功能还在开发中 敬请期待  ∩_∩ ",Toast.LENGTH_SHORT).show();
			break;
		}
		default:
			break;
		}
		
	}

}
