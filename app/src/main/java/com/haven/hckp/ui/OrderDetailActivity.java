package com.haven.hckp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
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
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OrderDetailActivity extends ActionBarActivity {

    private static final String TAG = "OrderDetailActivity";
    private TextView mTitleTv;
    private AppContext appContext;

    private LinearLayout linearLoad;
    private LinearLayout linearDesc;
    private LinearLayout linearForm;

    private TextView locationInfo;
    private TextView carTeam;
    private TextView startTime;
    private TextView endTime;
    private TextView desc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail);

        linearLoad = (LinearLayout) findViewById(R.id.linear_load);
        linearDesc = (LinearLayout) findViewById(R.id.linear_desc);
        linearForm = (LinearLayout) findViewById(R.id.linear_form);

        locationInfo = (TextView) findViewById(R.id.location_info);
        carTeam = (TextView) findViewById(R.id.tean_car_name);
        startTime = (TextView) findViewById(R.id.start_time);
        endTime = (TextView) findViewById(R.id.end_time);
        desc = (TextView) findViewById(R.id.desc);

        mTitleTv = (TextView) findViewById(R.id.title_tv);
        mTitleTv.setText(R.string.order_detail);
        appContext = (AppContext) getApplicationContext();

        initDataView();

    }

    private void initDataView() {
        Intent intent = this.getIntent();        //获取已有的intent对象
        Bundle bundle = intent.getExtras();    //获取intent里面的bundle对象
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
                        renderView((Map<String, Object>) obj.get("data"));
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
