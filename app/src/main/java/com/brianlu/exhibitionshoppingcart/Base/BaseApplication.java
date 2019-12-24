package com.brianlu.exhibitionshoppingcart.Base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.jakewharton.threetenabp.AndroidThreeTen;

public class BaseApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        AndroidThreeTen.init(this);
    }
}
