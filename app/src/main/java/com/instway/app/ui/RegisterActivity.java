package com.instway.app.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
import java.util.Map;

public class RegisterActivity extends BaseActivity {

    private AppContext appContext;

    @ViewInject(R.id.title_tv)
    private TextView mTitleTv;

    @ViewInject(R.id.back_img)
    private ImageView backBtn;

    @ViewInject(R.id.input_phone)
    private EditText inputPhone;

    @ViewInject(R.id.input_pwd)
    private EditText inputPwd;

    @ViewInject(R.id.input_pwd2)
    private EditText inputPwd2;

    @ViewInject(R.id.input_username)
    private EditText inputUsername;

    @ViewInject(R.id.input_realname)
    private EditText inputRealname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ViewUtils.inject(this);
        mTitleTv.setText(R.string.register_page);
        //显示返回按钮
        backBtn.setVisibility(View.VISIBLE);
        appContext = (AppContext) getApplicationContext();
    }


    @OnClick({R.id.btn_submit, R.id.back_img, R.id.btn_login})
    public void buttonClick(View v) {

        switch (v.getId()) {
            case R.id.back_img:
                this.finish();
                break;
            case R.id.btn_submit:
                loginAction();
                break;
            case R.id.btn_login:
                UIHelper.showLoginRedirect(appContext);
                break;
        }
    }

    private void loginAction() {

        String phone = StringUtils.toString(inputPhone.getText());
        String pwd = StringUtils.toString(inputPwd.getText());
        String pwd2 = StringUtils.toString(inputPwd2.getText());
        String username = StringUtils.toString(inputUsername.getText());
        String realname = StringUtils.toString(inputRealname.getText());

        if (StringUtils.isEmpty(phone)) {
            UIHelper.ToastMessage(appContext, R.string.register_param1_is_null);
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
        if (StringUtils.isEmpty(username)) {
            UIHelper.ToastMessage(appContext, R.string.register_param4_is_null);
            return;
        }

        if (StringUtils.isEmpty(realname)) {
            UIHelper.ToastMessage(appContext, R.string.register_param7_is_null);
            return;
        }

        if (!pwd.equals(pwd2)) {
            UIHelper.ToastMessage(appContext, R.string.register_param5_is_null);
            return;
        }

        String newUrl = ApiClient._MakeURL(URLs.REGISTER_POST, new HashMap<String, Object>(), appContext);
        RequestParams params = new RequestParams();
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("pwd", pwd);
        params.addBodyParameter("username", username);
        params.addBodyParameter("realname", realname);
        HttpUtils http = new HttpUtils();
        final ProgressDialog pd = ProgressDialog.show(this, null, "请稍后...");

        http.send(HttpRequest.HttpMethod.POST, newUrl, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> objectResponseInfo) {
                pd.dismiss();
                JSONObject obj = JSON.parseObject(objectResponseInfo.result);
                String code = obj.get("code").toString();
                if (code.equals("1")) {
                    Map<String, Object> userObj = (Map<String, Object>) obj.get("data");
                    appContext.setProperty("userId", StringUtils.toString(userObj.get("user_id")));
                    appContext.setProperty("userName", StringUtils.toString(userObj.get("user_username")));
                    appContext.setProperty("userPhone", StringUtils.toString(userObj.get("user_phone")));
                    appContext.setProperty("sessionId", StringUtils.toString(userObj.get("session_id")));
                    appContext.setProperty("headpic", StringUtils.toString(userObj.get("ts_u_headpic")));
                    UIHelper.ToastMessage(appContext, obj.get("msg").toString());
                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                    startActivity(intent);
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
