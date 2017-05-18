package com.yunqi.cardreader.rx;


import android.text.TextUtils;

import com.yunqi.cardreader.base.NetView;
import com.yunqi.cardreader.model.response.BaseHttpRsp;

/**
 * @author ghcui
 * @time 2017/3/16
 */
public abstract class BaseSubscriber extends ExSubscriber<BaseHttpRsp> {

    public BaseSubscriber(NetView view) {
        super(view);
    }
    public BaseSubscriber(NetView view,int requestCode) {
        super(view,requestCode);
    }

    @Override
    protected void onSuccess(BaseHttpRsp httpRsp) {
        if (httpRsp.getCode() == 200) {
            onSuccess();
        } else {
            int errorCode = httpRsp.getCode();
            String errorMsg = httpRsp.getMessage();
            if(TextUtils.isEmpty(errorMsg)){
                errorMsg="请求失败";
            }
            onFailure(errorCode, errorMsg);
        }
    }

    protected abstract void onSuccess();

    protected abstract void onFailure(int errorCode, String msg);
}
