package com.yunqi.cardreader.ui.activity;

import android.bluetooth.BluetoothAdapter;
import android.graphics.Bitmap;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.idcard.hs.Lua.BlueTool;
import com.idcard.hs.Lua.Info;
import com.jakewharton.rxbinding.view.RxView;
import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.PictureConfig;
import com.yalantis.ucrop.entity.LocalMedia;
import com.yunqi.cardreader.R;
import com.yunqi.cardreader.base.NetActivity;
import com.yunqi.cardreader.model.request.ClientInfoAddRequest;
import com.yunqi.cardreader.presenter.CheckOutPresenter;
import com.yunqi.cardreader.presenter.RegisterPresenter;
import com.yunqi.cardreader.presenter.contract.CheckOutContract;
import com.yunqi.cardreader.presenter.contract.RegisterContract;
import com.yunqi.cardreader.util.FileUtil;
import com.yunqi.cardreader.util.TimeUtil;
import com.yunqi.cardreader.util.ToastUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * 退房
 */
public class CheckOutActivity extends NetActivity<CheckOutPresenter> implements CheckOutContract.View {


    @BindView(R.id.tool_bar)
    Toolbar toolBar;


    private String time;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_check_out;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(toolBar, getString(R.string.module_check_out));
        setWidgetListener();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initData() {
    }

    private void setWidgetListener() {


    }

    @OnClick(R.id.btn_check_out)
    public void onCheckOut() {
    }


    @Override
    public void onSuccess() {

    }
    @Override
    public void showError(String msg, int requestCode) {
    }

    @Override
    public void cancelLoading(int requestCode) {

    }

    @Override
    public void showLoading(int requestCode) {
    }

}

