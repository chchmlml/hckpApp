package com.instway.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.instway.app.R;
import com.instway.app.common.StringUtils;
import com.instway.app.common.UIHelper;
import com.instway.app.widght.TopIndicatorOrder;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;


public class OrderFragment extends BaseFragment implements TopIndicatorOrder.OnTopIndicatorListener {

    private Activity mActivity;
    private TextView mTitleTv;
    private ViewPager mViewPager;
    private TabPagerAdapter mPagerAdapter;
    private TopIndicatorOrder mTopIndicator;

    private ImageView rightImg;
    private LinearLayout filterLiner;
    private TextView clearFilter;

    private OrderTabFragment fragment;

    private String pStartProvince;
    private String pStartCity;
    private String pEndProvince;
    private String pEndCity;
    private String pDate;

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
        pStartProvince = "";
        pStartCity = "";
        pEndProvince = "";
        pEndCity = "";
        pDate = "";
        return inflater.inflate(R.layout.fragment_order, container, false);
    }


    private final int REQUEST_CODE = 0;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ViewUtils.inject(view);
        rightImg = (ImageView) view.findViewById(R.id.right_img);
        filterLiner = (LinearLayout) view.findViewById(R.id.filter_liner);
        clearFilter = (TextView) view.findViewById(R.id.clear_filter);
        clearFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pStartProvince = "";
                pStartCity = "";
                pEndProvince = "";
                pEndCity = "";
                pDate = "";
                initDisplay();
            }
        });

        rightImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrderFilterActivity.class);
                OrderFragment.this.startActivityForResult(intent, REQUEST_CODE);
            }
        });
        initViews(view);
    }

    /*
   * (non-Javadoc)
   */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();
        pStartProvince = bundle.getString("startProvince");
        pStartCity = bundle.getString("startCity");
        pEndProvince = bundle.getString("endProvince");
        pEndCity = bundle.getString("endCity");
        pDate = bundle.getString("date");
        initDisplay();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDisplay();
    }

    private void initViews(View view) {
        mTitleTv = (TextView) view.findViewById(R.id.title_tv);
        mTitleTv.setText("货源");

        rightImg.setVisibility(View.VISIBLE);

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
        //清除筛选条件
        if (!StringUtils.isEmpty(pStartProvince) || !StringUtils.isEmpty(pStartCity)
                || !StringUtils.isEmpty(pEndProvince) || !StringUtils.isEmpty(pEndCity) || !StringUtils.isEmpty(pDate)) {
            filterLiner.setVisibility(View.VISIBLE);
        } else {
            filterLiner.setVisibility(View.GONE);
        }
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
            fragment = OrderTabFragment.newInstance(position, pStartProvince, pStartCity, pEndProvince, pEndCity, pDate);
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
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
