package com.haven.hckp.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyCarNoSelectActivity extends BaseActivity {

    private AppContext appContext;

    @ViewInject(R.id.no)
    private EditText no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_no_select);
        ViewUtils.inject(this);

        appContext = (AppContext) getApplicationContext();
    }

    final int RESULT_CODE = 101;

    @OnClick({R.id.button})
    public void buttonClick(View v) {

        switch (v.getId()) {
            case R.id.button:
                Intent intent = new Intent();
                intent.putExtra("car_no", StringUtils.toString(no.getText()));
                this.setResult(RESULT_CODE, intent);
                this.finish();
                break;
        }
    }

}
