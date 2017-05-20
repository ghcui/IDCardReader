package com.yunqi.cardreader.presenter;




import com.yunqi.cardreader.base.RxPresenter;
import com.yunqi.cardreader.model.http.RetrofitHelper;
import com.yunqi.cardreader.model.request.ChangePwdRequest;
import com.yunqi.cardreader.model.response.BaseHttpRsp;
import com.yunqi.cardreader.model.response.CommonHttpRsp;
import com.yunqi.cardreader.presenter.contract.ChangePwdContract;
import com.yunqi.cardreader.rx.BaseSubscriber;
import com.yunqi.cardreader.rx.ExSubscriber;
import com.yunqi.cardreader.util.RxUtil;

import javax.inject.Inject;

import rx.Subscription;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class ChangePwdPresenter extends RxPresenter<ChangePwdContract.View> implements ChangePwdContract.Presenter {


    private RetrofitHelper mRetrofitHelper;

    @Inject
    public ChangePwdPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }


    @Override
    public void changePwd(ChangePwdRequest request) {
        Subscription rxSubscription = mRetrofitHelper.changePwd(request)
                .compose(RxUtil.<BaseHttpRsp>rxSchedulerHelper())
                .subscribe(new BaseSubscriber(mView) {
                    @Override
                    protected void onSuccess() {
                        mView.onSuccess();
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        mView.showError(msg,0);
                    }
                });
        addSubscrebe(rxSubscription);
    }


}
