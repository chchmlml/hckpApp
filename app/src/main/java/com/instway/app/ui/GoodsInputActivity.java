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
import com.beardedhen.androidbootstrap.BootstrapButton;
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

public class GoodsInputActivity extends BaseActivity {

    @ViewInject(R.id.title_tv)
    private TextView mTitleTv;

    @ViewInject(R.id.back_img)
    private ImageView backBtn;

    @ViewInject(R.id.weight)
    private EditText weight;

    @ViewInject(R.id.nums)
    private EditText nums;

    @ViewInject(R.id.button)
    private BootstrapButton button;

    private AppContext appContext;

    private Intent intent;
    private Bundle bundle;

    private boolean enableEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_input_1);
        ViewUtils.inject(this);
        intent = this.getIntent();
        bundle = intent.getExtras();

        if (bundle.getString("type").equals("send")) {
            mTitleTv.setText("收货编辑");
        } else {
            mTitleTv.setText("签收编辑");
        }

        weight.setText("0".equals(bundle.getString("weight")) ? "" : bundle.getString("weight"));
        nums.setText("0".equals(bundle.getString("nums")) ? "" : bundle.getString("nums"));
        enableEdit = ("0".equals(bundle.getString("weight")) || "0".equals(bundle.getString("nums"))) ? true : false;
        //显示返回按钮
        backBtn.setVisibility(View.VISIBLE);
        appContext = (AppContext) getApplicationContext();
    }

    @OnClick({R.id.button, R.id.back_img, R.id.goods_input_3})
    public void buttonClick(View v) {

        switch (v.getId()) {
            case R.id.back_img:
                this.finish();
                break;
            case R.id.button:
                if (enableEdit) {
                    saveTransGoodsDetail();
                } else {
                    UIHelper.ToastMessage(appContext, "您已经编辑过，不能再编辑了");
                }
                break;
            case R.id.goods_input_3:
                UIHelper.showTakephotoRedirect3(appContext, 5, bundle.getString("tt_id"), bundle.getString("type"));
                break;
        }
    }

    private void saveTransGoodsDetail() {
        String getweight = StringUtils.toString(weight.getText());
        String getnums = StringUtils.toString(nums.getText());
        if (StringUtils.isEmpty(getweight) || StringUtils.isEmpty(getnums)) {
            UIHelper.ToastMessage(appContext, "请填写数量和重量信息");
            return;
        }
        String url;
        RequestParams params = new RequestParams();
        params.addBodyParameter("tp_tt_id", bundle.getString("tt_id"));
        if (bundle.getString("type").equals("get")) {
            url = URLs.ShippingTransport_POST;
        } else {
            url = URLs.SendTransport_POST;
        }
        params.addBodyParameter("weight", getweight);
        params.addBodyParameter("nums", getnums);
        String newUrl = ApiClient._MakeURL(url, new HashMap<String, Object>(), appContext);

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
                    Map<String, Object> news = new HashMap<String, Object>();
                    news.put("tp_tt_id", bundle.getString("tt_id"));
                    UIHelper.showDispathDetailRedirect(appContext, news);
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
