package com.haven.hckp.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haven.hckp.AppContext;
import com.haven.hckp.AppManager;
import com.haven.hckp.R;
import com.haven.hckp.adapter.DispathDetailAdapter;
import com.haven.hckp.api.ApiClient;
import com.haven.hckp.bean.Car;
import com.haven.hckp.bean.URLs;
import com.haven.hckp.common.StringUtils;
import com.haven.hckp.common.UIHelper;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyCarDetailActivity extends BaseActivity {

    private AppContext appContext;

    @ViewInject(R.id.title_tv)
    private TextView mTitleTv;

    @ViewInject(R.id.back_img)
    private ImageView backBtn;
    @ViewInject(R.id.right_img)
    private ImageView editBtn;

    @ViewInject(R.id.car_no)
    private TextView carNo;
    @ViewInject(R.id.car_name)
    private TextView carName;
    @ViewInject(R.id.car_weight)
    private TextView carWeight;
    @ViewInject(R.id.car_width)
    private TextView carWidth;
    @ViewInject(R.id.car_height)
    private TextView carHeight;
    @ViewInject(R.id.car_outdate)
    private TextView carOutdate;
    @ViewInject(R.id.car_length)
    private TextView carLength;

    private Intent intent;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_detail);
        ViewUtils.inject(this);

        appContext = (AppContext) getApplicationContext();

        intent = this.getIntent();
        bundle = intent.getExtras();

        mTitleTv.setText(R.string.home_detail);
        //显示返回按钮
        backBtn.setVisibility(View.VISIBLE);
        renderBaseView();
        editBtn.setVisibility(View.VISIBLE);
        editBtn.setImageDrawable(getResources().getDrawable(R.drawable.edit_btn));
    }


    @OnClick({R.id.back_img,R.id.right_img})
    public void buttonClick(View v) {

        switch (v.getId()) {
            case R.id.btn_login:
                finish();
                break;
            case R.id.right_img:
                UIHelper.showMyEditAddRedirect(appContext,bundle);
                finish();
                break;
        }
    }

    private void renderBaseView() {
        carNo.setText(bundle.getString("car_no"));
        carName.setText(bundle.getString("car_name"));
        carWeight.setText(bundle.getString("car_weight"));
        carWidth.setText(bundle.getString("car_width"));
        carLength.setText(bundle.getString("car_length"));
        carOutdate.setText(bundle.getString("car_outdate"));
        carHeight.setText(bundle.getString("car_height"));
    }
}
