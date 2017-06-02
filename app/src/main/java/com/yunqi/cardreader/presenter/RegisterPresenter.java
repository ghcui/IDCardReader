package com.yunqi.cardreader.presenter;


import android.text.TextUtils;
import android.util.Log;

import com.idcard.hs.Lua.BlueTool;
import com.idcard.hs.Lua.BlueToolListenr;
import com.idcard.hs.Lua.Info;
import com.yunqi.cardreader.base.RxPresenter;
import com.yunqi.cardreader.model.bean.ClientInfo;
import com.yunqi.cardreader.model.db.RealmHelper;
import com.yunqi.cardreader.model.http.RetrofitHelper;
import com.yunqi.cardreader.model.response.BaseHttpRsp;
import com.yunqi.cardreader.model.response.CommonHttpRsp;
import com.yunqi.cardreader.presenter.contract.RegisterContract;
import com.yunqi.cardreader.rx.BaseSubscriber;
import com.yunqi.cardreader.util.ImageUploaderUtils;
import com.yunqi.cardreader.util.RxUtil;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.R.id.list;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class RegisterPresenter extends RxPresenter<RegisterContract.View> implements RegisterContract.Presenter {

    private RetrofitHelper mRetrofitHelper;
    private RealmHelper mRealmHelper;

    @Inject
    public RegisterPresenter(RetrofitHelper retrofitHelper, RealmHelper realmHelper) {
        this.mRetrofitHelper = retrofitHelper;
        this.mRealmHelper = realmHelper;
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
    public void submitInfo(final ClientInfo request) {
        Subscription rxSubscription;
        if (TextUtils.isEmpty(request.card_photo_url) && TextUtils.isEmpty(request.user_photo_url)) {
            rxSubscription = mRetrofitHelper.submitInfo(request)
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
        } else {
            ArrayList listUrl = new ArrayList();
            if (!TextUtils.isEmpty(request.card_photo_url)) {
                listUrl.add(request.card_photo_url);
            }
            if (!TextUtils.isEmpty(request.user_photo_url)) {
                listUrl.add(request.user_photo_url);
            }
            rxSubscription = mRetrofitHelper.uploader(ImageUploaderUtils.getRequestBody(listUrl))
                    .flatMap(new Func1<CommonHttpRsp<String[]>, Observable<BaseHttpRsp>>() {
                        @Override
                        public Observable<BaseHttpRsp> call(CommonHttpRsp<String[]> httpRsp) {
                            String[] imgUrl = httpRsp.getData();
                            if (imgUrl == null) {
                                mView.showError("上传失败", RegisterContract.REQUST_CODE_SUBMIT);
                                new Throwable("上传失败");
                            }
                            if (!TextUtils.isEmpty(request.card_photo_url) && !TextUtils.isEmpty(request.user_photo_url) && imgUrl.length == 2) {
                                request.card_photo_url = httpRsp.getData()[0];
                                request.user_photo_url = httpRsp.getData()[1];
                            } else if (!TextUtils.isEmpty(request.card_photo_url) && imgUrl.length == 1) {
                                request.card_photo_url = httpRsp.getData()[0];
                            } else if (!TextUtils.isEmpty(request.user_photo_url) && imgUrl.length == 1) {
                                request.user_photo_url = httpRsp.getData()[0];
                            } else {
                                new Throwable("上传失败");
                            }
                            return mRetrofitHelper.submitInfo(request);
                        }
                    }).compose(RxUtil.<BaseHttpRsp>rxSchedulerHelper())
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
        }
        addSubscrebe(rxSubscription);
    }

    @Override
    public void saveLocal(ClientInfo request, String userid) {
        mRealmHelper.addClientInfo(request, userid);
    }


}
