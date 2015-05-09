package com.haven.hckp.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haven.hckp.AppContext;
import com.haven.hckp.AppException;
import com.haven.hckp.AppManager;
import com.haven.hckp.R;
import com.haven.hckp.adapter.DispathDetailAdapter;
import com.haven.hckp.api.ApiClient;
import com.haven.hckp.bean.URLs;
import com.haven.hckp.bean.User;
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

public class PersonalActivity extends BaseActivity {

    @ViewInject(R.id.title_tv)
    private TextView mTitleTv;

    @ViewInject(R.id.back_img)
    private ImageView backBtn;
    @ViewInject(R.id.right_img)
    private ImageView editBtn;

    private AppContext appContext;

    @ViewInject(R.id.ts_u_username)
    private TextView username;

    @ViewInject(R.id.ts_u_phone)
    private TextView phone;

    @ViewInject(R.id.ts_real_name)
    private TextView realName;

    @ViewInject(R.id.ts_u_regtime)
    private TextView regtime;

    private Intent intent;
    private Bundle bundle;

    private Map<String, Object> userData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        ViewUtils.inject(this);
        mTitleTv.setText(R.string.personal_info);
        //显示返回按钮
        backBtn.setVisibility(View.VISIBLE);
        appContext = (AppContext) getApplicationContext();
        editBtn.setVisibility(View.VISIBLE);
        editBtn.setImageDrawable(getResources().getDrawable(R.drawable.edit_btn));
        initDataView();
    }

    private void initDataView() {
        Map<String, Object> params = new HashMap<String, Object>();
        User u = null;
        try {
            u = ApiClient.getUser(appContext);
            if (StringUtils.isEmpty(u.getUserUsername())) {
                finish();
            }
        } catch (AppException e) {
            finish();
            e.printStackTrace();
        }
        params.put("uid", u.getUserIid());
        String newUrl = ApiClient._MakeURL(URLs.USER_DETAIL, params, appContext);
        HttpUtils http = new HttpUtils();
        final ProgressDialog pd = ProgressDialog.show(this, null, "请稍后...");

        http.send(HttpRequest.HttpMethod.GET,
                newUrl,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> objectResponseInfo) {
                        pd.dismiss();
                        LogUtils.i(objectResponseInfo.result);
                        JSONObject obj = JSON.parseObject(objectResponseInfo.result);
                        String code = obj.get("code").toString();
                        if (code.equals("1")) {
                            userData = (Map<String, Object>) obj.get("data");
                            renderBaseView(userData);
                        } else {
                            UIHelper.ToastMessage(appContext, obj.get("msg").toString());
                            AppManager.getAppManager().finishActivity();
                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        pd.dismiss();
                    }
                });
    }

    private void renderBaseView(Map<String, Object> data) {
        username.setText(StringUtils.toString(data.get("ts_u_username")));
        phone.setText(StringUtils.toString(data.get("ts_u_phone")));
        realName.setText(StringUtils.toString(data.get("ts_real_name")));
        regtime.setText(StringUtils.toString(data.get("ts_u_regtime")));

    }

    @OnClick({R.id.btn_logout, R.id.back_img, R.id.right_img})
    public void buttonClick(View v) {

        switch (v.getId()) {
            case R.id.back_img:
                this.finish();
                break;
            case R.id.btn_logout:

                LogUtils.i("stop service --->");
                Intent i = new Intent("com.haven.hckp.location");
                stopService(i);

                try {
                    ApiClient.logout(appContext);
                    UIHelper.ToastMessage(appContext, R.string.logont_success);
                    finish();
                } catch (AppException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.right_img:
                UIHelper.showPersonalEditAddRedirect(appContext, userData);
                finish();
                break;
        }
    }
}
