package com.haven.hckp.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.haven.hckp.R;
import com.haven.hckp.widght.TopIndicatorOrder;


public class OrderFragment extends BaseFragment implements TopIndicatorOrder.OnTopIndicatorListener {

    private Activity mActivity;
    private TextView mTitleTv;
    private ViewPager mViewPager;
    private TabPagerAdapter mPagerAdapter;
    private TopIndicatorOrder mTopIndicator;

    public static OrderFragment newInstance() {
        return new OrderFragment();
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
        return  inflater.inflate(R.layout.fragment_order, container, false);
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
        mTitleTv = (TextView) view.findViewById(R.id.title_tv);
        mTitleTv.setText(R.string.home);

        //viewpage容器
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager_order);
        mPagerAdapter = new TabPagerAdapter(getFragmentManager());
        //顶部按钮
        mTopIndicator = (TopIndicatorOrder) view.findViewById(R.id.top_indicator_order);
        mTopIndicator.setOnTopIndicatorListener(this);
    }

    private void initDisplay() {
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.invalidate();
        mPagerAdapter.notifyDataSetChanged();
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
    public void onIndicatorSelected(int index) {
        mViewPager.setCurrentItem(index);
    }

    private class TabPagerAdapter extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener {

        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
            mViewPager.setOnPageChangeListener(this);
        }

        @Override
        public Fragment getItem(int position) {
            OrderTabFragment fragment = OrderTabFragment.newInstance(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mTopIndicator.setTabsDisplay(mActivity, position);
        }
    }
}
