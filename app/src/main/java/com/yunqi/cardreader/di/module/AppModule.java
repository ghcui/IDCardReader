package com.yunqi.cardreader.di.module;

import com.yunqi.cardreader.app.App;
import com.yunqi.cardreader.di.ContextLife;
import com.yunqi.cardreader.model.db.RealmHelper;
import com.yunqi.cardreader.model.http.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author ghcui
 * @time 2017/1/11
 */
@Module
public class AppModule {
    private App application;

    public AppModule(App application){
        this.application=application;
    }
    @Provides
    @Singleton
    @ContextLife("Application")
    public App provideApplicationContext(){
        return application;
    }

    @Provides
    @Singleton
    public RetrofitHelper provideRetrofitHelper(){
        return new RetrofitHelper(application);
    }
    @Provides
    @Singleton
    public RealmHelper provideRealmHelper(){
        return new RealmHelper(application);
    }
}
