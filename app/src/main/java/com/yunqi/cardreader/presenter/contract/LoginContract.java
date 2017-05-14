package com.yunqi.cardreader.presenter.contract;

import com.yunqi.cardreader.base.BasePresenter;
import com.yunqi.cardreader.base.BaseView;
import com.yunqi.cardreader.base.NetView;
import com.yunqi.cardreader.model.bean.User;

/**
 * @author ghcui
 * @time 2017/1/11
 */
public interface LoginContract {

    interface View extends NetView{
        void jump2Main(User user);
    }
    interface Presenter extends BasePresenter<View> {
        void doLogin(String username,String password);
    }
}
