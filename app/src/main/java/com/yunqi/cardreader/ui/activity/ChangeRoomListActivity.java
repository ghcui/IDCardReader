package com.yunqi.cardreader.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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

public class ChangeRoomListActivity extends RoomListActivity{

    @Override
    protected void initEventAndData() {
        from=2;
        super.initEventAndData();
    }

}

