package com.yunqi.cardreader.presenter.contract;

import com.idcard.hs.Lua.BlueTool;
import com.idcard.hs.Lua.Info;
import com.yunqi.cardreader.base.BasePresenter;
import com.yunqi.cardreader.base.NetView;
import com.yunqi.cardreader.model.request.ClientInfoAddRequest;

/**
 * @author ghcui
 * @time 2017/1/11
 */
public interface WillSendContract {
    interface View extends NetView {
        void onSuccess();
    }

    interface Presenter extends BasePresenter<View> {

        void submitInfo(ClientInfoAddRequest request);

    }

}
