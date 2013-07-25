package com.massiveinfinity.slidingmenu.util;


import java.util.ArrayList;
import java.util.HashMap;

import com.massiveinfinity.slidingmenu.R;
import com.massiveinfinity.slidingmenu.SlidingActivity;
import com.massiveinfinity.slidingmenu.fragment.NewsDetailsFragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;


import android.view.LayoutInflater;

import android.view.MotionEvent;
import android.view.View;

import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import android.widget.RelativeLayout;
import android.widget.TextView;

public class LazyAdapterFAQ extends BaseAdapter {

    public static Activity activity;
    private String[] data;
    int number;
   
    private static LayoutInflater inflater=null;
    public String[] des;
    public String[] profile;
    public String[] views1;
    public String[] people;
    public ImageLoader imageLoader; 
    public int passtoprofile;
    public ArrayList<HashMap<String, Object>> menuItems;
    public static int count=1;
   // Animation animation,animation3,animation2,animation4,animation5,animation6,animation7,animation8;
    
   
    public LazyAdapterFAQ(Activity a, ArrayList<HashMap<String,Object>> a1) {
 
  	  activity = a;
  	  menuItems=a1;
      inflater = (LayoutInflater)a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      imageLoader=new ImageLoader(a.getApplicationContext());
    }
//      imageLoader3=new ImageLoader3(activity.getApplicationContext());  }
    
    public LazyAdapterFAQ(Activity a, String[] d) {
    	activity=a;
        people=d;
        inflater = (LayoutInflater)a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(a.getApplicationContext());
//        imageLoader3=new ImageLoader3(activity.getApplicationContext());
    }

    public int getCount() {
    	if(data!=null)
            return data.length;
        	else
        	return menuItems.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }
        
   
	@SuppressWarnings({ "static-access", "unchecked", "deprecation" })
	public View getView(final int position, View convertView, ViewGroup parent) {
//    	imageLoader.clearCache();
        View vi=convertView;
        if(convertView==null)
        	if(SlidingActivity.width>720&&SlidingActivity.screenInches>6)
    		{
        		vi = inflater.inflate(R.layout.faqrows_l,null);
    		}
    		else
    		{
    			vi = inflater.inflate(R.layout.faqrows,null);
    		}
        final HashMap<String, Object> h=menuItems.get(position);
        TextView t1,t2; 
      
        t1=(TextView)vi.findViewById(R.id.ndp);
//        t2=(TextView)vi.findViewById(R.id.date);
       
        
        t1.setText((String)h.get("title"));
//        t2.setText((String)h.get("published"));
          
		
	
        return vi;
        
    }
	
	
}










