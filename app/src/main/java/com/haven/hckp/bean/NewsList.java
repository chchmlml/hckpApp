package com.haven.hckp.bean;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haven.hckp.AppException;
import com.haven.hckp.common.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 新闻列表实体类
 */
public class NewsList extends Entity {

    public final static int CATALOG_ALL = 1;

    private int catalog;
    private int pageSize;
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
        Log.i("haven", "cui-->NewsList");
        NewsList newslist = new NewsList();
        String str = StringUtils.inputStreamToStr(obj);
        Log.i("havenCui", "cui-->" + str);
        JSONObject jsonStr = JSON.parseObject(str);
        String code = jsonStr.getString("code");
        String msg = jsonStr.getString("msg");
        List<Map<String, Object>> data = (List<Map<String, Object>>) jsonStr.get("data");
        if (code.equals("1")) {
            News news = null;
            for (Map<String, Object> d : data) {
                news = new News();
                news.setTp_diy_id(d.get("tp_diy_id").toString());
                news.setTp_diy_start_city(d.get("tp_diy_start_city").toString());
                news.setTp_diy_end_city(d.get("tp_diy_end_city").toString());
                news.setTp_diy_desc(d.get("tp_diy_desc").toString());
                news.setTp_diy_startdate(d.get("tp_diy_startdate").toString());
                news.setTp_diy_enddate(d.get("tp_diy_enddate").toString());
                news.setTp_diy_kms(d.get("tp_diy_kms").toString() + "km");
                news.setTp_tc_name(d.get("tp_tc_name").toString());
                newslist.newslist.add(news);
            }
        }
        obj.close();
        return newslist;
    }
}
