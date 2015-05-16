package com.instway.app.bean;


import com.alibaba.fastjson.JSON;
import com.instway.app.AppException;
import com.instway.app.common.StringUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * 新闻实体类
 */
public class Dispath extends Entity {

    private String tp_di_id;
    private String tp_di_sn;
    private String tp_di_enddate;
    private String tp_tc_name;

    public String getTp_di_status() {
        return tp_di_status;
    }

    public void setTp_di_status(String tp_di_status) {
        this.tp_di_status = tp_di_status;
    }

    private String tp_di_status;

    public String getTp_tc_phone() {
        return tp_tc_phone;
    }

    public void setTp_tc_phone(String tp_tc_phone) {
        this.tp_tc_phone = tp_tc_phone;
    }

    private String tp_tc_phone;

    public String getTp_di_id() {
        return tp_di_id;
    }

    public void setTp_di_id(String tp_di_id) {
        this.tp_di_id = tp_di_id;
    }

    public String getTp_di_sn() {
        return tp_di_sn;
    }

    public void setTp_di_sn(String tp_di_sn) {
        this.tp_di_sn = tp_di_sn;
    }

    public String getTp_di_enddate() {
        return tp_di_enddate;
    }

    public void setTp_di_enddate(String tp_di_enddate) {
        this.tp_di_enddate = tp_di_enddate;
    }

    public String getTp_tc_name() {
        return tp_tc_name;
    }

    public void setTp_tc_name(String tp_tc_name) {
        this.tp_tc_name = tp_tc_name;
    }

    public static Dispath parse(InputStream inputStream) throws IOException, AppException {
        String str = StringUtils.inputStreamToStr(inputStream);
        Dispath resultObj = JSON.parseObject(str, Dispath.class);
        return resultObj;
    }
}
