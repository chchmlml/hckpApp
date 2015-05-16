package com.instway.app.bean;


import com.alibaba.fastjson.JSON;
import com.instway.app.AppException;
import com.instway.app.common.StringUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * 新闻实体类
 */
public class News extends Entity {

    //id
    private String tp_diy_id;
    private String tp_di_id;

    private String tp_tc_id;
    //发货城市
    private String tp_diy_start_city;
    //目的地
    private String tp_diy_end_city;
    //描述
    private String tp_diy_desc;
    //
    private String tp_diy_time;
    //发货日期
    private String tp_diy_startdate;
    //收货日期
    private String tp_diy_enddate;
    //报价有效时间
    private String tp_diy_endtime;
    //报价1正常 2手动结束
    private String tp_diy_status;
    //1自助报价 2指定价格
    private String tp_diy_type;
    //指定价格
    private String tp_diy_price;
    private String tp_diyp_price;
    //1广播 2指定司机
    private String tp_diy_category;
    private String tp_diy_kms;
    //车队名称
    private String tp_tc_name;

    //车队id
    public String getTp_diy_startdate() {
        return tp_diy_startdate;
    }

    public void setTp_diy_startdate(String tp_diy_startdate) {
        this.tp_diy_startdate = tp_diy_startdate;
    }


    public String getTp_diyp_price() {
        return tp_diyp_price;
    }

    public void setTp_diyp_price(String tp_diyp_price) {
        this.tp_diyp_price = tp_diyp_price;
    }

    public String getTp_diy_id() {
        return tp_diy_id;
    }

    public void setTp_diy_id(String tp_diy_id) {
        this.tp_diy_id = tp_diy_id;
    }

    public String getTp_di_id() {
        return tp_di_id;
    }

    public void setTp_di_id(String tp_di_id) {
        this.tp_di_id = tp_di_id;
    }

    public String getTp_tc_id() {
        return tp_tc_id;
    }

    public void setTp_tc_id(String tp_tc_id) {
        this.tp_tc_id = tp_tc_id;
    }

    public String getTp_diy_start_city() {
        return tp_diy_start_city;
    }

    public void setTp_diy_start_city(String tp_diy_start_city) {
        this.tp_diy_start_city = tp_diy_start_city;
    }

    public String getTp_diy_end_city() {
        return tp_diy_end_city;
    }

    public void setTp_diy_end_city(String tp_diy_end_city) {
        this.tp_diy_end_city = tp_diy_end_city;
    }

    public String getTp_diy_desc() {
        return tp_diy_desc;
    }

    public void setTp_diy_desc(String tp_diy_desc) {
        this.tp_diy_desc = tp_diy_desc;
    }

    public String getTp_diy_time() {
        return tp_diy_time;
    }

    public void setTp_diy_time(String tp_diy_time) {
        this.tp_diy_time = tp_diy_time;
    }

    public String getTp_diy_enddate() {
        return tp_diy_enddate;
    }

    public void setTp_diy_enddate(String tp_diy_enddate) {
        this.tp_diy_enddate = tp_diy_enddate;
    }

    public String getTp_diy_endtime() {
        return tp_diy_endtime;
    }

    public void setTp_diy_endtime(String tp_diy_endtime) {
        this.tp_diy_endtime = tp_diy_endtime;
    }

    public String getTp_diy_status() {
        return tp_diy_status;
    }

    public void setTp_diy_status(String tp_diy_status) {
        this.tp_diy_status = tp_diy_status;
    }

    public String getTp_diy_type() {
        return tp_diy_type;
    }

    public void setTp_diy_type(String tp_diy_type) {
        this.tp_diy_type = tp_diy_type;
    }

    public String getTp_diy_price() {
        return tp_diy_price;
    }

    public void setTp_diy_price(String tp_diy_price) {
        this.tp_diy_price = tp_diy_price;
    }

    public String getTp_diy_category() {
        return tp_diy_category;
    }

    public void setTp_diy_category(String tp_diy_category) {
        this.tp_diy_category = tp_diy_category;
    }

    public String getTp_diy_kms() {
        return tp_diy_kms;
    }

    public void setTp_diy_kms(String tp_diy_kms) {
        this.tp_diy_kms = tp_diy_kms;
    }

    public String getTp_tc_name() {
        return tp_tc_name;
    }

    public void setTp_tc_name(String tp_tc_name) {
        this.tp_tc_name = tp_tc_name;
    }


    public static News parse(InputStream inputStream) throws IOException, AppException {
        String str = StringUtils.inputStreamToStr(inputStream);
        News resultObj = JSON.parseObject(str, News.class);
        return resultObj;
    }
}
