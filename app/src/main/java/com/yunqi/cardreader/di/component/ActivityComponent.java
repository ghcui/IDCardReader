package com.yunqi.cardreader.di.component;

import android.app.Activity;

import com.yunqi.cardreader.ui.activity.AboutActivity;
import com.yunqi.cardreader.ui.activity.LoginActivity;
import com.yunqi.cardreader.di.ActivityScope;
import com.yunqi.cardreader.di.module.ActivityModule;
import com.yunqi.cardreader.ui.activity.MainActivity;
import com.yunqi.cardreader.ui.activity.RegisterActivity;


import dagger.Component;

/**
 * @author ghcui
 * @time 2017/1/11
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(MainActivity activity);

    void inject(LoginActivity activity);

    void inject(RegisterActivity activity);

    void inject(AboutActivity activity);
}
