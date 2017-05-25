package com.yunqi.cardreader.model.response;

/**
 * 通用HttpResponse
 * @author ghcui
 * @time 2017/1/11
 */
public class BaseHttpRsp {

    private int code;

    private String msg;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
