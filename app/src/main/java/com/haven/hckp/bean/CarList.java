package com.haven.hckp.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haven.hckp.AppException;
import com.haven.hckp.common.StringUtils;
import com.lidroid.xutils.util.LogUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 新闻列表实体类
 */
public class CarList extends Entity {

    public final static int CATALOG_ALL = 1;

    private int catalog;
    private int pageSize;
    private int newsCount;
    private List<Car> newslist = new ArrayList<Car>();

    public int getCatalog() {
        return catalog;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getNewsCount() {
        return newsCount;
    }

    public List<Car> getNewslist() {
        return newslist;
    }

    public static CarList parse(InputStream obj) throws IOException, AppException {
        CarList newslist = new CarList();
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
            Car news = null;
            for (Map<String, Object> d : data) {
                news = new Car();
                news.setTp_d_id(StringUtils.toString(d.get("tp_d_id")));
                news.setTp_tc_id(StringUtils.toString(d.get("tp_tc_id")));
                news.setTp_tc_name(StringUtils.toString(d.get("tp_tc_name")));
                news.setTp_tc_status(StringUtils.toString(d.get("tp_tc_status")));
                news.setTp_tc_phone(StringUtils.toString(d.get("tp_tc_phone")));
                newslist.newslist.add(news);
            }
        }
        obj.close();
        return newslist;
    }
}
