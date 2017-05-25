package com.yunqi.cardreader.presenter;


import com.yunqi.cardreader.base.RxPresenter;
import com.yunqi.cardreader.model.bean.ClientInfo;
import com.yunqi.cardreader.model.db.RealmHelper;
import com.yunqi.cardreader.model.http.RetrofitHelper;
import com.yunqi.cardreader.model.response.BaseHttpRsp;
import com.yunqi.cardreader.presenter.contract.WillSendContract;
import com.yunqi.cardreader.rx.BaseSubscriber;
import com.yunqi.cardreader.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import io.realm.RealmResults;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class WillSendPresenter extends RxPresenter<WillSendContract.View> implements WillSendContract.Presenter {

    private RetrofitHelper mRetrofitHelper;
    private RealmHelper mRealmHelper;

    @Inject
    public WillSendPresenter(RetrofitHelper retrofitHelper, RealmHelper realmHelper) {
        this.mRetrofitHelper = retrofitHelper;
        this.mRealmHelper = realmHelper;
    }


    @Override
    public void submitInfo(final ClientInfo request) {
        Subscription rxSubscription = mRetrofitHelper.submitInfo(request)
                .compose(RxUtil.<BaseHttpRsp>rxSchedulerHelper())
                .subscribe(new BaseSubscriber(mView) {
                    @Override
                    protected void onSuccess() {
                        mView.onSuccess(request);
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        mView.cancelLoading(0);
                        mView.showError(msg, 0);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getWillSendData(String userid) {
        List<ClientInfo> data=mRealmHelper.getClientInfos(userid);
        mView.showContent(data);
    }

    @Override
    public void deleteData(long id) {
        mRealmHelper.deleteClientInfo(id);
    }
}
