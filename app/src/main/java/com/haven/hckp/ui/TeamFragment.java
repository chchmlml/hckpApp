package com.haven.hckp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.haven.hckp.AppContext;
import com.haven.hckp.AppException;
import com.haven.hckp.R;
import com.haven.hckp.adapter.TeamViewNewsAdapter;
import com.haven.hckp.bean.Notice;
import com.haven.hckp.bean.Team;
import com.haven.hckp.bean.TeamList;
import com.haven.hckp.common.StringUtils;
import com.haven.hckp.common.UIHelper;
import com.haven.hckp.widght.NewDataToast;
import com.haven.hckp.widght.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

public class TeamFragment extends BaseFragment {

    private Activity mActivity;
    private TextView mTitleTv;
    private AppContext appContext;
    private LayoutInflater inflater;
    private View mView;

    private Handler lvNewsHandler;
    private List<Team> lvNewsData = new ArrayList<Team>();
    private int lvNewsSumData;
    private TeamViewNewsAdapter lvNewsAdapter;

    private TextView lvNews_foot_more;
    private ProgressBar lvNews_foot_progress;
    private PullToRefreshListView lvNews;

    private View lvNews_footer;

    @ViewInject(R.id.to_find_linear)
    private LinearLayout toFindLinear;

    @ViewInject(R.id.right_img)
    private ImageView rightBtn;

    public static TeamFragment newInstance() {
        TeamFragment OrderFragment = new TeamFragment();
        return OrderFragment;
    }

    @OnClick({R.id.to_find_linear})
    public void buttonClick(View v) {
        switch (v.getId()) {
            case R.id.to_find_linear:
                UIHelper.showTeamfindRedirect(appContext);
                break;
        }
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
        mView = this.inflater.inflate(R.layout.fragment_team, container, false);
        ViewUtils.inject(this, mView);
        appContext = (AppContext) this.mActivity.getApplicationContext();
        return mView;
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

    private void initViews(View view) {

        mTitleTv = (TextView) view.findViewById(R.id.title_tv);
        mTitleTv.setText(R.string.team);
//        // 初始化部件，数据
        this.initFrameButton();
        this.initFrameListView();

    }


    /**
     * 初始化所有ListView
     */
    private void initFrameListView() {
        // 初始化listview控件
        this.initNewsListView();
        // 加载listview数据
        this.initFrameListViewData();
    }

    /**
     * 初始化所有ListView数据
     */
    private void initFrameListViewData() {
        // 初始化Handler
        lvNewsHandler = this.getLvHandler(lvNews, lvNewsAdapter, lvNews_foot_more, lvNews_foot_progress, AppContext.PAGE_SIZE);

        // 加载资讯数据
        if (lvNewsData.isEmpty()) {
            loadLvNewsData(0, lvNewsHandler, UIHelper.LISTVIEW_ACTION_INIT);
        }
    }

    /**
     * 获取listview的初始化Handler
     *
     * @param lv
     * @param adapter
     * @return
     */
    private Handler getLvHandler(final PullToRefreshListView lv, final BaseAdapter adapter, final TextView more, final ProgressBar progress, final int pageSize) {
        return new Handler() {

            public void handleMessage(Message msg) {
                Notice notice = handleLvData(msg.what, msg.obj, msg.arg2, msg.arg1);
                if (msg.what >= 0) {
                    if (msg.what < pageSize) {
                        lv.setTag(UIHelper.LISTVIEW_DATA_FULL);
                        adapter.notifyDataSetChanged();
//                        more.setText(R.string.load_full);
                        more.setText(R.string.load_full);
                    } else if (msg.what == pageSize) {
                        lv.setTag(UIHelper.LISTVIEW_DATA_MORE);
                        adapter.notifyDataSetChanged();
                        more.setText(R.string.load_more);
                    }
                } else if (msg.what == -1) {
                    // 有异常--显示加载出错 & 弹出错误消息
                    lv.setTag(UIHelper.LISTVIEW_DATA_MORE);
                    if (!notice.getCode().equals("1")) {
                        NewDataToast.makeText(mActivity, notice.getMsg(), appContext.isAppSound()).show();
                    }
                }
                if (adapter.getCount() == 0) {
                    lv.setTag(UIHelper.LISTVIEW_DATA_EMPTY);
                }
                progress.setVisibility(ProgressBar.GONE);
                if (msg.arg1 == UIHelper.LISTVIEW_ACTION_REFRESH) {
                    lv.onRefreshComplete();
                    lv.setSelection(0);
                } else if (msg.arg1 == UIHelper.LISTVIEW_ACTION_CHANGE_CATALOG) {
                    lv.onRefreshComplete();
                    lv.setSelection(0);
                }
            }
        };
    }

    /**
     * listview数据处理
     *
     * @param what       数量
     * @param obj        数据
     * @param objtype    数据类型
     * @param actiontype 操作类型
     * @return notice 通知信息
     */
    private Notice handleLvData(int what, Object obj, int objtype, int actiontype) {
        Notice notice = null;
        switch (actiontype) {
            case UIHelper.LISTVIEW_ACTION_INIT:
            case UIHelper.LISTVIEW_ACTION_REFRESH:
            case UIHelper.LISTVIEW_ACTION_CHANGE_CATALOG:
                int newdata = 0;// 新加载数据-只有刷新动作才会使用到
                TeamList nlist = (TeamList) obj;
                notice = nlist.getNotice();
                lvNewsSumData = what;
                if (actiontype == UIHelper.LISTVIEW_ACTION_REFRESH) {
                    if (lvNewsData.size() > 0) {
                        for (Team news1 : nlist.getNewslist()) {
                            boolean b = false;
                            for (Team news2 : lvNewsData) {
                                if (news1.getId() == news2.getId()) {
                                    b = true;
                                    break;
                                }
                            }
                            if (!b)
                                newdata++;
                        }
                    } else {
                        newdata = what;
                    }
                }
                lvNewsData.clear();// 先清除原有数据
                lvNewsData.addAll(nlist.getNewslist());

                if (actiontype == UIHelper.LISTVIEW_ACTION_REFRESH) {
                    // 提示新加载数据
                    if (newdata > 0) {
                        NewDataToast.makeText(mActivity, getString(R.string.new_data_toast_message, newdata), appContext.isAppSound()).show();
                    } else {
                        NewDataToast.makeText(mActivity, getString(R.string.new_data_toast_none, false), appContext.isAppSound()).show();
                    }
                }
                break;
            case UIHelper.LISTVIEW_ACTION_SCROLL:
                TeamList list = (TeamList) obj;
                notice = list.getNotice();
                lvNewsSumData += what;
                if (lvNewsData.size() > 0) {
                    for (Team news1 : list.getNewslist()) {
                        boolean b = false;
                        for (Team news2 : lvNewsData) {
                            if (news1.getId() == news2.getId()) {
                                b = true;
                                break;
                            }
                        }
                        if (!b)
                            lvNewsData.add(news1);
                    }
                } else {
                    lvNewsData.addAll(list.getNewslist());
                }
                break;
        }
        return notice;
    }


    /**
     * 初始化新闻列表
     */
    private void initNewsListView() {

        lvNews_footer = this.inflater.inflate(R.layout.listview_footer, null);
        lvNews_foot_progress = (ProgressBar) lvNews_footer.findViewById(R.id.listview_foot_progress);
        lvNews_foot_more = (TextView) lvNews_footer.findViewById(R.id.listview_foot_more);
        lvNews_foot_progress = (ProgressBar) lvNews_footer.findViewById(R.id.listview_foot_progress);
        lvNewsAdapter = new TeamViewNewsAdapter(mActivity, lvNewsData, R.layout.team_list_item);

        lvNews = (PullToRefreshListView) mView.findViewById(R.id.listview_order);
        lvNews.addFooterView(lvNews_footer);// 添加底部视图 必须在setAdapter前
        lvNews.setAdapter(lvNewsAdapter);

        lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                UIHelper.ToastMessage(appContext, "敬请期待");
                return;

//                //点击头部、底部栏无效
//                if (position == 0 || view == lvNews_footer)
//                    return;
//
//                News news = null;
//                // 判断是否是TextView
//                if (view instanceof TextView) {
//                    news = (News) view.getTag();
//                } else {
//                    TextView tv = (TextView) view
//                            .findViewById(R.id.order_title);
//                    news = (News) tv.getTag();
//                }
//                if (news == null)
//                    return;
//
//                // 跳转到新闻详情
//                UIHelper.showNewsRedirect(appContext, news);
            }
        });
        lvNews.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                lvNews.onScrollStateChanged(view, scrollState);

                // 数据为空--不用继续下面代码了
                if (lvNewsData.isEmpty())
                    return;

                // 判断是否滚动到底部
                boolean scrollEnd = false;
                try {
                    if (view.getPositionForView(lvNews_footer) == view.getLastVisiblePosition())
                        scrollEnd = true;
                } catch (Exception e) {
                    scrollEnd = false;
                }
                int lvDataState = StringUtils.toInt(lvNews.getTag());
                if (scrollEnd && lvDataState == UIHelper.LISTVIEW_DATA_MORE) {
                    lvNews.setTag(UIHelper.LISTVIEW_DATA_LOADING);
                    lvNews_foot_more.setText(R.string.load_ing);
                    lvNews_foot_progress.setVisibility(View.VISIBLE);
                    // 当前pageIndex
                    int pageIndex = lvNewsSumData / AppContext.PAGE_SIZE;
                    loadLvNewsData(pageIndex, lvNewsHandler, UIHelper.LISTVIEW_ACTION_SCROLL);
                }
            }

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lvNews.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        });
        lvNews.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
            public void onRefresh() {
                loadLvNewsData(0, lvNewsHandler, UIHelper.LISTVIEW_ACTION_REFRESH);
            }
        });
    }


    private void loadLvNewsData(final int pageIndex, final Handler handler, final int action) {
        new Thread() {
            public void run() {
                Message msg = new Message();
                boolean isRefresh = false;
                if (action == UIHelper.LISTVIEW_ACTION_REFRESH || action == UIHelper.LISTVIEW_ACTION_SCROLL)
                    isRefresh = true;
                try {
                    TeamList list = appContext.getTeamList(pageIndex, isRefresh);
                    msg.what = 0;
                    msg.obj = list;
                } catch (AppException e) {
                    e.printStackTrace();
                    msg.what = -1;
                    msg.obj = e;
                }
                msg.arg1 = action;
                msg.arg2 = UIHelper.LISTVIEW_DATATYPE_NEWS;
                handler.sendMessage(msg);
            }
        }.start();
    }

    /**
     * 初始化各个按钮
     */
    private void initFrameButton() {
        rightBtn = (ImageView) mView.findViewById(R.id.right_img);
        rightBtn.setVisibility(View.VISIBLE);
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.showFilterRedirect(appContext);
            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
