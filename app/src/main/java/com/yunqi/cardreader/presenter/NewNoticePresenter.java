package com.yunqi.cardreader.presenter;



import com.yunqi.cardreader.base.RxPresenter;
import com.yunqi.cardreader.model.bean.Notice;
import com.yunqi.cardreader.model.bean.Room;
import com.yunqi.cardreader.model.http.RetrofitHelper;
import com.yunqi.cardreader.model.response.CommonHttpRsp;
import com.yunqi.cardreader.presenter.contract.NewNoticeContract;
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
public class NewNoticePresenter extends RxPresenter<NewNoticeContract.View> implements NewNoticeContract.Presenter {

    private RetrofitHelper mRetrofitHelper;
    @Inject
    public NewNoticePresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper=retrofitHelper;
    }
    @Override
    public void getNotices(final int page) {
        Subscription rxSubscription = mRetrofitHelper.getNotices(page)
                .compose(RxUtil.<CommonHttpRsp<List<Notice>>>rxSchedulerHelper())
                .compose(RxUtil.<List<Notice>>handleResult())
                .subscribe(new ExSubscriber<List<Notice>>(mView) {
                    @Override
                    protected void onSuccess(List<Notice> noticeList) {
                        //加载第一页数据
                        if(page==1){
                            mView.showContent(noticeList);
                        }
                        //加载更多数据
                        else{
                            mView.showMoreContent(noticeList);
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
