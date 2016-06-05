package com.example.sinhaguild.staycationapp;

import android.content.Context;

import com.tramsun.libs.prefcompat.Pref;

public class Application extends android.app.Application {

    private static Application mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        Pref.init(this);
    }

    public static Application getInstance(){
        return mApplication;
    }

    public static Context getContext(){
        return mApplication.getApplicationContext();
    }
}
