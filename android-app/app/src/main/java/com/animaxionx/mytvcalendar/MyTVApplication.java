package com.animaxionx.mytvcalendar;
import android.app.Application;
import com.onesignal.OneSignal;
public class MyTVApplication extends Application {
 @Override public void onCreate(){
  super.onCreate();
  OneSignal.initWithContext(this,"6a45a10e-487b-4a01-9ab4-ed5e9c332660");
 }
}
