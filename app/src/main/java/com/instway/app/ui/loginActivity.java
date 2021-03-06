package com.instway.app.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.instway.app.AppContext;
import com.instway.app.AppException;
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
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.HashMap;
import java.util.Map;

public class loginActivity extends BaseActivity {

//    @ViewInject(R.id.title_tv)
//    private TextView mTitleTv;

    @ViewInject(R.id.btn_email)
    private TextView textEmail;

    @ViewInject(R.id.btn_pwd)
    private TextView textPwd;

//    @ViewInject(R.id.btn_login)
//    private BootstrapButton btnLogin;

    @ViewInject(R.id.btn_register)
    private Button btnRegister;

//    @ViewInject(R.id.back_img)
//    private ImageView backBtn;

    private AppContext appContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ViewUtils.inject(this);
        //mTitleTv.setText(R.string.login_page);
        //显示返回按钮
        //backBtn.setVisibility(View.VISIBLE);
        appContext = (AppContext) getApplicationContext();
        try {
            if (AppContext.isLogin(appContext)) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        } catch (AppException e) {
            e.printStackTrace();
        }

        String username = appContext.getProperty("userPhone");
        if(!StringUtils.isEmpty(username)){
            textEmail.setText(username);
        }
    }

    @OnClick({R.id.btn_login, R.id.btn_register, R.id.back_img})
    public void buttonClick(View v) {

        switch (v.getId()) {
//            case R.id.back_img:
//                this.finish();
//                break;
            case R.id.btn_login:
                loginAction();
                break;
            case R.id.btn_register:
                UIHelper.showRegisterRedirect(appContext);
                finish();
                break;
        }
    }

    private void loginAction() {

        String emailStr = textEmail.getText().toString();
        String pwdStr = textPwd.getText().toString();
        if (StringUtils.isEmpty(emailStr) || StringUtils.isEmpty(pwdStr)) {
            UIHelper.ToastMessage(appContext, R.string.login_param_is_null);
            return;
        }
        String newUrl = ApiClient._MakeURL(URLs.LOGIN_POST, new HashMap<String, Object>(),appContext);
        RequestParams params = new RequestParams();
        params.addBodyParameter("email", emailStr);
        params.addBodyParameter("pwd", pwdStr);
        HttpUtils http = new HttpUtils();
        final ProgressDialog pd = ProgressDialog.show(this,null,"请稍后...");

        http.send(HttpRequest.HttpMethod.POST, newUrl, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> objectResponseInfo) {
                pd.dismiss();
                LogUtils.i(objectResponseInfo.result);
                JSONObject obj = JSON.parseObject(objectResponseInfo.result);
                String code = obj.get("code").toString();
                if (code.equals("1")) {
                    Map<String,Object> userObj = (Map<String, Object>) obj.get("data");
                    appContext.setProperty("userId", StringUtils.toString(userObj.get("user_id")));
                    appContext.setProperty("userName", StringUtils.toString(userObj.get("user_username")));
                    appContext.setProperty("userPhone", StringUtils.toString(userObj.get("user_phone")));
                    appContext.setProperty("sessionId",  StringUtils.toString(userObj.get("session_id")));
                    appContext.setProperty("headpic", StringUtils.toString(userObj.get("user_headpic")));
                    UIHelper.ToastMessage(appContext, obj.get("msg").toString());
                    UIHelper.showLoginRedirect(appContext);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
