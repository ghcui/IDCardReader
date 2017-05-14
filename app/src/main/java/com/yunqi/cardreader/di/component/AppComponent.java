package com.yunqi.cardreader.di.component;

import com.yunqi.cardreader.app.App;
import com.yunqi.cardreader.di.ContextLife;
import com.yunqi.cardreader.di.module.AppModule;
import com.yunqi.cardreader.model.db.GreenDaoHelper;
import com.yunqi.cardreader.model.http.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author ghcui
 * @time 2017/1/11
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    @ContextLife("Application")
    App getContext();//提供App的Context

    RetrofitHelper  retrofitHelper(); //提供http的帮助类

    GreenDaoHelper greenDaoHelper();    //提供数据库帮助类
}
