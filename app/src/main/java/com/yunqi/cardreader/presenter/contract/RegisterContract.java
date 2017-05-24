package com.yunqi.cardreader.presenter.contract;

import com.idcard.hs.Lua.BlueTool;
import com.idcard.hs.Lua.Info;
import com.yunqi.cardreader.base.BasePresenter;
import com.yunqi.cardreader.base.BaseView;
import com.yunqi.cardreader.base.NetView;
import com.yunqi.cardreader.model.request.ClientInfoAddRequest;

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
        void connectBle(BlueTool ble);

        void readCarder(BlueTool ble);

        void submitInfo(ClientInfoAddRequest request);

        void saveLocal(ClientInfoAddRequest request);
    }

}
