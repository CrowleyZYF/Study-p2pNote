package com.crowley.p2pnote.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.PrivateCredentialPermission;

import com.crowley.p2pnote.R;
import com.crowley.p2pnote.db.DBOpenHelper;
import com.crowley.p2pnote.functions.ReturnList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class listAdapter extends SimpleAdapter{
	
	private LayoutInflater inflater;
	private List<Map<String, Object>> listItems;
	private String[] from;
	private int[] to;
	private Context context;
	private ReturnList returnList;

	public listAdapter(Context context, List<Map<String, Object>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		this.context=context;
		this.listItems=data;
		this.from=from;
		this.to=to;
		// TODO Auto-generated constructor stub
		this.returnList=new ReturnList(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View localView = convertView ;
		if(convertView==null){
			this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.index_listview_item, null);
			localView = super.getView(position, convertView, parent);			
		}else{
			localView = super.getView(position, convertView, parent);
		}
		String tempString=((TextView)convertView.findViewById(R.id.timeEnd)).getText().toString();
		String[] time=tempString.split(" ");
		if(returnList.parseDay(time[1])<returnList.daysNumber()){
			((ImageView)convertView.findViewById(R.id.circle)).setImageResource(R.drawable.red_circle);
		}else{
			((ImageView)convertView.findViewById(R.id.circle)).setImageResource(R.drawable.white_circle);
		}
		String nameString = ((TextView)convertView.findViewById(R.id.item_name)).getText().toString();
		String[] strarray=nameString.split("-");
		boolean findColor=false;
		for(int i=0;i<DBOpenHelper.PLATFORM_NAMES.length-1&&findColor==false;i++){
			if((strarray[0]).equals(this.context.getResources().getString(DBOpenHelper.PLATFORM_NAMES[i]))){
				((TextView)convertView.findViewById(R.id.item_name)).setTextColor(this.context.getResources().getColor(DBOpenHelper.COLOR[i]));
				findColor=true;
			}			
		}
		if (!findColor) {
			((TextView)convertView.findViewById(R.id.item_name)).setTextColor(this.context.getResources().getColor(DBOpenHelper.COLOR[DBOpenHelper.PLATFORM_NAMES.length-1]));
			findColor=true;
		}
		return localView;
	}

}
