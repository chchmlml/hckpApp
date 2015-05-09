package com.haven.hckp.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.haven.hckp.AppContext;
import com.haven.hckp.AppManager;
import com.haven.hckp.R;
import com.haven.hckp.adapter.DispathDetailAdapter;
import com.haven.hckp.api.ApiClient;
import com.haven.hckp.bean.URLs;
import com.haven.hckp.common.StringUtils;
import com.haven.hckp.common.UIHelper;
import com.haven.hckp.widght.CustomDialog;
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

import cn.pedant.SweetAlert.SweetAlertDialog;

public class TeamDetailActivity extends BaseActivity {

    private AppContext appContext;

    @ViewInject(R.id.title_tv)
    private TextView mTitleTv;

    @ViewInject(R.id.back_img)
    private ImageView backBtn;

    @ViewInject(R.id.tc_name)
    private TextView tcName;

    @ViewInject(R.id.tc_user)
    private TextView tcUser;

    @ViewInject(R.id.tc_phone)
    private TextView tcPhone;

    @ViewInject(R.id.tc_fax)
    private TextView tcFax;

    @ViewInject(R.id.tc_status)
    private TextView tcStatus;

    @ViewInject(R.id.button)
    private BootstrapButton button;

    private Intent intent;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_detail);
        ViewUtils.inject(this);

        appContext = (AppContext) getApplicationContext();

        intent = this.getIntent();
        bundle = intent.getExtras();

        mTitleTv.setText(R.string.car_detail);
        //显示返回按钮
        backBtn.setVisibility(View.VISIBLE);
        renderBaseView();
    }

    @OnClick({R.id.button, R.id.back_img})
    public void buttonClick(View v) {
        if (v.getId() == R.id.back_img) {
            AppManager.getAppManager().finishActivity();
        }
        if (v.getId() == R.id.button) {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("您确定解除挂靠此车队吗？")
                    .setConfirmText("确定")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            final ProgressDialog pd = ProgressDialog.show(TeamDetailActivity.this, null, "请稍后...");
                            String newUrl = ApiClient._MakeURL(URLs.TEAM_DEL_POST, new HashMap<String, Object>(), appContext);
                            RequestParams params = new RequestParams();
                            params.addBodyParameter("tc_id", bundle.getString("tc_id"));
                            HttpUtils http = new HttpUtils();
                            http.send(HttpRequest.HttpMethod.POST, newUrl, params, new RequestCallBack<String>() {
                                @Override
                                public void onSuccess(ResponseInfo<String> objectResponseInfo) {
                                    pd.dismiss();
                                    finish();
                                }

                                @Override
                                public void onFailure(HttpException e, String s) {
                                    pd.dismiss();
                                }
                            });
                        }
                    })
                    .showCancelButton(true)
                    .setCancelText("取消")
                    .setCancelClickListener(null)
                    .show();
        }
    }

    private void renderBaseView() {

        tcName.setText(bundle.getString("tc_name"));
        tcUser.setText(bundle.getString("tc_user"));
        tcPhone.setText(bundle.getString("tc_phone"));
        tcFax.setText(bundle.getString("tc_fax"));
        tcStatus.setText(bundle.getString("tc_status"));

    }


    private String getDispathStatus(String s) {
        switch (StringUtils.toInt(s)) {
            case 1:
                button.setText("下单");
                return "未下单";
            case 2:
                button.setText("接收运单");
                return "已下单";
            case 3:
                button.setText("开始运输");
                return "已接受";
            case 4:
                button.setText("完成");
                return "运输中";
            case 5:
                button.setClickable(false);
                button.setText("结束");
                return "已完成";
            case 6:
                button.setClickable(false);
                button.setText("结束");
                return "已中断";
        }
        return "null";
    }
}
