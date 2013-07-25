package com.massiveinfinity.slidingmenu.fragment;


import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.massiveinfinity.slidingmenu.R;
import com.massiveinfinity.slidingmenu.SlidingActivity;
import com.massiveinfinity.slidingmenu.util.CustomHttpClient;
import com.massiveinfinity.slidingmenu.util.ImageLoader;
import com.massiveinfinity.slidingmenu.util.LazyAdapterFAQ;
import com.massiveinfinity.slidingmenu.util.LazyAdapterVideo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
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
public class VideoFragment extends Fragment {
    String text = null;
    ListView listView;
    ImageView feat,play;
    Dialog dialog;
    public ImageLoader imageLoader; 
    TextView title,desc;
    SlidingActivity ma =new SlidingActivity();
    LazyAdapterVideo v;
    ArrayList<HashMap<String, Object>> videolist = new ArrayList<HashMap<String, Object>>();
    ArrayList<HashMap<String, Object>> videolistfeat = new ArrayList<HashMap<String, Object>>();
    private Button showRight;
    public VideoFragment() {
    }

    public VideoFragment(String text) {

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
    		view = inflater.inflate(R.layout.video_placeholder_l, null);
		}
		else
		{
			view = inflater.inflate(R.layout.video_placeholder, null);
		}
        feat=(ImageView) view.findViewById(R.id.imageView1);
        play=(ImageView) view.findViewById(R.id.imageView2);
        title=(TextView) view.findViewById(R.id.textView2);
        desc=(TextView) view.findViewById(R.id.textView3);
//        YouTubePlayerView youTubeView = (YouTubePlayerView) view.findViewById(R.id.youtube_view);
       // youTubeView.initialize(DeveloperKey.DEVELOPER_KEY, this);
        listView = (ListView)view.findViewById(R.id.videolistview);
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("title","Video1");
//        map.put("Url", "http://www.youtube.com/watch?v=0sDLIiBHaW8");
//        map.put("des", "asdasdasdasdasd");
//		map.put("image", "asd");
//		map.put("feat", "0");
//		
//        videolist.add(map);
//        map = new HashMap<String, Object>();
//        map.put("title","Video2");
//        map.put("Url", "http://www.youtube.com/watch?v=ckzkQCrV9YA");
//        map.put("image", "asd");
//        map.put("des", "asdasdasdasdasd");
//		map.put("feat", "0");
//        videolist.add(map);
//        map = new HashMap<String, Object>();
//        map.put("title","Video3");
//        map.put("Url", "http://www.youtube.com/watch?v=s5e9AExLsJ4");
//        map.put("image", "asd");
//        map.put("des", "asdasdasdasdasd");
//		map.put("feat", "0");
//        videolist.add(map);
//        v=new LazyAdapterVideo(getActivity(),videolist);
//        final ProgressDialog progress;
        
        
//        dialog = ProgressDialog.show(getActivity(), "", 
//                "", true);
//        progress = new ProgressDialog(getActivity());
//        progress.setCancelable(false);
//        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    dialog = new Dialog(getActivity(),R.style.new_circle_progress);  
        dialog.setContentView(R.layout.layout_progressbar); 
        dialog.setCancelable(false);
        dialog.show();
        final Handler handler1 = new Handler() {
			   public void handleMessage(Message msg) {
				   dialog.dismiss();
//			      imageLoader=new ImageLoader(getActivity());
			      v=new LazyAdapterVideo(getActivity(),videolist);
		        listView.setAdapter(v);
		        title.setText(videolistfeat.get(0).get("title").toString());
		        desc.setText(videolistfeat.get(0).get("des").toString());
		        imageLoader= LazyAdapterVideo.imageLoader;
		        imageLoader.DisplayImage(videolistfeat.get(0).get("image").toString(), play);
			      }
			   };
			Thread checkUpdate = new Thread() {  
			   public void run() {
				   
				   String url="https://ndp2013api.azure-mobile.net/tables/video1";
				   try {
				String response = CustomHttpClient
							.executeHttpGet(
									url
									);
				   JSONArray json = new JSONArray(response);
					for (int i = 0; i < json.length(); i++) {				
						JSONObject e = json.getJSONObject(i);
						 String Title,image,Url,feat,des,key;
						 
						        Url=e.get("Url").toString();
								Title = e.get("Title").toString();
								image = e.get("image").toString();
								feat=e.get("Featured").toString();
								des=e.get("Description").toString();
								key=e.get("key").toString();
//								image = (String)e.get("Image");
							
								HashMap<String, Object> map = new HashMap<String, Object>();
								map.put("title",Title);
								map.put("image", image);
								map.put("feat", feat);
								map.put("Url", Url);
								map.put("des", des);
								map.put("key", key);
								if(feat.equals("1"))
								{
									videolistfeat.add(map);
								}
								else
								{
								videolist.add(map);
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
      
        listView.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {   
//				LayoutInflater inflater = getActivity().getLayoutInflater();
//				View layout = inflater.inflate(R.layout.movie_dialog, null);  
//				TextView t1;
//				t1=(TextView)layout.findViewById(R.id.textView1);
//        		HashMap<String, Object> o = videolist.get(position);
//			    t1.setText(o.get("title").toString());
		        Dialog d=getInstanceMyDialog2(position);
				d.getWindow().setBackgroundDrawable(new ColorDrawable(0));
			    d.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
		                WindowManager.LayoutParams.MATCH_PARENT);
				d.show();
			}
		});

        feat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Dialog d=getInstanceMyDialog3();
				d.getWindow().setBackgroundDrawable(new ColorDrawable(0));
			    d.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
		                WindowManager.LayoutParams.MATCH_PARENT);
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

    
    private Dialog getInstanceMyDialog2(final int pos) {
        final Dialog d= new Dialog(getActivity());
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if(SlidingActivity.width>720&&SlidingActivity.screenInches>6)
		{
            d.setContentView(R.layout.movie_dialog_l);
		}
		else
		{
	        d.setContentView(R.layout.movie_dialog);
		}
        TextView title=(TextView) d.findViewById(R.id.textView1);
        TextView desc=(TextView) d.findViewById(R.id.textView2);
        desc.setText(videolist.get(pos).get("des").toString());
        desc.setMovementMethod(new ScrollingMovementMethod());
        title.setText(videolist.get(pos).get("title").toString());
        ImageView play1=(ImageView) d.findViewById(R.id.imageView1);
        play1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
         	    Intent i = new Intent();
 				i.setClassName(getActivity(), "com.massiveinfinity.slidingmenu.Movie");
 				i.putExtra("keyid", videolist.get(pos).get("key").toString());
 				startActivity(i);
          	  }
         });
        imageLoader.DisplayImage(videolist.get(pos).get("image").toString(), play1);  
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
        if(SlidingActivity.width>720&&SlidingActivity.screenInches>6)
		{
        	d.setContentView(R.layout.featdialog_l);
		}
		else
		{
			d.setContentView(R.layout.featdialog);
		}
        TextView title=(TextView) d.findViewById(R.id.textView1);
        TextView desc=(TextView) d.findViewById(R.id.textView2);
        desc.setText(videolistfeat.get(0).get("des").toString());
        desc.setMovementMethod(new ScrollingMovementMethod());
        title.setText(videolistfeat.get(0).get("title").toString());
        ImageView play1=(ImageView) d.findViewById(R.id.imageView1);
        imageLoader.DisplayImage(videolistfeat.get(0).get("image").toString(), play1);  
       ImageView close= (ImageView) d.findViewById(R.id.imageView3);
       play1.setOnClickListener(new OnClickListener() {
           public void onClick(View v) {
        	    Intent i = new Intent();
				i.setClassName(getActivity(), "com.massiveinfinity.slidingmenu.Movie");
				i.putExtra("keyid", videolistfeat.get(0).get("key").toString());
				startActivity(i);
         	  }
        });
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
