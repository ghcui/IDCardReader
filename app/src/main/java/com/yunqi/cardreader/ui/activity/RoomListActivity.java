package com.yunqi.cardreader.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.yunqi.cardreader.R;
import com.yunqi.cardreader.app.App;
import com.yunqi.cardreader.base.NetActivity;
import com.yunqi.cardreader.model.bean.Room;
import com.yunqi.cardreader.model.http.RetrofitHelper;
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
    EditText searchView;
    private List<Room> roomList = new ArrayList<>();
    private RoomAdapter mAdapter;
    private String keyword = "";
    private String uid;
    private String police_station_id = "";
    private int page = 1;
    private int type = 1;//1=普通查询 2=退换房查询 默认1 3、查询有空闲的房间
    private int operater = 0;//0表示查询，1：从其他界面选择房间
    protected int from = 0;//默认0表示查询所有房间，1、表示选择退房列表查询 2、表示选择换房列表查询

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
        operater = getIntent().getIntExtra("operater", 0);
        setSearchToolBar();
        initRecyclerView();
        initData();
        setWidgetListener();

    }

    private void setWidgetListener() {
        searchView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(RoomListActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    keyword = searchView.getText().toString().trim();
                    page=1;
                    loadData();
                }
                return false;
            }
        });
    }


    private void initData() {
        uid = App.getInstance().getUserInfo().id;
        if (from == 1 || from == 2) {
            type = 2;
        }
        if (operater == 1) {
            type = 3;
        }
        loadData();
    }

    private void setSearchToolBar() {
        toolBar.setTitle("");//使用处于中间位置自定义Title
        searchView = (EditText) toolBar.findViewById(R.id.edit_search_view);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressedSupport();
            }
        });
    }

    private void initRecyclerView() {
        swipeLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, RecycleViewDivider.VERTICAL_LIST));
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Room room = roomList.get(position);
                if (operater == 1) {
                    Intent intent = new Intent();
                    intent.putExtra("Room", room);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                if (from == 0) {

                } else if (from == 1) {
                    //退房
                    Intent intent = new Intent(RoomListActivity.this, CheckOutActivity.class);
                    intent.putExtra("Room", room);
                    intent.putExtra("type", 0);
                    startActivity(intent);
                } else if (from == 2) {
                    //换房
                    Intent intent = new Intent(RoomListActivity.this, CheckOutActivity.class);
                    intent.putExtra("Room", room);
                    intent.putExtra("type", 1);
                    startActivity(intent);
                }
            }
        });
        mAdapter = new RoomAdapter(roomList);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(this);
    }

    @Override
    public void showLoading(int requestCode) {
    }

    @Override
    public void showContent(List<Room> roomList) {
        if (roomList.isEmpty()) {
            Log.w(TAG, "No more data!");
        }
        this.roomList.clear();
        swipeLayout.setRefreshing(false);
        this.roomList.addAll(roomList);
        mAdapter.notifyDataSetChanged();
        if (roomList.size() < RetrofitHelper.PAGE_SIZE) {
            mAdapter.setEnableLoadMore(false);
        }
    }

    @Override
    public void showMoreContent(List<Room> roomListMore) {
        if (roomListMore.isEmpty()) {
            Log.w(TAG, "No more data!");
            mAdapter.setEnableLoadMore(false);
            return;
        }
        this.roomList.addAll(roomList);
        if (roomList.size() < RetrofitHelper.PAGE_SIZE) {
            mAdapter.setEnableLoadMore(false);
        }
        mAdapter.notifyDataSetChanged();
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
                resetData();
                loadData();
            }
        }, 500);
    }

    private void resetData() {
        page = 1;
        keyword = "";
    }

    private void loadData() {
        mPresenter.getRoomList(uid, police_station_id, keyword, type, page);
    }
}

