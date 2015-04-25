package com.haven.hckp.service;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;


import com.haven.hckp.R;

import java.io.FileDescriptor;
import java.io.PrintWriter;

public class MusicPlayerService extends Service {
	private MediaPlayer mediaPlayer = null;

	@Override
	public IBinder onBind(Intent intent) {
		Log.v("MusicPlayerService", "in  onBind(Intent intent)");
		return null;
	}

	@Override
	public void onCreate() {
		Log.v("MusicPlayerService", "in  onCreate()");
		if (mediaPlayer == null) {
			mediaPlayer = new MediaPlayer();
			mediaPlayer = MediaPlayer.create(this, R.raw.tmp);
			mediaPlayer.setLooping(false);

		}

	}
	
	@Override
	protected void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
		Log.v("MusicPlayerService", "in  dump(FileDescriptor fd, PrintWriter writer, String[] args)");
		super.dump(fd, writer, args);
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onLowMemory() {
		Log.v("MusicPlayerService", "in onLowMemory()");
		super.onLowMemory();
	}

	@Override
	public void onRebind(Intent intent) {
		Log.v("MusicPlayerService", "in onRebind(Intent intent)");
		super.onRebind(intent);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.v("MusicPlayerService", "in onUnbind(Intent intent)");
		return super.onUnbind(intent);
	}

	@Override
	public void onDestroy() {
		Log.v("MusicPlayerService", "in  onDestroy()");
		if(mediaPlayer!=null){
			mediaPlayer.stop();
			mediaPlayer.release();
		}
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.v("MusicPlayerService", "in  onStart(Intent intent, int startId)");
		if (intent != null) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				int op = bundle.getInt("op");
				switch (op) {
				case 1:
					play();
					break;

				case 2:
                  puase();
					break;
				case 3:
                  stop();
					break;
				/*case 4:
				   stop();
					stopService(intent);
					break;*/
				}
			}
		}
	}

	private void play() {
		Log.v("MusicPlayerService", "in  play()");
		if (mediaPlayer!=null&&!mediaPlayer.isPlaying()) {
			Log.v("MusicPlayerService", "in  (mediaPlayer!=null&&!mediaPlayer.isPlaying()");
			mediaPlayer.start();
		}
	}

	private void puase() {
		Log.v("MusicPlayerService", "in  puase()");
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			Log.v("MusicPlayerService", "in  (mediaPlayer != null && mediaPlayer.isPlaying()");
			mediaPlayer.pause();
		}
	}

	private void stop() {
		Log.v("MusicPlayerService", "in stop())");
		if (mediaPlayer != null) {
			Log.v("MusicPlayerService", "in  (mediaPlayer != null)");
			mediaPlayer.stop();
			try {
				mediaPlayer.prepare();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
	}
}
