package com.massiveinfinity.slidingmenu;





import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.massiveinfinity.slidingmenu.R;
import com.massiveinfinity.slidingmenu.util.YouTubeFailureRecoveryActivity;

import android.annotation.SuppressLint;

import android.app.ProgressDialog;
import android.os.Bundle;

import android.os.StrictMode;

public class Movie extends YouTubeFailureRecoveryActivity {
	ProgressDialog progress;

    
  /** Called when the activity is first created. */
 @SuppressLint("NewApi")
@Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.playerview_demo);
    String ssss = getIntent().getExtras().getString("keyid");
//    Thread.setDefaultUncaughtExceptionHandler( new UncaughtExceptionHandler(this));
    if (android.os.Build.VERSION.SDK_INT > 9) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
      }
    YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
    youTubeView.initialize(com.massiveinfinity.slidingmenu.util.DeveloperKey.DEVELOPER_KEY, this);
    
    
   
}

@Override
public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer player,
	      boolean wasRestored) {
	  String videoid = getIntent().getExtras().getString("keyid");
	 if (!wasRestored) {
		 player.loadVideo(videoid);
	 }
	 
	
}

@Override
protected Provider getYouTubePlayerProvider() {
	return (YouTubePlayerView) findViewById(R.id.youtube_view);
} 
}