package com.haven.hckp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.haven.hckp.ui.MainActivity;
import com.haven.hckp.widght.LoadingView;

/**
 * 启动界面
 */
public class AppStart extends Activity {

    private static final String TAG = "AppStart";

    private LoadingView mainImageview;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View view = View.inflate(this, R.layout.activity_start, null);
        setContentView(view);

        //渐变展示启动屏
//        AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
//        aa.setDuration(2000);
//        view.startAnimation(aa);
//        aa.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationEnd(Animation arg0) {
//                redirectTo();
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationStart(Animation animation) {
//            }
//
//        });
        initLoadingImages();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                redirectTo();
                finish();
            }
        }, 3000);

        //AppContext appContext = (AppContext) getApplication();
//        String cookie = appContext.getProperty("cookie");
//        if (StringUtils.isEmpty(cookie)) {
//            String cookie_name = appContext.getProperty("cookie_name");
//            String cookie_value = appContext.getProperty("cookie_value");
//            if (!StringUtils.isEmpty(cookie_name) && !StringUtils.isEmpty(cookie_value)) {
//                cookie = cookie_name + "=" + cookie_value;
//                appContext.setProperty("cookie", cookie);
//            }
//            Log.i(TAG, "cookie = " + cookie);
//        }
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
    /**
     * 分析显示的时间
     *
     * @param time
     * @return
     */
    private long[] getTime(String time) {
        long res[] = new long[2];
        try {
            time = time.substring(0, time.indexOf("."));
            String t[] = time.split("-");
            res[0] = Long.parseLong(t[0]);
            if (t.length >= 2) {
                res[1] = Long.parseLong(t[1]);
            } else {
                res[1] = Long.parseLong(t[0]);
            }
        } catch (Exception e) {
        }
        return res;
    }

    /**
     * 跳转到...
     */
    private void redirectTo() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
