package com.yunqi.cardreader.ui.activity;


import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.tencent.bugly.beta.Beta;
import com.yunqi.cardreader.R;
import com.yunqi.cardreader.app.App;
import com.yunqi.cardreader.base.BaseActivity;
import com.yunqi.cardreader.constants.Constants;
import com.yunqi.cardreader.presenter.AboutPresenter;
import com.yunqi.cardreader.presenter.contract.AboutContract;
import com.yunqi.cardreader.util.PrefrenceUtils;

import butterknife.BindView;
import butterknife.OnClick;


public class AboutActivity extends BaseActivity<AboutPresenter> implements AboutContract.View {


    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.txt_version)
    TextView txtVersion;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_about;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(toolBar, getString(R.string.module_about));
        mPresenter.getVersion(this);
    }
    @OnClick(R.id.btn_logout)
    public void onLogout() {
        App.getInstance().killAllActivities();
        PrefrenceUtils.getInstance(this).saveLoginStatus(Constants.STATUS_UNLOGIN);
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 注意本行的FLAG设置
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.rlayout_updata)
    public void onUpdate() {
        Beta.checkUpgrade();
    }
    @OnClick(R.id.rlayout_userinfo)
    public void onUserInfo() {

    }


    @Override
    public void showVersion(String versionName) {
        txtVersion.setText("V"+versionName);
    }
}

