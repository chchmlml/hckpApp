package com.haven.hckp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haven.hckp.AppContext;
import com.haven.hckp.R;
import com.haven.hckp.api.ApiClient;
import com.haven.hckp.bean.News;
import com.haven.hckp.bean.URLs;
import com.haven.hckp.common.StringUtils;
import com.haven.hckp.common.UIHelper;
import com.haven.hckp.widght.NewDataToast;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OrderDetailActivity extends ActionBarActivity {

    private static final String TAG = "OrderDetailActivity";

    private AppContext appContext;

    @ViewInject(R.id.title_tv)
    private TextView mTitleTv;

    @ViewInject(R.id.linear_load)
    private LinearLayout linearLoad;

    @ViewInject(R.id.linear_desc)
    private LinearLayout linearDesc;

    @ViewInject(R.id.linear_form)
    private LinearLayout linearForm;

    @ViewInject(R.id.location_info)
    private TextView locationInfo;

    @ViewInject(R.id.carteam_name)
    private TextView carTeam;

    @ViewInject(R.id.start_time)
    private TextView startTime;

    @ViewInject(R.id.end_time)
    private TextView endTime;

    @ViewInject(R.id.desc)
    private TextView desc;

    @ViewInject(R.id.button)
    private Button button;

    @ViewInject(R.id.edit_input)
    private EditText priceInput;

    @ViewInject(R.id.back_img)
    private ImageView backBtn;

    private Intent intent;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail);
        ViewUtils.inject(this);
        appContext = (AppContext) getApplicationContext();

        intent = this.getIntent();
        bundle = intent.getExtras();

        mTitleTv.setText(R.string.order_detail);
        //显示返回按钮
        backBtn.setVisibility(View.VISIBLE);
        initDataView();
    }

    @OnClick({R.id.button, R.id.back_img})
    public void buttonClick(View v) {
        if (v.getId() == R.id.button) {
            linearLoad.setVisibility(View.VISIBLE);
            String priceStr = priceInput.getText().toString();
            if (!StringUtils.isEmpty(priceStr)) {
                Double price = Double.parseDouble(priceStr);
                Log.i(TAG, "价格:" + price);
                getPricePort(price);
            } else {
                linearLoad.setVisibility(View.GONE);
                UIHelper.ToastMessage(appContext,R.string.error_input);
                //NewDataToast.makeText(appContext, getString(R.string.error_input, false), false).show();
            }
        } else if (v.getId() == R.id.back_img) {
            OrderDetailActivity.this.finish();
        }
    }

    private void getPricePort(Double price) {
        String newsId = bundle.getString("news_id");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("diy_id", newsId);
        params.put("diy_price", price);
        String newUrl = ApiClient._MakeURL(URLs.NEWS_DETAIL_POST, params);
        HttpUtils http = new HttpUtils();
        Log.i(TAG, "newUrl：" + newUrl);
        http.send(HttpRequest.HttpMethod.GET,
                newUrl,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> objectResponseInfo) {
                        Log.i(TAG, "加载数据成功" + JSON.toJSONString(objectResponseInfo.result));
                        JSONObject obj = JSON.parseObject(objectResponseInfo.result);
                        linearLoad.setVisibility(View.GONE);
                        //NewDataToast.makeText(appContext, getString(R.string.success_input, false), false).show();
                        UIHelper.ToastMessage(appContext,R.string.success_input);
                        OrderDetailActivity.this.finish();
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {

                    }
                });
    }

    private void initDataView() {
        String newsId = bundle.getString("news_id");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("diy_id", newsId);
        String newUrl = ApiClient._MakeURL(URLs.NEWS_DETAIL, params);
        HttpUtils http = new HttpUtils();
        Log.i(TAG, "newUrl：" + newUrl);
        http.send(HttpRequest.HttpMethod.GET,
                newUrl,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> objectResponseInfo) {
                        Log.i(TAG, "加载数据成功" + JSON.toJSONString(objectResponseInfo.result));
                        JSONObject obj = JSON.parseObject(objectResponseInfo.result);
                        String code = obj.get("code").toString();
                        if (code.equals("1")) {
                            renderView((Map<String, Object>) obj.get("data"));
                        } else {
                            UIHelper.ToastMessage(appContext, obj.get("msg").toString());
                            finish();
                        }
                        linearLoad.setVisibility(View.GONE);
                        linearDesc.setVisibility(View.VISIBLE);
                        linearForm.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {

                    }
                });
    }

    private void renderView(Map<String, Object> news) {
        locationInfo.setText(news.get("tp_diy_start_city") + "-" + news.get("tp_diy_end_city"));
        carTeam.setText(StringUtils.toString(news.get("tp_tc_name")));
        startTime.setText(StringUtils.toString(news.get("tp_diy_startdate")));
        endTime.setText(StringUtils.toString(news.get("tp_diy_enddate")));
        desc.setText(StringUtils.toString(news.get("tp_diy_desc")));
    }

}
