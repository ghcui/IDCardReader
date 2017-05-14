package com.yunqi.cardreader.app;

import android.support.v7.app.AppCompatDelegate;

import com.ivsign.android.IDCReader.IDCReaderSDK;
import com.yunqi.cardreader.R;
import com.yunqi.cardreader.base.BaseApplication;
import com.yunqi.cardreader.di.component.AppComponent;
import com.yunqi.cardreader.di.component.DaggerAppComponent;
import com.yunqi.cardreader.di.module.AppModule;
import com.yunqi.cardreader.model.bean.User;
import com.yunqi.cardreader.model.db.GreenDaoHelper;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.Bugly;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class App extends BaseApplication {
    private static App instance;
    private User user;

    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
    }
    private void init() {
        //加入内存泄露监控
        LeakCanary.install(this);
        //加入腾讯bugly
        initBugly();
    }
    private void initBugly() {
        Bugly.init(getApplicationContext(),getString(R.string.tencent_bugly_id), false);
    }


    public static synchronized App getInstance() {
        return instance;
    }


    public static AppComponent getAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(instance))
                .build();
    }


    public void saveUserInfo(User user) {
        this.user = user;
    }

    public User getUserInfo() {
        //为防止系统回收导致对象为null,需从持久化数据库获取对象
        if (user == null) {
            user = new GreenDaoHelper(this).getLastUser();
        }
        return user;
    }
}
