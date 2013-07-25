package com.massiveinfinity.slidingmenu;

import io.vov.vitamio.LibsChecker;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;

import com.massiveinfinity.slidingmenu.util.CustomHttpClient;
import com.massiveinfinity.slidingmenu.util.UncaughtExceptionHandler;

public class Splash extends Activity {

	
  // private static final int splashScreenDelay = 1000;
  private static final int splashScreenDelay = 400;
  ConnectionDetector cd;
  AlertDialogManager alert = new AlertDialogManager();
  Dialog dialog;
//  ProgressDialog pbarDialog; 

  public static ArrayList<HashMap<String, Object>> menulist = new ArrayList<HashMap<String, Object>>();  
  void showMainMenu() {
    Intent i = new Intent();
    i.setClassName(this, "com.massiveinfinity.slidingmenu.SlidingActivity");  //$NON-NLS-1$
    //i.setClassName(this, "sg.edu.rp.ndp.TestLayout");  //$NON-NLS-1$
    startActivity(i);
    finish();
  }
    
  /** Called when the activity is first created. */
  @SuppressLint("NewApi")
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.splash);
    if (!LibsChecker.checkVitamioLibs(this))
	{return;}
    cd = new ConnectionDetector(getApplicationContext());
    dialog = new Dialog(this,R.style.new_circle_progress);  
    dialog.setContentView(R.layout.layout_progressbar); 
    dialog.setCancelable(false);

//
//    pbarDialog = new ProgressDialog(Splash. this ); 
//    pbarDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); 
//    pbarDialog.setCancelable(false);
   

	// Check if Internet present
	if (!cd.isConnectingToInternet()) {
		// Internet Connection is not present
		alert.showAlertDialog(Splash.this,
				"Internet Connection Error",
				"No Internet connection", false);
		// stop executing code by return
		return;
	}

//    Thread.setDefaultUncaughtExceptionHandler( new UncaughtExceptionHandler(this));
//    Thread.setDefaultUncaughtExceptionHandler( new UncaughtExceptionHandler(this));
    if (android.os.Build.VERSION.SDK_INT > 9) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
      }
    dialog.show();
    final Handler handle=new Handler()
    {
    	public void handleMessage(Message msg){
    		finish();
            showMainMenu();
            dialog.dismiss();
           
    	}
    };
    
    Thread splashThread = new Thread() {
      @Override
      public void run() {
        try {
    		
        	JSONObject e;
    		String[] item = new String[11];
    	    String response;
    	   if(menulist.size()==0)
    	   {
    		try {
    			response = CustomHttpClient
    					.executeHttpGet(
    							"https://ndp2013api.azure-mobile.net/tables/MenuSTG/"
    							);
    			JSONArray json = new JSONArray(response);
    			for (int i = 0; i < json.length(); i++) {				
    				 e = json.getJSONObject(i);
    				 String Title,ID,image;
    				 
    					try {
    						Title = (String)e.get("Title");
    						ID = e.get("id").toString();
    						image = (String)e.get("Image");
    						Bitmap b=getBitmap(image);
    						if(Title.equals("Map"))
    						{}
    						else
    						{
    						HashMap<String, Object> map = new HashMap<String, Object>();
    						map.put("Title",Title);
    						map.put("ID", ID);
    						map.put("Image", b);
    						menulist.add(map);
    						}
    					} catch (JSONException e1) {
    						// TODO Auto-generated catch block
    						e1.printStackTrace();
    						Title=null;
    					}
    			}
    			handle.sendEmptyMessage(0);
    		} catch (Exception e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
       
        
        
        
        
        
        
        
//          int waited = 0;
//          while (waited < splashScreenDelay) {
//            sleep(400);
//            waited += 100;
//          }        
        } 
        }
        
        
        
        
        
        
        catch (Exception e) {
          // do nothing
        } 
        
        
        
//        finally {
//          finish();
//          showMainMenu();
//        }
      }
    };
    splashThread.start();
  }
  
  public Bitmap getBitmap(String url) throws IOException 
  {
  	URL myImageURL = null;
		try {
			myImageURL = new URL(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpURLConnection connection = (HttpURLConnection)myImageURL .openConnection();
		connection.setDoInput(true);
		connection.connect();
		InputStream input = connection.getInputStream();
		Bitmap b=BitmapFactory.decodeStream (input);
		return b;
  }
} 