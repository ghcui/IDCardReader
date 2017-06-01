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
import com.yunqi.cardreader.model.bean.Notice;
import com.yunqi.cardreader.model.http.RetrofitHelper;
import com.yunqi.cardreader.presenter.NewNoticePresenter;
import com.yunqi.cardreader.presenter.contract.NewNoticeContract;
import com.yunqi.cardreader.ui.adapter.NoticeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 最新通知
 */

public class NewNoticeActivity extends NetActivity<NewNoticePresenter> implements NewNoticeContract.View, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    private List<Notice> noticeList = new ArrayList<>();
    private NoticeAdapter mAdapter;
    private int page = 1;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_new_notice;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(toolBar, getString(R.string.module_new_notice));
        initRecyclerView();
        initData();
        setWidgetListener();

    }

    private void setWidgetListener() {
    }


    private void initData() {
        loadData();
    }
    private void initRecyclerView() {
        swipeLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        mAdapter = new NoticeAdapter(noticeList);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(this);
    }

    @Override
    public void showLoading(int requestCode) {
    }

    @Override
    public void showContent(List<Notice> noticeList) {
        if (noticeList.isEmpty()) {
            Log.w(TAG, "No more data!");
        }
        this.noticeList.clear();
        swipeLayout.setRefreshing(false);
        this.noticeList.addAll(noticeList);
        mAdapter.notifyDataSetChanged();
        if (noticeList.size() < RetrofitHelper.PAGE_SIZE) {
            mAdapter.setEnableLoadMore(false);
        }
    }

    @Override
    public void showMoreContent(List<Notice> noticeListMore) {
        if (noticeListMore.isEmpty()) {
            Log.w(TAG, "No more data!");
            mAdapter.setEnableLoadMore(false);
            return;
        }
        this.noticeList.addAll(noticeListMore);
        if (noticeList.size() < RetrofitHelper.PAGE_SIZE) {
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
    }

    private void loadData() {
        mPresenter.getNotices(page);
    }
}

