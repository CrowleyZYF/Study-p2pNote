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

public class listAdapter extends SimpleAdapter{
	
	private LayoutInflater inflater;
	private List<Map<String, Object>> listItems;
	private String[] from;
	private int[] to;
	private Context context;

	public listAdapter(Context context, List<Map<String, Object>> data,
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
			convertView = inflater.inflate(R.layout.index_listview_item, null);
			String teString=(String) ((TextView)convertView.findViewById(R.id.item_name)).getText();
			localView = super.getView(position, convertView, parent);
			if(((TextView)convertView.findViewById(R.id.item_name)).getText()=="½����-��Ӯ����"){
				((TextView)convertView.findViewById(R.id.item_name)).setTextColor(Color.rgb(233, 84, 4));
			}
		}		
		return localView;
	}

}
