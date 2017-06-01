package com.yunqi.cardreader.presenter.contract;


import com.yunqi.cardreader.base.BasePresenter;
import com.yunqi.cardreader.base.NetView;
import com.yunqi.cardreader.model.bean.Notice;

import java.util.List;

/**
 * @author ghcui
 * @time 2017/1/11
 */
public interface NewNoticeContract {

    interface View extends NetView{

        void showContent(List<Notice> noticeList);

        void showMoreContent(List<Notice> noticeListMore);
    }

    interface Presenter extends BasePresenter<View> {
        void getNotices(int page);
    }
}
