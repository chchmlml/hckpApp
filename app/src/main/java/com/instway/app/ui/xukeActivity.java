package com.instway.app.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.instway.app.AppContext;
import com.instway.app.AppException;
import com.instway.app.R;
import com.instway.app.api.ApiClient;
import com.instway.app.bean.URLs;
import com.instway.app.common.StringUtils;
import com.instway.app.common.UIHelper;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.HashMap;
import java.util.Map;

public class xukeActivity extends BaseActivity {

    @ViewInject(R.id.title_tv)
    private TextView mTitleTv;

    @ViewInject(R.id.btn_email)
    private TextView textEmail;

    @ViewInject(R.id.btn_pwd)
    private TextView textPwd;

//    @ViewInject(R.id.btn_login)
//    private BootstrapButton btnLogin;

    @ViewInject(R.id.btn_register)
    private Button btnRegister;

//    @ViewInject(R.id.back_img)
//    private ImageView backBtn;

    @ViewInject(R.id.back_img)
    private ImageView backBtn;
    private AppContext appContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuke);
        ViewUtils.inject(this);
        backBtn.setVisibility(View.VISIBLE);
        mTitleTv.setText(R.string.my_cars_add);

    }

    @OnClick({R.id.btn_login})
    public void buttonClick(View v) {

        switch (v.getId()) {
            case R.id.back_img:
                this.finish();
                break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
