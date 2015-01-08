package com.crowley.p2pnote.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.PrivateCredentialPermission;

import com.crowley.p2pnote.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class recordAdapter extends SimpleAdapter{
	
	public static final int[] PLATFORM_TYPE = {
		R.string.platform_in,
		R.string.platform_out
		};
	
	public static final int[] PLATFORM_SIGNAL = {
		R.string.add,
		R.string.minus
		};
	
	public static final int[] COLOR = {
		R.color.platform_in,
		R.color.platform_out
		};
	
	private LayoutInflater inflater;
	private List<Map<String, Object>> listItems;
	private String[] from;
	private int[] to;
	private Context context;

	public recordAdapter(Context context, List<Map<String, Object>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		this.context=context;
		this.listItems=data;
		this.from=from;
		this.to=to;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View localView = convertView ;
		if(convertView==null){
			this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.record_listview_item, null);
			localView = super.getView(position, convertView, parent);			
		}else{
			localView = super.getView(position, convertView, parent);
		}
		String nameString = ((TextView)convertView.findViewById(R.id.record_type)).getText().toString();
		for(int i=0;i<2;i++){
			if(nameString.equals(this.context.getResources().getString(PLATFORM_TYPE[i]))){
				((TextView)convertView.findViewById(R.id.record_money)).setTextColor(this.context.getResources().getColor(COLOR[i]));				
				((TextView)convertView.findViewById(R.id.record_signal)).setText(PLATFORM_SIGNAL[i]);
				((TextView)convertView.findViewById(R.id.record_signal)).setTextColor(this.context.getResources().getColor(COLOR[i]));
			}			
		}
		return localView;
	}

}
