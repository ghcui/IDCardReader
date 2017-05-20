package com.yunqi.cardreader.ui.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.yunqi.cardreader.R;
import com.yunqi.cardreader.app.App;
import com.yunqi.cardreader.base.BaseActivity;
import com.yunqi.cardreader.model.bean.Module;
import com.yunqi.cardreader.presenter.MainPresenter;
import com.yunqi.cardreader.presenter.contract.MainContract;
import com.yunqi.cardreader.ui.adapter.ModuleAdapter;
import com.yunqi.cardreader.util.ToastUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;


public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.grid_module)
    GridView gridModule;

    private List<Module> moduleList = new ArrayList<>();

    /**
     * 定义一个变量，来标识是否退出
     */
    private boolean isExit = false;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };


    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {
        setWigetListener();
        try {
            InputStream is = this.getAssets().open("home_module.xml");
            mPresenter.applist(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        initGridData();
    }

    private void setWigetListener() {

    }


    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(this, this.getResources().getString(R.string.warming_doubleclick_logout), Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            App.getInstance().killAllActivities();
            super.onBackPressedSupport();
        }
    }

    private void initGridData() {
        gridModule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent();
                String className = moduleList.get(position).className;
                if (TextUtils.isEmpty(className)) {
                    return;
                }
                try {
                    ComponentName cn = new ComponentName(getPackageName(), className);
                    intent.setComponent(cn);
                    startActivity(intent);
                } catch (Exception e) {
                    ToastUtil.showNoticeToast(MainActivity.this, "正在开发中，敬请期待!");
                }
            }
        });
    }

    @Override
    public void onBackPressedSupport() {
        exit();
    }

    @Override
    public void showContent(List<Module> moduleList) {
        this.moduleList = moduleList;
        ModuleAdapter adapter = new ModuleAdapter(this, moduleList);
        gridModule.setAdapter(adapter);
    }
}

