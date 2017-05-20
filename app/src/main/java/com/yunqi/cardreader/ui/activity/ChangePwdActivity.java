package com.yunqi.cardreader.ui.activity;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jakewharton.rxbinding.view.RxView;
import com.yunqi.cardreader.R;
import com.yunqi.cardreader.base.NetActivity;
import com.yunqi.cardreader.model.request.ChangePwdRequest;
import com.yunqi.cardreader.presenter.ChangePwdPresenter;
import com.yunqi.cardreader.presenter.contract.ChangePwdContract;
import com.yunqi.cardreader.util.ToastUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;


public class ChangePwdActivity extends NetActivity<ChangePwdPresenter> implements ChangePwdContract.View {


    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.img_del_uname)
    ImageView imgDelUname;
    @BindView(R.id.edit_account)
    EditText editAccount;
    @BindView(R.id.img_view_old_pwd)
    ImageView imgViewOldPwd;
    @BindView(R.id.edit_old_password)
    EditText editOldPassword;
    @BindView(R.id.img_view)
    ImageView imgView;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.img_view_confirm_pdw)
    ImageView imgViewConfirmPdw;
    @BindView(R.id.edit_confirm_password)
    EditText editConfirmPassword;

    private boolean isViewOldPwd=false;
    private boolean isViewNewPwd=false;
    private boolean isViewConfirmPwd=false;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_change_pwd;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(toolBar, getString(R.string.module_change_pwd));
        setWidgetListener();
    }

    @OnClick(R.id.btn_change_pwd)
    public void changePwd() {
        String account=editAccount.getText().toString();
        if(TextUtils.isEmpty(account)){
            ToastUtil.showNoticeToast(this,getString(R.string.warming_empty_account));
            return;
        }
        String oldPwd=editOldPassword.getText().toString();
        if(TextUtils.isEmpty(oldPwd)){
            ToastUtil.showNoticeToast(this,getString(R.string.warming_empty_old_pwd));
            return;
        }
        String newPdw=editPassword.getText().toString();
        if(TextUtils.isEmpty(newPdw)){
            ToastUtil.showNoticeToast(this,getString(R.string.warming_empty_new_pwd));
            return;
        }
        String confirmPwd=editConfirmPassword.getText().toString();
        if(newPdw.equals(oldPwd)){
            ToastUtil.showNoticeToast(this,getString(R.string.warming_old_same_with_new));
            return;
        }
        if(!newPdw.equals(confirmPwd)){
            ToastUtil.showNoticeToast(this,getString(R.string.warming_twice_no_same));
            return;
        }
        ChangePwdRequest request=new ChangePwdRequest();
        request.account=account;
        request.old_password=oldPwd;
        request.new_password=newPdw;
        mPresenter.changePwd(request);
    }
    @OnClick(R.id.layout_change_pwd_bg)
    public void onBgClick() {
        hideSoftInput();
    }

    @Override
    public void onSuccess() {
        ToastUtil.showHookToast(this,getString(R.string.password_successfully_modified));
    }
    private void setWidgetListener(){
        RxView.clicks(imgDelUname)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        editAccount.setText("");
                    }
                });
        RxView.clicks(imgView)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if(isViewNewPwd){
                            editPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        }
                        else {
                            editPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        }
                        isViewNewPwd=!isViewNewPwd;

                    }
                });
        RxView.clicks(imgViewOldPwd)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if(isViewOldPwd){
                            editOldPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        }
                        else {
                            editOldPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        }
                        isViewOldPwd=!isViewOldPwd;

                    }
                });
        RxView.clicks(imgViewConfirmPdw)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if(isViewConfirmPwd){
                            editConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        }
                        else {
                            editConfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        }
                        isViewConfirmPwd=!isViewConfirmPwd;

                    }
                });
        //添加输入监听
        editAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    imgDelUname.setVisibility(View.INVISIBLE);
                } else {
                    imgDelUname.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void hideSoftInput() {
        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

}

