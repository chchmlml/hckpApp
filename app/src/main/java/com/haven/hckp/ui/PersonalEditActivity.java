package com.haven.hckp.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.HashMap;

public class PersonalEditActivity extends BaseActivity {

    private AppContext appContext;

    @ViewInject(R.id.title_tv)
    private TextView mTitleTv;

    @ViewInject(R.id.back_img)
    private ImageView backBtn;

    @ViewInject(R.id.input_old_pwd)
    private EditText inputOldPwd;

    @ViewInject(R.id.input_pwd)
    private EditText inputPwd;

    @ViewInject(R.id.input_pwd2)
    private EditText inputPwd2;

    private Intent intent;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_edit);
        ViewUtils.inject(this);

        appContext = (AppContext) getApplicationContext();

        intent = this.getIntent();
        bundle = intent.getExtras();

        mTitleTv.setText(R.string.personal_edit);
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
                updateUser();
                break;
        }
    }

    private void updateUser() {
        String pwd = StringUtils.toString(inputPwd.getText());
        String oldPwd = StringUtils.toString(inputOldPwd.getText());
        String pwd2 = StringUtils.toString(inputPwd2.getText());
        if (StringUtils.isEmpty(oldPwd)) {
            UIHelper.ToastMessage(appContext, R.string.register_param2_is_null);
            return;
        }
        if (StringUtils.isEmpty(pwd)) {
            UIHelper.ToastMessage(appContext, R.string.register_param2_is_null);
            return;
        }
        if (StringUtils.isEmpty(pwd2)) {
            UIHelper.ToastMessage(appContext, R.string.register_param3_is_null);
            return;
        }
        if (!pwd.equals(pwd2)) {
            UIHelper.ToastMessage(appContext, R.string.register_param5_is_null);
            return;
        }

        HashMap<String, Object> p = new HashMap<String, Object>();
        RequestParams params = new RequestParams();
        params.addBodyParameter("name", bundle.getString("username"));
        params.addBodyParameter("phone", bundle.getString("phone"));
        params.addBodyParameter("pwd", pwd);
        String newUrl = ApiClient._MakeURL(URLs.EDIT_CAR, p, appContext);
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
}
