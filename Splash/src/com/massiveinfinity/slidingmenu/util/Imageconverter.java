package com.massiveinfinity.slidingmenu.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class Imageconverter {

	public Bitmap convert(String url) throws IOException
	{
        URL myImageURL = new URL(url);
		HttpURLConnection connection = (HttpURLConnection)myImageURL .openConnection();
		connection.setDoInput(true);
		connection.connect();
		InputStream input = connection.getInputStream();
		Bitmap b=BitmapFactory.decodeStream (input);
		return b;
	}


	
}
