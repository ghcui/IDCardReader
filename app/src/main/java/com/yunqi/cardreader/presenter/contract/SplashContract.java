package com.yunqi.cardreader.presenter.contract;

import android.content.Context;

import com.idcard.hs.Lua.BlueTool;
import com.idcard.hs.Lua.Info;
import com.yunqi.cardreader.base.BasePresenter;
import com.yunqi.cardreader.base.NetView;
import com.yunqi.cardreader.model.bean.User;
import com.yunqi.cardreader.model.request.ClientInfoAddRequest;

/**
 * @author ghcui
 * @time 2017/1/11
 */
public interface SplashContract  {
     int REQUST_CODE_COPY_ASSETS = 1;//从assets目录下拷贝文件
     int REQUST_CODE_AUTO_LOGIN = 2;//自动登陆

    interface View extends NetView {
        void onSuccess();
        void jump2Main(User user);
    }

    interface Presenter extends BasePresenter<View> {
        void copyAssets(Context context, String oldPath, String newPath);

        void autoLogin(String username,String password);

    }

}
