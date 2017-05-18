package com.yunqi.cardreader.rx;


import android.text.TextUtils;

import com.yunqi.cardreader.base.BaseView;
import com.yunqi.cardreader.base.NetView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;


/**
 * @author ghcui
 * @time 2017/1/13
 */
public abstract class ExSubscriber<T> extends Subscriber<T> {

    public static final String TAG = "ExSubscriber";
    private NetView view;
    /**
     * requestCode用来解决当界面有多个请求时来区分请求,默认为0
     */
    private int requestCode = 0;

    public ExSubscriber(NetView view) {
        this.view = view;
    }

    public ExSubscriber(NetView view, int requestCode) {
        this.view = view;
        this.requestCode = requestCode;
    }

    @Override
    public void onStart() {
        if (view.checkNetwork()) {
            return;
        }
        this.view.showLoading(requestCode);
    }

    @Override
    public void onCompleted() {
        this.view.cancelLoading(requestCode);
    }

    @Override
    public void onError(Throwable e) {
        this.view.cancelLoading(requestCode);
        e.printStackTrace();
        if (e instanceof retrofit2.HttpException) {
            this.view.showError("连接服务器异常!", requestCode);
        } else if (e instanceof ConnectException) {
            this.view.showError("网络断开,请检查网络!", requestCode);
        } else if (e instanceof SocketTimeoutException) {
            this.view.showError("网络连接超时!", requestCode);
        } else {
            String errormsg = e.getMessage();
            if (TextUtils.isEmpty(errormsg)) {
                errormsg = "请求数据失败!";
            }
            this.view.showError(errormsg, requestCode);
        }
    }


    @Override
    public void onNext(T t) {
        if (t == null) {
            this.view.showError("请求数据失败!", requestCode);
            return;
        }
        onSuccess(t);
    }

    protected abstract void onSuccess(T t);
}
