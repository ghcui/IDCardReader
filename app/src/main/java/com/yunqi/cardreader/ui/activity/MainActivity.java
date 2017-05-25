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
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.yunqi.cardreader.R;
import com.yunqi.cardreader.app.App;
import com.yunqi.cardreader.base.BaseActivity;
import com.yunqi.cardreader.base.NetActivity;
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


public class MainActivity extends NetActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.grid_module)
    GridView gridModule;
    @BindView(R.id.txt_sended_count)
    TextView txtSendedCount;
    @BindView(R.id.txt_will_send_count)
    TextView txtWillSendCount;

    private List<Module> moduleList = new ArrayList<>();
    private String user_id;

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
        initData();
    }

    private void initData() {
        user_id=App.getInstance().getUserInfo().id;
        mPresenter.getSendedCount(user_id);
        mPresenter.getWillSendCount(user_id);
    }

    private void setWigetListener() {

    }

    @Override
    public void showLoading(int requestCode) {
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
                int type = App.getInstance().getUserInfo().type;
                if (type == 2) {
                    if (className.equals("com.yunqi.cardreader.ui.activity.WillSendActivity")
                            || className.equals("com.yunqi.cardreader.ui.activity.RegisterActivity")
                            || className.equals("com.yunqi.cardreader.ui.activity.CheckOutRoomListActivity")
                            || className.equals("com.yunqi.cardreader.ui.activity.ChangeRoomListActivity")
                            || className.equals("com.yunqi.cardreader.ui.activity.CheckOutRoomListActivity")) {
                        ToastUtil.showNoticeToast(MainActivity.this, "该用户无此权限!");
                        return;
                    }
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

    @Override
    public void showSendedCount(String count) {
        txtSendedCount.setText(count);
    }

    @Override
    public void showWillSendCount(String count) {
        txtWillSendCount.setText(count);
    }
}

