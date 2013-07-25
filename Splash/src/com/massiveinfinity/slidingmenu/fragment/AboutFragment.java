package com.massiveinfinity.slidingmenu.fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import com.massiveinfinity.slidingmenu.R;
import com.massiveinfinity.slidingmenu.SlidingActivity;
import com.massiveinfinity.slidingmenu.fragment.RightFragment.RightClickListener;
import com.massiveinfinity.slidingmenu.util.CustomHttpClient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class AboutFragment extends Fragment {
    String text = null;
    Dialog dialog;
    String thememessage;
    SlidingActivity ma =new SlidingActivity();
    AboutClickListener mAboutClickListener;
    String displaymessasge;
    private Button showRight;
    public View view;
    public AboutFragment() {
    }

    public AboutFragment(String text) {
        this.text = text;
    }
    
    public interface AboutClickListener {
		public void navTologo(int num);
	}
	
	@Override
	public void onAttach(Activity activity) {
		mAboutClickListener = (AboutClickListener) activity;
		super.onAttach(activity);
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflater the layout
    	
    	if(SlidingActivity.width>720&&SlidingActivity.screenInches>6)
		{
    		view = inflater.inflate(R.layout.about_scoll_page_l, null);
		}
		else
    	    view = inflater.inflate(R.layout.about_scoll_page, null);

//    	   View view = inflater.inflate(R.layout.about_scoll_page, null);
  	       final TextView themetext=(TextView) view.findViewById(R.id.themetext);
    	   final TextView textview=(TextView) view.findViewById(R.id.about_scroll);
           final ScrollView scroll1=(ScrollView) view.findViewById(R.id.ScrollView1);
//           final TextView textview=(TextView) view.findViewById(R.id.about_scroll);

           showRight = (Button) view.findViewById(R.id.showRight);
           showRight.setOnClickListener(new OnClickListener() {
           	
   			@Override
   			public void onClick(View v) {
   				((SlidingActivity) getActivity()).showRight();
   			}
   		});
           try
           {
        	   dialog = new Dialog(getActivity(),R.style.new_circle_progress);  
       		   dialog.setCancelable(false);
               dialog.setContentView(R.layout.layout_progressbar); 
   		        final Handler handler1 = new Handler() {
   					   public void handleMessage(Message msg) {
   					      dialog.dismiss();
   					      
					     themetext.setText(thememessage);	
						 textview.setText(displaymessasge);	
   					      }
   					   };
   						Thread checkUpdate = new Thread() {  
   							   public void run() {
   								   
   								   String url="https://ndp2013api.azure-mobile.net/tables/about";							 
   								   try {
   								String response = CustomHttpClient
   											.executeHttpGet(
   													url
   													);
   								   JSONArray json = new JSONArray(response);
   									for (int i = 0; i < json.length(); i++) {				
   										JSONObject e = json.getJSONObject(i);
   								
   										displaymessasge = e.get("logo_text").toString();
   									 thememessage = e.get("theme_text").toString();	
   									}
   								       handler1.sendEmptyMessage(0);
   									
   								   }  catch (Exception e1) {
   									// TODO Auto-generated catch block
   									e1.printStackTrace();
   								}
   							      }
   							   };
   							   checkUpdate.start();

           }
           catch(Exception e)
           {
           	
           }   
           themetext.setOnTouchListener(new View.OnTouchListener() {  
          	   public boolean onTouch(View v, MotionEvent event) {
            	   	 if (event.getAction() == MotionEvent.ACTION_MOVE) {
            	   
            	   	scroll1.requestDisallowInterceptTouchEvent(true);
            	   	   }
            	   	   return true;
            	   }
            	});


           textview.setOnTouchListener(new View.OnTouchListener() {  
          	   public boolean onTouch(View v, MotionEvent event) {
          	   	 if (event.getAction() == MotionEvent.ACTION_MOVE) {
          	   
          	   	scroll1.requestDisallowInterceptTouchEvent(true);
          	   	   }
          	   	   return true;
          	   }
          	});

       
        return view;
    }

    @Override
	public void onDestroy() {
        super.onDestroy();
//        PhotoFragment.i.clearCache();
        unbindDrawables(view.findViewById(R.id.RootView));
        System.gc();
    }

    private void unbindDrawables(View view) {
    	try{
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    	}
    	catch(Exception e)
    	{
    		
    	}
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
    	ma.reco =0;
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
    
}
