package com.haven.hckp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haven.hckp.AppContext;
import com.haven.hckp.AppException;
import com.haven.hckp.R;
import com.haven.hckp.api.ApiClient;
import com.haven.hckp.bean.User;
import com.haven.hckp.common.StringUtils;
import com.haven.hckp.common.UIHelper;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 *
 */
public class SettingFragment extends BaseFragment {

    private Activity mActivity;
    private TextView mTitleTv;
    private AppContext appContext;
    private LayoutInflater inflater;
    private View mView;

    @ViewInject(R.id.setting_cars)
	private RelativeLayout settingCars;

    @ViewInject(R.id.setting_info)
	private RelativeLayout settingInfo;

    @ViewInject(R.id.setting_other)
	private RelativeLayout settingOther;

    @ViewInject(R.id.user_name_hode)
	private TextView userNameHode;

    @ViewInject(R.id.user_name)
	private TextView userName;

	public static SettingFragment newInstance() {
		return new SettingFragment();
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        mView = this.inflater.inflate(R.layout.fragment_setting, container, false);
        ViewUtils.inject(this, mView);
        appContext = (AppContext) this.mActivity.getApplicationContext();
        return mView;
	}

	@Override
	public void onStart() {
		super.onStart();
		initUser();
	}

    @OnClick({R.id.user_name_hode})
    public void buttonClick(View v) {
        if (v.getId() == R.id.user_name_hode) {
            UIHelper.showLoginRedirect(appContext);
        }
    }

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initViews(view);
	}

    private void initUser() {
        try {
            User u = ApiClient.getUser(appContext);
            if(!StringUtils.isEmpty(u.getUserUsername())){
                userName.setText(u.getUserUsername());
				userName.setVisibility(View.VISIBLE);
				userNameHode.setVisibility(View.GONE);
            }else{
				userName.setVisibility(View.GONE);
				userNameHode.setVisibility(View.VISIBLE);
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
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

}
