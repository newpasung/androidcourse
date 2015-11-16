package com.gof.scut.androidcourse;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

/**
 * Created by Administrator on 2015/11/10.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }

}
