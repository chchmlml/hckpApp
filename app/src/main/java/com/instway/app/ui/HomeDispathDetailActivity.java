package com.instway.app.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.instway.app.AppContext;
import com.instway.app.AppManager;
import com.instway.app.R;
import com.instway.app.api.ApiClient;
import com.instway.app.bean.URLs;
import com.instway.app.common.StringUtils;
import com.instway.app.common.UIHelper;
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

    @OnClick({R.id.back_img, R.id.goods_input_1, R.id.goods_input_2, R.id.call_load, R.id.call_get})
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
            case R.id.call_load:
                UIHelper.callPhone(appContext, loadPhone);
                break;
            case R.id.call_get:
                UIHelper.callPhone(appContext, getPhone);
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

    @ViewInject(R.id.load_user)
    private TextView load_user;

    @ViewInject(R.id.reachdate)
    private TextView reachdate;

    @ViewInject(R.id.getaddress)
    private TextView getaddress;

    @ViewInject(R.id.getuser)
    private TextView getuser;

    @ViewInject(R.id.category)
    private TextView category;

    @ViewInject(R.id.goodspack)
    private TextView goodspack;

    @ViewInject(R.id.loaddate)
    private TextView loaddate;

    @ViewInject(R.id.loadaddress)
    private TextView loadaddress;

    @ViewInject(R.id.status_icon)
    private ImageView status;

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

    String loadPhone;
    String getPhone;
    String tp_tt_id;

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
                    tp_tt_id = StringUtils.toString(transObj.get("tp_tt_id"));
                    load_user.setText(StringUtils.toString(transObj.get("tp_o_loaduser")) + "  " + StringUtils.toString(transObj.get("tp_o_loadphone")));
                    loaddate.setText(StringUtils.toString(transObj.get("tp_o_loaddate")) + "  " + StringUtils.toString(transObj.get("tp_o_loadhour")));
                    loadaddress.setText(StringUtils.toString(transObj.get("tp_o_loadaddress")));
                    loadPhone = StringUtils.toString(transObj.get("tp_o_loadphone"));

                    getuser.setText(StringUtils.toString(transObj.get("tp_o_getuser")) + "  " + StringUtils.toString(transObj.get("tp_o_getphone")));
                    reachdate.setText(StringUtils.toString(transObj.get("tp_o_reachdate")) + "  " + StringUtils.toString(transObj.get("tp_o_reachhour")));
                    getaddress.setText(StringUtils.toString(transObj.get("tp_o_getaddress")));
                    getPhone = StringUtils.toString(transObj.get("tp_o_getphone"));

                    category.setText(StringUtils.toString(transObj.get("tp_og_category")));
                    goodspack.setText(StringUtils.toString(transObj.get("tp_og_goodspack")));

                    startCity.setText(StringUtils.toString(transObj.get("tp_o_start_city")));
                    endCity.setText(StringUtils.toString(transObj.get("tp_o_end_city")));
                    //reachDate.setText(StringUtils.toString(transObj.get("tp_o_reachdate")));
                    goodsWeight.setText(StringUtils.toString(transObj.get("tp_tt_weight")));
                    goodsNum.setText(StringUtils.toString(transObj.get("tp_tt_nums")));
                    remark.setText(StringUtils.toString(transObj.get("tp_o_remark")));
                    String getGoods = StringUtils.toString(transObj.get("tp_tt_getweight")) + "/" + StringUtils.toString(transObj.get("tp_tt_getnums"));
                    goodsGet.setText(getGoods);
                    String sendGoods = StringUtils.toString(transObj.get("tp_tt_sendweight")) + "/" + StringUtils.toString(transObj.get("tp_tt_sendnums"));
                    goodsSend.setText(sendGoods);
                    int statusCode = StringUtils.toInt(transObj.get("tp_o_status"));
                    switch (statusCode) {
                        case 1:
                        case 2:
                            status.setImageDrawable(appContext.getResources().getDrawable(R.drawable.trans_status_3));
                            break;
                        case 3:
                            status.setImageDrawable(appContext.getResources().getDrawable(R.drawable.trans_status_1));
                            break;
                        case 4:
                            status.setImageDrawable(appContext.getResources().getDrawable(R.drawable.trans_status_0));
                            break;
                        case 5:
                        case 6:
                        case 7:
                            status.setImageDrawable(appContext.getResources().getDrawable(R.drawable.trans_status_2));
                            break;
                    }
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
