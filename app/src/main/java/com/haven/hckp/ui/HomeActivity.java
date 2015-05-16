package com.haven.hckp.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.haven.hckp.AppContext;
import com.haven.hckp.AppManager;
import com.haven.hckp.R;
import com.haven.hckp.common.UIHelper;
import com.haven.hckp.widght.HomeButton;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 首页
 */
public class HomeActivity extends BaseActivity implements HomeButton.HomeBtnOnClickListener {


    private AppContext appContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        appContext = (AppContext) getApplicationContext();
        ((HomeButton) findViewById(R.id.l1)).setHomeBtbOnClickListener(this);
        ((HomeButton) findViewById(R.id.l2)).setHomeBtbOnClickListener(this);
        ((HomeButton) findViewById(R.id.l3)).setHomeBtbOnClickListener(this);
    }


    @Override
    public void onClickDown(HomeButton homebtn) {

    }

    @Override
    public void onClickUp(HomeButton homebtn) {
        switch (homebtn.getId()) {
            case R.id.l1:
                UIHelper.showMainRedirect(appContext, 1);
                break;
            case R.id.l2:
                UIHelper.showMainRedirect(appContext, 2);
                break;
            case R.id.l3:
                UIHelper.showMainRedirect(appContext, 3);
                break;
            default:
                break;
        }
    }
}
