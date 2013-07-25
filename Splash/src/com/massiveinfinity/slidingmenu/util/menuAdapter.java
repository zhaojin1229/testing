package com.massiveinfinity.slidingmenu.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import com.massiveinfinity.slidingmenu.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

public class menuAdapter extends BaseAdapter {

	LayoutInflater inflater;
	ArrayList<HashMap<String,Object>> displaylist;
	Activity a;
	ImageLoader loader;
	
	public menuAdapter(Activity a,ArrayList<HashMap<String,Object>> lst)
	{
		this.a=a;
		inflater=(LayoutInflater)a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		displaylist=lst;
		loader=new ImageLoader(a.getApplicationContext());
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return displaylist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi=convertView;
		if(convertView==null)
		{
			vi=inflater.inflate(R.layout.menu_layout_page, null);
		}
		  final HashMap<String, Object> h=displaylist.get(position);
		ImageView v1;
		v1=(ImageView) vi.findViewById(R.id.menuoptions);
		
		v1.setFocusable(false);
//		String d=h.get("Image");
		Bitmap bm = (Bitmap) h.get("Image");;
//		try {
//			bm = getBitmap(d);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}//根据实际情况获得你的Bitmap对象

//		BitmapDrawable bd = new BitmapDrawable(bm);
        v1.setImageBitmap(bm);
//		loader.DisplayImage(h.get("Image"), v1);
		return vi;
	}
	
	 public Bitmap getBitmap(String url) throws IOException 
	    {
	    	URL myImageURL = null;
			try {
				myImageURL = new URL(url);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 		HttpURLConnection connection = (HttpURLConnection)myImageURL .openConnection();
	 		connection.setDoInput(true);
	 		connection.connect();
	 		InputStream input = connection.getInputStream();
	 		Bitmap b=BitmapFactory.decodeStream (input);
	 		return b;
	    }


}
