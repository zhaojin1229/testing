package com.massiveinfinity.slidingmenu.util;


import java.util.List;


import android.view.LayoutInflater; 

import com.massiveinfinity.slidingmenu.R;
import com.massiveinfinity.slidingmenu.util.NotificationDTO;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class adapters extends BaseAdapter {

	Activity a;
	LayoutInflater inflater=null;
	
	List<NotificationDTO> lst;
	
	public adapters()
	{
		
	}
	public adapters(Activity a,List<NotificationDTO> lst)
	{
		this.a=a;
		this.lst=lst;
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lst.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi=convertView;
		TextView t1,t2;
		if(convertView==null)
		{
			 vi = inflater.inflate(R.layout.notification_row_list, parent,false);
		}
		t1=(TextView) vi.findViewById(R.id.title);
		if(lst.size()>0)
		{
		NotificationDTO lk=lst.get(position);
			//	t2=(TextView) vi.findViewById(R.id.dt);
		
		//t2.setText(lk.getDt());

	if(lk.isFlag()==true)
		{
		t1.setText(lk.getMessage());
		}else
		{
			t1.setText(lk.getMessage());
			t1.setTypeface(null,Typeface.BOLD_ITALIC);
		}
		}
		else
		{
			t1.setText("No notifications received");
		}
		return vi;
	}

}
