package com.yunqi.cardreader.ui.activity;

import android.bluetooth.BluetoothAdapter;
import android.graphics.Bitmap;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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
import com.yunqi.cardreader.base.NetActivity;
import com.yunqi.cardreader.model.request.ClientInfoAddRequest;
import com.yunqi.cardreader.presenter.RegisterPresenter;
import com.yunqi.cardreader.presenter.contract.RegisterContract;
import com.yunqi.cardreader.util.FileUtil;
import com.yunqi.cardreader.util.TimeUtil;
import com.yunqi.cardreader.util.ToastUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * 入住登记
 */
public class RegisterActivity extends NetActivity<RegisterPresenter> implements RegisterContract.View {


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
    @BindView(R.id.btn_confirm_upload)
    Button btnConfirmUpload;
    private BlueTool ble;
    private String selectImg;
    private ClientInfoAddRequest request;

    private String time;

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
                if (request == null) {
                    ToastUtil.showNoticeToast(RegisterActivity.this,getString(R.string.warming_no_info));
                    return;
                }
                if (TextUtils.isEmpty(request.roomNo)) {
                    ToastUtil.showNoticeToast(RegisterActivity.this,getString(R.string.warming_no_room));
                    return;
                }
                if (TextUtils.isEmpty(request.bedNo)) {
                    ToastUtil.showNoticeToast(RegisterActivity.this,getString(R.string.warming_no_bed));
                    return;
                }
                if (TextUtils.isEmpty(request.time)) {
                    ToastUtil.showNoticeToast(RegisterActivity.this,getString(R.string.warming_no_time));
                    return;
                }
                mPresenter.submitInfo(request);
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
        RxView.clicks(btnConfirmUpload)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        mPresenter.readCarder(ble);
                    }
                });
        RxView.clicks(imgPersonal)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        open3rdCamera();
                    }
                });

    }

    @Override
    public void showContent(Info info) {
        if (!TextUtils.isEmpty(info.getName())) {
            txtName.setText(info.getName());
        }
        if (!TextUtils.isEmpty(info.getSex())) {
            String sex = info.getIdNo().substring(16, 17);
            if (Integer.parseInt(sex) % 2 == 0) {
                sex = "0";
                txtSex.setText("女");
            } else {
                txtSex.setText("男");
                sex = "1";
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
            txtCertificatesCode.setText(info.getIdNo());
        }
        if (!TextUtils.isEmpty(info.getIdNo())) {
            txtAddress.setText(info.getAddress());
        }
        txtCheckTime.setText(TimeUtil.format(TimeUtil.getSysTime(), "yyyy-mm-dd hh:mm"));
        if (info.getBmp() != null) {
            imgCertificates.setImageBitmap(info.getBmp());
        }
        request = new ClientInfoAddRequest();
        request.name = info.getName();
        request.sex = info.getSex();
        request.nationlity = info.getNationlity();
        request.birthdate = info.getBirthdate();
        request.idNo = info.getIdNo();
        request.address = info.getAddress();
        disconnect();

    }

    @Override
    public void onSuccess() {

    }

    public String saveBitmap(Bitmap photo, String spath) {
        String path = "";
        try {
            path = FileUtil.getSDPath(this) + File.separator + "IDCardReader" + File.separator + "IDCR" + System.currentTimeMillis() + ".png";
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(spath, false));
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

    @Override
    public void showError(String msg, int requestCode) {
        ToastUtil.showErrorToast(this, "读卡失败，请再试一次!");
        disconnect();
        resetData();
    }

    @Override
    public void cancelLoading(int requestCode) {
        resetData();
    }

    @Override
    public void showLoading(int requestCode) {
        btnConfirmUpload.setClickable(false);
        btnConfirmUpload.setText("正在读卡...");
    }

    private void resetData() {
        btnConfirmUpload.setClickable(true);
        btnConfirmUpload.setText("开始读卡");
        txtName.setText("");
        txtSex.setText("");
        txtNation.setText("");
        txtBirthday.setText("");
        txtCertificatesCode.setText("");
        txtAddress.setText("");
        imgCertificates.setImageResource(R.drawable.ic_launcher);
    }

    @Override
    public boolean checkNetwork() {
        boolean isNetworkAvailable = super.checkNetwork();
        if (!isNetworkAvailable) {
            mPresenter.saveLocal(request);
        }
        return isNetworkAvailable;
    }
}

