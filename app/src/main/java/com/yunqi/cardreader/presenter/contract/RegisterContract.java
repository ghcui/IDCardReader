package com.yunqi.cardreader.presenter.contract;

import com.ivsign.android.IDCReader.BlueTool;
import com.ivsign.android.IDCReader.Info;
import com.ivsign.android.IDCReader.ExBlueTool;
import com.yunqi.cardreader.base.BasePresenter;
import com.yunqi.cardreader.base.NetView;
import com.yunqi.cardreader.model.bean.ClientInfo;

/**
 * @author ghcui
 * @time 2017/1/11
 */
public interface RegisterContract {
     int REQUST_CODE_CONNECT_BLE = 1;//蓝牙连接
     int REQUST_CODE_READCARD = 2;//读卡
     int REQUST_CODE_SUBMIT = 3;//提交信息

    interface View extends NetView {
        void showContent(Info info);
        void onSuccess();
        void onConnect(boolean isConnect);
    }

    interface Presenter extends BasePresenter<View> {
        void connectBle(ExBlueTool ble);

        void readCarder(BlueTool ble);

        void submitInfo(ClientInfo request);

        void saveLocal(ClientInfo request,String userid);
    }

}
