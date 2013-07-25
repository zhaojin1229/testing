/*
 * Copyright (C) 2013 yixia.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.massiveinfinity.slidingmenu.fragment;

import com.massiveinfinity.slidingmenu.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnBufferingUpdateListener;
import io.vov.vitamio.MediaPlayer.OnCompletionListener;
import io.vov.vitamio.MediaPlayer.OnPreparedListener;
import io.vov.vitamio.MediaPlayer.OnVideoSizeChangedListener;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.MediaController.MediaPlayerControl;


public class MediaPlayerDemo_Video extends Activity implements OnBufferingUpdateListener, OnCompletionListener, OnPreparedListener, OnVideoSizeChangedListener, SurfaceHolder.Callback, MediaPlayerControl {

	private static final String TAG = "MediaPlayerDemo";
	private int mVideoWidth;
	private int mVideoHeight;
	private MediaPlayer mMediaPlayer;
	private MediaController mcontroller;
	private SurfaceView mPreview;
	private SurfaceHolder holder;
	private Handler handler = new Handler();
	private String path;
	private Bundle extras;
	private static final String MEDIA = "media";
	private static final int LOCAL_AUDIO = 1;
	private static final int STREAM_AUDIO = 2;
	private static final int RESOURCES_AUDIO = 3;
	private static final int LOCAL_VIDEO = 4;
	private static final int STREAM_VIDEO = 5;
	private boolean mIsVideoSizeKnown = false;
	private boolean mIsVideoReadyToBePlayed = false;
	public ProgressDialog mProgressDialog;
	
	@Override
    public boolean onTouchEvent(MotionEvent event) {
        /*
         * the MediaController will hide after 3 seconds - tap the screen to
         * make it appear again
         */
        mcontroller.show();
        return false;
    }
	/**
	 * 
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		if (!LibsChecker.checkVitamioLibs(this))
			return;
		setContentView(R.layout.mediaplayer_2);
		mProgressDialog = ProgressDialog.show(this, "", "Loading...", true);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mPreview = (SurfaceView) findViewById(R.id.surface);
		holder = mPreview.getHolder();
		holder.addCallback(this);
		holder.setFormat(PixelFormat.RGBA_8888); 
//		extras = getIntent().getExtras();

	}

	private void playVideo(Integer Media) {
		doCleanUp();
		try {

			switch (Media) {
			case LOCAL_VIDEO:
				/*
				 * TODO: Set the path variable to a local media file path.
				 */
				path = "";
				if (path == "") {
					// Tell the user to provide a media file URL.
					Toast.makeText(MediaPlayerDemo_Video.this, "Please edit MediaPlayerDemo_Video Activity, " + "and set the path variable to your media file path." + " Your media file must be stored on sdcard.", Toast.LENGTH_LONG).show();
					return;
				}
				break;
			case STREAM_VIDEO:
				/*
				 * TODO: Set path variable to progressive streamable mp4 or
				 * 3gpp format URL. Http protocol should be used.
				 * Mediaplayer can only play "progressive streamable
				 * contents" which basically means: 1. the movie atom has to
				 * precede all the media data atoms. 2. The clip has to be
				 * reasonably interleaved.
				 * 
				 */
				path = "http://edge.conversant.swiftserve.com/live/ndp/amlst:ndplive/playlist.m3u8";
				if (path == "") {
					// Tell the user to provide a media file URL.
					Toast.makeText(MediaPlayerDemo_Video.this, "Please edit MediaPlayerDemo_Video Activity," + " and set the path variable to your media file URL.", Toast.LENGTH_LONG).show();
					return;
				}

				break;

			}

			// Create a new media player and set the listeners
			mMediaPlayer = new MediaPlayer(this);
			mMediaPlayer.setDataSource(path);
			mMediaPlayer.setDisplay(holder);
			mMediaPlayer.prepare();
			mMediaPlayer.setOnBufferingUpdateListener(this);
			mMediaPlayer.setOnCompletionListener(this);
			mMediaPlayer.setOnPreparedListener(this);
			mMediaPlayer.setOnVideoSizeChangedListener(this);
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mcontroller = new MediaController(this);

		} catch (Exception e) {
			Log.e(TAG, "error: " + e.getMessage(), e);
		}
	}

	public void onBufferingUpdate(MediaPlayer arg0, int percent) {
		Log.d(TAG, "onBufferingUpdate percent:" + percent);

	}

	public void onCompletion(MediaPlayer arg0) {
		Log.d(TAG, "onCompletion called");
	}

	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		Log.v(TAG, "onVideoSizeChanged called");
		if (width == 0 || height == 0) {
			Log.e(TAG, "invalid video width(" + width + ") or height(" + height + ")");
			return;
		}
		mIsVideoSizeKnown = true;
		mVideoWidth = width;
		mVideoHeight = height;
		if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
			startVideoPlayback();
		}
	}

	public void onPrepared(MediaPlayer mediaplayer) {
		Log.d(TAG, "onPrepared called");
//		mProgressDialog= ProgressDialog.show(MediaPlayerDemo_Video.this, "", 
//                "Loading. Please wait...", true);
		mProgressDialog.dismiss();
		mIsVideoReadyToBePlayed = true;
		if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
			startVideoPlayback();
		}
		
	        mcontroller.setMediaPlayer(this);
	        mcontroller.setAnchorView(mPreview);
	        handler.post(new Runnable() {

	            public void run() {
	                mcontroller.setEnabled(true);
	                mcontroller.show();
	            }
	        });
	}

	public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k) {
		Log.d(TAG, "surfaceChanged called");

	}

	public void surfaceDestroyed(SurfaceHolder surfaceholder) {
		Log.d(TAG, "surfaceDestroyed called");
	}

	public void surfaceCreated(SurfaceHolder holder) {
		Log.d(TAG, "surfaceCreated called");
//		playVideo(extras.getInt(MEDIA));
		playVideo(STREAM_VIDEO);

	}

	@Override
	protected void onPause() {
		super.onPause();
		releaseMediaPlayer();
		doCleanUp();
		mcontroller.hide();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		releaseMediaPlayer();
		doCleanUp();
		mcontroller.hide();
	}

	private void releaseMediaPlayer() {
		if (mMediaPlayer != null) {
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}

	private void doCleanUp() {
		mVideoWidth = 0;
		mVideoHeight = 0;
		mIsVideoReadyToBePlayed = false;
		mIsVideoSizeKnown = false;
	}

	private void startVideoPlayback() {
		Log.v(TAG, "startVideoPlayback");
		holder.setFixedSize(mVideoWidth, mVideoHeight);
		mMediaPlayer.start();
	}

	@Override
	public void start() {
//		mProgressDialog.dismiss();
		// TODO Auto-generated method stub
		mMediaPlayer.start();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		mMediaPlayer.pause();
	}

	@Override
	public long getDuration() {
		// TODO Auto-generated method stub
		return mMediaPlayer.getDuration();
	}

	@Override
	public long getCurrentPosition() {
		// TODO Auto-generated method stub
		return mMediaPlayer.getCurrentPosition();
	}

	@Override
	public void seekTo(long pos) {
		// TODO Auto-generated method stub
//		mMediaPlayer.seekTo(pos);
	}

	@Override
	public boolean isPlaying() {
		// TODO Auto-generated method stub
		return mMediaPlayer.isPlaying();
	}

	@Override
	public int getBufferPercentage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean canPause() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean canSeekBackward() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canSeekForward() {
		// TODO Auto-generated method stub
		return false;
	}
}
