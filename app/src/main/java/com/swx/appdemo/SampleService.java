package com.swx.appdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by swx on 12/04/2017.
 * Mail: bjshenwenxing@netease.corp.com
 * Copyright (c) 2017 NetEase Spot Investment Platform.
 */

public class SampleService extends Service {
    public static final String TAG = SampleService.class.getSimpleName();
    MyBinder myBinder = new MyBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    public static class MyBinder extends Binder {
        public void doWork() {
            Log.d(TAG, "do work");
        }
    }
}
