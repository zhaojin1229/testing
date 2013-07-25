package com.massiveinfinity.slidingmenu;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.massiveinfinity.slidingmenu.R;
import com.massiveinfinity.slidingmenu.SlidingActivity;

public class EventMap extends Activity {
  String title;
  HashMap<String, String> map1 = new HashMap<String, String>();
  public LatLng HAMBURG = new LatLng(53.558, 9.927);
  public LatLng KIEL = new LatLng(53.551, 9.993);
  private GoogleMap map;
//  public static Location a;
//  public TextView place,web,email,phone,fax,service;
  @SuppressLint("NewApi")
@Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if(SlidingActivity.screenInches>6)
    {
    	setContentView(R.layout.map);
    }
    else
    setContentView(R.layout.map);
   
  
   
   
    Intent i= getIntent();
	  Bundle extras1=i.getExtras();
		if(i.hasExtra("title"))
	      {
			title=extras1.getString("title");
		
	      }
		if(i.hasExtra("whole"))
	      {
			map1=(HashMap<String, String>) extras1.get("whole");
//			place.setText(map1.get("address").toString().replace("&nbsp;", ""));
//			web.setText(map1.get("website").toString());
//			SpannableString content = new SpannableString(map1.get("website").toString());
//			content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
//			web.setText(content);
//			email.setText(map1.get("email").toString());
//			SpannableString content1 = new SpannableString(map1.get("email").toString());
//		    content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
//		    email.setText(content1);
//			phone.setText(map1.get("phone").toString());
//			fax.setText(map1.get("fax").toString());
//			service.setText(map1.get("service").toString());
//			service.setMovementMethod(new ScrollingMovementMethod());
	      }
		
		Double lat= Double.parseDouble(map1.get("lat").toString());
		Double longi= Double.parseDouble(map1.get("long").toString());
		HAMBURG=new LatLng(lat, longi);
    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
        .getMap();
//    Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG)
//        .title("Hamburg"));
    Marker kiel = map.addMarker(new MarkerOptions()
        .position(HAMBURG)
        .title(title)
        .snippet(map1.get("title").toString())
        .icon(BitmapDescriptorFactory
            .fromResource(R.drawable.ic_launcher)));

    // Move the camera instantly to hamburg with a zoom of 15.
    map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 14));
    map.setMyLocationEnabled(true);
//    a= map.getMyLocation();
    // Zoom in, animating the camera.
    map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

        @Override
        public void onInfoWindowClick(Marker marker) {
        	Double lat= Double.parseDouble(map1.get("lat").toString());
    		Double longi= Double.parseDouble(map1.get("long").toString());
    		String sd=lat+","+longi;
//    		String sd1=a.getLatitude()+","+a.getLongitude();
    		
    		
//        	Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
//        		    Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr="+sd));
//        		startActivity(intent);
        		Intent intent = new Intent(Intent.ACTION_VIEW, 
//        				 Uri.parse("http://maps.google.com/maps?saddr=Current%20Location&daddr="+sd));
        			    Uri.parse("http://maps.google.com/maps?f=d&daddr="+sd));
        			intent.setComponent(new ComponentName("com.google.android.apps.maps", 
        			    "com.google.android.maps.MapsActivity"));
        			startActivity(intent);
        			
//           Toast.makeText(DetailMap.this, marker.getTitle(), Toast.LENGTH_SHORT).show();
        }
    });
  }
  

	 
  

}