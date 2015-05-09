package com.haven.hckp.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.haven.hckp.AppContext;
import com.haven.hckp.AppManager;
import com.haven.hckp.R;
import com.haven.hckp.api.ApiClient;
import com.haven.hckp.bean.URLs;
import com.haven.hckp.common.DateUtils;
import com.haven.hckp.common.StringUtils;
import com.haven.hckp.common.UIHelper;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.apache.commons.httpclient.util.DateUtil;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class OrderDetailActivity extends BaseActivity {

    private AppContext appContext;

    @ViewInject(R.id.title_tv)
    private TextView mTitleTv;

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

    @ViewInject(R.id.order_end_time)
    private TextView orderEndTime;

    @ViewInject(R.id.desc)
    private TextView desc;

    @ViewInject(R.id.button)
    private BootstrapButton button;

    @ViewInject(R.id.edit_input)
    private EditText priceInput;

    @ViewInject(R.id.back_img)
    private ImageView backBtn;

    private Intent intent;
    private Bundle bundle;

    private String orderType;

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
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("您确定报价？")
                    .setConfirmText("确定")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            String priceStr = priceInput.getText().toString();
                            if (!StringUtils.isEmpty(priceStr)) {
                                Double price = Double.parseDouble(priceStr);
                                getPricePort(price);
                            } else {
                                UIHelper.ToastMessage(appContext, R.string.error_input);
                            }
                        }
                    })
                    .showCancelButton(true)
                    .setCancelText("取消")
                    .setCancelClickListener(null)
                    .show();
        } else if (v.getId() == R.id.back_img) {
            AppManager.getAppManager().finishActivity();
        }
    }

    private void getPricePort(Double price) {
        String newsId = bundle.getString("news_id");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("diy_id", newsId);
        params.put("diy_price", price);
        String newUrl = ApiClient._MakeURL(URLs.NEWS_DETAIL_POST, params,appContext);
        HttpUtils http = new HttpUtils();
        final ProgressDialog pd = ProgressDialog.show(this, null, "请稍后...");
        http.send(HttpRequest.HttpMethod.GET,
                newUrl,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> objectResponseInfo) {
                        pd.dismiss();
                        JSONObject obj = JSON.parseObject(objectResponseInfo.result);
                        String code = obj.get("code").toString();
                        if (code.equals("1")) {
                            AppManager.getAppManager().finishActivity();
                        }
                        UIHelper.ToastMessage(appContext, R.string.success_input);
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        pd.dismiss();
                    }
                });
    }

    private void initDataView() {
        String newsId = bundle.getString("news_id");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("diy_id", newsId);
        String newUrl = ApiClient._MakeURL(URLs.NEWS_DETAIL + "&r=" + StringUtils.randomNum(), params,appContext);
        HttpUtils http = new HttpUtils();
        final ProgressDialog pd = ProgressDialog.show(this, null, "请稍后...");

        http.send(HttpRequest.HttpMethod.GET,
                newUrl,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> objectResponseInfo) {
                        pd.dismiss();
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

    private void renderView(Map<String, Object> news) {
        locationInfo.setText(news.get("tp_diy_start_city") + "-" + news.get("tp_diy_end_city"));
        carTeam.setText(StringUtils.toString(news.get("tp_tc_name")));
        startTime.setText(StringUtils.toString(news.get("tp_diy_startdate")));
        endTime.setText(StringUtils.toString(news.get("tp_diy_enddate")));
        desc.setText(StringUtils.toString(news.get("tp_diy_desc")));

        orderEndTime.setText(DateUtils.getDateToString(StringUtils.toString(news.get("tp_diy_endtime"))));
        String tpType = StringUtils.toString(news.get("tp_diy_type"));
        this.orderType = tpType;
        if ("2".equals(tpType)) {
            priceInput.setText(StringUtils.toString(news.get("tp_diyp_price")));
            priceInput.setCursorVisible(false);
            priceInput.setFocusable(false);
            priceInput.setFocusableInTouchMode(false);

        } else {
            priceInput.setText(StringUtils.toString(news.get("tp_diyp_price")));
        }
    }

}
