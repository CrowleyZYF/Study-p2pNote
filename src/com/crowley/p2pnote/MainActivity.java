package com.crowley.p2pnote;

import cn.pedant.SweetAlert.SweetAlertDialog;

import com.crowley.p2pnote.ui.SlidingMenu;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.os.Build;
import android.preference.PreferenceManager;

public class MainActivity extends Activity implements OnClickListener{
	
	private int iCount = -1;
	
	private IndexFragment indexFragment;  
    private WaterFragment waterFragment;  
    private AnalyzeFragment analyzeFragment;  
    private PlatformFragment platformFragment;
    private MoreFragment moreFragment;
    
    private LinearLayout tabIndex;  
    private LinearLayout tabWater;
    private LinearLayout tabAnalyze;
    private LinearLayout tabPlatform;
    private LinearLayout tabMore;
    
    private SlidingMenu mLeftMenu;
    
    private FragmentManager fragmentManager;
    
    private TextView title;
    
    private Button newItem;
    private TextView login;
    private TextView checkTextView;
    private TextView backupTextView;
    private TextView adviceTextView;
    private TextView aboutTextView;
    private TextView securityTextView;
    private TextView shareTextView;
    
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);        
        initViews();  
        fragmentManager = getFragmentManager();  
        setTabSelection(0);
        
    }
    
    @Override
    protected void onStart() {
    	// TODO Auto-generated method stub
    	SharedPreferences preferences=getSharedPreferences("user", MODE_PRIVATE);
		boolean isLogined = preferences.getBoolean("isLogined", false);
		if(isLogined){
			login.setText(preferences.getString("account", "出错啦"));
		}else{
			login.setText("点击登录");
		}
    	super.onStart();
    }
    
    public void toggleMenu(View view){
    	mLeftMenu.toggle();
    }

	private void setTabSelection(int index) {
		// TODO Auto-generated method stub 
        resetBtn();
        FragmentTransaction transaction = fragmentManager.beginTransaction();  
        hideFragments(transaction);  
        switch (index)  
        {  
        case 0:   
        	((Button)findViewById(R.id.new_item)).setVisibility(View.VISIBLE);
        	title.setText(R.string.tab_index);
            ((ImageView)tabIndex.findViewById(R.id.tab_index_icon)).setImageResource(R.drawable.index_focus);
            ((TextView)tabIndex.findViewById(R.id.tab_index_text)).setTextColor(getResources().getColor(R.color.tab_text_chosen));
            if (indexFragment == null)  
            {   
            	indexFragment = new IndexFragment();  
                transaction.add(R.id.content, indexFragment);  
            } else  
            {   
                transaction.show(indexFragment);  
            }  
            break;  
        case 1:  
        	((Button)findViewById(R.id.new_item)).setVisibility(View.GONE);
        	title.setText(R.string.tab_water);
        	((ImageView)tabWater.findViewById(R.id.tab_water_icon)).setImageResource(R.drawable.water_focus);
            ((TextView)tabWater.findViewById(R.id.tab_water_text)).setTextColor(getResources().getColor(R.color.tab_text_chosen));  
            if (waterFragment == null)  
            {   
            	waterFragment = new WaterFragment();  
                transaction.add(R.id.content, waterFragment);  
            } else  
            {   
                transaction.show(waterFragment);  
            }  
            break;  
        case 2:  
        	((Button)findViewById(R.id.new_item)).setVisibility(View.GONE);
        	title.setText(R.string.tab_analyze);
        	((ImageView)tabAnalyze.findViewById(R.id.tab_analyze_icon)).setImageResource(R.drawable.analyze_focus);
            ((TextView)tabAnalyze.findViewById(R.id.tab_analyze_text)).setTextColor(getResources().getColor(R.color.tab_text_chosen));  
            if (analyzeFragment == null)  
            {   
            	analyzeFragment = new AnalyzeFragment();  
                transaction.add(R.id.content, analyzeFragment);  
            } else  
            {   
                transaction.show(analyzeFragment);  
            }  
            break;  
        case 3: 
        	((Button)findViewById(R.id.new_item)).setVisibility(View.GONE);
        	title.setText(R.string.tab_platform);
        	((ImageView)tabPlatform.findViewById(R.id.tab_platform_icon)).setImageResource(R.drawable.platform_focus); 
            ((TextView)tabPlatform.findViewById(R.id.tab_platform_text)).setTextColor(getResources().getColor(R.color.tab_text_chosen)); 
            if (platformFragment == null)  
            {   
            	platformFragment = new PlatformFragment();  
                transaction.add(R.id.content, platformFragment);  
            } else  
            {   
                transaction.show(platformFragment);  
            }  
            break;
		case 4:  
        	((Button)findViewById(R.id.new_item)).setVisibility(View.GONE);
			title.setText(R.string.tab_news);
	    	((ImageView)tabMore.findViewById(R.id.tab_more_icon)).setImageResource(R.drawable.more_focus);  
            ((TextView)tabMore.findViewById(R.id.tab_more_text)).setTextColor(getResources().getColor(R.color.tab_text_chosen));
	        if (moreFragment == null)  
	        {   
	        	moreFragment = new MoreFragment();  
	            transaction.add(R.id.content, moreFragment);  
	        } else  
	        {   
	            transaction.show(moreFragment);  
	        }  
	        break;  
	    }  
        transaction.commit();  
		
	}

	private void hideFragments(FragmentTransaction transaction) {
		// TODO Auto-generated method stub
		if (indexFragment != null)  
        {  
            transaction.hide(indexFragment);  
        }  
		if (waterFragment != null)  
        {  
            transaction.hide(waterFragment);  
        }  
		if (analyzeFragment != null)  
        {  
            transaction.hide(analyzeFragment);  
        }  
		if (platformFragment != null)  
        {  
            transaction.hide(platformFragment);  
        }  
		if (moreFragment != null)  
        {  
            transaction.hide(moreFragment);  
        }  		
	}

	private void resetBtn() {
		// TODO Auto-generated method stub
        ((ImageView)tabIndex.findViewById(R.id.tab_index_icon)).setImageResource(R.drawable.index); 
    	((ImageView)tabWater.findViewById(R.id.tab_water_icon)).setImageResource(R.drawable.water);
    	((ImageView)tabAnalyze.findViewById(R.id.tab_analyze_icon)).setImageResource(R.drawable.analyze);  
    	((ImageView)tabPlatform.findViewById(R.id.tab_platform_icon)).setImageResource(R.drawable.platform);  
		((ImageView)tabMore.findViewById(R.id.tab_more_icon)).setImageResource(R.drawable.more);
		((TextView)tabIndex.findViewById(R.id.tab_index_text)).setTextColor(getResources().getColor(R.color.tab_text));
		((TextView)tabWater.findViewById(R.id.tab_water_text)).setTextColor(getResources().getColor(R.color.tab_text));
		((TextView)tabAnalyze.findViewById(R.id.tab_analyze_text)).setTextColor(getResources().getColor(R.color.tab_text));
		((TextView)tabPlatform.findViewById(R.id.tab_platform_text)).setTextColor(getResources().getColor(R.color.tab_text));
		((TextView)tabMore.findViewById(R.id.tab_more_text)).setTextColor(getResources().getColor(R.color.tab_text));
	}

	private void initViews() {
		// TODO Auto-generated method stub
		tabIndex = (LinearLayout) findViewById(R.id.tab_index);
		tabWater = (LinearLayout) findViewById(R.id.tab_water);
		tabAnalyze = (LinearLayout) findViewById(R.id.tab_analyze);
		tabPlatform = (LinearLayout) findViewById(R.id.tab_platform);
		tabMore = (LinearLayout) findViewById(R.id.tab_more);
		
		newItem = (Button) findViewById(R.id.new_item);
		
		title = (TextView) findViewById(R.id.main_tab_banner_title);		

        mLeftMenu=(SlidingMenu) findViewById(R.id.id_menu);
        login=(TextView) findViewById(R.id.login);   
        backupTextView=(TextView) findViewById(R.id.backup);
        checkTextView=(TextView) findViewById(R.id.check_update);
        adviceTextView=(TextView) findViewById(R.id.advice);
        aboutTextView=(TextView) findViewById(R.id.about);
        securityTextView=(TextView) findViewById(R.id.security);
        shareTextView=(TextView) findViewById(R.id.share);
        
		
		tabIndex.setOnClickListener(this);
		tabWater.setOnClickListener(this);
		tabAnalyze.setOnClickListener(this);
		tabPlatform.setOnClickListener(this);
		tabMore.setOnClickListener(this);
		newItem.setOnClickListener(this);
		login.setOnClickListener(this);
		backupTextView.setOnClickListener(this);
		checkTextView.setOnClickListener(this);
		adviceTextView.setOnClickListener(this);
		aboutTextView.setOnClickListener(this);
		securityTextView.setOnClickListener(this);
		shareTextView.setOnClickListener(this);
		
		SharedPreferences preferences=getSharedPreferences("user", MODE_PRIVATE);
		boolean isLogined = preferences.getBoolean("isLogined", false);
		if(isLogined){
			login.setText(preferences.getString("account", "出错啦"));
		}else{
			login.setText("点击登录");
		}
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())  
        {  
        case R.id.tab_index:{
            setTabSelection(0);  
            break;       	
        }
        case R.id.tab_water:{
            setTabSelection(1);  
            break;        	
        } 
        case R.id.tab_analyze:{
        	setTabSelection(2);  
            break;        	
        }             
        case R.id.tab_platform:{
        	setTabSelection(3);  
            break;
        }             
        case R.id.tab_more:{
        	setTabSelection(4);  
            break;        	
        }                         
        case R.id.new_item:{
        	Intent intent=new Intent(this,NewItemActivity.class);
            startActivity(intent);
            break;        	
        }        	
        case R.id.login:{
        	if(login.getText().toString().equals("点击登录")){
            	Intent intent2=new Intent(this,LoginActivity.class);
                startActivity(intent2);
        	}else{
        		Intent intent=new Intent(this,UserActivity.class);
                startActivity(intent);
        	}
            break;
        }
        case R.id.backup:{
        	final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        	pDialog.setTitleText("正在备份中...");
        	pDialog.show();
            pDialog.setCancelable(false);
            new CountDownTimer(800 * 7, 800) {
                public void onTick(long millisUntilFinished) {
                    // you can change the progress bar color by ProgressHelper every 800 millis
                	iCount++;
                    switch (iCount){
                        case 0:
                            pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.blue_btn_bg_color));
                            break;
                        case 1:
                            pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_50));
                            break;
                        case 2:
                            pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
                            break;
                        case 3:
                            pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_20));
                            break;
                        case 4:
                            pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_blue_grey_80));
                            break;
                        case 5:
                            pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.warning_stroke_color));
                            break;
                        case 6:
                            pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
                            break;
                    }
                }
                public void onFinish() {
                	iCount = -1;
                    pDialog.setTitleText("备份完成!")
                            .setConfirmText("确定")
                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                }
            }.start();
            break;
        }
        case R.id.check_update:{
        	final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        	pDialog.setTitleText("检查更新...");
        	pDialog.show();
            pDialog.setCancelable(false);
            new CountDownTimer(800 * 2, 800) {
                public void onTick(long millisUntilFinished) {
                    // you can change the progress bar color by ProgressHelper every 800 millis
                	iCount++;
                    switch (iCount){
                        case 0:
                            pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.blue_btn_bg_color));
                            break;
                        case 1:
                            pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
                            break;
                    }
                }
                public void onFinish() {
                	iCount = -1;
                    pDialog.setTitleText("当前已为最新版本!")
                            .setConfirmText("确定")
                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                }
            }.start();
        	break;
        }
        case R.id.advice:{
        	Intent intent=new Intent(this,AdviceActivity.class);
            startActivity(intent);
        	break;
        }
        case R.id.about:{
        	Intent intent=new Intent(this,AboutActivity.class);
            startActivity(intent);
        	break;
        }
        case R.id.security:{
        	new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
	            .setTitleText("功能还在开发中")
	            .setContentText("敬请期待  ∩_∩")
                .setConfirmText("确定")
	            .setCustomImage(R.drawable.logo)
	            .show();
        	break;
        }
        case R.id.share:{
        	new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
	            .setTitleText("功能还在开发中")
	            .setContentText("敬请期待  ∩_∩")
	            .setConfirmText("确定")
	            .setCustomImage(R.drawable.logo)
	            .show();
        	break;
        }
        default:  
            break;  
        }  
	}
}
