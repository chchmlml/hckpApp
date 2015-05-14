package com.haven.hckp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.haven.hckp.AppContext;
import com.haven.hckp.AppException;
import com.haven.hckp.R;
import com.haven.hckp.api.ApiClient;
import com.haven.hckp.bean.URLs;
import com.haven.hckp.common.ConstantValues;
import com.haven.hckp.common.UIHelper;
import com.haven.hckp.widght.MyTabWidget;
import com.haven.hckp.widght.MyTabWidget.OnTabSelectedListener;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 *
 */
public class MainActivity2 extends BaseActivity implements OnTabSelectedListener {

    private MyTabWidget mTabWidget;
    private HomeFragment mHomeFragment;
    private OrderFragment mOrderFragment;
    private TeamFragment mTeamFragment;
    private SettingFragment mSettingFragment;
    private int mIndex = ConstantValues.HOME_FRAGMENT_INDEX;
    private FragmentManager mFragmentManager;


    private AppContext appContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appContext = (AppContext) getApplicationContext();

        // 网络连接判断
        if (!appContext.isNetworkConnected())
            UIHelper.ToastMessage(this, R.string.network_not_connected);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mIndex = bundle.getInt("index");
        init();
        initEvents();

        // 检查新版本
        if (appContext.isCheckUp()) {
            //UpdateManager.getUpdateManager().checkAppUpdate(this, false);
        }
    }

    private void init() {
        mFragmentManager = getSupportFragmentManager();
        mTabWidget = (MyTabWidget) findViewById(R.id.tab_widget);
    }

    private void initEvents() {
        mTabWidget.setOnTabSelectedListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        onTabSelected(mIndex);
        mTabWidget.setTabsDisplay(this, mIndex);
        mTabWidget.setIndicateDisplay(this, 3, true);
    }

    @Override
    public void onTabSelected(int index) {
        //登陆判断
        try {
            if (!AppContext.isLogin(appContext)) {
                UIHelper.showLogin2Redirect(appContext);
                finish();
            }
        } catch (AppException e) {
            e.printStackTrace();
        }
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case ConstantValues.HOME_FRAGMENT_INDEX:
                //去首页
                UIHelper.showHomeRedirect(appContext);
                break;
            case ConstantValues.CATEGORY_FRAGMENT_INDEX:
                //货源
                if (null == mOrderFragment) {
                    mOrderFragment = new OrderFragment();
                    transaction.add(R.id.center_layout, mOrderFragment);
                } else {
                    transaction.show(mOrderFragment);
                }

                break;
            case ConstantValues.COLLECT_FRAGMENT_INDEX:
                //订单
                if (null == mHomeFragment) {
                    mHomeFragment = new HomeFragment();
                    transaction.add(R.id.center_layout, mHomeFragment);
                } else {
                    transaction.show(mHomeFragment);
                }
                //车队
                //if (null == mTeamFragment) {
                //mTeamFragment = new TeamFragment();
                //transaction.add(R.id.center_layout, mTeamFragment);
                //} else {
                //transaction.show(mTeamFragment);
                //}
                break;
            case ConstantValues.SETTING_FRAGMENT_INDEX:
                if (null == mSettingFragment) {
                    mSettingFragment = new SettingFragment();
                    transaction.add(R.id.center_layout, mSettingFragment);
                } else {
                    transaction.show(mSettingFragment);
                }
                break;

            default:
                break;
        }
        mIndex = index;
        transaction.commitAllowingStateLoss();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (null != mHomeFragment) {
            transaction.hide(mHomeFragment);
        }
        if (null != mOrderFragment) {
            transaction.hide(mOrderFragment);
        }
//        if (null != mTeamFragment) {
//            transaction.hide(mTeamFragment);
//        }
        if (null != mSettingFragment) {
            transaction.hide(mSettingFragment);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("index", mIndex);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mIndex = savedInstanceState.getInt("index");
    }

    @Override
    protected void onStop() {
        super.onStop();
//        mLocationClient.stop();
    }

    /**
     * 监听返回--是否退出程序
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText("确定退出吗？")
                    .setContentText("点击确认，我们将无法定位您的位置信息，影响正常配送。")
                    .setConfirmText("确定")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            System.exit(0);
                        }
                    })
                    .showCancelButton(true)
                    .setCancelText("取消")
                    .setCancelClickListener(null)
                    .show();
        }
        return false;
    }
}
