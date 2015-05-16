package com.haven.hckp.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.haven.hckp.AppContext;
import com.haven.hckp.AppException;
import com.haven.hckp.R;
import com.haven.hckp.api.ApiClient;
import com.haven.hckp.bean.User;
import com.haven.hckp.common.StringUtils;
import com.haven.hckp.common.UIHelper;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 *
 */
public class SettingFragment extends BaseFragment {

    private Activity mActivity;
    private TextView mTitleTv;
    private AppContext appContext;
    private LayoutInflater inflater;
    private View mView;

    @ViewInject(R.id.setting_cars)
    private RelativeLayout settingCars;

    @ViewInject(R.id.setting_info)
    private RelativeLayout settingInfo;

    @ViewInject(R.id.user_name_hode)
    private TextView userNameHode;

    @ViewInject(R.id.user_name)
    private TextView userName;


    @ViewInject(R.id.user_thumb)
    private BootstrapCircleThumbnail userThumb;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        mView = this.inflater.inflate(R.layout.fragment_setting, container, false);
        ViewUtils.inject(this, mView);
        appContext = (AppContext) this.mActivity.getApplicationContext();
        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        initUser();
    }

    @OnClick({R.id.user_name_hode, R.id.setting_info, R.id.setting_cars, R.id.team_info, R.id.user_thumb})
    public void buttonClick(View v) {

        User u = null;
        try {
            u = ApiClient.getUser(appContext);
            switch (v.getId()) {
                case R.id.user_thumb:
                case R.id.user_name_hode:
                    if (StringUtils.isEmpty(u.getUserUsername())) {
                        UIHelper.showLoginRedirect(appContext);
                    }
                    break;
                case R.id.setting_cars:
                    UIHelper.showMycarsRedirect(appContext);
                    break;
                case R.id.setting_info:
                    UIHelper.showPersonalRedirect(appContext);
                    break;
                case R.id.team_info:
                    UIHelper.showTeamRedirect(appContext);
                    break;
            }
        } catch (AppException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initUser() {
        try {
            User u = ApiClient.getUser(appContext);
            if (!StringUtils.isEmpty(u.getUserPhone())) {
                userName.setText(u.getUserUsername());
                userName.setVisibility(View.VISIBLE);
                userNameHode.setVisibility(View.GONE);
                BitmapUtils bitmapUtils = new BitmapUtils(appContext);
                bitmapUtils.display(userThumb, u.getHeadpic(), new BitmapLoadCallBack<BootstrapCircleThumbnail>() {
                    @Override
                    public void onLoadCompleted(BootstrapCircleThumbnail bootstrapCircleThumbnail, String s, Bitmap bitmap, BitmapDisplayConfig bitmapDisplayConfig, BitmapLoadFrom bitmapLoadFrom) {
                        userThumb.setImage(bitmap);
                    }

                    @Override
                    public void onLoadFailed(BootstrapCircleThumbnail bootstrapCircleThumbnail, String s, Drawable drawable) {
                        //UIHelper.ToastMessage(appContext, "头像加载失败");
                    }
                });
            } else {
                userName.setVisibility(View.GONE);
                userNameHode.setVisibility(View.VISIBLE);
            }
        } catch (AppException e) {
            LogUtils.e(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initViews(View view) {
        mTitleTv = (TextView) view.findViewById(R.id.title_tv);
        mTitleTv.setText(R.string.setting);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
