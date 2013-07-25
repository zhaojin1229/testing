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

public class NewsLazyAdapter extends BaseAdapter {

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
    
   
    public NewsLazyAdapter(Activity a, ArrayList<HashMap<String,Object>> a1) {
 
  	  activity = a;
  	  menuItems=a1;
      inflater = (LayoutInflater)a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      imageLoader=new ImageLoader(a.getApplicationContext());
    }
//      imageLoader3=new ImageLoader3(activity.getApplicationContext());  }
    
    public NewsLazyAdapter(Activity a, String[] d) {
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
        vi = inflater.inflate(R.layout.news_rows, null);
        
        
        final HashMap<String, Object> h=menuItems.get(position);
        TextView t1,t2,t4;
      
        HorizontalScrollView sc=new HorizontalScrollView(activity);
        sc.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT));
        
        RelativeLayout rl=(RelativeLayout)vi.findViewById(R.id.scrollview);   
        rl.removeAllViews();
        rl.addView(sc);
        t1=(TextView)vi.findViewById(R.id.title);
        t2=(TextView)vi.findViewById(R.id.date);
        t4=(TextView)vi.findViewById(R.id.message);
        
        t1.setText((String)h.get("title"));
        t2.setText((String)h.get("published"));
          
        LinearLayout layout =new LinearLayout(activity);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		layout.setLayoutParams(new LayoutParams(android.widget.LinearLayout.LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		sc.addView(layout);
		
		String type=(String) h.get("type");
     if(type.equals("video"))
     {
    	 t4.setText((String)h.get("datavideo"));
    	    ImageView image1=new ImageView(activity);
			image1.setVisibility(vi.VISIBLE);
			image1.setFocusable(false);
			image1.setClickable(true);
			image1.setScaleX(100);
			image1.setScaleY(100);
			image1.setScaleType(ImageView.ScaleType.FIT_XY );
			imageLoader.DisplayImage((String)h.get("url"),image1);
			layout.addView(image1);
			image1.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {

				}
			});
			
     }
     if(type.equals("image"))
     { 	

    		ArrayList<String>lst=(ArrayList<String>)h.get("url");
    		
    		 for(final String n:lst)
    	{
    		ImageView image1=new ImageView(activity);
			image1.setVisibility(vi.VISIBLE);			
			
			image1.setClickable(true);
			
			image1.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
				}
			}); 
    		imageLoader.DisplayImage(n, image1);
    		image1.setPadding(15, 15, 15, 15);
			layout.addView(image1);
    	}
    	  lst=null;
    	}  
        
        convertView=null; 
        return vi;
        
    }
	
	
}










