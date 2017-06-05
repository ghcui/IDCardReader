package com.yunqi.cardreader.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
        if (!(FileUtil.isFileExit(new File(Environment.getExternalStorageDirectory() + "/wltlib/base.dat")) && (FileUtil.isFileExit(new File(Environment.getExternalStorageDirectory() + "/wltlib/license.lic"))))) {
            //判断是否6.0以上的手机   不是就不用
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                //判断是否有这个权限
//                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                    //2、申请权限: 参数二：权限的数组；参数三：请求码
//                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//                }
//                else{
//                    Log.d("","--------------------");
//                }
//            } else {
                mPresenter.copyAssets(this, "wltlib", Environment.getExternalStorageDirectory() + "/wltlib");
//            }
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkAutoLogin();
                }
            }, 1000);
        }
    }

    private void checkAutoLogin() {
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
        checkAutoLogin();
    }

    @Override
    public void showLoading(int requestCode) {
    }

    @Override
    public void jump2Main(User user) {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mPresenter.copyAssets(this, "wltlib", Environment.getExternalStorageDirectory() + "/wltlib");
        }
    }
}
