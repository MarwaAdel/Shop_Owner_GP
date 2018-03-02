package com.example.marwaadel.shopownertablet;

import android.content.SharedPreferences;

import com.onesignal.OneSignal;

/**
 * Includes one-time initialization of DatabaseReference related code
 */
public class ShopOwnerApplication extends android.app.Application {
    SharedPreferences pref;
    SharedPreferences.Editor editor;


    String mListId;
    String mUid;
    @Override
    public void onCreate() {
        super.onCreate();
   /* Initialize DatabaseReference */
       // DatabaseReference.setAndroidContext(this);
        OneSignal.startInit(this).init();

    }

}