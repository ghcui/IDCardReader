package com.yunqi.cardreader.ui.activity;


import android.support.v7.widget.Toolbar;

import com.yunqi.cardreader.R;
import com.yunqi.cardreader.base.BaseActivity;
import com.yunqi.cardreader.presenter.AboutPresenter;
import com.yunqi.cardreader.presenter.contract.AboutContract;

import butterknife.BindView;


public class AboutActivity extends BaseActivity<AboutPresenter> implements AboutContract.View {


    @BindView(R.id.tool_bar)
    Toolbar toolBar;

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
        initData();
        setWidgetListener();
    }

    private void initData() {
    }

    private void setWidgetListener(){

    }

}

