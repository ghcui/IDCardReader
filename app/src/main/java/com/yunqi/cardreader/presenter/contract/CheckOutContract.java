package com.yunqi.cardreader.presenter.contract;

import com.yunqi.cardreader.base.BasePresenter;
import com.yunqi.cardreader.base.NetView;
import com.yunqi.cardreader.model.request.CheckOutRequest;

/**
 * @author ghcui
 * @time 2017/1/11
 */
public interface CheckOutContract {

    interface View extends NetView {
        void onSuccess();
    }

    interface Presenter extends BasePresenter<View> {
        void checkOut(CheckOutRequest request);
    }
}
