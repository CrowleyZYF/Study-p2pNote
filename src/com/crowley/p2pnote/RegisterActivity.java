package com.crowley.p2pnote;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

public class RegisterActivity extends Activity implements OnClickListener {
	
	private Button registerButton;
	private ImageButton backButton;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register);
		
		registerButton=(Button) findViewById(R.id.register_button);
		backButton=(ImageButton) findViewById(R.id.back);
		
		backButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:{
			finish();			
		}
		default:
			break;
		}
		
	}

}
