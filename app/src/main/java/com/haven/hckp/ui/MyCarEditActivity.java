package com.haven.hckp.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.haven.hckp.AppContext;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MyCarEditActivity extends BaseActivity {

    private AppContext appContext;

    @ViewInject(R.id.title_tv)
    private TextView mTitleTv;

    @ViewInject(R.id.back_img)
    private ImageView backBtn;
    @ViewInject(R.id.button)
    private BootstrapButton button;

    @ViewInject(R.id.car_no)
    private TextView carNo;
    @ViewInject(R.id.car_name)
    private TextView carName;
    @ViewInject(R.id.car_weight)
    private TextView carWeight;
    @ViewInject(R.id.car_width)
    private TextView carWidth;
    @ViewInject(R.id.car_height)
    private TextView carHeight;
    @ViewInject(R.id.car_outdate)
    private TextView carOutdate;
    @ViewInject(R.id.car_length)
    private TextView carLength;

    private Intent intent;
    private Bundle bundle;

    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_edit);
        ViewUtils.inject(this);

        appContext = (AppContext) getApplicationContext();

        intent = this.getIntent();
        bundle = intent.getExtras();

        mTitleTv.setText(R.string.my_cars_edit);
        //显示返回按钮
        backBtn.setVisibility(View.VISIBLE);
        renderBaseView();
        button.setText(R.string.update);

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
    }


    @OnClick({R.id.back_img, R.id.show_date, R.id.button})
    public void buttonClick(View v) {

        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.show_date: {
                Message msg = new Message();
                msg.what = 0;
                this.dateandtimeHandler.sendMessage(msg);
            }
            break;
            case R.id.button:
                createCar();
                break;
        }
    }

    private void createCar() {
        HashMap<String, Object> p = new HashMap<String, Object>();
        RequestParams params = new RequestParams();
        params.addBodyParameter("car_no", StringUtils.toString(carNo.getText()));
        p.put("car_no", StringUtils.toString(carNo.getText()));
        params.addBodyParameter("car_weight", StringUtils.toString(carWeight.getText()));
        p.put("car_weight", StringUtils.toString(carWeight.getText()));
        params.addBodyParameter("car_hight", StringUtils.toString(carHeight.getText()));
        p.put("car_hight", StringUtils.toString(carHeight.getText()));
        params.addBodyParameter("car_length", StringUtils.toString(carLength.getText()));
        p.put("car_length", StringUtils.toString(carLength.getText()));
        params.addBodyParameter("car_width", StringUtils.toString(carWidth.getText()));
        p.put("car_width", StringUtils.toString(carWidth.getText()));
        params.addBodyParameter("car_outdate", StringUtils.toString(carOutdate.getText()));
        p.put("car_outdate", StringUtils.toString(carOutdate.getText()));
        params.addBodyParameter("car_name", StringUtils.toString(carName.getText()));
        p.put("car_name", StringUtils.toString(carName.getText()));
        String newUrl = ApiClient._MakeURL(URLs.EDIT_CAR, p, (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE));
        //LogUtils.i(JSON.toJSONString(params));
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

    /**
     * 处理日期和时间控件的Handler
     */
    Handler dateandtimeHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    showDialog(0);
                    break;
            }
        }

    };


    /**
     * 日期控件的事件
     */
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;

            updateDateDisplay();
        }
    };

    /**
     * 更新日期显示
     */
    private void updateDateDisplay() {
        carOutdate.setText(new StringBuilder().append(mYear).append("-")
                .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-")
                .append((mDay < 10) ? "0" + mDay : mDay));
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
    }

    private void renderBaseView() {
        carNo.setText(bundle.getString("car_no"));
        carName.setText(bundle.getString("car_name"));
        carWeight.setText(bundle.getString("car_weight"));
        carWidth.setText(bundle.getString("car_width"));
        carLength.setText(bundle.getString("car_length"));
        carOutdate.setText(bundle.getString("car_outdate"));
        carHeight.setText(bundle.getString("car_height"));
    }
}
