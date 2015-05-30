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
public class NewsList extends Entity {

    public final static int CATALOG_ALL = 1;

    private int catalog;
    private int pageSize;

    public void setNewsCount(int newsCount) {
        this.newsCount = newsCount;
    }

    private int newsCount;
    private List<News> newslist = new ArrayList<News>();

    public int getCatalog() {
        return catalog;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getNewsCount() {
        return newsCount;
    }

    public List<News> getNewslist() {
        return newslist;
    }

    public static NewsList parse(InputStream obj) throws IOException, AppException {
        NewsList newslist = new NewsList();
        String str = StringUtils.inputStreamToStr(obj);
        LogUtils.i( str);
        JSONObject jsonStr = JSON.parseObject(str);
        String code = jsonStr.getString("code");
        String msg = jsonStr.getString("msg");
        int msgCount = StringUtils.toInt(jsonStr.getString("nums"));
        newslist.setNewsCount(msgCount);
        Notice notice = new Notice();
        notice.setMsg(code);
        notice.setMsg(msg);
        notice.setMsgCount(msgCount);
        newslist.setNotice(notice);
        List<Map<String, Object>> data = (List<Map<String, Object>>) jsonStr.get("data");
        if (data != null) {
            News news = null;
            for (Map<String, Object> d : data) {
                news = new News();
                news.setTp_diy_id(StringUtils.toString(d.get("tp_diy_id")));
                news.setTp_diy_start_city(StringUtils.toString(d.get("tp_diy_start_city")));
                news.setTp_diy_end_city(StringUtils.toString(d.get("tp_diy_end_city")));
                news.setTp_diy_desc(StringUtils.toString(d.get("tp_diy_desc")));
                news.setTp_diy_startdate(StringUtils.toString(d.get("tp_diy_startdate")));
                news.setTp_diy_enddate(StringUtils.toString(d.get("tp_diy_enddate")));
                news.setTp_diy_kms(StringUtils.toString(d.get("tp_diy_kms")) + "km");
                news.setTp_tc_name(StringUtils.toString(d.get("tp_tc_name")));
                news.setTp_diy_type(StringUtils.toString(d.get("tp_diy_type")));
                news.setTp_diyp_price(StringUtils.toString(d.get("tp_diyp_price")));
                newslist.newslist.add(news);
            }
        }
        obj.close();
        return newslist;
    }
}
