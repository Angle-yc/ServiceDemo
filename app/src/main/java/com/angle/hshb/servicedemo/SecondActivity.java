package com.angle.hshb.servicedemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.angle.hshb.servicedemo.service.MethodTwoService;

public class SecondActivity extends AppCompatActivity {
    private MethodTwoService.DownLoadBinder binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            binder = (MethodTwoService.DownLoadBinder) iBinder;
            binder.startDownload();
            binder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    /**
     * 绑定服务
     * @param view
     */
    public void bindService(View view) {
        bindService(new Intent(this,MethodTwoService.class),connection,BIND_AUTO_CREATE);
    }

    /**
     * 解绑服务
     * @param view
     */
    public void unbindService(View view) {
        unbindService(connection);

    }

    public void toMethodOne(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }
}
