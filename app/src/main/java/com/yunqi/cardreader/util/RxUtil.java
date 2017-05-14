package com.yunqi.cardreader.util;



import com.yunqi.cardreader.model.http.CommonHttpRsp;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by codeest on 2016/8/3.
 */
public class RxUtil {

    /**
     * 统一线程处理
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
    /**
     * 统一返回结果处理
     * @param <T>

     * @return
     */
    public static <T> Observable.Transformer<CommonHttpRsp<T>, T> handleResult() {   //compose判断结果
        return new Observable.Transformer<CommonHttpRsp<T>, T>() {
            @Override
            public Observable<T> call(Observable<CommonHttpRsp<T>> httpResponseObservable) {
                return httpResponseObservable.flatMap(new Func1<CommonHttpRsp<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(CommonHttpRsp<T> t) {
                        if(t.getCode()==200) {
                            return createData(t.getData());
                        } else {
                            return Observable.error(new Exception(t.getMessage()));
                        }
                    }
                });
            }
        };
    }


    /**
     * 生成Observable
     * @param <T>
     * @return
     */
    public static <T> Observable<T> createData(final T t) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(t);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }



}
