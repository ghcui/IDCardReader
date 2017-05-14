package com.yunqi.cardreader.model.response;

/**
 * 通用HttpResponse
 * @author ghcui
 * @time 2017/1/11
 */
public class CommonHttpRsp<T> extends BaseHttpRsp {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }




}
