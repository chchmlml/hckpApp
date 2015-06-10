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
import com.instway.app.widght.ChangeAddressDialog;
import com.instway.app.widght.ChangeBirthDialog;
import com.instway.app.widght.CustomDialog;
import com.lidroid.xutils.ViewUtils;
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

    @ViewInject(R.id.start_date)
    private TextView startDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_filter);
        ViewUtils.inject(this);
        appContext = (AppContext) getApplicationContext();

        mTitleTv.setText(R.string.order_filter);
        //显示返回按钮
        backBtn.setVisibility(View.VISIBLE);

    }

    @OnClick({R.id.back_img,R.id.select_1,R.id.select_2,R.id.select_3})
    public void buttonClick(View v) {
        switch (v.getId())
        {
            case R.id.back_img:
                finish();
                break;
            case R.id.select_1:
                ChangeAddressDialog address1 = new ChangeAddressDialog(this);
                address1.show();
                address1.setAddresskListener(new ChangeAddressDialog.OnAddressCListener() {
                    @Override
                    public void onClick(String province, String city) {
                        startCity.setText(province + "-" + city);
                    }
                });
                break;
            case R.id.select_2:
                ChangeAddressDialog address2 = new ChangeAddressDialog(this);
                address2.show();
                address2.setAddresskListener(new ChangeAddressDialog.OnAddressCListener() {
                    @Override
                    public void onClick(String province, String city) {
                        endCity.setText(province + "-" + city);
                    }
                });
                break;
            case R.id.select_3:
                ChangeBirthDialog mChangeBirthDialog = new ChangeBirthDialog(
                        this);
                mChangeBirthDialog.setDate(2015, 03, 29);
                mChangeBirthDialog.show();
                mChangeBirthDialog.setBirthdayListener(new ChangeBirthDialog.OnBirthListener() {

                    @Override
                    public void onClick(String year, String month, String day) {
                        startDate.setText(year + "-" + month + "-" + day);
                    }
                });
                break;
        }



    }


    public void showAlertDialog(View view) {

        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setMessage("确认搜索");
        builder.setTitle("提示");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.create().show();

    }

}
