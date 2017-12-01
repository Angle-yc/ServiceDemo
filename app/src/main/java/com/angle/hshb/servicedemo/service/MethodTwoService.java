package com.angle.hshb.servicedemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MethodTwoService extends Service {
    public static final String TAG = "MethodTwoService";
    private DownLoadBinder binder = new DownLoadBinder();

    public MethodTwoService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"onBind");
        return binder;
    }


    public class DownLoadBinder extends Binder{
        public void startDownload(){
            Log.i(TAG,"startDownload");
        }

        public void getProgress(){
            Log.i(TAG,"getProgress");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy");
    }
}
