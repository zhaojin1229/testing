package com.massiveinfinity.slidingmenu.util;


	import java.util.ArrayList;
import java.util.HashMap;





	import android.app.Activity;
import android.content.Context;

import com.massiveinfinity.slidingmenu.R;
import com.massiveinfinity.slidingmenu.SlidingActivity;
import com.massiveinfinity.slidingmenu.fragment.PhotoFragment;
import com.massiveinfinity.slidingmenu.util.ImageLoader;
	
	import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
	
import android.widget.ImageView;

	public class gridAdapter extends BaseAdapter{
	ArrayList<HashMap<String,Object>>mylist;
		Activity mcontext;
		 private static LayoutInflater inflater=null;
//		public static com.massiveinfinity.slidingmenu.util.ImageLoader imageLoader;
		public gridAdapter(Activity some,ArrayList<HashMap<String,Object>>mylist)
		{
			this.mylist=mylist;
			 mcontext = some;
//			   imageLoader = new ImageLoader(some.getApplicationContext());
			 PhotoFragment.i= new ImageLoader(some.getApplicationContext());
			   inflater = (LayoutInflater)some.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mylist.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			View view=convertView;
			if(convertView==null)
			{
				if(SlidingActivity.width>720&&SlidingActivity.screenInches>6)
				{
					view=inflater.inflate(R.layout.photogridsingle_l, null);
				}
				else
					view=inflater.inflate(R.layout.photogridsingle, null);
				
			}
			ImageView image=(ImageView) view.findViewById(R.id.singlephotogrid);
			  final HashMap<String, Object> h=mylist.get(position);
				
			  PhotoFragment.i.DisplayImage(h.get("image").toString(), image);
			    		
			    	
			    	
			        
			        convertView=null;
			
			return view;
		}
			
//			ImageView imageView;
//			  
//	     //   final HashMap<String, Object> h=mylist.get(position);
//	        if (convertView == null) {  // if it's not recycled, initialize some attributes
//	            imageView = new ImageView(mcontext);
//	            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
//	            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//	            imageView.setPadding(8, 8, 8, 8);
//	        } else {
//	            imageView = (ImageView) convertView;
//	        }
//	    	       imageView.setImageResource(images[position]);
//	        return imageView;
//	    }

		
	//	private Integer[] images={R.drawable.arrow,R.drawable.arrow1,R.drawable.cell_bg,R.drawable.cross_btn,R.drawable.fail,R.drawable.ic_launcher,R.drawable.icon_lan,R.drawable.logo_btn,R.drawable.theme_btn};

	}


