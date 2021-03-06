package com.yunqi.cardreader.presenter.contract;

import com.yunqi.cardreader.base.BasePresenter;
import com.yunqi.cardreader.base.NetView;
import com.yunqi.cardreader.model.bean.ClientInfo;
import com.yunqi.cardreader.model.request.CheckOutRequest;

import java.util.List;

/**
 * @author ghcui
 * @time 2017/1/11
 */
public interface CheckOutContract {
    int REQUST_CODE_LIST_QUERY = 1;//列表查询
    int REQUST_CODE_CHECK_OUT = 2;//退房
    interface View extends NetView {
        void onSuccess();
        void showContent(List<ClientInfo> clientInfoList);
    }

    interface Presenter extends BasePresenter<View> {
        void checkOut(CheckOutRequest request);
        void getCustomByRoomCode(String room_code);
    }
}
