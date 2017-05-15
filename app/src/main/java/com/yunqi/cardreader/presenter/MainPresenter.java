package com.yunqi.cardreader.presenter;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.yunqi.cardreader.app.App;
import com.yunqi.cardreader.base.RxPresenter;
import com.yunqi.cardreader.model.bean.Module;
import com.yunqi.cardreader.model.http.RetrofitHelper;
import com.yunqi.cardreader.parser.IModuleParse;
import com.yunqi.cardreader.parser.ModuleParse;
import com.yunqi.cardreader.presenter.contract.MainContract;
import com.yunqi.cardreader.ui.activity.MainActivity;
import com.yunqi.cardreader.util.ToastUtil;

import java.io.File;
import java.io.InputStream;
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
public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    @Inject
    public MainPresenter() {
    }


    @Override
    public void applist(final InputStream is) {
        Observable.create(new Observable.OnSubscribe<List<Module>>() {
            @Override
            public void call(Subscriber<? super List<Module>> subscriber) {
                try {
                    //通过assertmanager的open方法获取到beauties.xml文件的输入流
                    //初始化自定义的实现类BeautyParserImpl
                    IModuleParse parser = new ModuleParse();
                    //调用pbp的parse()方法，将输入流传进去解析，返回的链表结果赋给beautyList
                    List<Module> moduleList = parser.parse(is);
                    subscriber.onNext(moduleList);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<List<Module>>() {
                    @Override
                    public void onNext(List<Module> modules) {
                        mView.showContent(modules);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}
