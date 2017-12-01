package com.angle.hshb.servicedemo.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

/**
 * 让app长时间运行在后台的服务
 */
public class LongRunningService extends Service {

    public static final String TAG = "LongRunningService";
    public LongRunningService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int time = 60 * 1000;//一分钟
        Long triggerAtTime = SystemClock.elapsedRealtime() + time;
        Intent i = new Intent(this,LongRunningService.class);
        PendingIntent pi = PendingIntent.getService(this,0,i,0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);//自动唤醒app
        Log.i(TAG,"app一分钟以后将被唤醒");
        return super.onStartCommand(intent, flags, startId);
    }
}
