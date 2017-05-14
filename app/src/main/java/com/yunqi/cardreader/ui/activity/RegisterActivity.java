package com.yunqi.cardreader.ui.activity;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.idcard.hs.Lua.BlueTool;
import com.idcard.hs.Lua.BlueToolListenr;
import com.idcard.hs.Lua.Info;
import com.jakewharton.rxbinding.view.RxView;
import com.yunqi.cardreader.R;
import com.yunqi.cardreader.base.BaseActivity;
import com.yunqi.cardreader.presenter.RegisterPresenter;
import com.yunqi.cardreader.presenter.contract.RegisterContract;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * 入住登记
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {


    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.btn_connect)
    Button btnConnect;
    @BindView(R.id.btn_readCard)
    Button btnReadCard;
    @BindView(R.id.btn_disconnect)
    Button btnDisconnect;
    @BindView(R.id.btn_sleep)
    Button btnSleep;
    @BindView(R.id.btn_awake)
    Button btnAwake;
    BlueTool ble;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(toolBar, getString(R.string.module_register), getString(R.string.action_submit), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        initData();
        setWidgetListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(ble!=null){
            ble.disconnect();
        }
    }

    private void initData() {
        ble = new BlueTool(RegisterActivity.this, BluetoothAdapter.getDefaultAdapter());
    }

    private void setWidgetListener() {
        RxView.clicks(btnSearch)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        findBlueDevices();
                    }
                });
        RxView.clicks(btnConnect)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {

                    }
                });
        RxView.clicks(btnReadCard)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                    }
                });
        RxView.clicks(btnDisconnect)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                    }
                });
        RxView.clicks(btnSleep)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                    }
                });
        RxView.clicks(btnAwake)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                    }
                });
    }

    private void findBlueDevices() {
        ble.setListenr(new BlueToolListenr() {
            @Override
            public void findForDevice(String device) {
                Log.d(TAG,"findForDevice:device"+device);
                if(!TextUtils.isEmpty(device)){
                    ble.connect(device);
                }
            }

            @Override
            public void BluetoothForState(Boolean isConnect) {
                Log.d(TAG,"BluetoothForState:isConnect"+isConnect);
                if(isConnect){
                    Info info=ble.read();
                    Log.d(TAG,"info:"+info);
                }
            }
        });
        ble.scanf();
    }
}

