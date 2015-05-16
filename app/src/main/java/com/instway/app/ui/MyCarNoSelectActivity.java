package com.instway.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.instway.app.AppContext;
import com.instway.app.R;
import com.instway.app.common.StringUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

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
