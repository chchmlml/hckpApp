package com.haven.hckp.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haven.hckp.AppContext;
import com.haven.hckp.AppManager;
import com.haven.hckp.R;
import com.haven.hckp.api.ApiClient;
import com.haven.hckp.bean.URLs;
import com.haven.hckp.common.StringUtils;
import com.haven.hckp.common.UIHelper;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.HashMap;
import java.util.Map;

public class HomeDetailActivity extends BaseActivity {

    private AppContext appContext;

    @ViewInject(R.id.title_tv)
    private TextView mTitleTv;

    @ViewInject(R.id.back_img)
    private ImageView backBtn;

    private Intent intent;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_detail);
        ViewUtils.inject(this);

        appContext = (AppContext) getApplicationContext();

        intent = this.getIntent();
        bundle = intent.getExtras();

        mTitleTv.setText(R.string.home_detail);
        //显示返回按钮
        backBtn.setVisibility(View.VISIBLE);
        initDataView();
    }

    @OnClick({R.id.button, R.id.back_img})
    public void buttonClick(View v) {
        if (v.getId() == R.id.back_img) {
            AppManager.getAppManager().finishActivity();
        }
    }

    private void initDataView() {
        String newsId = bundle.getString("di_id");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("di_id", newsId);
        String newUrl = ApiClient._MakeURL(URLs.DISPARH_DETAIL, params, (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE));
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
                            renderView((Map<String, Object>) obj.get("data"));
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

    @ViewInject(R.id.tp_di_sn)
    private TextView diSn;

    @ViewInject(R.id.tp_di_startdate)
    private TextView diStartdate;

    @ViewInject(R.id.tp_di_enddate)
    private TextView diEnddate;

    @ViewInject(R.id.tp_di_status)
    private TextView diStatus;

    @ViewInject(R.id.tp_di_remark)
    private TextView diRemark;

    private void renderView(Map<String, Object> data) {
        Map<String, Object> dispatchInfo = (Map<String, Object>) data.get("dispatch_info");
        diSn.setText(StringUtils.toString(dispatchInfo.get("tp_di_sn")));
        diStartdate.setText(StringUtils.toString(dispatchInfo.get("tp_di_startdate")));
        diEnddate.setText(StringUtils.toString(dispatchInfo.get("tp_di_enddate")));
        String dsipathStatus = getDispathStatus(StringUtils.toString(dispatchInfo.get("tp_di_status")));
        diStatus.setText(dsipathStatus);
        diRemark.setText(StringUtils.toString(dispatchInfo.get("tp_di_remark")));

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
