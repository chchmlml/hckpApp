package com.haven.hckp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.haven.hckp.R;
import com.haven.hckp.utils.CommonUtils;

/**
 *
 */
public class FeedBackActivity extends Activity implements OnClickListener {

	private TextView mTitleTv;
	private ImageView mBackImg;
	private ImageView mOkImg;
	private EditText mContentEdit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_feedback);
		
		init();
		initEvents();
	}
	
	private void init() {
		mTitleTv = (TextView) findViewById(R.id.title_tv);
		mTitleTv.setText(R.string.feedback);
		mBackImg = (ImageView) findViewById(R.id.back_img);
		mBackImg.setVisibility(View.VISIBLE);
		mOkImg = (ImageView) findViewById(R.id.right_img);
		mOkImg.setImageResource(R.drawable.check_mark_btn);
		mOkImg.setVisibility(View.VISIBLE);
		
		mContentEdit = (EditText) findViewById(R.id.content_edit);
	}
	
	private void initEvents() {
		mBackImg.setOnClickListener(this);
		mOkImg.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_img:
			this.finish();
			break;
		case R.id.right_img:
			CommonUtils.startShakeAnim(this, mContentEdit);
			break;

		default:
			break;
		}
	}
	
}
