package com.instway.app.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.instway.app.AppException;
import com.instway.app.common.StringUtils;
import com.lidroid.xutils.util.LogUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 新闻列表实体类
 */
public class TeamList extends Entity {

    public final static int CATALOG_ALL = 1;

    private int catalog;
    private int pageSize;
    private int newsCount;
    private List<Team> newslist = new ArrayList<Team>();

    public int getCatalog() {
        return catalog;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getNewsCount() {
        return newsCount;
    }

    public List<Team> getNewslist() {
        return newslist;
    }

    public static TeamList parse(InputStream obj) throws IOException, AppException {
        TeamList newslist = new TeamList();
        String str = StringUtils.inputStreamToStr(obj);
        LogUtils.i(str);
        JSONObject jsonStr = JSON.parseObject(str);
        String code = jsonStr.getString("code");
        String msg = jsonStr.getString("msg");
        Notice notice = new Notice();
        notice.setMsg(code);
        notice.setMsg(msg);
        newslist.setNotice(notice);
        List<Map<String, Object>> data = (List<Map<String, Object>>) jsonStr.get("data");
        if (data != null) {
            Team news = null;
            for (Map<String, Object> d : data) {
                news = new Team();
                news.setTp_d_id(StringUtils.toString(d.get("tp_d_id")));
                news.setTp_tc_id(StringUtils.toString(d.get("tp_tc_id")));
                news.setTp_tc_name(StringUtils.toString(d.get("tp_tc_name")));
                news.setTp_tc_d_status(StringUtils.toString(d.get("tp_tc_d_status")));
                news.setTp_tc_phone(StringUtils.toString(d.get("tp_tc_phone")));
                news.setTp_tc_fax(StringUtils.toString(d.get("tp_tc_fax")));
                news.setTp_tc_user(StringUtils.toString(d.get("tp_tc_user")));
                news.setTp_tc_sj(StringUtils.toString(d.get("tp_tc_sj")));
                newslist.newslist.add(news);
            }
        }
        obj.close();
        return newslist;
    }
}
