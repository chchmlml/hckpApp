package com.haven.hckp.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.haven.hckp.AppContext;
import com.haven.hckp.AppException;
import com.haven.hckp.R;
import com.haven.hckp.adapter.CateListAdapter;
import com.haven.hckp.adapter.OrderAdapter;
import com.haven.hckp.bean.Notice;
import com.haven.hckp.bean.Order;
import com.haven.hckp.bean.OrderList;
import com.haven.hckp.common.StringUtils;
import com.haven.hckp.fragment.AnimFragment.OnFragmentDismissListener;
import com.haven.hckp.widght.PullToRefreshListView;
import com.haven.hckp.widght.PullToRefreshListView.OnRefreshListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class CategoryFragment extends BaseFragment implements
        OnItemClickListener, OnClickListener, OnFragmentDismissListener {

    private static final String TAG = "CategoryFragment";
    private Activity mActivity;
    private TextView mTitleTv;
    private ListView mCateListView;
    private CateListAdapter mCateListAdapter;
    private String[] mCategories;
    private ImageView mCateIndicatorImg;
    private int mFromY = 0;
    private ImageButton mImageBtn;

    private PullToRefreshListView listView;
    private OrderAdapter orderAdapter;

    private AppContext appContext;

    public static CategoryFragment newInstance() {
        CategoryFragment categoryFragment = new CategoryFragment();
        return categoryFragment;
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
        View view = inflater.inflate(R.layout.fragment_category, container,
                false);
        appContext = (AppContext) this.mActivity.getApplicationContext();
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

    }

    private Handler orderHandler;
    private int lvNewsSumData;
    private List<Order> lvNewsData = new ArrayList<Order>();

    private void initViews(View view) {

        mTitleTv = (TextView) view.findViewById(R.id.title_tv);
        mTitleTv.setText(R.string.home);

        listView = (PullToRefreshListView) view.findViewById(R.id.listview_order);
        orderHandler = this.getLvHandler(listView, orderAdapter);

        listView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadLvNewsData( orderHandler);
            }
        });
    }

    private void loadLvNewsData(final Handler handler) {
        new Thread() {
            public void run() {
                Message msg = new Message();
                try {
                    OrderList list = appContext.getOrderList();
                    msg.what = list.getPageSize();
                    msg.obj = list;
                } catch (AppException e) {
                    e.printStackTrace();
                    msg.what = -1;
                    msg.obj = e;
                }
                msg.arg1 = 1;
                msg.arg2 = 1;
                handler.sendMessage(msg);
            }
        }.start();
    }

    private Handler getLvHandler(PullToRefreshListView listView, OrderAdapter orderAdapter) {
        return new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what >= 0) {
                    // listview数据处理
                    handleLvData(msg.what, msg.obj, msg.arg2,msg.arg1);
                } else if (msg.what == -1) {
                    // 有异常--显示加载出错 & 弹出错误消息
                    //todo
                }
            }
        };
    }

    private Notice handleLvData(int what, Object obj, int objtype,
                                int actiontype) {
        Notice notice = null;
        OrderList nlist = (OrderList) obj;
        notice = nlist.getNotice();
        lvNewsSumData = what;
        if (lvNewsData.size() > 0) {
            for (Order news1 : nlist.getNewslist()) {
                boolean b = false;
                for (Order news2 : lvNewsData) {
                    if (news1.getId() == news2.getId()) {
                        b = true;
                        break;
                    }
                }
            }
        }
        lvNewsData.clear();// 先清除原有数据
        lvNewsData.addAll(nlist.getNewslist());
        return notice;
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onFragmentDismiss() {

    }

}
