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
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.haven.hckp.AppContext;
import com.haven.hckp.AppManager;
import com.haven.hckp.R;
import com.haven.hckp.adapter.DispathDetailAdapter;
import com.haven.hckp.api.ApiClient;
import com.haven.hckp.bean.Dispath;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeDispathDetailActivity extends BaseActivity {

    private AppContext appContext;

    @ViewInject(R.id.title_tv)
    private TextView mTitleTv;

    @ViewInject(R.id.back_img)
    private ImageView backBtn;


    @ViewInject(R.id.getweight_1)
    private BootstrapEditText getweight_1;

    @ViewInject(R.id.getweight_2)
    private BootstrapEditText getweight_2;

    @ViewInject(R.id.getnums_1)
    private BootstrapEditText getnums_1;

    @ViewInject(R.id.getnums_2)
    private BootstrapEditText getnums_2;

    @ViewInject(R.id.transport_btn_1)
    private BootstrapButton transport_btn_1;

    @ViewInject(R.id.transport_btn_2)
    private BootstrapButton transport_btn_2;

    private Intent intent;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_dispath_detail);
        ViewUtils.inject(this);

        appContext = (AppContext) getApplicationContext();

        intent = this.getIntent();
        bundle = intent.getExtras();

        mTitleTv.setText(R.string.home_dispath_detail);
        //显示返回按钮
        backBtn.setVisibility(View.VISIBLE);
        renderBaseView(bundle);
    }

    @OnClick({R.id.back_img, R.id.transport_btn_1, R.id.transport_btn_2})
    public void buttonClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.transport_btn_1:
                ShippingTransport();
                break;
            case R.id.transport_btn_2:
                SendTransport();
                break;
        }
    }

    /**
     * 装货报备
     */
    private void ShippingTransport() {

        String getweight = StringUtils.toString(getweight_1.getText());
        String getnums = StringUtils.toString(getnums_1.getText());
        if (StringUtils.isEmpty(getweight) || StringUtils.isEmpty(getnums)) {
            UIHelper.ToastMessage(appContext, "请填写数量和重量信息");
            return;
        }
        String newUrl = ApiClient._MakeURL(URLs.ShippingTransport_POST, new HashMap<String, Object>(), appContext);
        RequestParams params = new RequestParams();
        params.addBodyParameter("tp_tt_id", bundle.getString("tp_tt_id"));
        params.addBodyParameter("tp_tt_getweight", getweight);
        params.addBodyParameter("tp_tt_getnums", getnums);
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


    /**
     * 装货报备
     */
    private void SendTransport() {

        String getweight = StringUtils.toString(getweight_2.getText());
        String getnums = StringUtils.toString(getnums_2.getText());
        if (StringUtils.isEmpty(getweight) || StringUtils.isEmpty(getnums)) {
            UIHelper.ToastMessage(appContext, "请填写数量和重量信息");
            return;
        }
        String newUrl = ApiClient._MakeURL(URLs.SendTransport_POST, new HashMap<String, Object>(),  appContext);
        RequestParams params = new RequestParams();
        params.addBodyParameter("tp_tt_id", bundle.getString("tp_tt_id"));
        params.addBodyParameter("tp_tt_sendweight", getweight);
        params.addBodyParameter("tp_tt_sendnums", getnums);
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

    @ViewInject(R.id.tp_o_sn)
    private TextView sn;
    @ViewInject(R.id.tp_o_getuser)
    private TextView getuser;
    @ViewInject(R.id.tp_o_loaddate)
    private TextView loaddate;
    @ViewInject(R.id.tp_o_reachdate)
    private TextView reachdate;
    @ViewInject(R.id.tp_og_name)
    private TextView ogName;
    @ViewInject(R.id.tp_og_nums)
    private TextView ogNums;
    @ViewInject(R.id.tp_og_goodspack)
    private TextView ogGoodspack;
    @ViewInject(R.id.tp_tt_status)
    private TextView status;
    @ViewInject(R.id.tp_tt_type)
    private TextView type;

    private void renderBaseView(Bundle bundle) {
        sn.setText(bundle.getString("tp_o_sn"));
        getuser.setText(bundle.getString("tp_o_getuser"));
        loaddate.setText(bundle.getString("tp_o_loaddate"));
        reachdate.setText(bundle.getString("tp_o_reachdate"));
        ogName.setText(bundle.getString("tp_og_name"));
        ogNums.setText(bundle.getString("tp_og_nums"));
        ogGoodspack.setText(bundle.getString("tp_og_goodspack"));
        status.setText(getDispathStatus(bundle.getString("tp_tt_status")));


        getweight_1.setText(formatInt(bundle.getString("tp_tt_getweight")));
        getnums_1.setText(formatInt(bundle.getString("tp_tt_getnums")));
        getweight_2.setText(formatInt(bundle.getString("tp_tt_sendweight")));
        getnums_2.setText(formatInt(bundle.getString("tp_tt_sendnums")));

        String typew = "";
        switch (StringUtils.toInt(bundle.getString("tp_tt_type"))) {
            case 1:
                typew = "正常";
                break;
            case 2:
                typew = "转交";
                break;
        }
        type.setText(typew);
    }

    private String formatInt(String obj) {
        return obj.equals("0") ? "" : obj;
    }

    private String getDispathStatus(String s) {
        switch (StringUtils.toInt(s)) {
            case 1:
                return "未下单";
            case 2:
                return "已下单";
            case 3:
                return "已接受";
            case 4:
                return "运输中";
            case 5:
                return "已完成";
            case 6:
                return "已中断";
        }
        return "null";
    }

}
