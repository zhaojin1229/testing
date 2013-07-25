package com.massiveinfinity.slidingmenu.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.massiveinfinity.slidingmenu.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ImageLoader {
    
    MemoryCache memoryCache=new MemoryCache();
    FileCache fileCache;
    private Map<ImageView, String> imageViews=Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    ExecutorService executorService; 
    
    public ImageLoader(Context context){
        fileCache=new FileCache(context);
        executorService=Executors.newFixedThreadPool(5);
    }
    
    final int stub_id=R.drawable.loading_icon;
    public void DisplayImage(String url, ImageView imageView)
    {
        imageViews.put(imageView, url);
        Bitmap bitmap=memoryCache.get(url);
 //       Bitmap bitmap = scaleImg(bitmap1,1.5);
        if(bitmap!=null)
        {
        	int heght,wth;
        	heght=bitmap.getHeight();
        	wth=bitmap.getHeight();
        	imageView.setMaxHeight(heght);
    		imageView.setMaxWidth(wth);
            imageView.setImageBitmap(bitmap);
        }
        else
        {
            queuePhoto(url, imageView);
            imageView.setImageResource(stub_id);
        }
//        bitmap.recycle();
    }
    
    public void DisplayImage1(String url, LinearLayout imageView)
    {
//    	 imageViews.put(imageView, url);
//         Bitmap bitmap=memoryCache.get(url);
//         if(bitmap!=null)
//             imageView.setImageBitmap(bitmap);
//         else
//         {
//             queuePhoto(url, imageView);
//             imageView.setImageResource(stub_id);
//         }
//        else
//        {
//       queuePhoto(url, imageView);
//            imageView.setImageResource(stub_id);
//        }
    }
        
    private void queuePhoto(String url, ImageView imageView)
    {
        PhotoToLoad p=new PhotoToLoad(url, imageView);
        executorService.submit(new PhotosLoader(p));
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

    //decodes image and scales it to reduce memory consumption
    @SuppressWarnings("unused")
	private Bitmap decodeFile(File f){
        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f),null,o);
            
            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE=200;
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale*=2;
            }
            
            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
//            scale=1;
            o2.inSampleSize=scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {}
        return null;
    }
    
    //Task for the queue
    private class PhotoToLoad
    {
        public String url;
        public ImageView imageView;
        public PhotoToLoad(String u, ImageView i){
            url=u; 
            imageView=i;
        }
    }
    
    class PhotosLoader implements Runnable {
        PhotoToLoad photoToLoad;
        PhotosLoader(PhotoToLoad photoToLoad){
            this.photoToLoad=photoToLoad;
        }
        
        @Override
        public void run() {
            if(imageViewReused(photoToLoad))
                return;
            Bitmap bmp = null;
			try {
				bmp = getBitmap(photoToLoad.url);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            memoryCache.put(photoToLoad.url, bmp);
            if(imageViewReused(photoToLoad))
                return;
            BitmapDisplayer bd=new BitmapDisplayer(bmp, photoToLoad);
            Activity a=(Activity)photoToLoad.imageView.getContext();
            a.runOnUiThread(bd);
        }
    }
    
    boolean imageViewReused(PhotoToLoad photoToLoad){
        String tag=imageViews.get(photoToLoad.imageView);
        if(tag==null || !tag.equals(photoToLoad.url))
            return true;
        return false;
    }
    
    //Used to display bitmap in the UI thread
    class BitmapDisplayer implements Runnable
    {
        Bitmap bitmap;
        PhotoToLoad photoToLoad;
        public BitmapDisplayer(Bitmap b, PhotoToLoad p){bitmap=b;photoToLoad=p;}
        public void run()
        {
            if(imageViewReused(photoToLoad))
                return;
            if(bitmap!=null)
                photoToLoad.imageView.setImageBitmap(bitmap);
            else
                photoToLoad.imageView.setImageResource(stub_id);
        }
    }

    public void clearCache() {
        memoryCache.clear();
        fileCache.clear();
    }

    protected Bitmap scaleImg(Bitmap bm, double per) {
        // 图片�?
        // Bitmap bm = BitmapFactory.decodeStream(getResources()
        // .openRawResource(id));
        // 获得图片的宽�?
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 设置想要的大�?
//        int newWidth1 = newWidth;
//        int newHeight1 = newHeight;
//        // 计算缩放比例
//        float scaleWidth = ((float) newWidth1) / width;
//        float scaleHeight = ((float) newHeight1) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        float per1 = (float)per;
        matrix.postScale(per1, per1);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
          true);
        return newbm;
       }
}
