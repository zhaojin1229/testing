package com.massiveinfinity.slidingmenu.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.massiveinfinity.slidingmenu.R;
import com.massiveinfinity.slidingmenu.SlidingActivity;
import com.massiveinfinity.slidingmenu.util.CustomHttpClient;
import com.massiveinfinity.slidingmenu.util.ImageLoader;
import com.massiveinfinity.slidingmenu.util.MyListView;
import com.massiveinfinity.slidingmenu.util.NewsLazyAdapter1;
import com.massiveinfinity.slidingmenu.util.MyListView.OnRefreshListener;

import android.annotation.SuppressLint;
import android.app.Dialog;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class NewsFragment extends Fragment {
    String text = null;
    SlidingActivity ma =new SlidingActivity();
    private Button showRight;
//    ProgressDialog progress;
    Dialog dialog;
	int start ;
	int end;
	public ImageLoader i;
//	final ArrayList<HashMap<String, Object>> mylist = new ArrayList<HashMap<String, Object>>();
//	final ArrayList<HashMap<String, Object>> faqlist = new ArrayList<HashMap<String, Object>>();
	String url = "";
	public static ListView listview;
	NewsLazyAdapter1 la;
	ArrayList<HashMap<String, Object>> mainlist = new ArrayList<HashMap<String, Object>>();
	@SuppressWarnings("unused")
	private String returnpath(int position,ArrayList<HashMap<String, Object>>menu)
	{
		HashMap<String,Object>item=menu.get(position);
		return (String) item.get("alternate");
	}
	

    public NewsFragment() {
    }

    public NewsFragment(String text) {

        this.text = text;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	final View view;
    	if(SlidingActivity.width>720&&SlidingActivity.screenInches>6)
		{
    		view = inflater.inflate(R.layout.news_page_l, null);
		}
		else
		{
    	    view = inflater.inflate(R.layout.news_page, null);
		}
//    	final View view = inflater.inflate(R.layout.news_page, null);
    	 showRight = (Button) view.findViewById(R.id.showRight);
	     showRight.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					((SlidingActivity) getActivity()).showRight();
				}
			});
	     
	     try{
    	mainlist = new ArrayList<HashMap<String, Object>>();
    	listview = (ListView)view.findViewById(R.id.mainfunlist);
		 try{
//		     	progress = new ProgressDialog(getActivity());
//		        progress.setCancelable(false);
//		        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//		        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", 
//		                "", true);
			    dialog = new Dialog(getActivity(),R.style.new_circle_progress);  
		        dialog.setContentView(R.layout.layout_progressbar); 
		        dialog.setCancelable(false);
		        dialog.show();
//		        dialog = ProgressDialog.show(getActivity(), "", 
//		                "", true);
		        final Handler handler1 = new Handler() {
					   public void handleMessage(Message msg) {
					      dialog.dismiss();
					      i=new ImageLoader(getActivity());
					      la = new NewsLazyAdapter1(getActivity(),mainlist);
					      i=NewsLazyAdapter1.imageLoader;
					      listview.setAdapter(la);
					      }
					   };
					Thread checkUpdate = new Thread() {  
					   public void run() {
						   
						   String url="https://ndp2013api.azure-mobile.net/tables/news";
						   try {
						String response = CustomHttpClient
									.executeHttpGet(
											url
											);
						   JSONArray json = new JSONArray(response);
							for (int i = 0; i < json.length(); i++) {				
								JSONObject e = json.getJSONObject(i);
								 String Title,image,Url,feat,des,key;
								 

										Title = e.get("Title").toString();
										image = e.get("ImageL").toString();
										des=e.get("Content").toString();

									
										HashMap<String, Object> map = new HashMap<String, Object>();
										map.put("title",Title);
										map.put("image", image);
										map.put("des", des);
										mainlist.add(map);
									
							}
						       handler1.sendEmptyMessage(0);
							
						   }  catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					      }
					   };
					   checkUpdate.start();
			
			 
		  
			  listview.setOnItemClickListener(new OnItemClickListener() {
						@Override
							public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {

							        Dialog d=getInstanceMyDialog2(position);
									d.getWindow().setBackgroundDrawable(new ColorDrawable(0));
								    d.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
							                WindowManager.LayoutParams.MATCH_PARENT);
									d.show();
							}
						});
//			  listview.setonRefreshListener(new OnRefreshListener() {
//					public void onRefresh() {
//						new AsyncTask<Void, Void, Void>() {
//							protected Void doInBackground(Void... params) {
//								try {
//									Thread.sleep(1000);
//								} catch (Exception e) {
//									e.printStackTrace();
//								}
//							data.add("add a new item");
//								return null;
//							}
 
//							@Override
//							protected void onPostExecute(Void result) {
//								la.notifyDataSetChanged();
//								listview.onRefreshComplete();
//							}
//
//						}.execute();
//					}
//				});

		    }
	        catch (Exception e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
			}	}
	     catch(Exception e)
	     {
	    	 Toast.makeText(getActivity().getApplicationContext(), "123213",Toast.LENGTH_LONG);
	     }
	     
		 
		

        return view;
    } 
    
    private Dialog getInstanceMyDialog2(int p) {
        final Dialog d= new Dialog(getActivity());
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if(SlidingActivity.width>720&&SlidingActivity.screenInches>6)
		{
        	d.setContentView(R.layout.dialog_l);
		}
		else
		{
 //   	    view = inflater.inflate(R.layout.about_scoll_page, null);
        d.setContentView(R.layout.dialog);
        
		}
        ImageView pic= (ImageView) d.findViewById(R.id.imageView1);
//        i=new ImageLoader(getActivity());
        String dd= mainlist.get(p).get("image").toString();
//        pic.setScaleType(ImageView.ScaleType.FIT_XY);
        i.DisplayImage(dd,pic);
        TextView title=(TextView) d.findViewById(R.id.textView1);
        TextView des=(TextView) d.findViewById(R.id.textView2);
        des.setMovementMethod(new ScrollingMovementMethod());
        title.setText(mainlist.get(p).get("title").toString());
        des.setText(mainlist.get(p).get("des").toString());
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
