package com.haven.hckp.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haven.hckp.AppContext;
import com.haven.hckp.AppManager;
import com.haven.hckp.R;
import com.haven.hckp.adapter.DispathDetailAdapter;
import com.haven.hckp.api.ApiClient;
import com.haven.hckp.bean.Dispath;
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
import java.util.List;
import java.util.Map;

public class HomeDispathDetailActivity extends BaseActivity {

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
        setContentView(R.layout.home_dispath_detail);
        ViewUtils.inject(this);

        appContext = (AppContext) getApplicationContext();

        intent = this.getIntent();
        bundle = intent.getExtras();

        mTitleTv.setText(R.string.home_dispath_detail);
        //显示返回按钮
        backBtn.setVisibility(View.VISIBLE);
        renderBaseView(bundle);
    }

    @OnClick({R.id.button, R.id.back_img})
    public void buttonClick(View v) {
        if (v.getId() == R.id.back_img) {
            AppManager.getAppManager().finishActivity();
        }
    }


    @ViewInject(R.id.tp_o_sn)
    private TextView sn;
    @ViewInject(R.id.tp_o_getuser)
    private TextView getuser;
    @ViewInject(R.id.tp_o_loaddate)
    private TextView loaddate;
    @ViewInject(R.id.tp_o_reachdate)
    private TextView reachdate;
    @ViewInject(R.id.tp_og_name)
    private TextView ogName;
    @ViewInject(R.id.tp_og_nums)
    private TextView ogNums;
    @ViewInject(R.id.tp_og_goodspack)
    private TextView ogGoodspack;
    @ViewInject(R.id.tp_tt_status)
    private TextView status;
    @ViewInject(R.id.tp_tt_type)
    private TextView type;

    private void renderBaseView(Bundle bundle) {
        sn.setText(bundle.getString("tp_o_sn"));
        getuser.setText(bundle.getString("tp_o_getuser"));
        loaddate.setText(bundle.getString("tp_o_loaddate"));
        reachdate.setText(bundle.getString("tp_o_reachdate"));
        ogName.setText(bundle.getString("tp_og_name"));
        ogNums.setText(bundle.getString("tp_og_nums"));
        ogGoodspack.setText(bundle.getString("tp_og_goodspack"));
        status.setText(getDispathStatus(bundle.getString("tp_tt_status")));
        String typew = "";
        switch (StringUtils.toInt(bundle.getString("tp_tt_type"))) {
            case 1:
                typew = "正常";
                break;
            case 2:
                typew = "转交";
                break;
        }
        type.setText(typew);
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
