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
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
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

    @OnClick({R.id.back_img, R.id.goods_input_1, R.id.goods_input_2})
    public void buttonClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.goods_input_1:
                ShippingTransport();
                break;
            case R.id.goods_input_2:
                SendTransport();
                break;
        }
    }

    /**
     * 装货报备
     */
    private void ShippingTransport() {
        Intent intent = new Intent(appContext, GoodsInputActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle b = new Bundle();
        b.putString("tt_id", StringUtils.toString(bundle.getString("tt_id")));
        b.putString("type", "get");
        String[] WeightNums = StringUtils.toString(goodsGet.getText()).split("/");
        b.putString("weight", WeightNums[0]);
        b.putString("nums", WeightNums[1]);
        intent.putExtras(b);
        appContext.startActivity(intent);
        finish();
    }


    /**
     * 装货报备
     */
    private void SendTransport() {
        Intent intent = new Intent(appContext, GoodsInputActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle b = new Bundle();
        b.putString("type", "send");
        b.putString("tt_id", StringUtils.toString(bundle.getString("tt_id")));
        String[] WeightNums = StringUtils.toString(goodsSend.getText()).split("/");
        b.putString("weight", WeightNums[0]);
        b.putString("nums", WeightNums[1]);
        intent.putExtras(b);
        appContext.startActivity(intent);
        finish();
    }

    @ViewInject(R.id.start_city)
    private TextView startCity;

    @ViewInject(R.id.end_city)
    private TextView endCity;

    @ViewInject(R.id.reach_date)
    private TextView reachDate;

    @ViewInject(R.id.status)
    private TextView status;

    @ViewInject(R.id.goods_weight)
    private TextView goodsWeight;

    @ViewInject(R.id.goods_num)
    private TextView goodsNum;

    @ViewInject(R.id.remark)
    private TextView remark;

    @ViewInject(R.id.goods_get)
    private TextView goodsGet;

    @ViewInject(R.id.goods_send)
    private TextView goodsSend;

    private void renderBaseView(Bundle bundle) {

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("tt_id", bundle.getString("tt_id"));
        params.put("r", StringUtils.randomNum());
        String newUrl = ApiClient._MakeURL(URLs.TRANSDETAIL, params, appContext);
        HttpUtils http = new HttpUtils();
        final ProgressDialog pd = ProgressDialog.show(this, null, "请稍后...");

        http.send(HttpRequest.HttpMethod.POST, newUrl, null, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> objectResponseInfo) {
                pd.dismiss();
                JSONObject obj = JSON.parseObject(objectResponseInfo.result);
                String code = obj.get("code").toString();
                if (code.equals("1")) {
                    Map<String, Object> transObj = (Map<String, Object>) obj.get("data");
                    startCity.setText(StringUtils.toString(transObj.get("tp_o_start_city")));
                    endCity.setText(StringUtils.toString(transObj.get("tp_o_end_city")));
                    reachDate.setText(StringUtils.toString(transObj.get("tp_o_reachdate")));
                    status.setText(StringUtils.toString(transObj.get("tp_o_status")));
                    goodsWeight.setText(StringUtils.toString(transObj.get("tp_tt_weight")));
                    goodsNum.setText(StringUtils.toString(transObj.get("tp_tt_nums")));
                    remark.setText(StringUtils.toString(transObj.get("tp_o_remark")));
                    String getGoods = StringUtils.toString(transObj.get("tp_tt_getweight")) + "/" + StringUtils.toString(transObj.get("tp_tt_getnums"));
                    goodsGet.setText(getGoods);
                    String sendGoods = StringUtils.toString(transObj.get("tp_tt_sendweight")) + "/" + StringUtils.toString(transObj.get("tp_tt_sendnums"));
                    goodsSend.setText(sendGoods);
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
