package com.haven.hckp.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.haven.hckp.AppContext;
import com.haven.hckp.R;

public class OrderDetailActivity extends ActionBarActivity {

    private TextView mTitleTv;
    private AppContext appContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail);

        mTitleTv = (TextView) findViewById(R.id.title_tv);
        mTitleTv.setText(R.string.order_detail);
        appContext= (AppContext) getApplicationContext();

    }

}
