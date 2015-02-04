package com.crowley.p2pnote;

import cn.pedant.SweetAlert.SweetAlertDialog;

import com.crowley.p2pnote.functions.Common;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class AboutActivity extends Activity implements OnClickListener {

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
		//weibo=(RelativeLayout) findViewById(R.id.weibo_button);
		
		backButton.setOnClickListener(this);
		weichat.setOnClickListener(this);
		//weibo.setOnClickListener(this);
	}	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:{
			finish();
			break;
		}
		/*case R.id.weibo_button:{
			Common.toBeContinuedDialog(this).show();
			break;
		}*/
		case R.id.weichat_button:{
			//Common.toBeContinuedDialog(this).show();
			new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
			    .setTitleText("请截屏后扫一扫")
			    .setContentText("关注我们的公众号O(∩_∩)O")
			    .setCustomImage(R.drawable.weichatcode)
			    .setConfirmText("确定")
			    .show();
			break;
		}
		default:
			break;
		}
		
	}

}
