package com.yunqi.cardreader.presenter.contract;

import com.yunqi.cardreader.base.BasePresenter;
import com.yunqi.cardreader.base.BaseView;
import com.yunqi.cardreader.base.NetView;
import com.yunqi.cardreader.model.bean.Module;

import java.io.InputStream;
import java.util.List;

/**
 * @author ghcui
 * @time 2017/1/11
 */
public interface MainContract {

    interface View extends NetView{
        void showContent(List<Module> moduleList);
        void showSendedCount(String count);
        void showWillSendCount(String count);
    }
    interface Presenter extends BasePresenter<View> {
        void applist(InputStream is);
        void getSendedCount(String user_id);
        void getWillSendCount(String user_id);
    }
}
