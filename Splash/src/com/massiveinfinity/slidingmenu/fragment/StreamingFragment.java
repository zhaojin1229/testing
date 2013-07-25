package com.massiveinfinity.slidingmenu.fragment;

import com.massiveinfinity.slidingmenu.R;
import com.massiveinfinity.slidingmenu.SlidingActivity;
import com.massiveinfinity.slidingmenu.fragment.AboutFragment.AboutClickListener;

import android.annotation.SuppressLint;
import android.app.Activity;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class StreamingFragment extends Fragment {
    String text = null;
    SlidingActivity ma =new SlidingActivity();
    private Button showRight;
    InfoClickListener mInfoClickListener;
    public View view;
    public interface InfoClickListener {
		public void navTocompany(String url);
	}
	
	@Override
	public void onAttach(Activity activity) {
		mInfoClickListener = (InfoClickListener) activity;
		super.onAttach(activity);
	}
	
    public StreamingFragment() {
    }

    public StreamingFragment(String text) {

        this.text = text;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { 
        
        if(SlidingActivity.width>720&&SlidingActivity.screenInches>6)
		{
        	view = inflater.inflate(R.layout.info_page_l, null);
		}
		else
		{
			view = inflater.inflate(R.layout.info_page, null);
		}
        
        ImageView im1,im2;
        im1=(ImageView) view.findViewById(R.id.mibutton);
        im2=(ImageView) view.findViewById(R.id.qybutton);
        im1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	String s = "http://www.local.com.sg/";
            	mInfoClickListener.navTocompany(s);
          	  }
         });
        im2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	String s = "http://massiveinfinity.com/";
            	mInfoClickListener.navTocompany(s);
          	  }
         });
        
        showRight = (Button) view.findViewById(R.id.showRight);
        showRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((SlidingActivity) getActivity()).showRight();
			}
		});
        
        return view;
    }

    @Override
   	public void onDestroy() {
           super.onDestroy();
//           PhotoFragment.i.clearCache();
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
    	ma.reco=0;
        super.onStart();
    }

    @Override
    public void onStop() {

        super.onStop();
    }
    
}
