package com.massiveinfinity.slidingmenu.fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.massiveinfinity.slidingmenu.R;
import com.massiveinfinity.slidingmenu.SlidingActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

@SuppressLint("ValidFragment")
public class MapsFragment extends Fragment implements LocationListener {
	public static View view;
	
    String text = null;
    private GoogleMap googlemap;
    SlidingActivity ma =new SlidingActivity();
 //   Context context = new MainActivity() ;
    static final LatLng NDP2013 = new LatLng(1.289228, 103.858521);
    public MapsFragment() {
    }

    public MapsFragment(String text) {

        this.text = text;
    }

    @Override
    public void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
    	if(view==null)
    	{
    	view = inflater.inflate(R.layout.map, null);
    	view.setBackgroundColor(Color.BLUE);
    	}
    	 FragmentManager fm = getActivity().getSupportFragmentManager();
    	 Fragment fr = fm.findFragmentById(R.id.map);
    	 SupportMapFragment smf = (SupportMapFragment)fr;
    	googlemap =  smf.getMap();
    	        
    	googlemap.setMyLocationEnabled(true);
  	    googlemap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    	Marker kiel = googlemap.addMarker(new MarkerOptions()
	        .position(NDP2013)
	        .title("NDP2013")
	        .snippet("floating platform")
	        .icon(BitmapDescriptorFactory
	            .fromResource(R.drawable.ic_launcher)));
	    googlemap.moveCamera(CameraUpdateFactory.newLatLngZoom(NDP2013, 15));
	    googlemap.animateCamera(CameraUpdateFactory.zoomTo(12), 2000, null); 
	    googlemap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
		
		@Override
		public void onMapLongClick(final LatLng point) {

	      LayoutInflater li = (LayoutInflater)getActivity().getSystemService
				      (Context.LAYOUT_INFLATER_SERVICE);
		  final  View v = li.inflate(R.layout.map_menu, null); 
		  AlertDialog.Builder bu = new  AlertDialog.Builder(getActivity());
		  bu.setView(v);
		  bu.setCancelable(false);
		  bu.setPositiveButton("Create", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				EditText first = (EditText)v.findViewById(R.id.titleText);
				EditText second = (EditText)v.findViewById(R.id.descriptionText);
				googlemap.addMarker(new MarkerOptions()
				.title(first.getText().toString())
				.position(point)
				.snippet(second.getText().toString())
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.images)));
				
			}
		});
		  bu.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				
			}
		});
		  AlertDialog alter = bu.create();
	      alter.show();
		}
	});
	    return inflater.inflate(R.layout.map, container, false);
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

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("GPS is closed");
		builder.setCancelable(false);
		builder.setPositiveButton("Enable GPS", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent startGPS = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivity(startGPS);
			}
		});
		builder.setNegativeButton("leave GPS OFF",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		AlertDialog alter = builder.create();
		alter.show();		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
    
}
