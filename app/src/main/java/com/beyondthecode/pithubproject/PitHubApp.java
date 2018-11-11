package com.beyondthecode.pithubproject;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;


public class PitHubApp extends Application {

    private static PitHubApp singleton;


    public static synchronized PitHubApp getInstance(){
        return singleton;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
