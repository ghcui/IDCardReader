package com.yunqi.cardreader.ui.activity;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.idcard.hs.Lua.BlueTool;
import com.idcard.hs.Lua.BlueToolListenr;
import com.idcard.hs.Lua.Info;
import com.jakewharton.rxbinding.view.RxView;
import com.yunqi.cardreader.R;
import com.yunqi.cardreader.base.BaseActivity;
import com.yunqi.cardreader.presenter.RegisterPresenter;
import com.yunqi.cardreader.presenter.contract.RegisterContract;
import com.yunqi.cardreader.util.TimeUtil;
import com.yunqi.cardreader.util.ToastUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * 入住登记
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {


    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.txt_room_number)
    TextView txtRoomNumber;
    @BindView(R.id.txt_bed_number)
    TextView txtBedNumber;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_sex)
    TextView txtSex;
    @BindView(R.id.txt_nation)
    TextView txtNation;
    @BindView(R.id.txt_birthday)
    TextView txtBirthday;
    @BindView(R.id.txt_certificates_type)
    TextView txtCertificatesType;
    @BindView(R.id.txt_certificates_code)
    TextView txtCertificatesCode;
    @BindView(R.id.txt_address)
    TextView txtAddress;
    @BindView(R.id.txt_check_time)
    TextView txtCheckTime;
    @BindView(R.id.img_certificates)
    ImageView imgCertificates;
    @BindView(R.id.img_personal)
    ImageView imgPersonal;
    @BindView(R.id.btn_read_card)
    Button btnReadCard;
    BlueTool ble;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(toolBar, getString(R.string.module_register), getString(R.string.action_submit), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        initData();
        setWidgetListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disconnect();
    }

    private void initData() {
        ble = new BlueTool(RegisterActivity.this, BluetoothAdapter.getDefaultAdapter());
    }

    private void setWidgetListener() {
        RxView.clicks(btnReadCard)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        mPresenter.readCarder(ble);
                    }
                });

    }

    @Override
    public void showContent(Info info) {
        resetData();
        if(!TextUtils.isEmpty(info.getName())){
            txtName.setText(info.getName());
        }
        if(!TextUtils.isEmpty(info.getSex())){
            String sex = info.getIdNo().substring(16, 17);
            if(Integer.parseInt(sex)%2==0){
                sex = "0";
                txtSex.setText("女");
            }else{
                txtSex.setText("男");
                sex = "1";
            }
            info.setSex(sex);
        }
        if(!TextUtils.isEmpty(info.getName())){
            txtNation.setText(info.getNationlity());
        }
        if(!TextUtils.isEmpty(info.getBirthdate())){
            txtBirthday.setText(info.getBirthdate());
        }
        if(!TextUtils.isEmpty(info.getIdNo())){
            txtCertificatesCode.setText(info.getIdNo());
        }
        if(!TextUtils.isEmpty(info.getIdNo())){
            txtAddress.setText(info.getAddress());
        }
        txtCheckTime.setText(TimeUtil.format(TimeUtil.getSysTime(),"yyyy-mm-dd hh:mm"));
        if(info.getBmp()!=null){
            imgCertificates.setImageBitmap(info.getBmp());
        }
        disconnect();
    }

    private void disconnect(){
        if (ble != null) {
            ble.disconnect();
        }
    }

    @Override
    public void onError() {
        ToastUtil.showErrorToast(this,"读卡失败，请再试一次!");
        disconnect();
        resetData();
    }
    @Override
    public void onLoading() {
        btnReadCard.setClickable(false);
        btnReadCard.setText("正在读卡...");
    }

    private void resetData(){
        btnReadCard.setClickable(true);
        btnReadCard.setText("开始读卡");
        txtName.setText("");
        txtSex.setText("");
        txtNation.setText("");
        txtBirthday.setText("");
        txtCertificatesCode.setText("");
        txtAddress.setText("");
        imgCertificates.setImageResource(R.drawable.ic_launcher);
    }
}

