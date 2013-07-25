package com.massiveinfinity.slidingmenu.fragment;

import com.massiveinfinity.slidingmenu.R;
import com.massiveinfinity.slidingmenu.SlidingActivity;

import android.annotation.SuppressLint;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;

@SuppressLint("ValidFragment")
public class WebFragment extends Fragment {
    String text = null;
 //   ProgressDialog progress;
    Dialog dialog;
    SlidingActivity ma =new SlidingActivity();
    private Button showRight;
    final static String homepageurl = "http://www.ndp.org.sg/";
    public WebFragment() {
    }

    public WebFragment(String text) {

        this.text = text;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
    	final View view = inflater.inflate(R.layout.web_page, null);
		final WebView webView = (WebView)view.findViewById(R.id.homewebView); 
		ImageView bck = (ImageView)view.findViewById(R.id.webview_back_btn);
		ImageView fwd = (ImageView)view.findViewById(R.id.webview_fwd_btn);
		ImageView rld = (ImageView)view.findViewById(R.id.webview_rld_btn);
		view.requestFocus();
//		WebSettings settings = webView.getSettings();
//		settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webView.setWebViewClient(new myClient());
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setAllowFileAccess(true);
		webView.getSettings().setPluginsEnabled(true);
		webView.getSettings().setUseWideViewPort(true);
		
//		progress = new ProgressDialog(getActivity());
//        progress.setCancelable(false);
//        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog = new Dialog(getActivity(),R.style.new_circle_progress);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_progressbar);
//        dialog = ProgressDialog.show(getActivity(), "", 
//                "", true);
//        
		final Handler handle=new Handler(){
			public void handleMessage(Message msg)
			{
				dialog.dismiss();
			}
		};
		new Thread(){
			public void run()
			{
				webView.loadUrl(homepageurl);		
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
								if(webView.canGoBack())
									webView.goBack();
							}
						});
						
		fwd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(webView.canGoForward())
					webView.goForward();
			}
		});
					
		rld.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				webView.reload();
			}
		});
		return view;
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
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
    
}
