package com.haven.hckp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haven.hckp.AppContext;
import com.haven.hckp.AppException;
import com.haven.hckp.R;
import com.haven.hckp.api.ApiClient;
import com.haven.hckp.bean.User;
import com.haven.hckp.common.StringUtils;
import com.haven.hckp.common.UIHelper;
import com.haven.hckp.widght.NewDataToast;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
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

    @ViewInject(R.id.clear_cache_layout)
	private LinearLayout mClearCacheLayout;

    @ViewInject(R.id.user_name)
	private TextView userName;

    @ViewInject(R.id.logout_btn)
	private TextView logoutBtn;

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


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        this.inflater = inflater;
        mView = this.inflater.inflate(R.layout.fragment_setting, container, false);
        ViewUtils.inject(this, mView); //注入view和事件
        appContext = (AppContext) this.mActivity.getApplicationContext();
        return mView;
	}


    @OnClick({R.id.clear_cache_layout,R.id.logout_btn})
    public void buttonClick(View v) {
        if (v.getId() == R.id.clear_cache_layout) {
            UIHelper.showLoginRedirect(appContext);
        }else if(v.getId() == R.id.logout_btn){
            try {
                ApiClient.logout(appContext);
                userName.setText(R.string.click_to_login);
                logoutBtn.setVisibility(View.VISIBLE);
            } catch (AppException e) {
                e.printStackTrace();
            }
        }
    }

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initViews(view);
		initEvents();
        initUser();
	}



    private void initUser() {

        try {
            User u = ApiClient.getUser(appContext);
            if(!StringUtils.isEmpty(u.getUserUsername())){
                userName.setText(u.getUserUsername());
            }else{
                logoutBtn.setVisibility(View.GONE);
            }
        } catch (AppException e) {
            LogUtils.e(e.getMessage());
            e.printStackTrace();
        }


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
		mClearCacheLayout = (LinearLayout) view.findViewById(R.id.clear_cache_layout);
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
