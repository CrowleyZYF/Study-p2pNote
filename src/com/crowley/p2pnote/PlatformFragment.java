package com.crowley.p2pnote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.crowley.p2pnote.db.DBOpenHelper;
import com.crowley.p2pnote.db.RecordModel;
import com.crowley.p2pnote.functions.ReturnList;
import com.crowley.p2pnote.ui.HorizontalScrollViewAdapter;
import com.crowley.p2pnote.ui.MyHorizontalScrollView;
import com.crowley.p2pnote.ui.MyHorizontalScrollView.OnItemClickListener;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class PlatformFragment extends Fragment implements OnClickListener{
	
	private ReturnList returnList;
	
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
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.platform_fragment, container, false);
		
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
		
		returnList=new ReturnList(this.getActivity());
		mDatas=returnList.getPlatformsIcon();
		mDatas2=returnList.getPlatformsName();
		
		dialog = new Dialog(this.getActivity(), R.style.MyDialog);
        //设置它的ContentView
        dialog.setContentView(R.layout.takeout_dialog);
        timeEndTextView=(TextView) dialog.findViewById(R.id.end_time);
        productTextView=(TextView) dialog.findViewById(R.id.item_name);
        getOutText=(EditText) dialog.findViewById(R.id.get_out);
        
        sureButton = (Button) dialog.findViewById(R.id.sure_button);
        cancelButton = (Button) dialog.findViewById(R.id.cancel_button);
        sureButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
		
		if(!mDatas2.isEmpty()){
			updatePlatform(mDatas2.get(0));
		}	
		
		mAdapter = new HorizontalScrollViewAdapter(this.getActivity(), mDatas, mDatas2);
		
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
		    }  
		});  
		
		mHorizontalScrollView.initDatas(mAdapter); 
		return view;
	}


	public void updatePlatform(String platformString) {
		title.setText(platformString);
		platform_earning.setText(Float.valueOf(returnList.getEarningAll(platformString)).toString());
		platform_rest.setText(Float.valueOf(returnList.getRest(platformString)).toString());
		platform_earning_rate.setText(Float.valueOf(returnList.getEarningRateAll(platformString)).toString());
		platform_amount.setText(Float.valueOf(returnList.getAllAmount(platformString)).toString());
		newest_date.setText(returnList.getNewestDate(platformString));
		if(returnList.getNewestBool(platformString)){
			newest_judge01.setText("收益");
			newest_judge02.setText("+");
			newest_money.setTextColor(getResources().getColor(DBOpenHelper.platform_in));
			newest_judge02.setTextColor(getResources().getColor(DBOpenHelper.platform_in));
		}else{
			newest_judge01.setText("取出");
			newest_judge02.setText("-");
			newest_money.setTextColor(getResources().getColor(DBOpenHelper.platform_out));
			newest_judge02.setTextColor(getResources().getColor(DBOpenHelper.platform_out));
		}
		newest_money.setText(returnList.getNewestMoney(platformString, returnList.getNewestBool(platformString)));
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
			Intent intent=new Intent(this.getActivity(),NewItemActivity.class);
			intent.putExtra("model", "2");
			intent.putExtra("id", "");
			intent.putExtra("platform", title.getText());
            startActivity(intent);
            break;			
		}
		case R.id.platform_out:{
			productTextView.setText(title.getText());
			timeEndTextView.setText(returnList.getTime());
			getOutText.setHint(platform_rest.getText());
			dialog.show();
			break;
		}
		case R.id.cancel_button:{
			dialog.dismiss();
			break;
		}
		case R.id.sure_button:{
			//创建记录
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
			String sqlString="insert into record(platform,type,money,earningMin,earningMax,method,timeBegin,timeEnd,timeStamp,state,isDeleted,userName,restBegin,restEnd,timeStampEnd,rest) values('"+title.getText()+"','"+""+"',"+0+","+0+","+0+","+0+",'"+returnList.getTime()+"','"+returnList.getTime()+"','"+ts+"',0,0,'"+userNameString+"',0.0,0.0,'',"+returnList.getRest((String) title.getText())+")";
			DBOpenHelper helper = new DBOpenHelper(this.getActivity(), "record.db");
			SQLiteDatabase db = helper.getWritableDatabase();
			db.execSQL(sqlString);
			Cursor tempCursor=db.rawQuery("select * from record WHERE timeStamp = "+ts, null);
			tempCursor.moveToFirst();
			RecordModel tempRecordModel=new RecordModel(tempCursor);
			String idString=(Integer.valueOf((tempRecordModel.getID()))).toString();
			float take_out=Float.valueOf(getOutText.getText().toString());
			returnList.dealRecord(idString, 0.0f, take_out);
			dialog.dismiss();
			break;
		}
		default:
			break;
		}
		
	}
	
	public void reflash(){
		mDatas=returnList.getPlatformsIcon();
		mDatas2=returnList.getPlatformsName();
		if(!mDatas2.isEmpty()){
			updatePlatform(mDatas2.get(0));
		}
		mAdapter=new HorizontalScrollViewAdapter(this.getActivity(), mDatas, mDatas2);
		mHorizontalScrollView.updateDate(mAdapter);
	}

	

}
