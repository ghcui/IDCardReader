package com.yunqi.cardreader.presenter;


import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.idcard.hs.Lua.BlueToolListenr;
import com.idcard.hs.Lua.Info;
import com.yunqi.cardreader.app.App;
import com.yunqi.cardreader.base.RxPresenter;
import com.yunqi.cardreader.model.bean.User;
import com.yunqi.cardreader.model.db.GreenDaoHelper;
import com.yunqi.cardreader.model.http.RetrofitHelper;
import com.yunqi.cardreader.model.response.CommonHttpRsp;
import com.yunqi.cardreader.presenter.contract.LoginContract;
import com.yunqi.cardreader.presenter.contract.RegisterContract;
import com.yunqi.cardreader.presenter.contract.SplashContract;
import com.yunqi.cardreader.rx.ExSubscriber;
import com.yunqi.cardreader.util.FileUtil;
import com.yunqi.cardreader.util.RxUtil;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author ghcui
 * @time 2017/1/11
 */
public class SplashPresenter extends RxPresenter<SplashContract.View> implements SplashContract.Presenter {

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public SplashPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void copyAssets(final Context context, final String oldPath, final String newPath) {
        Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(final Subscriber<? super Boolean> subscriber) {
                try {
                    FileUtil.copyAssets(context,oldPath,newPath);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onNext(Boolean isSuccess) {
                        mView.onSuccess();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void autoLogin(final String username, final String password) {
        Subscription rxSubscription = mRetrofitHelper.doLogin(username, password)
                .compose(RxUtil.<CommonHttpRsp<User>>rxSchedulerHelper())
                .compose(RxUtil.<User>handleResult())
                .subscribe(new ExSubscriber<User>(mView) {
                    @Override
                    protected void onSuccess(User user) {
                        mView.jump2Main(user);
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
