package com.yunqi.cardreader.presenter;


import com.yunqi.cardreader.base.RxPresenter;
import com.yunqi.cardreader.model.http.RetrofitHelper;
import com.yunqi.cardreader.model.request.ClientInfoAddRequest;
import com.yunqi.cardreader.model.response.BaseHttpRsp;
import com.yunqi.cardreader.presenter.contract.WillSendContract;
import com.yunqi.cardreader.rx.BaseSubscriber;
import com.yunqi.cardreader.util.RxUtil;

import javax.inject.Inject;

import rx.Subscription;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class WillSendPresenter extends RxPresenter<WillSendContract.View> implements WillSendContract.Presenter {

    private RetrofitHelper mRetrofitHelper;
    @Inject
    public WillSendPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }



    @Override
    public void submitInfo(ClientInfoAddRequest request) {
        Subscription rxSubscription = mRetrofitHelper.submitInfo(request)
                .compose(RxUtil.<BaseHttpRsp>rxSchedulerHelper())
                .subscribe(new BaseSubscriber(mView) {
                    @Override
                    protected void onSuccess() {
                        mView.onSuccess();
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        mView.cancelLoading(0);
                        mView.showError(msg,0);
                    }
                });
        addSubscrebe(rxSubscription);
    }

}
