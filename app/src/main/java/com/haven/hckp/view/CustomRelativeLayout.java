package com.haven.hckp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 *
 */
public class CustomRelativeLayout extends RelativeLayout {

	public CustomRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CustomRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomRelativeLayout(Context context) {
		super(context);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return true;
	}

}
