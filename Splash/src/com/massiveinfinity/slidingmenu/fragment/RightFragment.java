/*
 * Copyright (C) 2012 yueyueniao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.massiveinfinity.slidingmenu.fragment;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.massiveinfinity.slidingmenu.R;
import com.massiveinfinity.slidingmenu.SlidingActivity;
import com.massiveinfinity.slidingmenu.Splash;
import com.massiveinfinity.slidingmenu.util.CustomHttpClient;
import com.massiveinfinity.slidingmenu.util.JSON;
import com.massiveinfinity.slidingmenu.util.menuAdapter;

public class RightFragment extends Fragment {
	    ListView listView;
	    ViewPageFragment viewPageFragment;
	    private RightClickListener mRightClickListener;
	    Splash sp ;
	    ArrayList<HashMap<String, Object>> menulist = sp.menulist;
//	    public static ArrayList<HashMap<String, Object>> menulist = new ArrayList<HashMap<String, Object>>();
	    
		public interface RightClickListener {
			public void navToPage(String title);
		}
		
		@Override
		public void onAttach(Activity activity) {
			mRightClickListener = (RightClickListener) activity;
			super.onAttach(activity);
		}
		
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.right, null);
		listView = (ListView) view.findViewById(R.id.listView);
//		JSONObject e;
//		String[] item = new String[11];
//	    String response;
//	   if(menulist.size()==0)
//	   {
//		try {
//			response = CustomHttpClient
//					.executeHttpGet(
//							"https://ndp2013api.azure-mobile.net/tables/Menu/"
//							);
//			JSONArray json = new JSONArray(response);
//			for (int i = 0; i < json.length(); i++) {				
//				 e = json.getJSONObject(i);
//				 String Title,ID,image;
//				 
//					try {
//						Title = (String)e.get("Title");
//						ID = e.get("id").toString();
//						image = (String)e.get("Image");
//						Bitmap b=getBitmap(image);
//						if(Title.equals("Map"))
//						{}
//						else
//						{
//						HashMap<String, Object> map = new HashMap<String, Object>();
//						map.put("Title",Title);
//						map.put("ID", ID);
//						map.put("Image", b);
//						menulist.add(map);
//						}
//					} catch (JSONException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//						Title=null;
//					}
//			}
//			
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//	   }
		
		View vi=inflater.inflate(R.layout.right, null);
		ListView lst=(ListView) vi.findViewById(R.id.listView);
		menuAdapter a=new menuAdapter(getActivity(),menulist);
		lst.setAdapter(a);
		
    //  String[] item = new String[] {"News", "About", "Videos", "Web Pages","Tickets","FaQ","Info","tab"};
	//	ArrayAdapter<String> files = new ArrayAdapter<String>(getActivity(), 
    //            android.R.layout.simple_list_item_1,item);     
		 
       // listView.setAdapter(files);
		
		lst.setOnItemClickListener(new OnItemClickListener() 
        {
	         @Override
	         public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
	        	 String ttt =(menulist.get(position)).get("Title").toString();
	        	 mRightClickListener.navToPage(ttt);
	            }
		       });
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
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

}
