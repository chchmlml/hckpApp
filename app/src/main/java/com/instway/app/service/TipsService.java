package com.instway.app.service;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.lidroid.xutils.util.LogUtils;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Timer;
import java.util.TimerTask;

public class TipsService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    Handler handler = new Handler() {
        public void handleMessage(Message msg) {

            if (msg.what == 1) {

            }
            super.handleMessage(msg);
        }
    };
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {

        @Override
        public void run() {
            // 需要做的事:发送消息
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

    @Override
    public void onCreate() {

        timer.schedule(task, 1000, 1000); // 1s后执行task,经过1s再次执行
    }

    private int TIME = 1000;

    @Override
    protected void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(fd, writer, args);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        LogUtils.i("location onDestroy");
        //timer.cancel();
    }

    @Override
    public void onStart(Intent intent, int startId) {

    }
}
