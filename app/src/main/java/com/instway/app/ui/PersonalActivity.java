package com.instway.app.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.instway.app.AppContext;
import com.instway.app.AppException;
import com.instway.app.AppManager;
import com.instway.app.R;
import com.instway.app.api.ApiClient;
import com.instway.app.bean.URLs;
import com.instway.app.bean.User;
import com.instway.app.common.StringUtils;
import com.instway.app.common.UIHelper;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.HashMap;
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

    @ViewInject(R.id.ID_thumb)
    private ImageView IDThumb;

    @ViewInject(R.id.head_thumb)
    private ImageView headThumb;

    @ViewInject(R.id.drive_thumb)
    private ImageView driveThumb;

    @ViewInject(R.id.ts_u_phone)
    private TextView phone;
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
//        editBtn.setVisibility(View.VISIBLE);
//        editBtn.setImageDrawable(getResources().getDrawable(R.drawable.edit_btn));
        initDataView();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        params.put("r", StringUtils.randomNum());
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

    String headSrc;
    String idSrc;
    String driveId;

    private void renderBaseView(Map<String, Object> data) {
        username.setText(StringUtils.toString(data.get("ts_u_username")));
        phone.setText(StringUtils.toString(data.get("ts_u_phone")));
        //身份证 头像 驾驶证
        BitmapUtils bitmapUtils = new BitmapUtils(appContext);
        headSrc = StringUtils.toString(data.get("ts_u_headpic"));
        if (!StringUtils.isEmpty(headSrc)) {
            bitmapUtils.display(headThumb, headSrc);
        }
        idSrc = StringUtils.toString(data.get("tp_d_idcard"));
        if (!StringUtils.isEmpty(idSrc)) {
            bitmapUtils.display(IDThumb, idSrc);
        }
        driveId = StringUtils.toString(data.get("tp_d_drilic"));
        if (!StringUtils.isEmpty(driveId)) {
            bitmapUtils.display(driveThumb, driveId);
        }
    }

    @OnClick({R.id.btn_logout, R.id.back_img, R.id.change_pwd_btn, R.id.form_car_id, R.id.form_person_id, R.id.form_drive_id})
    public void buttonClick(View v) {

        switch (v.getId()) {
            case R.id.back_img:
                this.finish();
                break;
            case R.id.form_car_id:
                UIHelper.showTakephotoRedirect(appContext, 1, headSrc);
                break;
            case R.id.form_person_id:
                UIHelper.showTakephotoRedirect(appContext, 2,idSrc);
                break;
            case R.id.form_drive_id:
                UIHelper.showTakephotoRedirect(appContext, 3,driveId);
                break;
            case R.id.btn_logout:

                try {
                    ApiClient.logout(appContext);
                    UIHelper.ToastMessage(appContext, R.string.logont_success);
                    finish();
                } catch (AppException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.change_pwd_btn:
                UIHelper.showPersonalEditAddRedirect(appContext, userData);
                break;
        }
    }
}
