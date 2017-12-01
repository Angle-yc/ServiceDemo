package com.angle.hshb.servicedemo;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.angle.hshb.servicedemo.service.LongRunningService;

/**
 * Created by angle
 * 2017/12/1.
 */

public class MyApplication extends Application {

    private static Context context;

    public static final String TAG ="MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Log.i(TAG,"app启动了");
        Intent intent = new Intent(this, LongRunningService.class);
        stopService(intent);
    }

    public static Context getContext() {
        return context;
    }
}
