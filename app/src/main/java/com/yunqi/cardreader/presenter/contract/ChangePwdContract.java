package com.yunqi.cardreader.presenter.contract;

import android.content.Context;

import com.yunqi.cardreader.base.BasePresenter;
import com.yunqi.cardreader.base.BaseView;
import com.yunqi.cardreader.base.NetView;
import com.yunqi.cardreader.model.request.ChangePwdRequest;

/**
 * @author ghcui
 * @time 2017/1/11
 */
public interface ChangePwdContract {

    interface View extends NetView{
        void onSuccess();
    }
    interface Presenter extends BasePresenter<View> {
        void changePwd(ChangePwdRequest request);
    }
}
