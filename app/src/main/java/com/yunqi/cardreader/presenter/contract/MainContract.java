package com.yunqi.cardreader.presenter.contract;

import com.yunqi.cardreader.base.BasePresenter;
import com.yunqi.cardreader.base.BaseView;
import com.yunqi.cardreader.model.bean.Module;

import java.io.InputStream;
import java.util.List;

/**
 * @author ghcui
 * @time 2017/1/11
 */
public interface MainContract {

    interface View extends BaseView{
        void showContent(List<Module> moduleList);
    }
    interface Presenter extends BasePresenter<View> {
        void applist(InputStream is);
    }
}
