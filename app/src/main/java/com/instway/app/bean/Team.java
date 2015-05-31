package com.instway.app.bean;


import com.alibaba.fastjson.JSON;
import com.instway.app.AppException;
import com.instway.app.common.StringUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * 新闻实体类
 */
public class Team extends Entity {

    private String tp_tc_d_id;
    private String tp_d_id;
    private String tp_tc_id;
    private String tp_tc_d_type;

    private String tp_tc_d_status;
    private String tp_tc_name;
    private String tp_tc_phone;
    private String tp_tc_fax;
    private String tp_tc_user;

    private String tp_tc_sj;
    private String tp_tc_taxcertificate;
    private String tp_tc_orgcode;
    private String tp_tc_busilicense;
    private String tp_tc_trsplicense;
    private String tp_tc_type;
    private String tp_u_id;


    public String getTp_tc_sj() {
        return tp_tc_sj;
    }

    public void setTp_tc_sj(String tp_tc_sj) {
        this.tp_tc_sj = tp_tc_sj;
    }

    public String getTp_tc_d_status() {
        return tp_tc_d_status;
    }

    public void setTp_tc_d_status(String tp_tc_d_status) {
        this.tp_tc_d_status = tp_tc_d_status;
    }
    public String getTp_tc_d_id() {
        return tp_tc_d_id;
    }

    public void setTp_tc_d_id(String tp_tc_d_id) {
        this.tp_tc_d_id = tp_tc_d_id;
    }

    public String getTp_d_id() {
        return tp_d_id;
    }

    public void setTp_d_id(String tp_d_id) {
        this.tp_d_id = tp_d_id;
    }

    public String getTp_tc_id() {
        return tp_tc_id;
    }

    public void setTp_tc_id(String tp_tc_id) {
        this.tp_tc_id = tp_tc_id;
    }

    public String getTp_tc_d_type() {
        return tp_tc_d_type;
    }

    public void setTp_tc_d_type(String tp_tc_d_type) {
        this.tp_tc_d_type = tp_tc_d_type;
    }

    public String getTp_tc_status() {
        return tp_tc_d_status;
    }

    public void setTp_tc_status(String tp_tc_status) {
        this.tp_tc_d_status = tp_tc_status;
    }

    public String getTp_tc_name() {
        return tp_tc_name;
    }

    public void setTp_tc_name(String tp_tc_name) {
        this.tp_tc_name = tp_tc_name;
    }

    public String getTp_tc_phone() {
        return tp_tc_phone;
    }

    public void setTp_tc_phone(String tp_tc_phone) {
        this.tp_tc_phone = tp_tc_phone;
    }

    public String getTp_tc_fax() {
        return tp_tc_fax;
    }

    public void setTp_tc_fax(String tp_tc_fax) {
        this.tp_tc_fax = tp_tc_fax;
    }

    public String getTp_tc_user() {
        return tp_tc_user;
    }

    public void setTp_tc_user(String tp_tc_user) {
        this.tp_tc_user = tp_tc_user;
    }

    public String getTp_tc_taxcertificate() {
        return tp_tc_taxcertificate;
    }

    public void setTp_tc_taxcertificate(String tp_tc_taxcertificate) {
        this.tp_tc_taxcertificate = tp_tc_taxcertificate;
    }

    public String getTp_tc_orgcode() {
        return tp_tc_orgcode;
    }

    public void setTp_tc_orgcode(String tp_tc_orgcode) {
        this.tp_tc_orgcode = tp_tc_orgcode;
    }

    public String getTp_tc_busilicense() {
        return tp_tc_busilicense;
    }

    public void setTp_tc_busilicense(String tp_tc_busilicense) {
        this.tp_tc_busilicense = tp_tc_busilicense;
    }

    public String getTp_tc_trsplicense() {
        return tp_tc_trsplicense;
    }

    public void setTp_tc_trsplicense(String tp_tc_trsplicense) {
        this.tp_tc_trsplicense = tp_tc_trsplicense;
    }

    public String getTp_tc_type() {
        return tp_tc_type;
    }

    public void setTp_tc_type(String tp_tc_type) {
        this.tp_tc_type = tp_tc_type;
    }

    public String getTp_u_id() {
        return tp_u_id;
    }

    public void setTp_u_id(String tp_u_id) {
        this.tp_u_id = tp_u_id;
    }

    public static Team parse(InputStream inputStream) throws IOException, AppException {
        String str = StringUtils.inputStreamToStr(inputStream);
        Team resultObj = JSON.parseObject(str, Team.class);
        return resultObj;
    }
}
