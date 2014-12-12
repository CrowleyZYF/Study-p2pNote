package com.crowley.p2pnote;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class NewItemActivity extends Activity {
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.new_item);
		
	}

}
