package com.haven.hckp.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
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


    @OnClick({R.id.btn_submit,R.id.back_img})
    public void buttonClick(View v) {

        switch (v.getId()) {
            case R.id.back_img:
                this.finish();
                break;
            case R.id.btn_submit:
                loginAction();
                break;
        }
    }

    private void loginAction() {

        String phone = StringUtils.toString(inputPhone.getText());
        String pwd = StringUtils.toString(inputPwd.getText());
        String pwd2 = StringUtils.toString(inputPwd2.getText());
        String username = StringUtils.toString(inputUsername.getText());

        if (StringUtils.isEmpty(phone)) {
            UIHelper.ToastMessage(appContext,R.string.register_param1_is_null);
            return;
        }
        if (StringUtils.isEmpty(pwd)) {
            UIHelper.ToastMessage(appContext,R.string.register_param2_is_null);
            return;
        }
        if (StringUtils.isEmpty(pwd2)) {
            UIHelper.ToastMessage(appContext,R.string.register_param3_is_null);
            return;
        }
        if (StringUtils.isEmpty(username)) {
            UIHelper.ToastMessage(appContext,R.string.register_param4_is_null);
            return;
        }

        if(!pwd.equals(pwd2)){
            UIHelper.ToastMessage(appContext,R.string.register_param5_is_null);
            return;
        }

        String newUrl = ApiClient._MakeURL(URLs.REGISTER_POST, new HashMap<String, Object>(),(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE));
        RequestParams params = new RequestParams();
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("pwd", pwd);
        params.addBodyParameter("username", username);
        HttpUtils http = new HttpUtils();
        final ProgressDialog pd = ProgressDialog.show(this,null,"请稍后...");

        http.send(HttpRequest.HttpMethod.POST, newUrl, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> objectResponseInfo) {
                pd.dismiss();
                JSONObject obj = JSON.parseObject(objectResponseInfo.result);
                String code = obj.get("code").toString();
                if (code.equals("1")) {
                    Map<String,Object> userObj = (Map<String, Object>) obj.get("data");
                    appContext.setProperty("userId", StringUtils.toString(userObj.get("user_id")));
                    appContext.setProperty("userName", StringUtils.toString(userObj.get("user_username")));
                    appContext.setProperty("userPhone", StringUtils.toString(userObj.get("user_phone")));
                    appContext.setProperty("sessionId",  StringUtils.toString(userObj.get("session_id")));
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
