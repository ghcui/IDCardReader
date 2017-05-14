package com.yunqi.cardreader.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.yunqi.cardreader.constants.Constants;


public class PrefrenceUtils {

    private static SharedPreferences mSharedPreferences;
    private static PrefrenceUtils mPreferenceUtils;

    public static final String PREFERENCE_NAME = "appSaveInfo";

    private PrefrenceUtils(Context cxt) {
        mSharedPreferences = cxt.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
    }

    public static synchronized void init(Context cxt) {
        if (mPreferenceUtils == null) {
            mPreferenceUtils = new PrefrenceUtils(cxt);
        }
    }

    public static PrefrenceUtils getInstance(Context cxt) {
        if (mPreferenceUtils == null) {
            return new PrefrenceUtils(cxt);
        }
        return mPreferenceUtils;
    }

    public void saveLoginName(String loginName) {
        mSharedPreferences.edit().putString(Constants.LOGIN_NAME, loginName).apply();
        mSharedPreferences.edit().commit();
    }
    public String getLoginName() {
        return mSharedPreferences.getString(Constants.LOGIN_NAME, "");
    }

    public void savePassword(String password) {
        mSharedPreferences.edit().putString(Constants.PASSWORD, password).apply();
        mSharedPreferences.edit().commit();
    }
    public String getPassword() {
        return mSharedPreferences.getString(Constants.PASSWORD, "");
    }

    public void saveLoginStatus(int status) {
        mSharedPreferences.edit().putInt(Constants.LOGIN_STATUS, status).apply();
        mSharedPreferences.edit().commit();
    }
    public int getLoginStatus() {
        return mSharedPreferences.getInt(Constants.LOGIN_STATUS, Constants.STATUS_UNLOGIN);
    }



}
