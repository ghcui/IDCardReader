package com.ivsign.android.IDCReader;

/**
 * Created by Administrator on 2017/6/5.
 */

public interface BlueToolListenr {
    void findForDevice(String var1);

    void BluetoothForState(Boolean var1);

    void findDeviceTimeOut();

    void connectTimeOut();
}
