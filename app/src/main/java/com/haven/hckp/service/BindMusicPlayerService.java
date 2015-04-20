package com.haven.hckp.service;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;


public class BindMusicPlayerService extends Service {
    private MediaPlayer mediaPlayer;
    
   private final IBinder binder = new MyBinder();
   
   public class MyBinder extends Binder{
	   
	   public BindMusicPlayerService getService(){
		   Log.v("BindMusicPlayerService", "in  BindMusicPlayerService getService()");
		   return BindMusicPlayerService.this;
	   }
   }
    
	@Override
	public IBinder onBind(Intent intent) {
		  Log.v("BindMusicPlayerService", "in  onBind(Intent intent)");
		return binder;
	}

	
	@Override
	protected void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
		 Log.v("BindMusicPlayerService", "in dump(FileDescriptor fd, PrintWriter writer, String[] args)");
		super.dump(fd, writer, args);
	}


	@Override
	protected void finalize() throws Throwable {
		 Log.v("BindMusicPlayerService", "in finalize() ");
		super.finalize();
	}


	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		 Log.v("BindMusicPlayerService", "in onConfigurationChanged(Configuration newConfig) ");
		super.onConfigurationChanged(newConfig);
	}


	@Override
	public void onLowMemory() {
		 Log.v("BindMusicPlayerService", "in onLowMemory() ");
		super.onLowMemory();
	}


	@Override
	public void onRebind(Intent intent) {
		 Log.v("BindMusicPlayerService", "in onRebind(Intent intent) ");
		super.onRebind(intent);
	}


	@Override
	public void onStart(Intent intent, int startId) {
		 Log.v("BindMusicPlayerService", "in onStart(Intent intent, int startId) ");
		super.onStart(intent, startId);
	}


	@Override
	public boolean onUnbind(Intent intent) {
		 Log.v("BindMusicPlayerService", "in onUnbind(Intent intent) ");
		return super.onUnbind(intent);
	}


	@Override
	public void onCreate() {
		 Log.v("BindMusicPlayerService", "in   onCreate()");
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		 Log.v("BindMusicPlayerService", "in   onDestroy()");
		if(mediaPlayer!=null){
			mediaPlayer.stop();
			mediaPlayer.release();
		}
	}

	
	public void play(){
		 Log.v("BindMusicPlayerService", "in  play()");
//		if (mediaPlayer == null) {
//			mediaPlayer = MediaPlayer.create(this, R.raw.tmp);
//			mediaPlayer.setLooping(false);
//		}
//		if (!mediaPlayer.isPlaying()) {
//			mediaPlayer.start();
//		}
		/*if(mediaPlayer==null){
			mediaPlayer = MediaPlayer.create(this, R.raw.tmp);
			mediaPlayer.start();
		}
		if(!mediaPlayer.isPlaying()){
			mediaPlayer.start();
		}*/
	}
	
	public void puase(){
		 Log.v("BindMusicPlayerService", "in puase()");
		if(mediaPlayer.isPlaying()&&mediaPlayer!=null){
			mediaPlayer.pause();
		}
	}
	
	public void stop(){
		 Log.v("BindMusicPlayerService", "in stop()");
		if(mediaPlayer!=null){
			mediaPlayer.stop();
			try {
				mediaPlayer.prepare();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
