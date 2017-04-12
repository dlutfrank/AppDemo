package com.swx.appdemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

/**
 * Created by swx on 12/04/2017.
 * Mail: bjshenwenxing@netease.corp.com
 * Copyright (c) 2017 NetEase Spot Investment Platform.
 */

public class SecondActivity extends Activity {
    private static final String TAG = SecondActivity.class.getSimpleName();
    private SampleService.MyBinder myBinder = null;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            myBinder = (SampleService.MyBinder) iBinder;
            Log.d(TAG, "onServiceConnected");
            myBinder.doWork();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_page_layout);
        init();
    }

    private void init() {
        findViewById(R.id.tv_start_service).setOnClickListener(onClickListener);
        findViewById(R.id.tv_stop_service).setOnClickListener(onClickListener);
        findViewById(R.id.tv_bind_service).setOnClickListener(onClickListener);
        findViewById(R.id.tv_unbind_service).setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_start_service: {
                    Intent intent = new Intent(SecondActivity.this, SampleService.class);
                    startService(intent);
                }
                break;
                case R.id.tv_stop_service: {
                    Intent intent = new Intent(SecondActivity.this, SampleService.class);
                    stopService(intent);
                }
                break;
                case R.id.tv_bind_service: {
                    Intent intent = new Intent(SecondActivity.this, SampleService.class);
                    bindService(intent, serviceConnection, BIND_AUTO_CREATE);
                }
                break;
                case R.id.tv_unbind_service: {
                    unbindService(serviceConnection);
                }
                break;
            }
        }
    };

}
