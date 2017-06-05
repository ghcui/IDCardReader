package com.ivsign.android.IDCReader;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Handler;


public class ExBlueTool extends BlueTool {
    Handler mHandler;
    public ExBlueTool(Context mContext, BluetoothAdapter mBluetoothAdapter) {
        super(mContext, mBluetoothAdapter);
        mHandler=new Handler();
    }

    @Override
    public void scanf() {
        //这里是发送一个延时任务
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //停止扫描
                mBluetoothAdapter.cancelDiscovery();
                if(!isDiscovery){
                    listenr.findDeviceTimeOut();
                }

            }
        }, 1000*8);
        super.scanf();
    }

    @Override
    public Boolean connect(String mac) {
        //处理超时连接的方法
        mHandler.postDelayed(mConnTimeOutRunnable, 5*1000);

        return super.connect(mac);
    }

    /**
     * 连接超时，回调
     */
    private Runnable mConnTimeOutRunnable = new Runnable() {
        @Override
        public void run() {
            //资源释放
            listenr.BluetoothForState(false);
            if(!isConnect){
                listenr.connectTimeOut();
                disconnect();
            }
        }
    };

}
