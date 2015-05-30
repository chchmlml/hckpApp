package com.instway.app.ui;

import android.os.Bundle;

import com.instway.app.AppConfig;
import com.instway.app.AppContext;
import com.instway.app.R;
import com.instway.app.common.UIHelper;
import com.instway.app.widght.HomeButton;
import com.pgyersdk.update.PgyUpdateManager;

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
        PgyUpdateManager.register(this, AppConfig.APP_ID);

        ((HomeButton) findViewById(R.id.l1)).setHomeBtbOnClickListener(this);
        ((HomeButton) findViewById(R.id.l2)).setHomeBtbOnClickListener(this);
        ((HomeButton) findViewById(R.id.l3)).setHomeBtbOnClickListener(this);
        ((HomeButton) findViewById(R.id.l4)).setHomeBtbOnClickListener(this);
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
            case R.id.l4:
                UIHelper.showTeamRedirect(appContext);
                break;
            default:
                break;
        }
    }
}
