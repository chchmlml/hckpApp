package com.instway.app.ui;

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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.instway.app.AppContext;
import com.instway.app.AppException;
import com.instway.app.R;
import com.instway.app.adapter.DispathViewAdapter;
import com.instway.app.bean.Dispath;
import com.instway.app.bean.DispathList;
import com.instway.app.bean.Notice;
import com.instway.app.common.StringUtils;
import com.instway.app.common.UIHelper;
import com.instway.app.widght.NewDataToast;
import com.instway.app.widght.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class HomeTabFragment extends BaseFragment {

    private Activity mActivity;
    private AppContext appContext;
    private LayoutInflater inflater;

    private Handler lvNewsHandler;
    private List<Dispath> lvNewsData = new ArrayList<Dispath>();
    private int lvNewsSumData;
    private DispathViewAdapter lvNewsAdapter;

    private TextView lvNews_foot_more;
    private ProgressBar lvNews_foot_progress;
    private PullToRefreshListView lvNews;

    private View lvNews_footer;
    private View mView;


    private int listType;

    public static HomeTabFragment newInstance(int arg) {
        HomeTabFragment fragment = new HomeTabFragment();
        fragment.listType = arg;
        return fragment;
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
        mView = this.inflater.inflate(R.layout.dispath_tab_fragment, container, false);
        ViewUtils.inject(this, mView); //注入view和事件
        appContext = (AppContext) this.mActivity.getApplicationContext();
        return mView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initViews();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	public void initViews() {
        this.initFrameButton();
        this.initFrameListView();
	}
    @Override
    public void onResume() {
        this.initFrameListViewData();
        super.onResume();
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
        //if (lvNewsData.isEmpty()) {
            loadLvNewsData(0, lvNewsHandler, UIHelper.LISTVIEW_ACTION_INIT);
        //}
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
                DispathList nlist = (DispathList) obj;
                notice = nlist.getNotice();
                lvNewsSumData = what;
                if (actiontype == UIHelper.LISTVIEW_ACTION_REFRESH) {
                    if (lvNewsData.size() > 0) {
                        for (Dispath news1 : nlist.getNewslist()) {
                            boolean b = false;
                            for (Dispath news2 : lvNewsData) {
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
                        NewDataToast.makeText(mActivity, getString(R.string.new_data_toast_message, newdata)).show();
                    } else {
                        NewDataToast.makeText(mActivity, getString(R.string.new_data_toast_none, false)).show();
                    }
                }
                break;
            case UIHelper.LISTVIEW_ACTION_SCROLL:
                DispathList list = (DispathList) obj;
                notice = list.getNotice();
                lvNewsSumData += what;
                if (lvNewsData.size() > 0) {
                    for (Dispath news1 : list.getNewslist()) {
                        boolean b = false;
                        for (Dispath news2 : lvNewsData) {
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

        lvNewsAdapter = new DispathViewAdapter(mActivity, lvNewsData, R.layout.dispath_list_item);

        lvNews = (PullToRefreshListView) mView.findViewById(R.id.listview_dispatch);
        lvNews.addFooterView(lvNews_footer);// 添加底部视图 必须在setAdapter前
        lvNews.setAdapter(lvNewsAdapter);

        lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                //点击头部、底部栏无效
                if (position == 0 || view == lvNews_footer)
                    return;

                Dispath news = null;
                // 判断是否是TextView
                if (view instanceof TextView) {
                    news = (Dispath) view.getTag();
                } else {
                    TextView tv = (TextView) view.findViewById(R.id.tp_tc_phone);
                    news = (Dispath) tv.getTag();
                }
                if (news == null)
                    return;

                // 跳转到新闻详情
                UIHelper.showDispathDetailRedirect(appContext, news);
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
                LogUtils.i( "scroll 到底了 = " + scrollEnd);
                int lvDataState = StringUtils.toInt(lvNews.getTag());
                if (scrollEnd && lvDataState == UIHelper.LISTVIEW_DATA_MORE) {
                    LogUtils.i( "scroll 到底了...加载数据");
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
                    //额外参数
                    Map<String,Object> params = new HashMap<String, Object>();
                    switch (listType){
                        case 1:
                            params.put("status","5");
                            break;
                        default:
                            params.put("status","");
                            break;
                    }
                    DispathList list = appContext.getDispathList(pageIndex, isRefresh,params);
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
                        NewDataToast.makeText(mActivity, notice.getMsg()).show();
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

    private void initFrameButton() {
    }

}
