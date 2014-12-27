package com.crowley.p2pnote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

public class LoginActivity extends Activity implements OnClickListener {
	
	private Button registerButton;
	private ImageButton backButton;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		
		registerButton = (Button) findViewById(R.id.register_button);
		backButton = (ImageButton) findViewById(R.id.back);
		
		registerButton.setOnClickListener(this);
		backButton.setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.register_button:{
			Intent intent=new Intent(this,RegisterActivity.class);
            startActivity(intent);
            break;			
		}
		case R.id.back:{
			//Intent intent = new Intent();
			finish();
		}
		default:
			break;
		}
		
	}

}
