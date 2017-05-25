package com.yunqi.cardreader.presenter.contract;


import com.yunqi.cardreader.base.BasePresenter;
import com.yunqi.cardreader.base.NetView;
import com.yunqi.cardreader.model.bean.Room;

import java.util.List;

/**
 * @author ghcui
 * @time 2017/1/11
 */
public interface RoomListContract {

    interface View extends NetView{

        void showContent(List<Room> listInvoiceApply);

        void showMoreContent(List<Room> listInvoiceApplyMore);
    }

    interface Presenter extends BasePresenter<View> {
        void getRoomList(String uid, String police_station_id,String keyword,int type, int page);
    }
}
