package com.yunqi.cardreader.ui.activity;


import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.yunqi.cardreader.R;
import com.yunqi.cardreader.base.NetActivity;
import com.yunqi.cardreader.model.bean.Room;
import com.yunqi.cardreader.presenter.RoomListPresenter;
import com.yunqi.cardreader.presenter.contract.RoomListContract;
import com.yunqi.cardreader.ui.adapter.RoomAdapter;
import com.yunqi.cardreader.ui.view.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 房间列表界面
 */

public class RoomListActivity extends NetActivity<RoomListPresenter> implements RoomListContract.View, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    private List<Room> roomList = new ArrayList<>();
    private RoomAdapter mAdapter;
    private int page = 1;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_room_list;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(toolBar, getString(R.string.module_room_list));
        initRecyclerView();
    }

    private void initRecyclerView() {
        swipeLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, RecycleViewDivider.VERTICAL_LIST));
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        mAdapter = new RoomAdapter(roomList);
        recyclerView.setAdapter(mAdapter);
        mPresenter.getRoomList("", "", page);
        mAdapter.setOnLoadMoreListener(this);
    }

    @Override
    public void showContent(List<Room> roomList) {
        if (roomList.isEmpty()) {
            Log.w(TAG, "No more data!");
            return;
        }
        this.roomList.addAll(roomList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMoreContent(List<Room> roomListMore) {

    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        loadData();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 1;
                loadData();
            }
        }, 500);
    }

    private void loadData() {
        mPresenter.getRoomList("", "", page);
    }
}

