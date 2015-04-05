package com.haven.hckp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;

import com.haven.hckp.R;
import com.haven.hckp.widght.LoadingView;

public class LoadingActivity extends ActionBarActivity {


    private LoadingView mainImageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        initLoadingImages();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent it = new Intent(LoadingActivity.this, MainActivity.class);
                startActivity(it);
                finish();
            }
        }, 3000);
    }

    private void initLoadingImages() {
        mainImageview = (LoadingView) findViewById(R.id.main_imageview);
        int[] imageIds = new int[6];
        imageIds[0] = R.drawable.loader_frame_1;
        imageIds[1] = R.drawable.loader_frame_2;
        imageIds[2] = R.drawable.loader_frame_3;
        imageIds[3] = R.drawable.loader_frame_4;
        imageIds[4] = R.drawable.loader_frame_5;
        imageIds[5] = R.drawable.loader_frame_6;

        mainImageview.setImageIds(imageIds);
        new Thread() {
            @Override
            public void run() {
                mainImageview.startAnim();
            }
        }.start();
    }

}
