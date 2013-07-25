package com.massiveinfinity.slidingmenu.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.massiveinfinity.slidingmenu.R;
import com.massiveinfinity.slidingmenu.SlidingActivity;
import com.massiveinfinity.slidingmenu.fragment.RightFragment.RightClickListener;
import com.massiveinfinity.slidingmenu.util.CustomHttpClient;
import com.massiveinfinity.slidingmenu.util.LazyAdapterFAQ;

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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("ValidFragment")
public class FaqFragment extends Fragment {
	 HashMap<String, Object> map = new HashMap<String, Object>();
    String text = null;
    ListView listView;
    Dialog dialog;
    LazyAdapterFAQ faqadapter;
    SlidingActivity ma =new SlidingActivity();
    ArrayList<HashMap<String, Object>> faqlist = new ArrayList<HashMap<String, Object>>();
    private Button showRight;
	
    public FaqFragment() {
    }

    public FaqFragment(String text) {

        this.text = text;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Log.e("Krislq", "onCreate:"+text);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view;
    	if(SlidingActivity.width>720&&SlidingActivity.screenInches>6)
		{
    		view = inflater.inflate(R.layout.faq_placeholder_l, null);
		}
		else
		{
    	    view = inflater.inflate(R.layout.faq_placeholder, null);
		}
//    	View view = inflater.inflate(R.layout.faq_placeholder, null);
        listView = (ListView)view.findViewById(R.id.faqlistview);
//        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", 
//                "", true);
        dialog = new Dialog(getActivity(),R.style.new_circle_progress);  
        dialog.setContentView(R.layout.layout_progressbar); 
        dialog.setCancelable(false);
        dialog.show();
//        dialog = ProgressDialog.show(getActivity(), "", 
//                "", true);
        final Handler handler1 = new Handler() {
			   public void handleMessage(Message msg) {
			      dialog.dismiss();
			      faqadapter=new LazyAdapterFAQ(getActivity(),faqlist);
		        listView.setAdapter(faqadapter);
			      }
			   };
			Thread checkUpdate = new Thread() {  
			   public void run() {
				   
				   String url="https://ndp2013api.azure-mobile.net/tables/Faq";
				   try {
				String   response = CustomHttpClient
							.executeHttpGet(
									url
									);
				   JSONArray json = new JSONArray(response);
					for (int i = 0; i < json.length(); i++) {				
						JSONObject e = json.getJSONObject(i);
						 String Title,ID,image;
						 
							
								Title = (String)e.get("Title");
								ID = e.get("Content").toString();
//								image = (String)e.get("Image");
								
								HashMap<String, Object> map = new HashMap<String, Object>();
								map.put("title",Title);
								map.put("published", ID);
//								map.put("Image", image);
								faqlist.add(map);
								
							
					}
				       handler1.sendEmptyMessage(0);
					
				   }  catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			      }
			   };
			   checkUpdate.start();
//        map.put("title","faq1");
//        map.put("published", "345");
//        faqlist.add(map);
//        map = new HashMap<String, Object>();
//        map.put("title","faq2");
//        map.put("published", "234");
//        faqlist.add(map);
//        map = new HashMap<String, Object>();
//        map.put("title","faq3");
//        map.put("published", "123");
//        faqlist.add(map);
        
        listView.setTextFilterEnabled(true);	
        listView.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {   
				LayoutInflater inflater = getActivity().getLayoutInflater();
//				View layout = inflater.inflate(R.layout.faq_dialog, null);  
				
//        		HashMap<String, Object> o = faqlist.get(position);
//			    t1.setText(o.get("title").toString());
		        Dialog d=getInstanceMyDialog2(position);
				d.getWindow().setBackgroundDrawable(new ColorDrawable(0));
			    d.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
		                WindowManager.LayoutParams.WRAP_CONTENT);
//			    Window mWindow = d.getWindow();   
//                WindowManager.LayoutParams lp = mWindow.getAttributes(); 
//                lp.x=0;
//                lp.y=-120;
//                d.onWindowAttributesChanged(lp);
			    
				d.show();
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
    
    private Dialog getInstanceMyDialog2(int position) {
        final Dialog d= new Dialog(getActivity());
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if(SlidingActivity.width>720&&SlidingActivity.screenInches>6)
		{
        	d.setContentView(R.layout.faq_dialog_l);
		}
		else
		{
            d.setContentView(R.layout.faq_dialog);
		}
        TextView t1,t2;
		t1=(TextView)d.findViewById(R.id.textView1);
		t2=(TextView)d.findViewById(R.id.textView2);
		t1.setText(faqlist.get(position).get("title").toString());
		t2.setText(faqlist.get(position).get("published").toString());
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
