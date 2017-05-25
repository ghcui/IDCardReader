package com.yunqi.cardreader.ui.activity;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.yunqi.cardreader.R;
import com.yunqi.cardreader.app.App;
import com.yunqi.cardreader.base.NetActivity;
import com.yunqi.cardreader.model.bean.ClientInfo;
import com.yunqi.cardreader.presenter.WillSendPresenter;
import com.yunqi.cardreader.presenter.contract.WillSendContract;
import com.yunqi.cardreader.ui.adapter.WillSendAdapter;
import com.yunqi.cardreader.ui.view.RecycleViewDivider;
import com.yunqi.cardreader.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class WillSendActivity extends NetActivity<WillSendPresenter> implements WillSendContract.View {


    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btn_upload)
    Button btnUpload;
    private WillSendAdapter mAdapter;
    private List<ClientInfo> clientInfoList=new ArrayList<>();
    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_will_send;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(toolBar, getString(R.string.module_will_send));
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, RecycleViewDivider.VERTICAL_LIST));
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        mAdapter=new WillSendAdapter(clientInfoList);
        recyclerView.setAdapter(mAdapter);
        mPresenter.getWillSendData(App.getInstance().getUserInfo().id);
    }

    @OnClick(R.id.btn_upload)
    public void onUpload() {
        for (ClientInfo info:clientInfoList){
            mPresenter.submitInfo(info);
        }
    }

    @Override
    public void showLoading(int requestCode) {
        btnUpload.setText("上传中...");
        btnUpload.setClickable(false);
    }

    @Override
    public void onSuccess(ClientInfo request) {
        clientInfoList.remove(request);
        mAdapter.notifyDataSetChanged();
        mPresenter.deleteData(request.id);
        if(clientInfoList.size()==0){
            ToastUtil.showHookToast(this,"数据已全部上传");
            btnUpload.setClickable(true);
            btnUpload.setText("确认上传");
        }
    }
    @Override
    public void showContent(List<ClientInfo> infoList) {
        if (infoList.isEmpty()) {
            Log.w(TAG, "No more data!");
            btnUpload.setClickable(false);
            return;
        }
        btnUpload.setClickable(true);
        this.clientInfoList.clear();
        this.clientInfoList.addAll(infoList);
        mAdapter.notifyDataSetChanged();
    }
}

