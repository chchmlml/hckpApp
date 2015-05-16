package com.instway.app.bean;


import com.alibaba.fastjson.JSON;
import com.instway.app.AppException;
import com.instway.app.common.StringUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * 新闻实体类
 */
public class Car extends Entity {

    private String tp_d_c_id;
    private String tp_d_id;
    private String tp_car_id;
    private String tp_d_c_type;
    private String tp_d_c_time;
    private String tp_car_no;
    private String tp_car_weight;
    private String tp_car_hight;
    private String tp_car_length;
    private String tp_car_outdate;
    private String tp_tc_id;
    private String tp_car_name;
    private String tp_car_width;

    public String getTp_d_c_id() {
        return tp_d_c_id;
    }

    public void setTp_d_c_id(String tp_d_c_id) {
        this.tp_d_c_id = tp_d_c_id;
    }

    public String getTp_d_id() {
        return tp_d_id;
    }

    public void setTp_d_id(String tp_d_id) {
        this.tp_d_id = tp_d_id;
    }

    public String getTp_car_id() {
        return tp_car_id;
    }

    public void setTp_car_id(String tp_car_id) {
        this.tp_car_id = tp_car_id;
    }

    public String getTp_d_c_type() {
        return tp_d_c_type;
    }

    public void setTp_d_c_type(String tp_d_c_type) {
        this.tp_d_c_type = tp_d_c_type;
    }

    public String getTp_d_c_time() {
        return tp_d_c_time;
    }

    public void setTp_d_c_time(String tp_d_c_time) {
        this.tp_d_c_time = tp_d_c_time;
    }

    public String getTp_car_no() {
        return tp_car_no;
    }

    public void setTp_car_no(String tp_car_no) {
        this.tp_car_no = tp_car_no;
    }

    public String getTp_car_weight() {
        return tp_car_weight;
    }

    public void setTp_car_weight(String tp_car_weight) {
        this.tp_car_weight = tp_car_weight;
    }

    public String getTp_car_hight() {
        return tp_car_hight;
    }

    public void setTp_car_hight(String tp_car_hight) {
        this.tp_car_hight = tp_car_hight;
    }

    public String getTp_car_length() {
        return tp_car_length;
    }

    public void setTp_car_length(String tp_car_length) {
        this.tp_car_length = tp_car_length;
    }

    public String getTp_car_outdate() {
        return tp_car_outdate;
    }

    public void setTp_car_outdate(String tp_car_outdate) {
        this.tp_car_outdate = tp_car_outdate;
    }

    public String getTp_tc_id() {
        return tp_tc_id;
    }

    public void setTp_tc_id(String tp_tc_id) {
        this.tp_tc_id = tp_tc_id;
    }

    public String getTp_car_name() {
        return tp_car_name;
    }

    public void setTp_car_name(String tp_car_name) {
        this.tp_car_name = tp_car_name;
    }

    public String getTp_car_width() {
        return tp_car_width;
    }

    public void setTp_car_width(String tp_car_width) {
        this.tp_car_width = tp_car_width;
    }

    public static Car parse(InputStream inputStream) throws IOException, AppException {
        String str = StringUtils.inputStreamToStr(inputStream);
        Car resultObj = JSON.parseObject(str, Car.class);
        return resultObj;
    }
}
