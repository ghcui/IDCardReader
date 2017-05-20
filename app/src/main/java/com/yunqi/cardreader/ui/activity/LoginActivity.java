package com.yunqi.cardreader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.yunqi.cardreader.R;
import com.yunqi.cardreader.base.NetActivity;
import com.yunqi.cardreader.model.bean.User;
import com.jakewharton.rxbinding.view.RxView;
import com.yunqi.cardreader.constants.Constants;
import com.yunqi.cardreader.presenter.LoginPresenter;
import com.yunqi.cardreader.presenter.contract.LoginContract;
import com.yunqi.cardreader.util.PrefrenceUtils;
import com.yunqi.cardreader.util.ToastUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

public class LoginActivity extends NetActivity<LoginPresenter> implements LoginContract.View {
    @BindView(R.id.edit_account)
    EditText editAccount;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.img_del_uname)
    ImageView imgDelUname;
    @BindView(R.id.img_view)
    ImageView imgView;
    @BindView(R.id.layout_login_bg)
    ViewGroup layoutLoginBg;


    private String loginname;
    private String password;
    private boolean isViewPwd=false;



    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initEventAndData() {
        loginname = PrefrenceUtils.getInstance(this).getLoginName();
        password = PrefrenceUtils.getInstance(this).getPassword();
        if(!TextUtils.isEmpty(loginname)&&!TextUtils.isEmpty(password)){
            imgDelUname.setVisibility(View.VISIBLE);
            editAccount.setText(loginname);
            editAccount.setSelection(loginname.length());
            editPassword.setText(password);
            editPassword.setSelection(password.length());
        }
        setWidgetListener();
    }

    private void setWidgetListener(){
        RxView.clicks(btnLogin)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        loginname = editAccount.getText().toString();
                        password = editPassword.getText().toString();
                        if (TextUtils.isEmpty(loginname) || TextUtils.isEmpty(password)) {
                            ToastUtil.showNoticeToast(LoginActivity.this, "用户名或密码为空!");
                            return;
                        }
                        mPresenter.doLogin(loginname, password);
                    }
                });
        RxView.clicks(imgDelUname)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        editAccount.setText("");
                        editPassword.setText("");
                    }
                });
        RxView.clicks(imgView)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if(isViewPwd){
                            editPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        }
                        else {
                            editPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        }
                        isViewPwd=!isViewPwd;
                        //设置光标位置
                        editPassword.setSelection(editPassword.getText().length());
                    }
                });
        RxView.clicks(layoutLoginBg)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        hideSoftInput();
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

    @OnClick(R.id.checkbox)
    public void onAutoLogin() {

    }
    @OnClick(R.id.txt_forgot_pwd)
    public void onForgotPwd() {
        Intent intent=new Intent(this,ChangePwdActivity.class);
        startActivity(intent);
    }

    private void hideSoftInput() {
        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }


    @Override
    public void jump2Main(User user) {
        PrefrenceUtils.getInstance(this).saveLoginName(loginname);
        PrefrenceUtils.getInstance(this).savePassword(password);
        PrefrenceUtils.getInstance(this).saveLoginStatus(Constants.STATUS_LOGINED);
        Intent intent = new Intent();
        intent.setClass(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

