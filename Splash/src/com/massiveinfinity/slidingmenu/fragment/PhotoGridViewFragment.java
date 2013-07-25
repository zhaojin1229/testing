package com.massiveinfinity.slidingmenu.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.massiveinfinity.slidingmenu.R;
import com.massiveinfinity.slidingmenu.SlidingActivity;
import com.massiveinfinity.slidingmenu.util.*;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.widget.AdapterView.OnItemClickListener;


public class PhotoGridViewFragment extends Fragment{
	 public View view;
	 String text = null;
	 public static  ArrayList<HashMap<String, Object>> mylist = new ArrayList<HashMap<String, Object>>();
	 ProgressDialog progress;
	 gridAdapter a;
	 ImageLoader i;
	    SlidingActivity ma =new SlidingActivity();
	    AboutClickListener mAboutClickListener;
	    private Button showRight;
	    
	public PhotoGridViewFragment() {
		
	    }

	    
	    
	    public interface AboutClickListener {
			public void navTologo(int num);
		}
		
	
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setRetainInstance(true);
	    }

	    @SuppressLint("HandlerLeak")
		@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        //inflater the layout 
	    	ma.reco=3;
	    	 view = inflater.inflate(R.layout.photogrid, null);
	    	final TextView text=(TextView) view.findViewById(R.id.photoTextView);
	    	
	    	
//	    	mylist = new ArrayList<HashMap<String, Object>>();
	    	// showRight = (Button) view.findViewById(R.id.showRight);
	    	 final GridView grid = (GridView)view.findViewById(R.id.photoGridView);
	    	 showRight = (Button) view.findViewById(R.id.showRight);
	         showRight.setOnClickListener(new OnClickListener() {

	 			@Override
	 			public void onClick(View v) {
	 				((SlidingActivity) getActivity()).showRight();
	 			}
	 		});
		   		     
	    			
	    	try
			{
					 a=new gridAdapter(getActivity(),mylist);
					 text.setText(mylist.get(0).get("title").toString());

						grid.setAdapter(a);
						grid.setOnItemClickListener(new OnItemClickListener() {
							
						
							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
								// TODO Auto-generated method stub
								
//								        Dialog d=getInstanceMyDialog2(arg2);
//										d.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//									    d.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
//								                WindowManager.LayoutParams.MATCH_PARENT);
//										d.show();
								Intent I=new Intent(getActivity(),Photodetail.class);
								I.putExtra("pos", arg2);
								getActivity().startActivity(I);
							
								
								}
							
						});
			}
			catch(Exception e)
			{
			Toast.makeText(getActivity(), "some problem here", Toast.LENGTH_LONG).show();
			}	        
	    	return view;
	    }
	    private Dialog getInstanceMyDialog2(int pos) {
	        final Dialog d= new Dialog(getActivity());
	        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        if(SlidingActivity.width>720&&SlidingActivity.screenInches>6)
			{
	        	d.setContentView(R.layout.photodialog_l);
			}
			else
			{
				d.setContentView(R.layout.photodialog);
			}
	        ImageView image=(ImageView) d.findViewById(R.id.imageView1);
	        TextView title,des;
	        title=(TextView) d.findViewById(R.id.textView1);
	        des=(TextView) d.findViewById(R.id.textView2);
	        des.setMovementMethod(new ScrollingMovementMethod());
//	        String da=mylist.get(pos).get("title").toString();
	        title.setText(mylist.get(pos).get("title").toString());
	        des.setText(mylist.get(pos).get("des").toString());
	        if(mylist.get(pos).get("album").toString().equals("3"))
	        {
	        	des.setVisibility(View.VISIBLE);
	        }
//	        i=a.imageLoader;
	        PhotoFragment.i.DisplayImage(mylist.get(pos).get("image").toString(), image);
	       ImageView close= (ImageView) d.findViewById(R.id.imageView3);
	       close.setOnClickListener(new OnClickListener() {
	           public void onClick(View v) {
	           d.dismiss();
	         	  }
	        });
	        return d;
	 	 }

//	    @Override
//	    public void onDestroy() {
//
//	        super.onDestroy();
////	        a.imageLoader=new ImageLoader(this);
//	        PhotoFragment.i.clearCache();
//	        System.gc();
//	        
////	        Intent intent = new Intent(getActivity(), SlidingActivity.class);
////	        intent.putExtra("reopen","");
////	        getActivity().startActivity(intent);
//////	        Process.killProcess(Process.myPid());
////	        System.exit(10);
//	    }
	    @Override
		public void onDestroy() {
	        super.onDestroy();
	        PhotoFragment.i.clearCache();
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
	    	ma.reco =3;
	        super.onStart();
	    }

	    @Override
	    public void onStop() {
	        
	        super.onStop();
	    }

}
