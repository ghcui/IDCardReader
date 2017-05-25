package com.yunqi.cardreader.presenter;



import com.yunqi.cardreader.base.RxPresenter;
import com.yunqi.cardreader.model.bean.ClientInfo;
import com.yunqi.cardreader.model.bean.Room;
import com.yunqi.cardreader.model.http.RetrofitHelper;
import com.yunqi.cardreader.model.request.CheckOutRequest;
import com.yunqi.cardreader.model.response.BaseHttpRsp;
import com.yunqi.cardreader.model.response.CommonHttpRsp;
import com.yunqi.cardreader.presenter.contract.CheckOutContract;
import com.yunqi.cardreader.rx.BaseSubscriber;
import com.yunqi.cardreader.rx.ExSubscriber;
import com.yunqi.cardreader.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class CheckOutPresenter extends RxPresenter<CheckOutContract.View> implements CheckOutContract.Presenter {

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public CheckOutPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }


    @Override
    public void checkOut(CheckOutRequest request) {
        Subscription rxSubscription = mRetrofitHelper.checkOut(request)
                .compose(RxUtil.<BaseHttpRsp>rxSchedulerHelper())
                .subscribe(new BaseSubscriber(mView) {
                    @Override
                    protected void onSuccess() {
                        mView.onSuccess();
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        mView.showError(msg,CheckOutContract.REQUST_CODE_CHECK_OUT);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getCustomByRoomCode(String room_code) {
        Subscription rxSubscription = mRetrofitHelper.getCustomByRoomCode(room_code)
                .compose(RxUtil.<CommonHttpRsp<List<ClientInfo>>>rxSchedulerHelper())
                .compose(RxUtil.<List<ClientInfo>>handleResult())
                .subscribe(new ExSubscriber<List<ClientInfo>>(mView) {
                    @Override
                    protected void onSuccess(List<ClientInfo> listClientInfo) {
                            mView.showContent(listClientInfo);
                    }
                });
        addSubscrebe(rxSubscription);
    }


}
