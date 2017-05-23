package com.yunqi.cardreader.presenter;



import com.yunqi.cardreader.base.RxPresenter;
import com.yunqi.cardreader.model.bean.Room;
import com.yunqi.cardreader.model.http.RetrofitHelper;
import com.yunqi.cardreader.model.response.CommonHttpRsp;
import com.yunqi.cardreader.presenter.contract.RoomListContract;
import com.yunqi.cardreader.rx.ExSubscriber;
import com.yunqi.cardreader.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class RoomListPresenter extends RxPresenter<RoomListContract.View> implements RoomListContract.Presenter {

    private RetrofitHelper mRetrofitHelper;
    @Inject
    public RoomListPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper=retrofitHelper;
    }
    @Override
    public void getRoomList(String uid, String police_station_id, final int page) {
        Subscription rxSubscription = mRetrofitHelper.getRoomList(uid,police_station_id,page)
                .compose(RxUtil.<CommonHttpRsp<List<Room>>>rxSchedulerHelper())
                .compose(RxUtil.<List<Room>>handleResult())
                .subscribe(new ExSubscriber<List<Room>>(mView) {
                    @Override
                    protected void onSuccess(List<Room> listRoom) {
                        //加载第一页数据
                        if(page==1){
                            mView.showContent(listRoom);
                        }
                        //加载更多数据
                        else{
                            mView.showMoreContent(listRoom);
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
