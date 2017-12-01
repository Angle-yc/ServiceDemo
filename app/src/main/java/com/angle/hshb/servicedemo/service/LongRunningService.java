package com.angle.hshb.servicedemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * 让app长时间运行在后台的服务
 */
public class LongRunningService extends Service {
    public LongRunningService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
