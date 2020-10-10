package com.burnmycalories;

import android.app.Application;
import android.content.Context;

public class MainApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=getApplicationContext();
    }

    public static Context getCustomUtilApplicationContext(){
        return mContext;
    }
}
