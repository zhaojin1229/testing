package com.massiveinfinity.slidingmenu.fragment;

import com.massiveinfinity.slidingmenu.R;
import com.massiveinfinity.slidingmenu.SlidingActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class ThemeFragment extends Fragment {
    String text = null;
    String message;
    private Button showRight;
    SlidingActivity ma =new SlidingActivity();
    public ThemeFragment() {
    }

    public ThemeFragment(String text) {
        Log.e("Krislq", text);
        this.text = text;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Log.e("Krislq", "onCreate:"+text);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
        //inflater the layout 
        View view = inflater.inflate(R.layout.theme_page, null);
        
        showRight = (Button) view.findViewById(R.id.showRight);
        showRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((SlidingActivity) getActivity()).showRight();
			}
		});
        
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
    	 ma.reco =1;
        super.onStart();
    }

    @Override
    public void onStop() {

        super.onStop();
    }
    
}
