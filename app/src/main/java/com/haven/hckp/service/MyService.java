package com.haven.hckp.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {

    class RemindTask extends TimerTask {
        @Override
        public void run() {
            Log.d("RemindTask", "=======================================");
        }
    };

    private static final String TAG = "BindService";

    public void MyMethod() {
        Timer timer = new Timer();
        timer.schedule(new RemindTask(), 1000, 1000);
        Log.i(TAG, "BindService-->MyMethod()");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return myBinder;
    }

    public class MyBinder extends Binder {

        public MyService getService() {
            return MyService.this;
        }
    }

    private MyBinder myBinder = new MyBinder();
}
