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
     int REQUST_CODE_READCARD = 1;//读卡
     int REQUST_CODE_SUBMIT = 2;//提交信息

    interface View extends NetView {
        void showContent(Info info);
        void onSuccess();
    }

    interface Presenter extends BasePresenter<View> {
        void readCarder(BlueTool ble);

        void submitInfo(ClientInfoAddRequest request);

        void saveLocal(ClientInfoAddRequest request);
    }

}
