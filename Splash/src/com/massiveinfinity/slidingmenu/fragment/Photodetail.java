package com.massiveinfinity.slidingmenu.fragment;


import it.sephiroth.android.library.imagezoom.ImageViewTouch;

import com.massiveinfinity.slidingmenu.R;
import com.massiveinfinity.slidingmenu.util.ImageLoader;
import com.massiveinfinity.slidingmenu.util.gridAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class Photodetail extends Activity{
	ImageViewTouch mImage;
	gridAdapter a;
	ImageLoader I;
	@Override
	   public void onBackPressed() {
		finish();
	}
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		requestWindowFeature( Window.FEATURE_NO_TITLE );
		setContentView(R.layout.photodialognew);
		mImage=(ImageViewTouch) findViewById(R.id.imageView1);
//		I=new ImageLoader(this);
		PhotoFragment.i.DisplayImage(PhotoGridViewFragment.mylist.get(getIntent().getExtras().getInt("pos")).get("image").toString(), mImage);
	}

}
