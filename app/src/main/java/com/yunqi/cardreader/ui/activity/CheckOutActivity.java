package com.yunqi.cardreader.ui.activity;

import android.support.v7.widget.Toolbar;

import com.yunqi.cardreader.R;
import com.yunqi.cardreader.base.NetActivity;
import com.yunqi.cardreader.presenter.CheckOutPresenter;
import com.yunqi.cardreader.presenter.contract.CheckOutContract;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 退房
 */
public class CheckOutActivity extends NetActivity<CheckOutPresenter> implements CheckOutContract.View {


    @BindView(R.id.tool_bar)
    Toolbar toolBar;


    private String time;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_check_out;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(toolBar, getString(R.string.module_check_out));
        setWidgetListener();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initData() {
    }

    private void setWidgetListener() {


    }

    @OnClick(R.id.btn_check_out)
    public void onCheckOut() {
    }


    @Override
    public void onSuccess() {

    }
    @Override
    public void showError(String msg, int requestCode) {
    }

    @Override
    public void cancelLoading(int requestCode) {

    }

    @Override
    public void showLoading(int requestCode) {
    }

}

