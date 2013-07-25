package com.massiveinfinity.slidingmenu.fragment;

import com.massiveinfinity.slidingmenu.R;
import com.massiveinfinity.slidingmenu.SlidingActivity;

import android.annotation.SuppressLint;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
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
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.Button;
import android.widget.ImageView;

@SuppressLint("ValidFragment")
public class TicketFragment extends Fragment {
    String text = null;
//    ProgressDialog progress;
    private Button showRight;
    Dialog dialog;
    SlidingActivity ma =new SlidingActivity();
    private final static int PROGRESSFORWAIT = 1000; 
    
    final static String ticketpageurl = "http://ndp.org.sg/ticketing-app/";
    public TicketFragment() {
    }

    public TicketFragment(String text) {

        this.text = text;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
    	final View view = inflater.inflate(R.layout.ticket_page, null);
		final WebView webView = (WebView)view.findViewById(R.id.ticketwebView);
		ImageView bck = (ImageView)view.findViewById(R.id.webview_back_btn);
		ImageView fwd = (ImageView)view.findViewById(R.id.webview_fwd_btn);
		ImageView rld = (ImageView)view.findViewById(R.id.webview_rld_btn);
		view.requestFocus();
		WebSettings settings = webView.getSettings();
		settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
//		webView.loadUrl("http://www.ndp.org.sg/");

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
        


        
		final Handler handle=new Handler(){
			public void handleMessage(Message msg)
			{
				dialog.dismiss();
			}
		};
		new Thread(){
			public void run()
			{
				webView.loadUrl(ticketpageurl);		
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
    	ma.reco=0;
        super.onStart();
    }

    @Override
    public void onStop() {

        super.onStop();
    }
    

    
    
    
}
