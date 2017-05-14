package com.yunqi.cardreader.presenter;


import com.yunqi.cardreader.base.RxPresenter;
import com.yunqi.cardreader.presenter.contract.RegisterContract;

import javax.inject.Inject;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class RegisterPresenter extends RxPresenter<RegisterContract.View> implements RegisterContract.Presenter {


    @Inject
    public RegisterPresenter() {
    }


}
