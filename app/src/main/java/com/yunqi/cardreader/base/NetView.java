package com.yunqi.cardreader.base;

/**
 * Created by codeest on 2016/8/2.
 * View基类
 */
public interface NetView extends BaseView{

    void showLoading(int requestCode);

    void cancelLoading(int requestCode);

    void showError(String msg, int requestCode);

}
