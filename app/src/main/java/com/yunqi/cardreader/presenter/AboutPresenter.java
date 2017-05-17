package com.yunqi.cardreader.presenter;


import com.yunqi.cardreader.app.App;
import com.yunqi.cardreader.base.RxPresenter;
import com.yunqi.cardreader.model.bean.User;
import com.yunqi.cardreader.model.db.GreenDaoHelper;
import com.yunqi.cardreader.model.http.CommonHttpRsp;
import com.yunqi.cardreader.model.http.RetrofitHelper;
import com.yunqi.cardreader.presenter.contract.AboutContract;
import com.yunqi.cardreader.presenter.contract.LoginContract;
import com.yunqi.cardreader.rx.ExSubscriber;
import com.yunqi.cardreader.util.RxUtil;

import javax.inject.Inject;

import rx.Subscription;

/**
 * @author ghcui
 * @time 2017/1/11
 */
public class AboutPresenter extends RxPresenter<AboutContract.View> implements AboutContract.Presenter {


    @Inject
    public AboutPresenter() {
    }



}
