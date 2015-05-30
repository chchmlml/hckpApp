package com.instway.app.widght;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by haven on 15/5/30.
 */
public class HavenInput extends RelativeLayout implements View.OnClickListener {
    public HavenInput(Context context) {
        super(context);
        init(context);
    }

    public HavenInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HavenInput(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HavenInput(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {

        //设置宽高度
    }


    @Override
    public void onClick(View v) {

    }
}
