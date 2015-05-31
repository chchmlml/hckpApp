package com.instway.app.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.instway.app.AppContext;
import com.instway.app.AppManager;
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

    @ViewInject(R.id.tc_sj)
    private TextView tcSj;

    @ViewInject(R.id.status_icon)
    private ImageView statusIcon;
//
//    @ViewInject(R.id.tc_fax)
//    private TextView tcFax;
//
//    @ViewInject(R.id.tc_status)
//    private TextView tcStatus;

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

    String phone;

    @OnClick({R.id.button, R.id.back_img, R.id.call_btn})
    public void buttonClick(View v) {
        if (v.getId() == R.id.call_btn) {
            UIHelper.callPhone(appContext, phone);
        }
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
        phone = bundle.getString("tc_phone");
        tcPhone.setText(phone);
        tcSj.setText(bundle.getString("tc_sj"));
        int status = StringUtils.toInt(bundle.getString("tc_status"));
        Drawable dDrawable = null;
        switch (status) {
            case 1:
                dDrawable = appContext.getResources().getDrawable(R.drawable.team_status_1);
                break;
            case 2:
                dDrawable = appContext.getResources().getDrawable(R.drawable.team_status_2);
                break;
        }
        statusIcon.setImageDrawable(dDrawable);
    }
}
