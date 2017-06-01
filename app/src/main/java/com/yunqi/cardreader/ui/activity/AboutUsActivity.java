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


public class AboutUsActivity extends BaseActivity<AboutPresenter> implements AboutContract.View {


    @BindView(R.id.tool_bar)
    Toolbar toolBar;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(toolBar, getString(R.string.module_about_us));
    }

    @Override
    public void showVersion(String versionName) {

    }
}

