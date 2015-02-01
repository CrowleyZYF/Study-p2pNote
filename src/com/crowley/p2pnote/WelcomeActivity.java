package com.crowley.p2pnote;

import com.crowley.p2pnote.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class WelcomeActivity extends Activity{
	private final int SPLASH_DISPLAY_LENGHT = 1500;  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        // TODO Auto-generated method stub  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.welcome);  
        new Handler().postDelayed(new Runnable(){  
            @Override  
            public void run() {  
                // TODO Auto-generated method stub  
                Intent mainIntent = new Intent(WelcomeActivity.this, MainActivity.class);  
                WelcomeActivity.this.startActivity(mainIntent);  
                WelcomeActivity.this.finish();  
            }  
        }, SPLASH_DISPLAY_LENGHT);  
    }  

}
