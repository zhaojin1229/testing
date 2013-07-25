package com.massiveinfinity.slidingmenu.fragment;

import com.massiveinfinity.slidingmenu.R;
import com.massiveinfinity.slidingmenu.SlidingActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class NdpeepsFragment extends Fragment {
    String text = null;
//    ProgressDialog progress;
    Dialog dialog;
    
    SlidingActivity ma =new SlidingActivity();
    static String homepageurl = "https://m.facebook.com/ndpeep";
    private Button showRight;
    public NdpeepsFragment() {
    }

    public NdpeepsFragment(String text) {

        this.text = text;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }
    
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	 
        //inflater the layout 
        View view = inflater.inflate(R.layout.ndpeepsweb_page, null);
      
	final WebView wv=(WebView)view.findViewById(R.id.ndpeepswebView);
	ImageView bck = (ImageView)view.findViewById(R.id.webview_back_btn);
	ImageView fwd = (ImageView)view.findViewById(R.id.webview_fwd_btn);
	ImageView rld = (ImageView)view.findViewById(R.id.webview_rld_btn);

	wv.getSettings().setDomStorageEnabled(true);
	wv.getSettings().setBuiltInZoomControls(true);
	wv.getSettings().setLoadsImagesAutomatically(true);
	wv.getSettings().setUserAgentString("Mozilla/5.0 (Linux; U; Android 2.0; en-us; Droid Build/ESD20) AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Mobile Safari/530.17");
	wv.getSettings().setJavaScriptEnabled(true);
	//final String Url=extras.getString("url");
	wv.setWebViewClient(new MyWebViewClient());
//	dialog = new Dialog(getActivity(),R.style.new_circle_progress); 
//	dialog.setCancelable(false);
//    dialog.setContentView(R.layout.layout_progressbar); 
////	progress = new ProgressDialog(getActivity());
////    progress.setCancelable(false);
////    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
////    dialog = ProgressDialog.show(getActivity(), "", 
////            "", true);
//	final Handler handle=new Handler(){
//		public void handleMessage(Message msg)
//		{
//			dialog.dismiss();
//		}
//	};
//	new Thread(){
//		public void run()
//		{   
//			wv.loadUrl(homepageurl);
//			handle.sendEmptyMessage(0);	
//		}
//	}.start();
	
    dialog = new Dialog(getActivity(),R.style.new_circle_progress);
    dialog.setCancelable(false);
    dialog.setContentView(R.layout.layout_progressbar);
    if(SlidingActivity.width>720&&SlidingActivity.screenInches>6)
	{
    	homepageurl = "https://www.facebook.com/ndpeep";
	}
	else
	{
		homepageurl = "https://m.facebook.com/ndpeep";
	}
    final Handler handler=new Handler(){
		 public void handleMessage(Message msg) {
    			dialog.dismiss();
  		
	       	// Toast.makeText(getApplicationContext(), "kjn", Toast.LENGTH_SHORT);
	       	     }
	       	  };
	       	  new Thread(){
	       		  public void run()
	       		  {
	       			  wv.loadUrl(homepageurl);
	       			  handler.sendEmptyMessage(0);
	       		  }
	       	  }.start();
	 		 showRight = (Button) view.findViewById(R.id.showRight);
		        showRight.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						((SlidingActivity) getActivity()).showRight();
					}
				});
		        
bck.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(wv.canGoBack())
							wv.goBack();
					}
				});
				
fwd.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(wv.canGoForward())
			wv.goForward();
	}
});
			
rld.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		wv.reload();
	}
});
				

	return view;
    }
	private class MyWebViewClient extends WebViewClient  
	{
		@Override
		public void onPageStarted(WebView web,String url, Bitmap favicon)
		{
			dialog.show();
		}
		@Override
		public void onPageFinished(WebView web,String url)
		{
			dialog.dismiss();
		}
	@Override
	public boolean shouldOverrideUrlLoading(WebView web,String url)
	{
		web.loadUrl(url);
		return true;
		
	}
	}
    


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//    	 
//        //inflater the layout 
//        View view = inflater.inflate(R.layout.ndpeepsweb_page, null);
//        final WebView webView = (WebView)view.findViewById(R.id.ndpeepswebView); 
//		view.requestFocus();
//		ImageView bck = (ImageView)view.findViewById(R.id.webview_back_btn);
//		ImageView fwd = (ImageView)view.findViewById(R.id.webview_fwd_btn);
//		ImageView rld = (ImageView)view.findViewById(R.id.webview_rld_btn);
//		WebSettings settings = webView.getSettings();
//		settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
//		webView.setWebViewClient(new myClient());
//		webView.getSettings().setJavaScriptEnabled(true);
//		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);	
//		webView.getSettings().setDomStorageEnabled(true);
//		webView.getSettings().setLoadsImagesAutomatically(true);
//		webView.getSettings().setDomStorageEnabled(true);
//		webView.getSettings().setBuiltInZoomControls(true);
//		webView.getSettings().setLoadsImagesAutomatically(true);
//		webView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; U; Android 2.0; en-us; Droid Build/ESD20) AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Mobile Safari/530.17");
//		webView.getSettings().setJavaScriptEnabled(true);
//		webView.getSettings().setLoadWithOverviewMode(true);
//		webView.getSettings().setAllowFileAccess(true);
//		webView.getSettings().setPluginsEnabled(true);
//		webView.getSettings().setUseWideViewPort(true);
//		dialog = new Dialog(getActivity(),R.style.new_circle_progress); 
//		dialog.setCancelable(false);
//        dialog.setContentView(R.layout.layout_progressbar); 
////		progress = new ProgressDialog(getActivity());
////        progress.setCancelable(false);
////        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
////        dialog = ProgressDialog.show(getActivity(), "", 
////                "", true);
//		final Handler handle=new Handler(){
//			public void handleMessage(Message msg)
//			{
//				dialog.dismiss();
//			}
//		};
//		new Thread(){
//			public void run()
//			{   
//				webView.loadUrl(homepageurl);		
//			}
//					}.start();
//					
//		 showRight = (Button) view.findViewById(R.id.showRight);
//				        showRight.setOnClickListener(new OnClickListener() {
//
//							@Override
//							public void onClick(View v) {
//								((SlidingActivity) getActivity()).showRight();
//							}
//						});
//				        
// bck.setOnClickListener(new View.OnClickListener() {
//							
//							@Override
//							public void onClick(View v) {
//								// TODO Auto-generated method stub
//								if(webView.canGoBack())
//									webView.goBack();
//							}
//						});
//						
//		fwd.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if(webView.canGoForward())
//					webView.goForward();
//			}
//		});
//					
//		rld.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				webView.reload();
//			}
//		});
//						
//        return view;
//    }

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
    	ma.reco =0;
        super.onStart();
    }

    @Override
    public void onStop() {

        super.onStop();
    }
    
    private class myClient extends WebViewClient
    {
    	 public void onPageStarted(WebView view, String url, Bitmap favicon) {
             Log.d("TAG", url);
             dialog.show();
         }
    	@Override
    	public boolean shouldOverrideUrlLoading(WebView web,String url)
    	{
    		web.loadUrl(url);
    		return true;
    		
    	}
    	@Override 
    	public void onPageFinished(WebView view, String url)
    	{
    		dialog.dismiss();
    	}
    }
    	
    
}
