package com.yunqi.cardreader.presenter.contract;

import com.yunqi.cardreader.base.BasePresenter;
import com.yunqi.cardreader.base.BaseView;
import com.yunqi.cardreader.base.NetView;
import com.yunqi.cardreader.model.bean.User;

/**
 * @author ghcui
 * @time 2017/1/11
 */
public interface RegisterContract {

    interface View extends BaseView{
    }
    interface Presenter extends BasePresenter<View> {
    }
}
