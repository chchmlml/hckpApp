package com.haven.hckp.bean;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haven.hckp.AppException;
import com.haven.hckp.common.StringUtils;

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
        Log.i("haven", "cui-->NewsList");
        TeamList newslist = new TeamList();
        String str = StringUtils.inputStreamToStr(obj);
        Log.i("havenCui", "cui-->" + str);
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
                news.setTp_d_id(d.get("tp_d_id").toString());
                news.setTp_tc_name(d.get("tp_tc_name").toString());
                news.setTp_tc_phone(d.get("tp_tc_phone").toString());
                newslist.newslist.add(news);
            }
        }
        obj.close();
        return newslist;
    }
}
