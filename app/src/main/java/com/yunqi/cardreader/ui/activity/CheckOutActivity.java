package com.yunqi.cardreader.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.yunqi.cardreader.R;
import com.yunqi.cardreader.base.NetActivity;
import com.yunqi.cardreader.model.bean.ClientInfo;
import com.yunqi.cardreader.model.bean.Room;
import com.yunqi.cardreader.presenter.CheckOutPresenter;
import com.yunqi.cardreader.presenter.contract.CheckOutContract;
import com.yunqi.cardreader.ui.adapter.CheckOutAdapter;
import com.yunqi.cardreader.ui.view.RecycleViewDivider;
import com.yunqi.cardreader.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 退房和换房复用
 */
public class CheckOutActivity extends NetActivity<CheckOutPresenter> implements CheckOutContract.View {


    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private Room room;
    private CheckOutAdapter mAdapter;
    private List<ClientInfo> clientInfoList = new ArrayList<>();
    private int type;

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
        type = getIntent().getIntExtra("type", 0);
        if (type == 0) {
            setToolBar(toolBar, getString(R.string.module_check_out));
        } else {
            setToolBar(toolBar, getString(R.string.module_change_room));
        }
        initData();
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, RecycleViewDivider.VERTICAL_LIST));
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initData() {
        room = (Room) getIntent().getSerializableExtra("Room");
        mAdapter = new CheckOutAdapter(this, clientInfoList, mPresenter, type);
        mPresenter.getCustomByRoomCode(room.room_code);
    }

    @Override
    public void onSuccess() {
        ToastUtil.showHookToast(this, "退房成功！");
        mPresenter.getCustomByRoomCode(room.room_code);
    }

    @Override
    public void showContent(List<ClientInfo> infoList) {
        if (infoList.isEmpty()) {
            Log.w(TAG, "No more data!");
            return;
        }
        this.clientInfoList.clear();
        this.clientInfoList.addAll(infoList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String msg, int requestCode) {
        if (requestCode == CheckOutContract.REQUST_CODE_CHECK_OUT) {
            ToastUtil.showErrorToast(this, "退房失败");
        }
    }

    @Override
    public void showLoading(int requestCode) {
        if (requestCode == CheckOutContract.REQUST_CODE_CHECK_OUT) {
            super.showLoading("正在退房...");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            mPresenter.getCustomByRoomCode(room.room_code);
        }
    }
}

