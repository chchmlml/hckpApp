package com.instway.app.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.instway.app.AppContext;
import com.instway.app.R;
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

    @OnClick({R.id.back_img})
    public void buttonClick(View v) {
        if (v.getId() == R.id.back_img) {
            OrderFilterActivity.this.finish();
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
