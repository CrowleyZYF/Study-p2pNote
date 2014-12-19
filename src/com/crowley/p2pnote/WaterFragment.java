package com.crowley.p2pnote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.crowley.p2pnote.db.DBOpenHelper;
import com.crowley.p2pnote.ui.listAdapter;

import android.R.integer;
import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class WaterFragment extends Fragment{
	
	private View view;
	
	private ListView listView;
	private listAdapter list_adapter;
	private List<Map<String, Object>> dataList;
	
	List list = new ArrayList();
	
	public static final int[] PLATFORM_NAMES = {
		R.string.company_name01,
		R.string.company_name02,
		R.string.company_name03,
		R.string.company_name04,
		R.string.company_name05,
		R.string.company_name06,
		R.string.company_name07,
		R.string.company_name08,
		R.string.company_name09
		};
	
	public static final int[] PLATFORM_ICONS = {
		R.drawable.company_icon01,
		R.drawable.company_icon02,
		R.drawable.company_icon03,
		R.drawable.company_icon04,
		R.drawable.company_icon05,
		R.drawable.company_icon06,
		R.drawable.company_icon07,
		R.drawable.company_icon08,
		R.drawable.company_icon09
		};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.water_fragment, container, false);

		dataList=new ArrayList<Map<String,Object>>();
        listView=(ListView) view.findViewById(R.id.water_list_view);
        getData();
        list_adapter=new listAdapter(this.getActivity(), dataList, R.layout.index_listview_item, new String[]{"time","item_icon","item_name","item_money","item_profit"}, new int[]{R.id.time,R.id.item_icon,R.id.item_name,R.id.item_money,R.id.item_profit});
        
               
        listView.setAdapter(list_adapter);
        
		return view;
	}

	private void getData() {
		// TODO Auto-generated method stub
		dataList.clear();
		DBOpenHelper helper = new DBOpenHelper(this.getActivity(), "record.db");
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from record", null);
		if(cursor!=null){
			while (cursor.moveToNext()) {
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("time", cursor.getString(cursor.getColumnIndex("timeEnd")));
				int icon = PLATFORM_ICONS[0];
				for(int i=0;i<9;i++){
					if(cursor.getString(cursor.getColumnIndex("platform")).equals(getResources().getString(PLATFORM_NAMES[i]))){
						icon=PLATFORM_ICONS[i];						
					}
				}
				map.put("item_icon", icon);
				map.put("item_name", cursor.getString(cursor.getColumnIndex("platform"))+"-"+cursor.getString(cursor.getColumnIndex("type")));
				map.put("item_money", cursor.getFloat(cursor.getColumnIndex("money")));
				if (cursor.getFloat(cursor.getColumnIndex("earningMin"))==0.0) {
					map.put("item_profit", (cursor.getFloat(cursor.getColumnIndex("earningMax"))*100)+"%");					
				}else{
					map.put("item_profit", (cursor.getFloat(cursor.getColumnIndex("earningMin"))*100)+"%~"+(cursor.getFloat(cursor.getColumnIndex("earningMax"))*100)+"%");
				}
				dataList.add(map); 
			}
			cursor.close();
		}
		db.close();
    	/*for(int i=0;i<20;i++){
    		Map<String, Object> map=new HashMap<String, Object>();
    		if(i%2==0){
    			map.put("time", "2014-10-1"+i);
        		map.put("item_icon", R.drawable.company_icon02);
        		map.put("item_name", "陆金所-富赢人生");
        		map.put("item_money", "12,000");
        		map.put("item_profit", "12%");
        		list.add("0");
    		}else{    			
        		map.put("time", "2014-08-1"+i);
        		map.put("item_icon", R.drawable.company_icon01);
        		map.put("item_name", "人人贷-优选计划");
        		map.put("item_money", "11,000");
        		map.put("item_profit", "8%");
        		list.add("1");
    		}    		
    		dataList.add(map);    		
    	}*/
	}

}
