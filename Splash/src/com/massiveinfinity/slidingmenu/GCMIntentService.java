package com.massiveinfinity.slidingmenu;




import java.text.DateFormat;
import java.util.Calendar;

import static com.massiveinfinity.slidingmenu.CommonUtilities.SENDER_ID;
import static com.massiveinfinity.slidingmenu.CommonUtilities.displayMessage;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.google.android.gcm.GCMBaseIntentService;
import com.massiveinfinity.slidingmenu.R;
import com.massiveinfinity.slidingmenu.SlidingActivity;
import com.massiveinfinity.slidingmenu.util.NotificationDTO;
import com.massiveinfinity.slidingmenu.util.NotificationGetData;

public class GCMIntentService extends GCMBaseIntentService {

	private static final String TAG = "GCMIntentService";

    public GCMIntentService() {
        super(SENDER_ID);
    }

    /**
     * Method called on device registered
     **/
    @Override
    protected void onRegistered(Context context, String registrationId) {
        Log.i(TAG, "Device registered: regId = " + registrationId);
        displayMessage(context, "Your device registred with GCMSERVER");
        Log.d("NAME", SlidingActivity.name);
        ServerUtilities.register(context, SlidingActivity.name, SlidingActivity.email, registrationId);
    }

    /**
     * Method called on device un registred
     * */
    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Device unregistered");
        displayMessage(context, getString(R.string.gcm_unregistered));
        ServerUtilities.unregister(context, registrationId);
    }

    /**
     * Method called on Receiving a new message
     * */
    @Override
    protected void onMessage(Context context, Intent intent) {
        Log.i(TAG, "Received message");
        String message = intent.getExtras().getString("price");
        
        displayMessage(context, message);
        // notifies user
        generateNotification(context, message);
    }

    /**
     * Method called on receiving a deleted message
     * */
    @Override
    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");
        String message = getString(R.string.gcm_deleted, total);
        displayMessage(context, message);
        // notifies user
        generateNotification(context, message);
    }

    /**
     * Method called on Error
     * */
    @Override
    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
        displayMessage(context, getString(R.string.gcm_error, errorId));
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        // log message
        Log.i(TAG, "Received recoverable error: " + errorId);
        displayMessage(context, getString(R.string.gcm_recoverable_error,
                errorId));
        return super.onRecoverableError(context, errorId);
    }

    /**
     * Issues a notification to inform the user that server has sent a message.
     */
    @SuppressWarnings("deprecation")
    private  void generateNotification(Context context, String message) {
        int icon = R.drawable.ndp2013_icon_114;
   	 SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());//context
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, message, when);
        
        String title = context.getString(R.string.app_name);
     	
        
    		SharedPreferences.Editor editor=pref.edit();
    		editor.putString("message", message);
    		editor.commit();
    		String dt= DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
    	//	insertNotification(message,dt);
    			
     	
        		boolean flag=pref.getBoolean("running", false);
        		if(flag)
        		{
                Intent notificationIntent = new Intent(context, SlidingActivity.class);
                notificationIntent.putExtra("notification", "true");
               
                PendingIntent intent =
                        PendingIntent.getActivity(context, 0, notificationIntent,Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                notification.setLatestEventInfo(context, title, message, intent);
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
             
                // Play default notification sound
                notification.defaults |= Notification.DEFAULT_SOUND;
                
                //tification.sound = Uri.parse("android.resource://" + context.getPackageName() + "your_sound_file_name.mp3");
                
                // Vibrate if vibrate is enabled
                notification.defaults |= Notification.DEFAULT_VIBRATE;
                notificationManager.notify(0, notification);      
           //    notificationManager.cancel(0);

        		}
        		else
        		{
        Intent notificationIntent = new Intent(context, Splash.class);
        notificationIntent.putExtra("notification", "true");
       
        PendingIntent intent =
                PendingIntent.getActivity(context, 0, notificationIntent,Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        notification.setLatestEventInfo(context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
     
        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;
        
        //tification.sound = Uri.parse("android.resource://" + context.getPackageName() + "your_sound_file_name.mp3");
        
        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notification);      
       
  //   PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        	}
    }

    public void insertNotification(String message,String dt)
	{
			NotificationGetData insertData = new NotificationGetData(getApplicationContext());
			NotificationDTO notificate = new NotificationDTO();
			notificate.setFlag(false);
			notificate.setMessage(message);
			notificate.setDate(dt);
			insertData.addNotification(notificate);
	}


}
