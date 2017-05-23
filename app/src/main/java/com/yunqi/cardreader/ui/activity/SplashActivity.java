package com.yunqi.cardreader.ui.activity;

import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.yunqi.cardreader.R;
import com.yunqi.cardreader.base.NetActivity;
import com.yunqi.cardreader.model.bean.User;
import com.yunqi.cardreader.presenter.SplashPresenter;
import com.yunqi.cardreader.presenter.contract.SplashContract;
import com.yunqi.cardreader.util.FileUtil;
import com.yunqi.cardreader.util.PrefrenceUtils;


import java.io.File;

import butterknife.BindView;

public class SplashActivity extends NetActivity<SplashPresenter> implements SplashContract.View {

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initEventAndData() {
        //如果wltlib目录下base.dat和license.lic两个文件同时存在，则不需要拷贝,否则需要拷贝
        if(!(FileUtil.isFileExit(new File(Environment.getExternalStorageDirectory() + "/wltlib/base.dat"))&&(FileUtil.isFileExit(new File(Environment.getExternalStorageDirectory() + "/wltlib/license.lic"))))){
            mPresenter.copyAssets(this,"wltlib", Environment.getExternalStorageDirectory() + "/wltlib");
        }
        else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkAutoLogin();
                }
            },1000);
        }
    }
    private void checkAutoLogin(){
        String loginName = PrefrenceUtils.getInstance(this).getLoginName();
        String password = PrefrenceUtils.getInstance(this).getPassword();
        boolean isAutoLogin = PrefrenceUtils.getInstance(this).isAutoLogin();
        //如果是自动登陆
        if (isAutoLogin) {
            mPresenter.autoLogin(loginName, password);
        } else {
            jump2Login();
        }
    }

    @Override
    public void onSuccess() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkAutoLogin();
            }
        },1000);
    }
    @Override
    public void jump2Main(User user) {
        Intent intent = new Intent();
        intent.setClass(this,MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void showError(String msg, int requestCode) {
        //一旦自动登陆出错则跳转到登陆界面
        jump2Login();
    }
    private void jump2Login() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
