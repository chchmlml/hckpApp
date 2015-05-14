package com.haven.hckp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.haven.hckp.AppContext;
import com.haven.hckp.AppException;
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
import com.lidroid.xutils.util.LogUtils;
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

    private AppContext appContext;

    private Intent intent;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_input_1);
        ViewUtils.inject(this);
        intent = this.getIntent();
        bundle = intent.getExtras();

        if (bundle.getString("type").equals("get")) {
            mTitleTv.setText("收货编辑");
        } else {
            mTitleTv.setText("签收编辑");
        }

        weight.setText(bundle.getString("weight"));
        nums.setText(bundle.getString("nums"));

        //显示返回按钮
        backBtn.setVisibility(View.VISIBLE);
        appContext = (AppContext) getApplicationContext();
    }

    @OnClick({R.id.button, R.id.back_img})
    public void buttonClick(View v) {

        switch (v.getId()) {
            case R.id.back_img:
                this.finish();
                break;
            case R.id.button:
                saveTransGoodsDetail();
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
            params.addBodyParameter("tp_tt_getweight", getweight);
            params.addBodyParameter("tp_tt_getnums", getnums);
            url = URLs.ShippingTransport_POST;
        } else {
            params.addBodyParameter("tp_tt_sendweight", getweight);
            params.addBodyParameter("tp_tt_sendnums", getnums);
            url = URLs.SendTransport_POST;
        }
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
