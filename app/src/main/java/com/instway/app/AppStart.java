package com.instway.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.instway.app.api.ApiClient;
import com.instway.app.bean.URLs;
import com.instway.app.common.StringUtils;
import com.instway.app.common.UIHelper;
import com.instway.app.ui.HomeActivity;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.pgyersdk.update.PgyUpdateManager;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 启动界面
 */
public class AppStart extends Activity {


    private AppContext appContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appContext = (AppContext) getApplicationContext();
        final View view = View.inflate(this, R.layout.activity_start, null);
        setContentView(view);
        ViewUtils.inject(this);
        //initLoadingImages();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                redirectTo();
                finish();
            }
        }, 1500);

//        Intent intent = new Intent("com.instway.app.tips");
//        startService(intent);
    }

//    private void initLoadingImages() {
//        int[] imageIds = new int[6];
//        imageIds[0] = R.drawable.loader_frame_1;
//        imageIds[1] = R.drawable.loader_frame_2;
//        imageIds[2] = R.drawable.loader_frame_3;
//        imageIds[3] = R.drawable.loader_frame_4;
//        imageIds[4] = R.drawable.loader_frame_5;
//        imageIds[5] = R.drawable.loader_frame_6;
//
//        mainImageview.setImageIds(imageIds);
//        new Thread() {
//            @Override
//            public void run() {
//                mainImageview.startAnim();
//            }
//        }.start();
//    }

    /**
     * 跳转到...
     */
    private void redirectTo() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
