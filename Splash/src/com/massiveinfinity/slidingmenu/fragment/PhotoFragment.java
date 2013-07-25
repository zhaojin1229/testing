package com.massiveinfinity.slidingmenu.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.massiveinfinity.slidingmenu.R;
import com.massiveinfinity.slidingmenu.SlidingActivity;
import com.massiveinfinity.slidingmenu.fragment.AboutFragment.AboutClickListener;
import com.massiveinfinity.slidingmenu.util.CustomHttpClient;
import com.massiveinfinity.slidingmenu.util.ImageLoader;
import com.massiveinfinity.slidingmenu.util.LazyAdapterFAQ;
import com.massiveinfinity.slidingmenu.util.LazyAdapterPhotos;
import com.massiveinfinity.slidingmenu.util.LazyAdapterVideo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class PhotoFragment extends Fragment {
	 HashMap<String, Object> map = new HashMap<String, Object>();
	    String text = null;
	    ListView listView;
	    Dialog dialog;
	    ImageView feat,featphoto,bac;
	    TextView phototitle,photodes;
	    PhotoClickListener mPhotoClickListener;
	    LazyAdapterPhotos Photoadapter;
	    private Button showRight;
	    public static ImageLoader i;
	    SlidingActivity ma =new SlidingActivity();
	    ArrayList<HashMap<String, Object>> photolist = new ArrayList<HashMap<String, Object>>();
	    ArrayList<HashMap<String, Object>> album1 = new ArrayList<HashMap<String, Object>>();
	    ArrayList<HashMap<String, Object>> album2 = new ArrayList<HashMap<String, Object>>();
	    ArrayList<HashMap<String, Object>> featured = new ArrayList<HashMap<String, Object>>();
	    
	    public interface PhotoClickListener {
			public void navToPhotoGridView(ArrayList<HashMap<String, Object>> url);
		}
		
		@Override
		public void onAttach(Activity activity) {
			mPhotoClickListener = (PhotoClickListener) activity;
			super.onAttach(activity);
		}
		
		
    public PhotoFragment() {
    }

    public PhotoFragment(String text) {

        this.text = text;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { 
    	 View view;
    	if(SlidingActivity.width>720&&SlidingActivity.screenInches>6)
		{
    		view = inflater.inflate(R.layout.event_placeholder_l, null);
		}
		else
    	    view = inflater.inflate(R.layout.event_placeholder, null);
//        View view = inflater.inflate(R.layout.event_placeholder, null);
        i=new ImageLoader(getActivity());
        listView = (ListView)view.findViewById(R.id.eventlistview);
        feat=(ImageView) view.findViewById(R.id.imageView1);
        featphoto=(ImageView) view.findViewById(R.id.imageView2);
        bac= (ImageView)view.findViewById(R.id.imageView1);
        phototitle=(TextView) view.findViewById(R.id.textView2);
        photodes=(TextView) view.findViewById(R.id.textView3);
        dialog = new Dialog(getActivity(),R.style.new_circle_progress);  
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_progressbar); 
        dialog.show();
//        dialog = ProgressDialog.show(getActivity(), "", 
//                "", true);
        final Handler handler1 = new Handler() {
			   public void handleMessage(Message msg) {
			      dialog.dismiss();
			      Photoadapter=new LazyAdapterPhotos(getActivity(),photolist);
			      i=Photoadapter.imageLoader;
			      int ds=Integer.parseInt(album1.get(album1.size()-1).get("album").toString());
			     for(int dss=1;dss<=ds;dss++)
			     {
			    	 album2 = new ArrayList<HashMap<String, Object>>();
			      for(int i=0;i<album1.size();i++)
			      {
			    	  if(album1.get(i).get("album").toString().equals(dss+""))
			    	  {
			    		  album2.add(album1.get(i));
			    	  }
			    	  
			      }
			      photolist.add(album2.get(0));
			     }
//			      photolist.add(album2.get(0));
			     bac.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							mPhotoClickListener.navToPhotoGridView(featured);
						}
					});
			      
			      i.DisplayImage(featured.get(0).get("image").toString(), featphoto);
//			      featphoto.setOnClickListener(new OnClickListener() {
//
//						@Override
//						public void onClick(View v) {
//							mPhotoClickListener.navToPhotoGridView(featured);
//						}
//					});
			      photodes.setText(featured.get(0).get("des1").toString());
			      phototitle.setText(featured.get(0).get("title").toString());
			      
			        listView.setAdapter(Photoadapter);
			      }
			   };
			Thread checkUpdate = new Thread() {  
			   public void run() {
				   
				   String url="https://ndp2013api.azure-mobile.net/tables/Photos?$top=1000";
				   try {
				String response = CustomHttpClient
							.executeHttpGet(
									url
									);
				   JSONArray json = new JSONArray(response);
					for (int i = 0; i < json.length(); i++) {				
						JSONObject e = json.getJSONObject(i);
						 String Title,image,feat,des,album,des1;
						 

								Title = e.get("Title").toString();
								image = e.get("image").toString();
								feat=e.get("Featured").toString();
								des=e.get("Description").toString();
								des1=e.get("Des").toString();
								album=e.get("album").toString();
							
								HashMap<String, Object> map = new HashMap<String, Object>();
								map.put("title",Title);
								map.put("image", image);
								map.put("feat", feat);
								map.put("album", album);
								map.put("des", des);
								map.put("des1", des1);

								
									album1.add(map);
								
								if(feat.equals("1"))
								{
									featured.add(map);
								}
								
							
					}
				       handler1.sendEmptyMessage(0);
					
				   }  catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			      }
			   };
			   checkUpdate.start();
       
        listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
						// TODO Auto-generated method stub
                int position=arg2+1;    
                album2 = new ArrayList<HashMap<String, Object>>();
                for(int i=0;i<album1.size();i++)
			      {
			    	  if(album1.get(i).get("album").toString().equals(position+""))
			    	  {
			    		  album2.add(album1.get(i));
			    	  }
			    	  
			      }
				
				mPhotoClickListener.navToPhotoGridView(album2);
				
			}
        	
        });
        
        showRight = (Button) view.findViewById(R.id.showRight);
        feat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				Dialog d=getInstanceMyDialog3();
//				d.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//			    d.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
//		                WindowManager.LayoutParams.MATCH_PARENT);
//				d.show();
			}
		});
        showRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((SlidingActivity) getActivity()).showRight();
			}
		});
        
        return view;
    }

    
    
    private Dialog getInstanceMyDialog2() {
        final Dialog d= new Dialog(getActivity());
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.dialog);
        
       ImageView close= (ImageView) d.findViewById(R.id.imageView3);
       close.setOnClickListener(new OnClickListener() {
           public void onClick(View v) {
           d.dismiss();
         	  }
        });
        return d;
 	 }
    private Dialog getInstanceMyDialog3() {
        final Dialog d= new Dialog(getActivity());
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.featphoto);
        
       ImageView close= (ImageView) d.findViewById(R.id.imageView3);
       close.setOnClickListener(new OnClickListener() {
           public void onClick(View v) {
           d.dismiss();
         	  }
        });
        return d;
 	 }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {

        super.onDetach();
    }

    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    @Override
    public void onStart() {
    	 ma.reco=0;
        super.onStart();
    }

    @Override
    public void onStop() {

        super.onStop();
    }
    
}
