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
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment {
    String text = null;
    String message;
    static TextView textview;
    private Button showRight;
    SlidingActivity ma =new SlidingActivity();
    public View view;
    public HomeFragment() {
    }

    public HomeFragment(String text) {

        this.text = text;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//    	View view;
        //inflater the layout 
    	if(SlidingActivity.width>720&&SlidingActivity.screenInches>6)
		{
    		view = inflater.inflate(R.layout.home_page_l, null);
		}
		else
		{
			view = inflater.inflate(R.layout.home_page, null);
		}
    	SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        
        message=pref.getString("message", "No Notifications received");
        textview=(TextView) view.findViewById(R.id.hometextview);
        textview.setText(message);
        textview.setSelected(true);
		
        textview.setEllipsize(TruncateAt.MARQUEE);

      AnimationDrawable animationDrawable =(AnimationDrawable)getResources().getDrawable(R.drawable.home_anmi);
        
      ImageView imgView = (ImageView)view.findViewById(R.id.homeimageview);

      imgView.setBackgroundDrawable(animationDrawable);
      
      animationDrawable.start();
      
      showRight = (Button) view.findViewById(R.id.showRight);
      showRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((SlidingActivity) getActivity()).showRight();
			}
		});
      
//        TextView textView =(TextView)view.findViewById(R.id.textView);
//        if(!TextUtils.isEmpty(text)) {
//            textView.setText(text);
//        }
        return view;
    }

    @Override
   	public void onDestroy() {
           super.onDestroy();
//           PhotoFragment.i.clearCache();
           unbindDrawables(view.findViewById(R.id.RootView));
           System.gc();
       }

       private void unbindDrawables(View view) {
       	try{
           if (view.getBackground() != null) {
               view.getBackground().setCallback(null);
           }
           if (view instanceof ViewGroup) {
               for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                   unbindDrawables(((ViewGroup) view).getChildAt(i));
               }
               ((ViewGroup) view).removeAllViews();
           }
       	}
       	catch(Exception e)
       	{
       		
       	}
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
public static void update(String newMessage){
	textview.setText(newMessage);
}
    @Override
    public void onStop() {

        super.onStop();
    }
    
}
