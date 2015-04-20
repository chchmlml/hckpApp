package com.haven.hckp.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.lidroid.xutils.util.LogUtils;

public class LocationService extends Service {

    private Handler objHandler = new Handler();

    private int intCounter = 0;

    private Runnable mTasks = new Runnable() {

        public void run() {
            LogUtils.i("intCounter:" + intCounter);
            intCounter++;
            objHandler.postDelayed(mTasks, 1000);
        }
    };

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        LogUtils.i("service start");
        objHandler.postDelayed(mTasks, 1000);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        objHandler.removeCallbacks(mTasks);
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
