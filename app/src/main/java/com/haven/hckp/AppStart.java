package com.haven.hckp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import com.haven.hckp.common.UIHelper;
import com.haven.hckp.ui.HomeActivity;
import com.haven.hckp.ui.MainActivity;
import com.haven.hckp.widght.LoadingView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 启动界面
 */
public class AppStart extends Activity {

    @ViewInject(R.id.main_imageview)
    private LoadingView mainImageview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        final View view = View.inflate(this, R.layout.activity_start, null);
        setContentView(view);
        ViewUtils.inject(this);
        initLoadingImages();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                redirectTo();
                finish();
            }
        }, 4000);

        //是否登录
        AppContext appContext = (AppContext) getApplication();
        try {
            if (!AppContext.isLogin(appContext)) {
                UIHelper.showLogin2Redirect(appContext);
                finish();
            }
        } catch (AppException e) {
            e.printStackTrace();
        }
    }


    private void initLoadingImages() {
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
     * 跳转到...
     */
    private void redirectTo() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
