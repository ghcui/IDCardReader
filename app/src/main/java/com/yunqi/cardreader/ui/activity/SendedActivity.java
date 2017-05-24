package com.yunqi.cardreader.ui.activity;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.yunqi.cardreader.R;
import com.yunqi.cardreader.base.NetActivity;
import com.yunqi.cardreader.model.bean.ClientInfo;
import com.yunqi.cardreader.presenter.WillSendPresenter;
import com.yunqi.cardreader.presenter.contract.WillSendContract;
import com.yunqi.cardreader.ui.adapter.WillSendAdapter;
import com.yunqi.cardreader.ui.view.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class SendedActivity extends NetActivity<WillSendPresenter> implements WillSendContract.View {


    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private WillSendAdapter mAdapter;
    private List<ClientInfo> clientInfoList=new ArrayList<>();
    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_sended;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(toolBar, getString(R.string.module_sended));
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
        for (int i=0;i<20;i++){

        }
        mAdapter=new WillSendAdapter(clientInfoList);
        recyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.btn_upload)
    public void onUpload() {

    }

    @Override
    public void onSuccess() {

    }
}

