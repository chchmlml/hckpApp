package com.instway.app.bean;

import com.alibaba.fastjson.JSON;
import com.instway.app.AppException;
import com.instway.app.common.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * 数据操作结果实体类
 */
public class Result extends Entity {

    private String code;
    private String msg;
    private Object data;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 解析调用结果
     */
    public static Result parse(InputStream stream) throws IOException, AppException {
        Result res = new Result();
        String str = StringUtils.inputStreamToStr(stream);
        HashMap resultJson = JSON.parseObject(str, HashMap.class);
        res.setCode(StringUtils.toString(resultJson.get("code")));
        res.setMsg(StringUtils.toString(resultJson.get("msg")));
        res.setData(resultJson.get("data"));
        return res;
    }
}
