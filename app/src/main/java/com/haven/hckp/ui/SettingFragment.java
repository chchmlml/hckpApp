package com.haven.hckp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haven.hckp.AppContext;
import com.haven.hckp.R;
import com.haven.hckp.common.StringUtils;
import com.haven.hckp.common.UIHelper;
import com.haven.hckp.widght.NewDataToast;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 *
 */
public class SettingFragment extends BaseFragment implements OnClickListener {

    private static final String TAG = "SettingFragment";
    private Activity mActivity;
    private TextView mTitleTv;
    private AppContext appContext;
    private LayoutInflater inflater;
    private View mView;

	private RelativeLayout mRecommondToWeixinLayout;
	private RelativeLayout mFeedbackLayout;
	private RelativeLayout mAboutUsLayout;
	private RelativeLayout mAppRecommendLayout;
	private RelativeLayout mClearCacheLayout;

	public static SettingFragment newInstance() {
		SettingFragment settingFragment = new SettingFragment();

		return settingFragment;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
    @ViewInject(R.id.clear_cache_layout)
    private RelativeLayout relativeLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        this.inflater = inflater;
        mView = this.inflater.inflate(R.layout.fragment_setting, container, false);
        ViewUtils.inject(this, mView); //注入view和事件
        appContext = (AppContext) this.mActivity.getApplicationContext();
        return mView;
	}

    @OnClick({R.id.clear_cache_layout})
    public void buttonClick(View v) {
        if (v.getId() == R.id.clear_cache_layout) {
            UIHelper.showLoginRedirect(appContext);
        }
    }

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initViews(view);
		initEvents();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private void initViews(View view) {
		mTitleTv = (TextView) view.findViewById(R.id.title_tv);
		mTitleTv.setText(R.string.setting);

		mFeedbackLayout = (RelativeLayout) view.findViewById(R.id.feedback_layout);
		mAboutUsLayout = (RelativeLayout) view.findViewById(R.id.about_us_layout);
		mAppRecommendLayout = (RelativeLayout) view.findViewById(R.id.app_recommend_layout);
		mClearCacheLayout = (RelativeLayout) view.findViewById(R.id.clear_cache_layout);
	}
	
	private void initEvents() {
//		mFeedbackLayout.setOnClickListener(this);
//		mAboutUsLayout.setOnClickListener(this);
//		mAppRecommendLayout.setOnClickListener(this);
//		mClearCacheLayout.setOnClickListener(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public String getFragmentName() {
		return TAG;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.feedback_layout:
			break;
		case R.id.about_us_layout:
			break;
		case R.id.app_recommend_layout:
			break;
		case R.id.clear_cache_layout:
			break;

		default:
			break;
		}
	}

}
