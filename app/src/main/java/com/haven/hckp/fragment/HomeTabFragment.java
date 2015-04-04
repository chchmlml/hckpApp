package com.haven.hckp.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haven.hckp.R;

/**
 *
 */
public class HomeTabFragment extends BaseFragment {
	
	private static final String TAG = "HomeTabFragment";
	private Activity mActivity;
	private TextView mMsgTv;
	private String mMsgName;
	
	public void setMsgName(String msgName) {
		this.mMsgName = msgName;
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
		View view = inflater.inflate(R.layout.fragment_home_tab, container, false);
		return view;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initViews(view);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initDisplay();
	}
	
	private void initViews(View view) {
		mMsgTv = (TextView) view.findViewById(R.id.msg_tv);
	}
	
	private void initDisplay() {
		mMsgTv.setText(mMsgName + "");
	}

	@Override
	public String getFragmentName() {
		return TAG;
	}

}
