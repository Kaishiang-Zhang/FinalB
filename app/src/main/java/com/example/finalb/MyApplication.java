package com.example.finalb;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class MyApplication extends Application {
    public void onCreate(){
        super.onCreate();
        Stetho.initializeWithDefaults(this);
//        測試用  chrome://inspect/#devices
    }
}
