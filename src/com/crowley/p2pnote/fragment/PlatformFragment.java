package com.crowley.p2pnote.fragment;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import com.crowley.p2pnote.NewItemActivity;
import com.crowley.p2pnote.R;
import com.crowley.p2pnote.RecordActivity;
import com.crowley.p2pnote.R.drawable;
import com.crowley.p2pnote.R.id;
import com.crowley.p2pnote.R.layout;
import com.crowley.p2pnote.R.style;
import com.crowley.p2pnote.db.DBOpenHelper;
import com.crowley.p2pnote.db.RecordModel;
import com.crowley.p2pnote.functions.Common;
import com.crowley.p2pnote.functions.Index;
import com.crowley.p2pnote.functions.Platform;
import com.crowley.p2pnote.ui.HorizontalScrollViewAdapter;
import com.crowley.p2pnote.ui.MyHorizontalScrollView;
import com.crowley.p2pnote.ui.MyHorizontalScrollView.OnItemClickListener;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class PlatformFragment extends Fragment implements OnClickListener,OnTouchListener{
	
	private Platform platform;
	
	private LinearLayout platformLinearLayout;
	private MyHorizontalScrollView mHorizontalScrollView;  
    private HorizontalScrollViewAdapter mAdapter;  
    private LinearLayout recordsLinearLayout; 
    private List<Integer> mDatas;
    private List<String> mDatas2;
    private TextView title;
    private TextView platform_earning;
    private TextView platform_rest;
    private TextView platform_earning_rate;
    private TextView platform_amount;
    private TextView newest_date;
    private TextView newest_judge01;
    private TextView newest_judge02;
    private TextView newest_money;
    private TextView platform_invest;
    private TextView platform_out;
    
    private Dialog dialog;
    private Button cancelButton;
	private Button sureButton;
	private TextView timeEndTextView;
	private TextView productTextView;
	private EditText getOutText;
	private TextView actionTitleTextView;
	private TextView actionNameTextView;
	
	private String statePlatformString;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.platform_fragment, container, false);
		
		initView(view);		
		initData();        
        initEvent();	
		 
		return view;
	}


	public void initEvent() {
		platformLinearLayout.setOnTouchListener(this);
		sureButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        recordsLinearLayout.setOnClickListener(this);
		platform_invest.setOnClickListener(this);
		platform_out.setOnClickListener(this);
		//添加点击回调  
		mHorizontalScrollView.setOnItemClickListener(new OnItemClickListener()  
		{  		
		    @Override  
		    public void onClick(View view, int position)
		    {  
		        ((ImageView)(((RelativeLayout) view).getChildAt(2))).setImageResource(R.drawable.platform_arrow_grey);
		        updatePlatform(((TextView)(((RelativeLayout) view).getChildAt(0))).getText().toString());
		        statePlatformString=((TextView)(((RelativeLayout) view).getChildAt(0))).getText().toString();
		    }  
		});
	}


	public void initData() {
		platform=new Platform(this.getActivity());
		mDatas2=platform.getPlatformsName();
		mDatas=platform.getPlatformsIcon(mDatas2);
		if(!mDatas2.isEmpty()){
			updatePlatform(mDatas2.get(0));
			statePlatformString=mDatas2.get(0);
		}		
		mAdapter = new HorizontalScrollViewAdapter(this.getActivity(), mDatas, mDatas2);
		mHorizontalScrollView.initDatas(mAdapter);
	}


	public void initView(View view) {
		platformLinearLayout=(LinearLayout) view.findViewById(R.id.platform_linearlayout);
		mHorizontalScrollView = (MyHorizontalScrollView) view.findViewById(R.id.scroll_view);
		recordsLinearLayout = (LinearLayout) view.findViewById(R.id.platform_record);
		title = (TextView) this.getActivity().findViewById(R.id.main_tab_banner_title);
		platform_earning = (TextView) view.findViewById(R.id.platform_earning);
		platform_rest = (TextView) view.findViewById(R.id.platform_rest);
		platform_earning_rate = (TextView) view.findViewById(R.id.platform_earning_rate);
		platform_amount = (TextView) view.findViewById(R.id.platform_amount);
		newest_date = (TextView) view.findViewById(R.id.newest_date);
		newest_judge01 = (TextView) view.findViewById(R.id.newest_judge01);
		newest_judge02 = (TextView) view.findViewById(R.id.newest_judge02);
		newest_money = (TextView) view.findViewById(R.id.newest_money);
		platform_invest = (TextView) view.findViewById(R.id.platform_invest);
		platform_out = (TextView) view.findViewById(R.id.platform_out);
		dialog = new Dialog(this.getActivity(), R.style.MyDialog);
        //设置它的ContentView
        dialog.setContentView(R.layout.takeout_dialog);
        timeEndTextView=(TextView) dialog.findViewById(R.id.end_time);
        productTextView=(TextView) dialog.findViewById(R.id.item_name);
        getOutText=(EditText) dialog.findViewById(R.id.get_out);     
        sureButton = (Button) dialog.findViewById(R.id.sure_button);
        cancelButton = (Button) dialog.findViewById(R.id.cancel_button);
        actionTitleTextView = (TextView) dialog.findViewById(R.id.action_title);
        actionNameTextView = (TextView) dialog.findViewById(R.id.action_name);
	}


	public void updatePlatform(String platformString) {
		title.setText(platformString);
		platform_earning.setText(Float.valueOf(platform.getEarningAll(platformString)).toString());
		platform_rest.setText(Float.valueOf(platform.getRest(platformString)).toString());
		platform_earning_rate.setText(Float.valueOf(platform.getEarningRateAll(platformString)).toString());
		platform_amount.setText(Float.valueOf(platform.getAllAmount(platformString)).toString());
		newest_date.setText(platform.getNewestDate(platformString));
		//1表示新增，2表示取出，3表示收益，4表示投资,5代表回款
		switch (platform.getNewestBool(platformString)) {
		case 1:{
			newest_judge01.setText("新增");
			newest_judge02.setText("+");
			newest_money.setTextColor(getResources().getColor(DBOpenHelper.platform_in));
			newest_judge02.setTextColor(getResources().getColor(DBOpenHelper.platform_in));
			break;
		}
		case 2:{
			newest_judge01.setText("取出");
			newest_judge02.setText("-");
			newest_money.setTextColor(getResources().getColor(DBOpenHelper.platform_out));
			newest_judge02.setTextColor(getResources().getColor(DBOpenHelper.platform_out));
			break;
		}
		case 3:{
			newest_judge01.setText("收益");
			newest_judge02.setText("+");
			newest_money.setTextColor(getResources().getColor(DBOpenHelper.platform_in));
			newest_judge02.setTextColor(getResources().getColor(DBOpenHelper.platform_in));
			break;
		}
		case 4:{
			newest_judge01.setText("投资");
			newest_judge02.setText("-");
			newest_money.setTextColor(getResources().getColor(DBOpenHelper.platform_out));
			newest_judge02.setTextColor(getResources().getColor(DBOpenHelper.platform_out));
			break;
		}
		case 5:{
			newest_judge01.setText("回款");
			newest_judge02.setText("+");
			newest_money.setTextColor(getResources().getColor(DBOpenHelper.platform_in));
			newest_judge02.setTextColor(getResources().getColor(DBOpenHelper.platform_in));
			break;
		}
		default:
			break;
		}
		newest_money.setText(platform.getNewestMoney(platformString));
	}


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.platform_record:{
			Intent intent=new Intent(this.getActivity(),RecordActivity.class);
			intent.putExtra("platform", title.getText());
            startActivity(intent);
            break;			
		}
		case R.id.platform_invest:{
			if((title.getText().toString()).equals("平台")){
				new SweetAlertDialog(this.getActivity(), SweetAlertDialog.ERROR_TYPE)
	                .setTitleText("操作失败")
	                .setContentText("请先创建投资记录")
	                .setConfirmText("确定")
	                .show();
			}else{
				actionNameTextView.setText("充值金额");
				actionTitleTextView.setText("充值金额");
				productTextView.setText(title.getText());
				timeEndTextView.setText(Common.getTime());
				getOutText.setHint("0");
				dialog.show();
			}			
            break;			
		}
		case R.id.platform_out:{
			if((title.getText().toString()).equals("平台")){
				new SweetAlertDialog(this.getActivity(), SweetAlertDialog.ERROR_TYPE)
                .setTitleText("操作失败")
                .setContentText("请先创建投资记录")
                .setConfirmText("确定")
                .show();
			}else{
				actionNameTextView.setText("取出余额");
				actionTitleTextView.setText("取出余额");
				productTextView.setText(title.getText());
				timeEndTextView.setText(Common.getTime());
				getOutText.setHint(platform_rest.getText());
				dialog.show();
			}			
			break;
		}
		case R.id.cancel_button:{
			dialog.dismiss();
			break;
		}
		case R.id.sure_button:{
			//创建记录
			boolean erroredBoolean=false;
			String errorString="";
			if(TextUtils.isEmpty(getOutText.getText())){
				erroredBoolean=true;
				errorString="请输入具体金额";	
			}else if(Float.parseFloat(getOutText.getText().toString())<0&&erroredBoolean==false){
				erroredBoolean=true;
				errorString="金额不可小于0";
			}
			if ((actionNameTextView.getText().toString()).equals("取出余额")&&erroredBoolean==false) {
				if (Float.parseFloat(getOutText.getText().toString())>Float.parseFloat(getOutText.getHint().toString())) {
					erroredBoolean=true;
					errorString="取出金额不可大于现余额";	
				}
			}
			if(erroredBoolean){
				new SweetAlertDialog(this.getActivity(), SweetAlertDialog.ERROR_TYPE)
	                .setTitleText((actionNameTextView.getText().toString())+"失败")
	                .setContentText(errorString)
	                .setConfirmText("确定")
	                .show();
			}else{
				Long tsLong = System.currentTimeMillis();
				String ts = tsLong.toString();
				String userNameString="";
				SharedPreferences preferences=this.getActivity().getSharedPreferences("user", android.content.Context.MODE_PRIVATE);
				boolean isLogined = preferences.getBoolean("isLogined", false);
				if(isLogined){
					userNameString=preferences.getString("account", "出错啦");
				}else{
					userNameString="not_login";
				}
				float take_out=Float.valueOf(getOutText.getText().toString());
				DBOpenHelper helper = new DBOpenHelper(this.getActivity(), "record.db");
				SQLiteDatabase db = helper.getWritableDatabase();
				if ((actionNameTextView.getText().toString()).equals("取出余额")) {
					db.execSQL("insert into rest(platform,name,type,money,timeStamp,userName,createTime) values('"+title.getText()+"','取出操作',2,"+take_out+",'"+ts+"','"+userNameString+"','"+ts+"')");
				}else{
					db.execSQL("insert into rest(platform,name,type,money,timeStamp,userName,createTime) values('"+title.getText()+"','充值操作',1,"+take_out+",'"+ts+"','"+userNameString+"','"+ts+"')");
				}
				db.close();
				helper.close();
				dialog.dismiss();
				getOutText.setText("");
				reflash();
			}			
			break;
		}
		default:
			break;
		}
		
	}
	
	public void reflash(){
		mDatas2=platform.getPlatformsName();
		mDatas=platform.getPlatformsIcon(mDatas2);
		if(!mDatas2.isEmpty()){
			updatePlatform(statePlatformString);
		}
		mAdapter=new HorizontalScrollViewAdapter(this.getActivity(), mDatas, mDatas2);
		mHorizontalScrollView.updateDate(mAdapter,statePlatformString);
	}


	@Override
	public boolean onTouch(View v, MotionEvent ev) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.platform_linearlayout:
			mHorizontalScrollView.onTouchEvent(ev);
			break;
		default:
			break;
		}		
		return true;
	}  

	

}
