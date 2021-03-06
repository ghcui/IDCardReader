package com.yunqi.cardreader.presenter;


import com.yunqi.cardreader.app.App;
import com.yunqi.cardreader.base.RxPresenter;
import com.yunqi.cardreader.model.bean.User;
import com.yunqi.cardreader.model.db.RealmHelper;
import com.yunqi.cardreader.model.http.RetrofitHelper;
import com.yunqi.cardreader.model.response.CommonHttpRsp;
import com.yunqi.cardreader.presenter.contract.LoginContract;
import com.yunqi.cardreader.rx.ExSubscriber;
import com.yunqi.cardreader.util.RxUtil;



import javax.inject.Inject;

import rx.Subscription;

/**
 * @author ghcui
 * @time 2017/1/11
 */
public class LoginPresenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter {

    private RetrofitHelper mRetrofitHelper;
    private RealmHelper mRealmHelper;

    @Inject
    public LoginPresenter(RetrofitHelper retrofitHelper,RealmHelper realmHelper) {
        mRetrofitHelper = retrofitHelper;
        mRealmHelper=realmHelper;
    }

    @Override
    public void doLogin(final String username, final String password) {
        Subscription rxSubscription = mRetrofitHelper.doLogin(username, password)
                .compose(RxUtil.<CommonHttpRsp<User>>rxSchedulerHelper())
                .compose(RxUtil.<User>handleResult())
                .subscribe(new ExSubscriber<User>(mView) {
                    @Override
                    protected void onSuccess(User user) {
                        App.getInstance().saveUserInfo(user);
                        mRealmHelper.addUser(user);
                        mView.jump2Main(user);
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
