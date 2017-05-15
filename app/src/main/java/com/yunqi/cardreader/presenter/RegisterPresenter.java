package com.yunqi.cardreader.presenter;


import android.text.TextUtils;
import android.util.Log;

import com.idcard.hs.Lua.BlueTool;
import com.idcard.hs.Lua.BlueToolListenr;
import com.idcard.hs.Lua.Info;
import com.yunqi.cardreader.base.RxPresenter;
import com.yunqi.cardreader.model.bean.Module;
import com.yunqi.cardreader.parser.IModuleParse;
import com.yunqi.cardreader.parser.ModuleParse;
import com.yunqi.cardreader.presenter.contract.RegisterContract;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class RegisterPresenter extends RxPresenter<RegisterContract.View> implements RegisterContract.Presenter {


    @Inject
    public RegisterPresenter() {
    }


    @Override
    public void readCarder(final BlueTool ble) {

        Observable.create(new Observable.OnSubscribe<Info>() {
            @Override
            public void call(final Subscriber<? super Info> subscriber) {
                ble.setListenr(new BlueToolListenr() {
                    @Override
                    public void findForDevice(String device) {
                        Log.d(TAG, "findForDevice:device" + device);
                        if (!TextUtils.isEmpty(device)) {
                            ble.connect(device);
                        } else {
                            subscriber.onError(new Throwable("info is null"));
                        }
                    }

                    @Override
                    public void BluetoothForState(Boolean isConnect) {
                        Log.d(TAG, "BluetoothForState:isConnect" + isConnect);
                        if (isConnect) {
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
                    }
                });
                ble.scanf();
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

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.onError();
                    }

                    @Override
                    public void onStart() {
                        mView.onLoading();
                    }
                });
    }


}
