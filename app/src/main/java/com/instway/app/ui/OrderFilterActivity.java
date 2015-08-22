package com.instway.app.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.instway.app.AppContext;
import com.instway.app.R;
import com.instway.app.common.StringUtils;
import com.instway.app.widght.ChangeAddressDialog;
import com.instway.app.widght.ChangeBirthDialog;
import com.instway.app.widght.CustomDialog;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class OrderFilterActivity extends BaseActivity {


    private AppContext appContext;
    private Intent intent;

    @ViewInject(R.id.title_tv)
    private TextView mTitleTv;

    @ViewInject(R.id.back_img)
    private ImageView backBtn;

    @ViewInject(R.id.start_city)
    private TextView startCity;

    @ViewInject(R.id.end_city)
    private TextView endCity;

//    @ViewInject(R.id.start_date)
//    private TextView startDate;

    private String pStartProvince;
    private String pStartCity;
    private String pEndProvince;
    private String pEndCity;
    private String pDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_filter);
        ViewUtils.inject(this);
        appContext = (AppContext) getApplicationContext();

        mTitleTv.setText(R.string.order_filter);
        //显示返回按钮
        backBtn.setVisibility(View.VISIBLE);

        pStartProvince = "";
        pStartCity = "";
        pEndProvince = "";
        pEndCity = "";
        pDate = "";
    }

    @OnClick({R.id.back_img, R.id.select_1, R.id.select_2, R.id.button})
    public void buttonClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.select_1:
                ChangeAddressDialog address1 = new ChangeAddressDialog(this);
                address1.show();
                address1.setAddresskListener(new ChangeAddressDialog.OnAddressCListener() {
                    @Override
                    public void onClick(String province, String city) {
                        LogUtils.i(province);
                        if(province == "全境" || StringUtils.isEmpty(city)){
                            startCity.setText("全境");
                            pStartProvince = "全境";
                            pStartCity = "";
                        }else{
                            startCity.setText(province + "-" + city);
                            pStartProvince = province;
                            pStartCity = city;
                        }
                    }
                });
                break;
            case R.id.select_2:
                ChangeAddressDialog address2 = new ChangeAddressDialog(this);
                address2.show();
                address2.setAddresskListener(new ChangeAddressDialog.OnAddressCListener() {
                    @Override
                    public void onClick(String province, String city) {
                        if(province == "全境"|| StringUtils.isEmpty(city)){
                            endCity.setText("全境");
                            pEndProvince = "全境";
                            pEndCity = "";
                        }else{
                            endCity.setText(province + "-" + city);
                            pEndProvince = province;
                            pEndCity = city;
                        }
                    }
                });
                break;
//            case R.id.select_3:
//                ChangeBirthDialog mChangeBirthDialog = new ChangeBirthDialog(
//                        this);
//                mChangeBirthDialog.setDate(2015, 03, 29);
//                mChangeBirthDialog.show();
//                mChangeBirthDialog.setBirthdayListener(new ChangeBirthDialog.OnBirthListener() {
//
//                    @Override
//                    public void onClick(String year, String month, String day) {
//                        pDate = year + "-" + month + "-" + day;
//                        startDate.setText(pDate);
//                    }
//                });
//                break;
            case R.id.button:
                showAlertDialog();
                break;
        }

    }


    public static final int RESULT_CODE = 1;

    public void showAlertDialog() {
        Intent intent = new Intent();
        intent.putExtra("startProvince", pStartProvince);
        intent.putExtra("startCity", pStartCity);
        intent.putExtra("endProvince", pEndProvince);
        intent.putExtra("endCity", pEndCity);
        intent.putExtra("date", pDate);
        setResult(RESULT_CODE, intent);
        finish();
    }

}
