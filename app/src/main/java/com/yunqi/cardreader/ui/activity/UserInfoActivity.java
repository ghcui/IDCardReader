package com.yunqi.cardreader.ui.activity;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.yunqi.cardreader.R;
import com.yunqi.cardreader.app.App;
import com.yunqi.cardreader.base.BaseActivity;
import com.yunqi.cardreader.model.bean.User;
import com.yunqi.cardreader.presenter.AboutPresenter;
import com.yunqi.cardreader.presenter.contract.AboutContract;

import butterknife.BindView;
import butterknife.ButterKnife;


public class UserInfoActivity extends BaseActivity<AboutPresenter> implements AboutContract.View {


    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.txt_nickname)
    TextView txtNickname;
    @BindView(R.id.txt_phone)
    TextView txtPhone;
    @BindView(R.id.txt_type)
    TextView txtType;
    @BindView(R.id.txt_area)
    TextView txtArea;
    @BindView(R.id.txt_police)
    TextView txtPolice;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_userinfo;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(toolBar, getString(R.string.module_user_info));
        User user = App.getInstance().getUserInfo();
        txtNickname.setText(user.nick_name);
        txtPhone.setText(user.phone);
        String strType = "";
        if (user.type == 2) {
            strType = "公安管理人员";
        } else if (user.type == 1) {
            strType = "房东";
        }
        txtType.setText(strType);
    }

    @Override
    public void showVersion(String versionName) {

    }
}

