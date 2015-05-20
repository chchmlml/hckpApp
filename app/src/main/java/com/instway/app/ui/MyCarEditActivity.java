package com.instway.app.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MyCarEditActivity extends BaseActivity {

    private AppContext appContext;

    @ViewInject(R.id.title_tv)
    private TextView mTitleTv;

    @ViewInject(R.id.back_img)
    private ImageView backBtn;

    @ViewInject(R.id.right_img)
    private ImageView delBtn;

    @ViewInject(R.id.car_no)
    private TextView carNo;

    @ViewInject(R.id.car_weight)
    private TextView carWeight;

    @ViewInject(R.id.car_length)
    private TextView carLength;

    @ViewInject(R.id.car_width)
    private TextView carWidth;

    @ViewInject(R.id.car_height)
    private TextView carHeight;

    String carId;

    private Intent intent;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_edit);
        ViewUtils.inject(this);

        appContext = (AppContext) getApplicationContext();

        intent = this.getIntent();
        bundle = intent.getExtras();

        mTitleTv.setText(R.string.my_cars_add);
        renderBaseView();
        //显示返回按钮
    }


    @OnClick({R.id.back_img, R.id.button,R.id.delbtn, R.id.form_car_no, R.id.form_car_weight, R.id.form_car_length, R.id.form_car_id, R.id.form_car_height, R.id.form_car_width})
    public void buttonClick(View v) {

        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.button:
                createCar();
                break;
            case R.id.delbtn:
                new SweetAlertDialog(appContext, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("您确定删除这辆车？")
                        .setConfirmText("确定")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                delCar();
                            }
                        })
                        .showCancelButton(true)
                        .setCancelText("取消")
                        .setCancelClickListener(null)
                        .show();
                break;
            case R.id.form_car_no: {
            }
            break;
            case R.id.form_car_weight: {
                final String[] items = getResources().getStringArray(
                        R.array.item_car_weight);
                new AlertDialog.Builder(this)
                        .setTitle("请点击选择")
                        .setItems(items, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                                carWeight.setText(items[which]);

                            }
                        }).show();
            }
            break;
            case R.id.form_car_length: {
                final String[] items = getResources().getStringArray(
                        R.array.item_car_length);
                new AlertDialog.Builder(this)
                        .setTitle("请点击选择")
                        .setItems(items, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                                carLength.setText(items[which]);

                            }
                        }).show();
            }
            break;
            case R.id.form_car_width: {
                final String[] items = getResources().getStringArray(
                        R.array.item_car_width);
                new AlertDialog.Builder(this)
                        .setTitle("请点击选择")
                        .setItems(items, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                                carWidth.setText(items[which]);

                            }
                        }).show();
            }
            break;
            case R.id.form_car_height: {
                final String[] items = getResources().getStringArray(
                        R.array.item_car_height);
                new AlertDialog.Builder(this)
                        .setTitle("请点击选择")
                        .setItems(items, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                                carHeight.setText(items[which]);

                            }
                        }).show();
            }
            break;
            case R.id.form_car_id:
                UIHelper.showTakephotoRedirect(appContext, 4, "");
                break;
        }
    }

    private void delCar() {
        HashMap<String, Object> p = new HashMap<String, Object>();
        RequestParams params = new RequestParams();
        params.addBodyParameter("car_id", carId);
        p.put("car_id", carId);
        String newUrl = ApiClient._MakeURL(URLs.DEL_CAR, p, appContext);
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

    private void createCar() {
        if (!StringUtils.isCarNo(StringUtils.toString(carNo.getText()))) {
            UIHelper.ToastMessage(appContext, "请输入合法的车牌号");
            return;
        }
        HashMap<String, Object> p = new HashMap<String, Object>();
        p.put("car_id", carId);
        p.put("car_no", StringUtils.toString(carNo.getText()));
        p.put("car_weight", StringUtils.toString(carWeight.getText()));
        p.put("car_height", StringUtils.toString(carLength.getText()));
        p.put("car_width", StringUtils.toString(carWidth.getText()));
        p.put("car_hight", StringUtils.toString(carHeight.getText()));
        StringBuilder url = new StringBuilder();
        if (StringUtils.isEmpty(carId)) {
            url.append(URLs.CREATE_CAR);
        } else {
            url.append(URLs.EDIT_CAR);
        }
        String newUrl = ApiClient._MakeURL(url.toString(), p, appContext);
        HttpUtils http = new HttpUtils();
        final ProgressDialog pd = ProgressDialog.show(this, null, "请稍后...");

        http.send(HttpRequest.HttpMethod.POST, newUrl, null, new RequestCallBack<String>() {
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
        carWidth.setText(bundle.getString("car_width"));
        carHeight.setText(bundle.getString("car_height"));
        carId = bundle.getString("car_id");
    }
}