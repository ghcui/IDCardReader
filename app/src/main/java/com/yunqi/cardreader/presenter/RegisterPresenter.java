package com.yunqi.cardreader.presenter;


import android.text.TextUtils;
import android.util.Log;

import com.idcard.hs.Lua.BlueTool;
import com.idcard.hs.Lua.BlueToolListenr;
import com.idcard.hs.Lua.Info;
import com.yunqi.cardreader.base.RxPresenter;
import com.yunqi.cardreader.model.http.RetrofitHelper;
import com.yunqi.cardreader.model.request.ClientInfoAddRequest;
import com.yunqi.cardreader.model.response.BaseHttpRsp;
import com.yunqi.cardreader.presenter.contract.RegisterContract;
import com.yunqi.cardreader.rx.BaseSubscriber;
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
public class RegisterPresenter extends RxPresenter<RegisterContract.View> implements RegisterContract.Presenter {

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public RegisterPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }


    @Override
    public void connectBle(final BlueTool ble) {
        Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(final Subscriber<? super Boolean> subscriber) {
                ble.setListenr(new BlueToolListenr() {
                    @Override
                    public void findForDevice(String device) {
                        Log.d(TAG, "findForDevice:device" + device);
                        if (!TextUtils.isEmpty(device)) {
                            ble.connect(device);
                        } else {
                            subscriber.onError(new Throwable("No Found device!"));
                        }
                    }

                    @Override
                    public void BluetoothForState(Boolean isConnect) {
                        Log.d(TAG, "BluetoothForState:isConnect" + isConnect);
                        subscriber.onNext(isConnect);
                        subscriber.onCompleted();
                    }
                });
                ble.scanf();
            }
        }).subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onNext(Boolean isConnect) {
                        if (mView != null) {
                            mView.onConnect(isConnect);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        mView.cancelLoading(RegisterContract.REQUST_CODE_CONNECT_BLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.cancelLoading(RegisterContract.REQUST_CODE_CONNECT_BLE);
                        mView.showError("蓝牙连接失败，请重新连接!", RegisterContract.REQUST_CODE_CONNECT_BLE);
                    }

                    @Override
                    public void onStart() {
                        mView.showLoading(RegisterContract.REQUST_CODE_CONNECT_BLE);
                    }
                });
    }

    @Override
    public void readCarder(final BlueTool ble) {
        Observable.create(new Observable.OnSubscribe<Info>() {
            @Override
            public void call(final Subscriber<? super Info> subscriber) {
                Info info = ble.read();
                Log.d(TAG, "info:" + info);
                if (info == null) {
                    subscriber.onError(new Throwable("info is null"));
                } else {
                    if (TextUtils.isEmpty(info.getIdNo())) {
                        subscriber.onError(new Throwable("info's content is null"));
                    } else {
                        subscriber.onNext(info);
                        subscriber.onCompleted();
                    }
                }
            }
        }).subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Subscriber<Info>() {
                    @Override
                    public void onNext(Info info) {
                        mView.showContent(info);
                    }

                    @Override
                    public void onCompleted() {
                        mView.cancelLoading(RegisterContract.REQUST_CODE_READCARD);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.cancelLoading(RegisterContract.REQUST_CODE_READCARD);
                        mView.showError("读卡失败，请再试一次!", RegisterContract.REQUST_CODE_READCARD);
                    }

                    @Override
                    public void onStart() {
                        mView.showLoading(RegisterContract.REQUST_CODE_READCARD);
                    }
                });
    }

    @Override
    public void submitInfo(ClientInfoAddRequest request) {
        Subscription rxSubscription = mRetrofitHelper.submitInfo(request)
                .compose(RxUtil.<BaseHttpRsp>rxSchedulerHelper())
                .subscribe(new BaseSubscriber(mView, RegisterContract.REQUST_CODE_SUBMIT) {
                    @Override
                    protected void onSuccess() {
                        mView.onSuccess();
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        mView.cancelLoading(RegisterContract.REQUST_CODE_SUBMIT);
                        mView.showError(msg, RegisterContract.REQUST_CODE_SUBMIT);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void saveLocal(ClientInfoAddRequest request) {

    }


}
