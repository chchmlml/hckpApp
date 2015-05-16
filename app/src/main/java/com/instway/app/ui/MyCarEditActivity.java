package com.instway.app.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.instway.app.AppContext;
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
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.HashMap;

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
            case R.id.form_car_id:
                UIHelper.showTakephotoRedirect(appContext,4);
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