package com.massiveinfinity.slidingmenu.fragment;

import com.massiveinfinity.slidingmenu.R;
import com.massiveinfinity.slidingmenu.SlidingActivity;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.graphics.Bitmap;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;

@SuppressLint("ValidFragment")
public class NewsDetailsFragment extends Fragment {
    String text = null;
    ProgressDialog progress;
    public String  newsdetailspageurl;
    SlidingActivity ma =new SlidingActivity();
    public NewsDetailsFragment() {
    }

    public NewsDetailsFragment(String text) {

        this.text = text;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	ma.reco=2;
    	final View view = inflater.inflate(R.layout.news_details_page, null);
		final WebView webView = (WebView)view.findViewById(R.id.newsdetailsView);
		  
		view.requestFocus();
		WebSettings settings = webView.getSettings();
		settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webView.setWebViewClient(new myClient());
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.getSettings().setBuiltInZoomControls(true);
		progress=new ProgressDialog(getActivity());
		progress.setTitle("Loading!");
	    progress.setMessage("Please Wait!!");
	    progress.setCancelable(false);
	    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		final Handler handle=new Handler(){
			public void handleMessage(Message msg)
			{
				progress.dismiss();
			}
		};
		new Thread(){
			public void run()
			{
				webView.loadUrl(newsdetailspageurl);		
			}
					}.start();
        return view;
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
             progress.show();
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
    		progress.dismiss();
    	}
    }
    
}
