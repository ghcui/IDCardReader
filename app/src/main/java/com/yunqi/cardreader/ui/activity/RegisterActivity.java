package com.yunqi.cardreader.ui.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
import com.yunqi.cardreader.app.App;
import com.yunqi.cardreader.base.NetActivity;
import com.yunqi.cardreader.model.bean.ClientInfo;
import com.yunqi.cardreader.model.bean.Room;
import com.yunqi.cardreader.presenter.RegisterPresenter;
import com.yunqi.cardreader.presenter.contract.RegisterContract;
import com.yunqi.cardreader.ui.view.ListDialog;
import com.yunqi.cardreader.ui.view.SexSelectPopWindow;
import com.yunqi.cardreader.util.FileUtil;
import com.yunqi.cardreader.util.IdcardValidator;
import com.yunqi.cardreader.util.NationUtil;
import com.yunqi.cardreader.util.TimeUtil;
import com.yunqi.cardreader.util.ToastUtil;

import org.feezu.liuli.timeselector.TimeSelector;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * 入住登记
 */
public class RegisterActivity extends NetActivity<RegisterPresenter> implements RegisterContract.View {


    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.txt_room_no)
    TextView txtRoomNo;
    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_person_number)
    EditText editPersonNumber;
    @BindView(R.id.checkbox_male)
    ImageView checkMale;
    @BindView(R.id.checkbox_female)
    ImageView checkFemale;
    @BindView(R.id.txt_nation)
    TextView txtNation;
    @BindView(R.id.txt_birthday)
    TextView txtBirthday;
    @BindView(R.id.txt_certificates_type)
    TextView txtCertificatesType;
    @BindView(R.id.edit_certificates_code)
    EditText editCertificatesCode;
    @BindView(R.id.edit_address)
    EditText editAddress;
    @BindView(R.id.txt_check_time)
    TextView txtCheckTime;
    @BindView(R.id.edit_from)
    EditText editFrom;
    @BindView(R.id.img_certificates)
    ImageView imgCertificates;
    @BindView(R.id.img_personal)
    ImageView imgPersonal;
    @BindView(R.id.img_del)
    ImageView imgDel;
    @BindView(R.id.btn_confirm_upload)
    Button btnConfirmUpload;
    @BindView(R.id.img_certificates_default)
    ImageView imgCertificatesDefault;
    @BindView(R.id.img_personal_default)
    ImageView imgPersonalDefault;
    private BlueTool ble;
    private String selectImg;
    private ClientInfo request;
    private String time;
    private String cardUrl="";
    private String personUrl;
    private boolean isConnect = false;
    private Bitmap bitmapCard;
    private SexSelectPopWindow popWindow;
    private Room selectRoom;
    private String sex="男";
    private String IDCardType="";

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
        initData();
        setToolBar(toolBar, getString(R.string.module_register), "连接设备", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnect) {
                    disconnect();
                }
                mPresenter.connectBle(ble);
            }
        });
        setWidgetListener();
    }
    private void summitInfo() {
        request.uid = App.getInstance().getUserInfo().id + "";
        if (TextUtils.isEmpty(request.room_code)) {
            ToastUtil.showNoticeToast(RegisterActivity.this, getString(R.string.warming_no_room_code));
            return;
        }
        request.custom_name = editName.getText().toString();
        if (TextUtils.isEmpty(request.custom_name)) {
            ToastUtil.showNoticeToast(RegisterActivity.this, getString(R.string.warming_no_room_code));
            return;
        }
        try {
            request.retinue = Integer.parseInt(editPersonNumber.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            ToastUtil.showNoticeToast(RegisterActivity.this, getString(R.string.warming_no_person_num));
            return;
        }
        if (request.retinue < 0) {
            ToastUtil.showNoticeToast(RegisterActivity.this, getString(R.string.warming_no_person_num));
            return;
        }

        request.custom_sex = sex;
        if (TextUtils.isEmpty(request.custom_sex)) {
            ToastUtil.showNoticeToast(RegisterActivity.this, getString(R.string.warming_no_custom_sex));
            return;
        }
        request.custom_nation = txtNation.getText().toString();
        if (TextUtils.isEmpty(request.custom_nation)) {
            ToastUtil.showNoticeToast(RegisterActivity.this, getString(R.string.warming_no_custom_nation));
            return;
        }
        request.custom_id_card = editCertificatesCode.getText().toString();
        if (TextUtils.isEmpty(request.custom_id_card)) {
            ToastUtil.showNoticeToast(RegisterActivity.this, getString(R.string.warming_no_certificates_code));
            return;
        }
        if(IDCardType.equals(getString(R.string.txt_certificates_type))&&!IdcardValidator.isValidatedAllIdcard(request.custom_id_card)){
            ToastUtil.showNoticeToast(RegisterActivity.this, getString(R.string.warming_id_card_validated));
            return;
        }
        request.custom_birth_date = txtBirthday.getText().toString();
        request.custom_residence = editAddress.getText().toString();
        request.user_from = editFrom.getText().toString();
        request.sign_time = time;
        if(bitmapCard!=null){
            cardUrl=saveBitmap(bitmapCard);
        }
        request.card_photo_url = cardUrl;
        personUrl=selectImg;
        request.user_photo_url = personUrl;
        mPresenter.submitInfo(request);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disconnect();
    }

    private void initData() {
        request = new ClientInfo();
        ble = new BlueTool(RegisterActivity.this, BluetoothAdapter.getDefaultAdapter());
    }

    private void setWidgetListener() {
        RxView.clicks(btnConfirmUpload)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {

                        summitInfo();
                    }
                });
        RxView.clicks(imgDel)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        imgPersonal.setVisibility(View.INVISIBLE);
                        selectImg="";
                        imgDel.setVisibility(View.GONE);
                    }
                });

    }

    @OnClick(R.id.rlayout_personal)
    public void onPersonalClick() {
        open3rdCamera();
    }

    @OnClick(R.id.txt_room_no)
    public void onSelectRoom() {
        Intent intent = new Intent(this, RoomListActivity.class);
        intent.putExtra("operater", 1);
        startActivityForResult(intent, 1);
    }

    @Override
    public void showContent(Info info) {
        resetData();
        if (!TextUtils.isEmpty(info.getName())) {
            editName.setText(info.getName());
        }
        if (!TextUtils.isEmpty(info.getSex())) {
            String sex = info.getIdNo().substring(16, 17);
            if (Integer.parseInt(sex) % 2 == 0) {
                sex = "0";
                chooseFemale();
            } else {
                sex = "1";
                chooseMale();
            }
            info.setSex(sex);
        }
        if (!TextUtils.isEmpty(info.getName())) {
            txtNation.setText(info.getNationlity());
        }
        if (!TextUtils.isEmpty(info.getBirthdate())) {
            txtBirthday.setText(info.getBirthdate());
        }
        if (!TextUtils.isEmpty(info.getIdNo())) {
            editCertificatesCode.setText(info.getIdNo());
        }
        txtCertificatesType.setText(getText(R.string.txt_certificates_type));
        if (!TextUtils.isEmpty(info.getIdNo())) {
            editAddress.setText(info.getAddress());
        }
        if (info.getBmp() != null) {
            imgCertificates.setVisibility(View.VISIBLE);
            bitmapCard=info.getBmp();
            imgCertificates.setImageBitmap(info.getBmp());
        }
    }

    private void chooseMale(){
        sex="男";
        checkFemale.setImageResource(R.drawable.check_unselect);
        checkMale.setImageResource(R.drawable.check_selected);
    }
    private void chooseFemale(){
        sex="女";
        checkFemale.setImageResource(R.drawable.check_selected);
        checkMale.setImageResource(R.drawable.check_unselect);
    }

    @Override
    public void onSuccess() {
        ToastUtil.showHookToast(this, "上传成功!");
        finish();
    }

    @Override
    public void onConnect(boolean isConnect) {
        this.isConnect = isConnect;
        if (isConnect) {
            ToastUtil.showHookToast(this, "设备连接成功!");
        } else {
            ToastUtil.showHookToast(this, "设备连接失败!");
        }

    }

    public String saveBitmap(Bitmap photo) {
        String path = "";
        try {
            path = FileUtil.getSDPath(this) + File.separator + "IDCardReader" + File.separator + "idCard" + ".png";
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(path, false));
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }


    private void disconnect() {
        if (ble != null) {
            ble.disconnect();
        }
    }

    private void open3rdCamera() {
//        int selector = R.drawable.select_cb;
        FunctionConfig config = new FunctionConfig();
        config.setEnablePixelCompress(true);
        config.setEnableQualityCompress(true);
        config.setMaxSelectNum(1);
        config.setCheckNumMode(true);
        config.setCompressQuality(100);
        config.setImageSpanCount(3);
        config.setEnablePreview(true);
//        config.setSelectMedia(selectMedia);
//        config.setCheckedBoxDrawable(selector);
        // 先初始化参数配置，在启动相册
        PictureConfig.init(config);
        PictureConfig.getPictureConfig().openPhoto(this, resultCallback);
    }

    /**
     * 图片回调方法0
     */
    private PictureConfig.OnSelectResultCallback resultCallback = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            if (resultList != null && resultList.size() > 0) {
                selectImg = resultList.get(0).getPath();
                if (!TextUtils.isEmpty(selectImg)) {
                    imgPersonal.setVisibility(View.VISIBLE);
                    imgDel.setVisibility(View.VISIBLE);
                    Glide.with(mContext)
                            .load(selectImg)
                            .asBitmap().centerCrop()
                            .placeholder(R.color.color_f6)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imgPersonal);
                }
            }
        }
    };

    @OnClick(R.id.llayout_check_time)
    public void onSelectCheckTime() {
        TimeSelector timeSelector = new TimeSelector(this, new TimeSelector.ResultHandler() {
            @Override
            public void handle(String time) {
                RegisterActivity.this.time = time;
                txtCheckTime.setText(time);
            }
        }, TimeUtil.getCurrentTime("yyyy-MM-dd HH:mm"), TimeUtil.getNextYear("yyyy-MM-dd HH:mm"));
//        timeSelector.disScrollUnit(TimeSelector.SCROLLTYPE.HOUR, TimeSelector.SCROLLTYPE.MINUTE);
        timeSelector.show();
    }

    @Override
    public void showError(String msg, int requestCode) {
        if (requestCode == RegisterContract.REQUST_CODE_CONNECT_BLE) {
            ToastUtil.showHookToast(this, "设备连接失败!");
        } else if (requestCode == RegisterContract.REQUST_CODE_READCARD) {
            ToastUtil.showErrorToast(this, "读卡失败，请再试一次!");
            resetData();
        } else if (requestCode == RegisterContract.REQUST_CODE_SUBMIT) {
            ToastUtil.showErrorToast(this, "登记失败，请再试一次");
        }
    }

    private void resetData() {
        btnConfirmUpload.setClickable(true);
        editName.setText("");
        chooseMale();
        txtNation.setText("");
        txtBirthday.setText("");
        editCertificatesCode.setText("");
        txtCertificatesType.setText(getText(R.string.txt_certificates_type));
        editAddress.setText("");
        imgCertificates.setVisibility(View.GONE);
        cardUrl="";
    }

    @OnClick(R.id.img_readcard)
    public void onReadcard() {
        if (isConnect) {
            mPresenter.readCarder(ble);
        } else {
            ToastUtil.showNoticeToast(this, "设备未连接，请先连接设备！");
        }
    }
    @OnClick(R.id.txt_certificates_type)
    public void onSelectIDCardType() {
        String[] arrayStr=getResources().getStringArray(R.array.idcard_type_array);
        ListDialog dialog=new ListDialog(this, "请选择证件类型", arrayStr, R.layout.dialog_list, new ListDialog.OnSelectedListener() {
            @Override
            public void onItemSelected(int position, String str) {
                IDCardType=str;
                txtCertificatesType.setText(str);
            }
        });
        dialog.show();
    }
    @OnClick(R.id.txt_nation)
    public void onSelectNation() {
        String[] arrayStr= NationUtil.getAllNations();
        ListDialog dialog=new ListDialog(this, "请选择民族", arrayStr, R.layout.dialog_list_nation,new ListDialog.OnSelectedListener() {
            @Override
            public void onItemSelected(int position, String str) {
                txtNation.setText(str);
            }
        });
        dialog.show();
    }
    @OnClick(R.id.txt_birthday)
    public void onSelectBirthday() {
        TimeSelector timeSelector = new TimeSelector(this, new TimeSelector.ResultHandler() {
            @Override
            public void handle(String time) {
               String formatTime= TimeUtil.converTime("yyyy-MM-dd HH:mm","yyyy-MM-dd",time);
                txtBirthday.setText(formatTime);
            }
        },"1900-01-01 00:00" , TimeUtil.getCurrentTime("yyyy-MM-dd HH:mm"));
//        timeSelector.disScrollUnit(TimeSelector.SCROLLTYPE.HOUR, TimeSelector.SCROLLTYPE.MINUTE);
        timeSelector.setMode(TimeSelector.MODE.YMD);
        timeSelector.show();
    }
    @OnClick(R.id.checkbox_male)
    public void onSelectMale() {
       chooseMale();
    }
    @OnClick(R.id.checkbox_female)
    public void onSelectFemale() {
        chooseFemale();
    }


    @Override
    public void showLoading(int requestCode) {
        if (requestCode == RegisterContract.REQUST_CODE_READCARD) {
            super.showLoading("正在读卡...");
        } else if (requestCode == RegisterContract.REQUST_CODE_SUBMIT) {
            super.showLoading("正在上传...");
        } else if (requestCode == RegisterContract.REQUST_CODE_CONNECT_BLE) {
            super.showLoading("正在连接...");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            selectRoom = (Room) data.getSerializableExtra("Room");
            request.room_code = selectRoom.room_code;
            txtRoomNo.setText(selectRoom.room_code);
        }
    }
}

