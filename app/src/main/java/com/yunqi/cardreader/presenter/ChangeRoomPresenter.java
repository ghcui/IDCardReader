package com.yunqi.cardreader.presenter;



import com.yunqi.cardreader.base.RxPresenter;
import com.yunqi.cardreader.model.http.RetrofitHelper;
import com.yunqi.cardreader.model.request.ChangeRoomRequest;
import com.yunqi.cardreader.model.response.BaseHttpRsp;
import com.yunqi.cardreader.presenter.contract.ChangeRoomContract;
import com.yunqi.cardreader.rx.BaseSubscriber;
import com.yunqi.cardreader.util.RxUtil;

import javax.inject.Inject;

import rx.Subscription;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class ChangeRoomPresenter extends RxPresenter<ChangeRoomContract.View> implements ChangeRoomContract.Presenter {

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public ChangeRoomPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }


    @Override
    public void changeRoom(ChangeRoomRequest request) {
        Subscription rxSubscription = mRetrofitHelper.changeRoom(request)
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
