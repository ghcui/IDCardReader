package com.yunqi.cardreader.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunqi.cardreader.R;
import com.yunqi.cardreader.base.NetActivity;
import com.yunqi.cardreader.model.bean.ClientInfo;
import com.yunqi.cardreader.model.bean.Room;
import com.yunqi.cardreader.model.request.ChangeRoomRequest;
import com.yunqi.cardreader.presenter.ChangeRoomPresenter;
import com.yunqi.cardreader.presenter.contract.ChangeRoomContract;
import com.yunqi.cardreader.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 退房
 */
public class ChangeRoomActivity extends NetActivity<ChangeRoomPresenter> implements ChangeRoomContract.View {


    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.txt_old_room_no)
    TextView txtOldRoomNo;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_certificates_code)
    TextView txtCertificatesCode;
    @BindView(R.id.txt_room_number)
    TextView txtRoomNumber;
    @BindView(R.id.txt_person_number)
    TextView txtPersonNumber;
    @BindView(R.id.txt_room_no)
    TextView txtRoomNo;
    @BindView(R.id.btn_change)
    Button btnChange;
    @BindView(R.id.layout_login_bg)
    RelativeLayout layoutLoginBg;


    private String time;
    private ClientInfo clientInfo;
    private Room selectRoom;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_change_room;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(toolBar, getString(R.string.module_change_room));
        initData();
        setWidgetListener();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initData() {
        clientInfo = (ClientInfo) getIntent().getSerializableExtra("ClientInfo");
        txtName.setText(clientInfo.custom_name);
        txtCertificatesCode.setText(clientInfo.custom_id_card);
        txtRoomNumber.setText(clientInfo.room_number + "");
        txtPersonNumber.setText(clientInfo.retinue + "");
        txtOldRoomNo.setText(clientInfo.room_code);
    }

    private void setWidgetListener() {


    }

    @OnClick(R.id.btn_change)
    public void onChangeRoom() {
        if (selectRoom == null) {
            ToastUtil.showNoticeToast(this, getString(R.string.warming_no_room_code));
            return;
        }
        if (selectRoom.room_code.equals(clientInfo.room_code)) {
            ToastUtil.showNoticeToast(this, getString(R.string.warming_no_same_room));
            return;
        }
        ChangeRoomRequest request = new ChangeRoomRequest();
        request.order_id = clientInfo.order_id;
        request.room_code = selectRoom.room_code;
        mPresenter.changeRoom(request);
    }

    @OnClick(R.id.txt_room_no)
    public void onSelectRoom() {
        Intent intent = new Intent(this, RoomListActivity.class);
        intent.putExtra("operater", 1);
        startActivityForResult(intent, 1);
    }


    @Override
    public void onSuccess() {
        ToastUtil.showHookToast(this, "换房成功！");
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void showError(String msg, int requestCode) {
        ToastUtil.showHookToast(this, "换房失败！");
    }


    @Override
    public void showLoading(int requestCode) {
        super.showLoading("提交中...");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            selectRoom = (Room) data.getSerializableExtra("Room");
            txtRoomNo.setText(selectRoom.room_code);
        }
    }
}

