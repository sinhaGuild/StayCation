package com.example.sinhaguild.staycationapp;

import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by anuragsinha on 16-06-04.
 */
public class VolleySingleton {

    public static final String TAG = "VolleySingleton";
    private static VolleySingleton sInstance = null;
    private RequestQueue mRequestQueue;

    private VolleySingleton(){

        mRequestQueue = Volley.newRequestQueue(Application.getContext());
    }

    public static VolleySingleton getInstance(){
        if (sInstance == null){
            sInstance = new VolleySingleton();
        }else {
            Log.i(TAG, "getInstance: Singleton error");
        }
        return sInstance;
    }

    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }

}
