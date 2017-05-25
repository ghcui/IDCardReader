package com.yunqi.cardreader.presenter.contract;

import com.yunqi.cardreader.base.BasePresenter;
import com.yunqi.cardreader.base.NetView;
import com.yunqi.cardreader.model.bean.ClientInfo;

import java.util.List;

/**
 * @author ghcui
 * @time 2017/1/11
 */
public interface WillSendContract {
    interface View extends NetView {
        void onSuccess(ClientInfo request);
        void showContent(List<ClientInfo> infoList);
    }

    interface Presenter extends BasePresenter<View> {

        void submitInfo(ClientInfo request);

        void getWillSendData(long userid);

        void deleteData(long id);
    }

}
