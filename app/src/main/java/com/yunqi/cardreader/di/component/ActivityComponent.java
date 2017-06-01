package com.yunqi.cardreader.di.component;

import android.app.Activity;

import com.yunqi.cardreader.ui.activity.AboutActivity;
import com.yunqi.cardreader.ui.activity.AboutUsActivity;
import com.yunqi.cardreader.ui.activity.ChangePwdActivity;
import com.yunqi.cardreader.ui.activity.ChangeRoomActivity;
import com.yunqi.cardreader.ui.activity.CheckOutActivity;
import com.yunqi.cardreader.ui.activity.LoginActivity;
import com.yunqi.cardreader.di.ActivityScope;
import com.yunqi.cardreader.di.module.ActivityModule;
import com.yunqi.cardreader.ui.activity.MainActivity;
import com.yunqi.cardreader.ui.activity.NewNoticeActivity;
import com.yunqi.cardreader.ui.activity.RegisterActivity;
import com.yunqi.cardreader.ui.activity.RoomListActivity;
import com.yunqi.cardreader.ui.activity.SendedActivity;
import com.yunqi.cardreader.ui.activity.SplashActivity;
import com.yunqi.cardreader.ui.activity.UserInfoActivity;
import com.yunqi.cardreader.ui.activity.WillSendActivity;


import dagger.Component;

/**
 * @author ghcui
 * @time 2017/1/11
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();
    void inject(SplashActivity activity);

    void inject(MainActivity activity);

    void inject(LoginActivity activity);

    void inject(RegisterActivity activity);

    void inject(AboutActivity activity);

    void inject(ChangePwdActivity activity);

    void inject(CheckOutActivity activity);

    void inject(WillSendActivity activity);

    void inject(RoomListActivity activity);

    void inject(SendedActivity activity);

    void inject(ChangeRoomActivity activity);

    void inject(UserInfoActivity activity);

    void inject(NewNoticeActivity activity);

    void inject(AboutUsActivity activity);
}
