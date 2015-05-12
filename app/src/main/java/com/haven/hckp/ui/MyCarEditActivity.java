package com.haven.hckp.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.haven.hckp.AppContext;
import com.haven.hckp.R;
import com.haven.hckp.api.ApiClient;
import com.haven.hckp.bean.URLs;
import com.haven.hckp.common.StringUtils;
import com.haven.hckp.common.UIHelper;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MyCarEditActivity extends BaseActivity {

    private AppContext appContext;

    @ViewInject(R.id.title_tv)
    private TextView mTitleTv;

    @ViewInject(R.id.back_img)
    private ImageView backBtn;

    @ViewInject(R.id.car_no)
    private TextView carNo;

    @ViewInject(R.id.car_weight)
    private TextView carWeight;

    @ViewInject(R.id.car_length)
    private TextView carLength;

    private Intent intent;
    private Bundle bundle;

    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_edit);
        ViewUtils.inject(this);

        appContext = (AppContext) getApplicationContext();

        intent = this.getIntent();
        bundle = intent.getExtras();

        mTitleTv.setText(R.string.my_cars_add);
        //显示返回按钮
        backBtn.setVisibility(View.VISIBLE);
    }


    @OnClick({R.id.back_img, R.id.button})
    public void buttonClick(View v) {

        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.button:
                createCar();
                break;
        }
    }

    private void createCar() {
        HashMap<String, Object> p = new HashMap<String, Object>();
        RequestParams params = new RequestParams();
        params.addBodyParameter("car_no", StringUtils.toString(carNo.getText()));
        p.put("car_no", StringUtils.toString(carNo.getText()));
        params.addBodyParameter("car_weight", StringUtils.toString(carWeight.getText()));
        p.put("car_weight", StringUtils.toString(carWeight.getText()));
        params.addBodyParameter("car_length", StringUtils.toString(carLength.getText()));
        p.put("car_length", StringUtils.toString(carLength.getText()));
        String newUrl = ApiClient._MakeURL(URLs.CREATE_CAR, p, appContext);
        HttpUtils http = new HttpUtils();
        final ProgressDialog pd = ProgressDialog.show(this, null, "请稍后...");

        http.send(HttpRequest.HttpMethod.POST, newUrl, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> objectResponseInfo) {
                pd.dismiss();
                JSONObject obj = JSON.parseObject(objectResponseInfo.result);
                String code = obj.get("code").toString();
                if (code.equals("1")) {
                    UIHelper.ToastMessage(appContext, obj.get("msg").toString());
                    finish();
                } else {
                    UIHelper.ToastMessage(appContext, obj.get("msg").toString());
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                pd.dismiss();
            }
        });
    }

    private void renderBaseView() {
        carNo.setText(bundle.getString("car_no"));
        carWeight.setText(bundle.getString("car_weight"));
        carLength.setText(bundle.getString("car_length"));
    }
}