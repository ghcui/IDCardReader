package com.yunqi.cardreader.presenter.contract;

import android.content.Context;

import com.yunqi.cardreader.base.BasePresenter;
import com.yunqi.cardreader.base.BaseView;
import com.yunqi.cardreader.base.NetView;
import com.yunqi.cardreader.model.bean.User;

/**
 * @author ghcui
 * @time 2017/1/11
 */
public interface AboutContract {

    interface View extends BaseView{
        void showVersion(String versionName);
    }
    interface Presenter extends BasePresenter<View> {
        void getVersion(Context context);
    }
}
