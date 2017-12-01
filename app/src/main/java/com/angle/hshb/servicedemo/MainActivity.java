package com.angle.hshb.servicedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.angle.hshb.servicedemo.service.MethodOneService;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 开启服务
     * @param view
     */
    public void startService(View view) {
        startService(new Intent(this, MethodOneService.class));
    }

    /**
     * 停止服务
     * @param view
     */
    public void stopService(View view) {
        stopService(new Intent(this, MethodOneService.class));
    }

    public void toMethodTwo(View view) {
        startActivity(new Intent(this,DownloadActivity.class));
    }
}
