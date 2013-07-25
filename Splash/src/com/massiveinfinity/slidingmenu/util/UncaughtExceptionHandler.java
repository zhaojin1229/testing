package com.massiveinfinity.slidingmenu.util;

import java.io.*;

import com.massiveinfinity.slidingmenu.SlidingActivity;

import android.content.*;
import android.os.Process;
import android.widget.Toast;

public class UncaughtExceptionHandler implements java.lang.Thread.UncaughtExceptionHandler {
  private final Context myContext;

  public UncaughtExceptionHandler(Context context) {
    myContext = context;
  }

  public void uncaughtException(Thread thread, Throwable exception) {
    StringWriter stackTrace = new StringWriter();
    exception.printStackTrace(new PrintWriter(stackTrace));
    System.err.println(stackTrace);
    Process.killProcess(Process.myPid());
    System.exit(10);
    Intent intent = new Intent(myContext, SlidingActivity.class);
//    intent.putExtra("exception", stackTrace.toString());
    myContext.startActivity(intent);
//    Toast.makeText(myContext, "connection error", Toast.LENGTH_SHORT).show();
    
  }
}