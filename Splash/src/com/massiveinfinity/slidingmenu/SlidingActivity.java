
package com.massiveinfinity.slidingmenu;

import static com.massiveinfinity.slidingmenu.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.massiveinfinity.slidingmenu.CommonUtilities.EXTRA_MESSAGE;
import static com.massiveinfinity.slidingmenu.CommonUtilities.SENDER_ID;

import io.vov.vitamio.LibsChecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Process;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.maps.MapFragment;
import com.massiveinfinity.slidingmenu.R;
import com.massiveinfinity.slidingmenu.fragment.AboutFragment;
import com.massiveinfinity.slidingmenu.fragment.CompanyWebFragment;
import com.massiveinfinity.slidingmenu.fragment.EventFragment;

import com.massiveinfinity.slidingmenu.fragment.FaqFragment;
import com.massiveinfinity.slidingmenu.fragment.HomeFragment;
import com.massiveinfinity.slidingmenu.fragment.InfoFragment;
import com.massiveinfinity.slidingmenu.fragment.LeftFragment;
import com.massiveinfinity.slidingmenu.fragment.LogoFragment;
import com.massiveinfinity.slidingmenu.fragment.MapsFragment;
import com.massiveinfinity.slidingmenu.fragment.MediaPlayerDemo_Video;
import com.massiveinfinity.slidingmenu.fragment.NdpeepsFragment;
import com.massiveinfinity.slidingmenu.fragment.NewsFragment;
import com.massiveinfinity.slidingmenu.fragment.PhotoFragment;
import com.massiveinfinity.slidingmenu.fragment.PhotoGridViewFragment;
import com.massiveinfinity.slidingmenu.fragment.RightFragment;
import com.massiveinfinity.slidingmenu.fragment.StreamingFragment;
import com.massiveinfinity.slidingmenu.fragment.ThemeFragment;
import com.massiveinfinity.slidingmenu.fragment.TicketFragment;
import com.massiveinfinity.slidingmenu.fragment.VideoFragment;
import com.massiveinfinity.slidingmenu.fragment.ViewPageFragment;
import com.massiveinfinity.slidingmenu.fragment.WebFragment;
import com.massiveinfinity.slidingmenu.fragment.AboutFragment.AboutClickListener;
import com.massiveinfinity.slidingmenu.fragment.InfoFragment.InfoClickListener;
import com.massiveinfinity.slidingmenu.fragment.PhotoFragment.PhotoClickListener;
import com.massiveinfinity.slidingmenu.fragment.RightFragment.RightClickListener;
import com.massiveinfinity.slidingmenu.fragment.ViewPageFragment.MyPageChangeListener;
import com.massiveinfinity.slidingmenu.util.ImageLoader;
import com.massiveinfinity.slidingmenu.util.NotificationDTO;
import com.massiveinfinity.slidingmenu.util.NotificationGetData;
import com.massiveinfinity.slidingmenu.util.UncaughtExceptionHandler;
import com.massiveinfinity.slidingmenu.util.gridAdapter;
import com.massiveinfinity.slidingmenu.view.SlidingMenu;

public class SlidingActivity extends FragmentActivity implements RightClickListener ,PhotoClickListener, InfoClickListener,AboutClickListener{
	SlidingMenu mSlidingMenu;
	LeftFragment leftFragment;
	RightFragment rightFragment;
	SharedPreferences pref;
	ViewPageFragment viewPageFragment;
	PhotoGridViewFragment pgf;
	gridAdapter a;
	 public static int width;
	 public static double screenInches;
	 public static int reco =0;	
	 AsyncTask<Void, Void, Void> mRegisterTask;
		NotificationGetData insertData;
		
		AlertDialogManager alert = new AlertDialogManager();
		NotificationDTO notificate;
		
		ConnectionDetector cd;
		LayoutInflater inflater;
		
		public static String name;
		public static String email;

	public ViewPageFragment getViewPageFragment (){
		return viewPageFragment;
	}
	
	private static Point getDisplaySize(final Display display) {
		Point point = new Point();
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//			return point;
//		} else {
			point.x = display.getWidth();
			point.y = display.getHeight();
//		}
		return point;

	}
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
//			
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
		.permitAll().build();
        StrictMode.setThreadPolicy(policy);
//        Thread.setDefaultUncaughtExceptionHandler( new UncaughtExceptionHandler(this));
        Display display = getWindowManager().getDefaultDisplay();
        Point size = getDisplaySize(display);
        int width1 = display.getWidth();  // deprecated
        int height = display.getHeight();  // deprecated
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(width1/dm.xdpi,2);
        double y = Math.pow(height/dm.ydpi,2);
         screenInches = Math.sqrt(x+y);
		width = size.x;
		setContentView(R.layout.main);
		init();
//		initListener();
		Intent d=getIntent();
		Bundle e=d.getExtras();
		if(e!=null)
		{}
		else{
		 pref=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			final SharedPreferences.Editor editor=pref.edit();
			editor.putBoolean("running", true);
			boolean flag=pref.getBoolean("registered", false);
			editor.commit();

//		cd = new ConnectionDetector(getApplicationContext());
//
//		// Check if Internet present
//		if (!cd.isConnectingToInternet()) {
//			// Internet Connection is not present
//			alert.showAlertDialog(SlidingActivity.this,
//					"Internet Connection Error",
//					"Poor Internet connection", false);
//			// stop executing code by return
//			return;
//		}
		if(!flag)
		{
		// Getting name, email from intent
		final String deviceId = Secure.getString(this.getContentResolver(),
                Secure.ANDROID_ID);
		Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
		Account[] accounts = AccountManager.get(this).getAccounts();
		for (Account account : accounts) {
		    if (emailPattern.matcher(account.name).matches()) {
		        
		    	if(!account.name.isEmpty())
		    	{
		    	name =  account.name;
				email = account.name;
		    	}
				else
				{
					name="test";
					email="testmail@testmail.com";
				}
		    }
		}
		name=deviceId;
				inflater = getLayoutInflater();
		registerReceiver(mHandleMessageReceiver, new IntentFilter(
				DISPLAY_MESSAGE_ACTION));
		
		final Context context = this;
		mRegisterTask = new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				
				GCMRegistrar.checkDevice(getApplicationContext());

				
				GCMRegistrar.checkManifest(getApplicationContext());
				final String regId = GCMRegistrar.getRegistrationId(getApplicationContext());

				if (regId.equals("")) {
							
					GCMRegistrar.register(getApplicationContext(), SENDER_ID);
				}
				 else if (GCMRegistrar.isRegisteredOnServer(getApplicationContext())) {
					//do nothing
				}
				else
				{
				if(regId.length()>0)
					{
					ServerUtilities.register(context, deviceId, email, regId);
					editor.putBoolean("registered", true);
					editor.commit();
				
					}
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				mRegisterTask = null;
			}

		};
		mRegisterTask.execute(null, null, null);
		}
		}
	}	

  private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			// Waking up mobile if it is sleeping
			WakeLocker.acquire(getApplicationContext());
			/**
			 * Take appropriate action on this message
			 * depending upon your app requirement
			 * For now i am just displaying it on the screen
			 * */
			
		/*	SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			SharedPreferences.Editor editor=pref.edit();
			editor.putString("message", newMessage);
			editor.commit();*/
			if(newMessage.contains("GCMSERVER")|| newMessage.contains("DemoServer")||newMessage.contains("gcmserver"))
			{
				//do nothing
			}
			else
				{
				HomeFragment.update(newMessage);
		//		callHomeFragment();
				}
		
		/*	View layout = inflater.inflate(R.layout.notifcationtoast,
                    (ViewGroup) findViewById(R.id.notify));

			TextView text = (TextView) layout.findViewById(R.id.trackName);	
			
			text.setText(newMessage);
			text.setSelected(true);
			
			text.setEllipsize(TruncateAt.MARQUEE);
			Toast toast = new Toast(getApplicationContext());
			toast.setDuration(Toast.LENGTH_LONG);
			toast.setView(layout);
			toast.show();
		*/
			// Showing received message
			//lblMessage.append(newMessage + "\n");			
		
			
			// Releasing wake lock
			WakeLocker.release();
		}
	};
	@Override
	protected void onDestroy() {
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		try {
			unregisterReceiver(mHandleMessageReceiver);
			GCMRegistrar.onDestroy(this);
		} catch (Exception e) {
			Log.e("UnRegister Receiver Error on GCMSERVER", "> " + e.getMessage());
		}
		super.onDestroy();
	}



	private void init() {
		mSlidingMenu = (SlidingMenu) findViewById(R.id.slidingMenu);
//		mSlidingMenu.setLeftView(getLayoutInflater().inflate(
//				R.layout.left_frame, null));
		mSlidingMenu.setRightView(getLayoutInflater().inflate(
				R.layout.right_frame, null));
		mSlidingMenu.setCenterView(getLayoutInflater().inflate(
				R.layout.center_frame, null));

		FragmentTransaction t = this.getSupportFragmentManager()
				.beginTransaction();
//		leftFragment = new LeftFragment();
//		t.replace(R.id.left_frame, leftFragment);

		rightFragment = new RightFragment();
		t.replace(R.id.right_frame, rightFragment);

//		viewPageFragment = new ViewPageFragment();
		Intent d=getIntent();
		Bundle e=d.getExtras();
		if(e!=null)
		{
			t.replace(R.id.center_frame, new PhotoFragment());
			t.commit();
		}
		else
		{
		t.replace(R.id.center_frame, new HomeFragment());
		t.commit();
		}
		
	}

//	private void initListener() {
//		viewPageFragment.setMyPageChangeListener(new MyPageChangeListener() {
//			
//			@Override
//			public void onPageSelected(int position) {
//				if(viewPageFragment.isFirst()){
//					mSlidingMenu.setCanSliding(true,false);
//				}else if(viewPageFragment.isEnd()){
//					mSlidingMenu.setCanSliding(false,true);
//				}else{
//					mSlidingMenu.setCanSliding(false,false);
//				}
//			}
//		});
//	}

//	public void showLeft() {
//		mSlidingMenu.showLeftView();
//	}

	public void showRight() {
		mSlidingMenu.showRightView();
	}


	@Override
	public void navToPage(String Title) {
//		viewPageFragment.getViewPager().setCurrentItem(position);
		FragmentTransaction t = this.getSupportFragmentManager()
		.beginTransaction();
		if(Title.equalsIgnoreCase("Home"))
		{
			HomeFragment hf = new HomeFragment();
	        t.replace(R.id.center_frame, hf);
	        t.commit();
		}
		else if(Title.equalsIgnoreCase("About")){
	        AboutFragment af = new AboutFragment();
	        t.replace(R.id.center_frame, af);
	        t.commit();
	}
		else if(Title.equalsIgnoreCase("News")){
			NewsFragment nf = new NewsFragment();
	        t.replace(R.id.center_frame, nf);
	        t.commit();
		}
		else if(Title.equalsIgnoreCase("NDPeeps")){
			NdpeepsFragment ndpf = new NdpeepsFragment();
	        t.replace(R.id.center_frame, ndpf);
	        t.commit();
	}
		else if(Title.equalsIgnoreCase("Photos")){
			PhotoFragment pf = new PhotoFragment();
			t.replace(R.id.center_frame, pf);
			t.commit();
		}
		else if(Title.equalsIgnoreCase("Video"))
		{
			VideoFragment vf = new VideoFragment();
			t.replace(R.id.center_frame, vf);
			t.commit();
	}
		else if(Title.equalsIgnoreCase("NDP.org")){
			WebFragment wf = new WebFragment();
			t.replace(R.id.center_frame, wf);
			t.commit();
		}
		else if(Title.equalsIgnoreCase("Tickets"))
		{
			TicketFragment tf = new TicketFragment();
			t.replace(R.id.center_frame, tf);
			t.commit();
	}
		else if(Title.equalsIgnoreCase("FAQ")){
			FaqFragment ff = new FaqFragment();
			t.replace(R.id.center_frame, ff);
			t.commit();
		}
		else if(Title.equalsIgnoreCase("Info")){
			InfoFragment inf = new InfoFragment();
			t.replace(R.id.center_frame, inf);
			t.commit();
		}
		else if(Title.equalsIgnoreCase("Event")){
			EventFragment event = new EventFragment();
			t.replace(R.id.center_frame, event);
			t.commit();
		}
		else if(Title.equalsIgnoreCase("Streaming")){
			
			 Intent i=new Intent(SlidingActivity.this,MediaPlayerDemo_Video.class);
       
           	startActivity(i);
		}
		else if(Title.equalsIgnoreCase("Singalong")){
			
		}
		
		mSlidingMenu.showRightView();
	}

	
	 @Override
	    public boolean onKeyDown(int keyCode, KeyEvent event) {  
//		 if(keyCode==KeyEvent.KEYCODE_HOME)
//		 { 
//		 pref=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//		SharedPreferences.Editor editor=pref.edit();
//		editor.putBoolean("running", false);
//		editor.commit();
//		Process.killProcess(android.os.Process.myPid());
//	    System.exit(10);
//		 }
	//	 else
	//	 {
		 
		 
	        	 if (keyCode == KeyEvent.KEYCODE_BACK){
	        		  if( reco == 0) {
	     	        Log.d("CDA", "onKeyDown Called");
	     	        onBackPressed();
	     	        return true;}
	        		  else if (reco == 1){
	        			  AboutFragment af = new AboutFragment();
	        			  FragmentTransaction t = this.getSupportFragmentManager()
	        						.beginTransaction();
	        		        t.replace(R.id.center_frame, af);
	        		        t.commit();
	      	             return true;
	      	           }
	      	           else if (reco == 2){
	      	 			InfoFragment inf = new InfoFragment();
	      	 			FragmentTransaction t = this.getSupportFragmentManager()
	      						.beginTransaction();
	      				t.replace(R.id.center_frame, inf);
	      				t.commit();
	       	            return true;
	      	           }
	      	         else if (reco == 3){
//	      	        	navToPhotoGridView(pgf.mylist);
	      	        	
		      	 			PhotoFragment pf = new PhotoFragment();
		      	 			FragmentTransaction t = this.getSupportFragmentManager()
		      						.beginTransaction();
		      				t.replace(R.id.center_frame, pf);
		      				t.commit();
		       	            return true;
		      	           }
	     	           }
		// }
		 return super.onKeyDown(keyCode, event);
	     	}
	     	@Override
	     	   public void onBackPressed() {
	             Log.d("CDA", "onBackPressed Called");
	             new AlertDialog.Builder(this)
	             .setMessage("Are you sure you want to exit?")
	             .setCancelable(false)
	             .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	                 public void onClick(DialogInterface dialog, int id) {
	                	 pref=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		             		SharedPreferences.Editor editor=pref.edit();
		             		editor.putBoolean("running", false);
		             		editor.commit();
		             		Process.killProcess(android.os.Process.myPid());
		             	    System.exit(10);

	                 }
	             })
	             .setNegativeButton("No", null)
	             .show();
	    }


			@Override
			public void navTologo(int num) {
				FragmentTransaction t = this.getSupportFragmentManager()
						.beginTransaction();
				switch(num){
				case 0 :
					LogoFragment lf = new LogoFragment();
					t.replace(R.id.center_frame, lf);
					t.commit();
					break;
				case 1 :
					ThemeFragment themef = new ThemeFragment();
					t.replace(R.id.center_frame, themef);
					t.commit();
					break;
				}

			}


			@Override
			public void navTocompany(String url) {
				// TODO Auto-generated method stub
				CompanyWebFragment cwf = new CompanyWebFragment();
				cwf.companypageurl=url;
				FragmentTransaction t = this.getSupportFragmentManager()
						.beginTransaction();
				t.replace(R.id.center_frame, cwf);
				t.commit();
			}  
			private void callHomeFragment() {
				// TODO Auto-generated method stub
				FragmentTransaction t = this.getSupportFragmentManager()
						.beginTransaction();
				HomeFragment hf = new HomeFragment();
		        t.replace(R.id.center_frame, hf);
		        t.commit();
			}


			@Override
			public void navToPhotoGridView(ArrayList<HashMap<String, Object>> url) {
				// TODO Auto-generated method stub
				FragmentTransaction t = this.getSupportFragmentManager()
						.beginTransaction();
				 pgf = new PhotoGridViewFragment();
				pgf.mylist=new ArrayList<HashMap<String, Object>>();
				pgf.mylist=url;
		        t.replace(R.id.center_frame, pgf);
		        t.commit();
			}



}
