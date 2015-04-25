package com.haven.hckp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.haven.hckp.AppContext;
import com.haven.hckp.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class TestActivity extends BaseActivity implements View.OnClickListener {


    private Button play, puase, stop, exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        innit();
        bangButton();
        setTitle("1111111");

    }

    private void bangButton() {
        play.setOnClickListener(this);
        stop.setOnClickListener(this);
        puase.setOnClickListener(this);
        exit.setOnClickListener(this);

    }

    private void innit() {
        play = (Button) this.findViewById(R.id.play);
        puase = (Button) this.findViewById(R.id.pause);
        stop = (Button) this.findViewById(R.id.stop);
        exit = (Button) this.findViewById(R.id.exit);

    }


    @Override
    public void onClick(View v) {

        Intent i = new Intent("com.yang.music");
        int op = -1;
        switch (v.getId()) {
            case R.id.play:
                op = 1;
                break;

            case R.id.pause:
                op = 2;
                break;
            case R.id.stop:
                op = 3;
                //  stopService(i);
                break;
            case R.id.exit:
                op = 4;
                stopService(i);
                finish();
                break;
        }
        Log.v("MusicButton", "" + String.valueOf(op));
        Bundle bundle = new Bundle();
        bundle.putInt("op", op);
        i.putExtras(bundle);
        startService(i);

    }


}

