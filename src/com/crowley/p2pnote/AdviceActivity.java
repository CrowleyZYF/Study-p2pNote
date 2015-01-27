package com.crowley.p2pnote;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import com.crowley.p2pnote.db.HttpUtils;
import com.crowley.p2pnote.functions.Common;
import cn.pedant.SweetAlert.SweetAlertDialog;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

@SuppressLint("HandlerLeak") 
public class AdviceActivity extends Activity implements OnClickListener {

	private ImageButton backButton;
	private Button sendButton;
	private TextView accountTextView;
	private EditText adviceEditText;
	private TextView advice_tag;
	private TextView wrong_tag;
	private TextView help_tag;
	private boolean advice_tag_bool=true;
	private boolean wrong_tag_bool=false;
	private boolean help_tag_bool=false;
	
	private int error_code = 3;
	private final int SEND_SUCCESS = 0;
	private Context nowContext=this;
	
	public String urlString="http://120.27.44.42/p2pbeerich/index.php/news/feedback";
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.advice);
		
		initView();		
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg){
			if(msg.what==0x123){
				switch (error_code) {
				case SEND_SUCCESS:{
					new SweetAlertDialog(nowContext, SweetAlertDialog.SUCCESS_TYPE)
			            .setTitleText("感谢你的意见")
			            .setConfirmText("确定")
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
				default:
					new SweetAlertDialog(nowContext, SweetAlertDialog.SUCCESS_TYPE)
			            .setTitleText("服务器出错啦")
			            .setConfirmText("确定")
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
			}
		}		
	};
	
	private void initView(){
		
		backButton=(ImageButton) findViewById(R.id.back);
		sendButton=(Button) findViewById(R.id.send_button);
		accountTextView=(TextView) findViewById(R.id.account);
		
		adviceEditText=(EditText) findViewById(R.id.advice);
		advice_tag=(TextView) findViewById(R.id.advice_tag);
		wrong_tag=(TextView) findViewById(R.id.wrong_tag);
		help_tag=(TextView) findViewById(R.id.help_tag);
		
		backButton.setOnClickListener(this);
		sendButton.setOnClickListener(this);
		advice_tag.setOnClickListener(this);
		wrong_tag.setOnClickListener(this);
		help_tag.setOnClickListener(this);
		
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
			try {
				sendAdvice();
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		case R.id.advice_tag:{
			advice_tag_bool=!advice_tag_bool;
			if(advice_tag_bool){
				advice_tag.setTextColor(getResources().getColor(R.color.platform_chosen));
			}else{
				advice_tag.setTextColor(getResources().getColor(R.color.light_black));
			}
			break;
		}
		case R.id.wrong_tag:{
			wrong_tag_bool=!wrong_tag_bool;
			if(wrong_tag_bool){
				wrong_tag.setTextColor(getResources().getColor(R.color.platform_chosen));
			}else{
				wrong_tag.setTextColor(getResources().getColor(R.color.light_black));
			}
			break;
		}
		case R.id.help_tag:{
			help_tag_bool=!help_tag_bool;
			if(help_tag_bool){
				help_tag.setTextColor(getResources().getColor(R.color.platform_chosen));
			}else{
				help_tag.setTextColor(getResources().getColor(R.color.light_black));
			}
			break;
		}
		default:
			break;
		}
		
	}

	private void sendAdvice() throws NameNotFoundException {
		// TODO Auto-generated method stub
		String advice=adviceEditText.getText().toString();
		if(!Common.isOpenNetwork(this)){
			Common.errorDialog(nowContext, "发送反馈失败", "网络未链接").show();
		}else if(advice.equals("")){
			Common.errorDialog(nowContext, "发送反馈失败", "意见不可为空!").show();
		}else{
			final Map<String, String> params = new HashMap<String, String>();
			String typeString="";
			if(advice_tag_bool){
				typeString+="建议、";
			}
			if(wrong_tag_bool){
				typeString+="出错、";
			}
			if(help_tag_bool){
				typeString+="帮助、";
			}
			if(typeString.equals("")){
				typeString+="建议";
			}else{
				typeString=typeString.substring(0, typeString.length()-1);
			}
			params.put("type", typeString);
			params.put("content", advice);
			params.put("extra", "应用的版本号:"+getPackageManager().getPackageInfo(getPackageName(),0).versionName+"；系统:安卓；系统版本号："+android.os.Build.MODEL + "," + android.os.Build.VERSION.RELEASE);
			new Thread(){
				public void run(){String teString=HttpUtils.submitPostData(urlString, params, "utf-8");
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

}
