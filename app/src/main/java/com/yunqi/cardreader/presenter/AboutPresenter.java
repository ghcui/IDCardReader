package com.yunqi.cardreader.presenter;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import com.yunqi.cardreader.base.RxPresenter;
import com.yunqi.cardreader.presenter.contract.AboutContract;

import javax.inject.Inject;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class AboutPresenter extends RxPresenter<AboutContract.View> implements AboutContract.Presenter {


    @Inject
    public AboutPresenter() {

    }


    @Override
    public void getVersion(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if(TextUtils.isEmpty(versionName)){
                versionName="";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        mView.showVersion(versionName);
    }
}
