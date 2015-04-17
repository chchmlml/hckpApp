package com.haven.hckp.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.haven.hckp.AppContext;
import com.haven.hckp.AppManager;
import com.haven.hckp.R;
import com.haven.hckp.common.ConstantValues;
import com.haven.hckp.common.DoubleClickExitHelper;
import com.haven.hckp.common.UIHelper;
import com.haven.hckp.common.UpdateManager;
import com.haven.hckp.widght.CustomDialog;
import com.haven.hckp.widght.MyTabWidget;
import com.haven.hckp.widght.MyTabWidget.OnTabSelectedListener;

/**
 *
 */
public class MainActivity extends BaseActivity implements OnTabSelectedListener {

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

        init();
        initEvents();

        // 检查新版本
//        if (appContext.isCheckUp()) {
//            UpdateManager.getUpdateManager().checkAppUpdate(this, false);
//        }
    }

    private void init() {
        mFragmentManager = getSupportFragmentManager();
        mTabWidget = (MyTabWidget) findViewById(R.id.tab_widget);
    }

    private void initEvents() {
        mTabWidget.setOnTabSelectedListener(this);
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
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case ConstantValues.HOME_FRAGMENT_INDEX:
                if (null == mOrderFragment) {
                    mOrderFragment = new OrderFragment();
                    transaction.add(R.id.center_layout, mOrderFragment);
                } else {
                    transaction.show(mOrderFragment);
                }
                break;
            case ConstantValues.CATEGORY_FRAGMENT_INDEX:
                if (null == mHomeFragment) {
                    mHomeFragment = new HomeFragment();
                    transaction.add(R.id.center_layout, mHomeFragment);
                } else {
                    transaction.show(mHomeFragment);
                }
                break;
            case ConstantValues.COLLECT_FRAGMENT_INDEX:
                if (null == mTeamFragment) {
                    mTeamFragment = new TeamFragment();
                    transaction.add(R.id.center_layout, mTeamFragment);
                } else {
                    transaction.show(mTeamFragment);
                }
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
        if (null != mTeamFragment) {
            transaction.hide(mTeamFragment);
        }
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

    /**
     * 监听返回--是否退出程序
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean flag = true;
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            CustomDialog.Builder builder = new CustomDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("您确定退出吗？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    AppManager.getAppManager().finishAllActivity();
                    System.exit(0);
                    dialog.dismiss();
                }
            });

            builder.setNegativeButton("取消",
                    new android.content.DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            builder.create().show();
            // 是否退出应用
            //return mDoubleClickExitHelper.onKeyDown(keyCode, event);
        }
        return flag;
    }
}
