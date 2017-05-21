package com.yunqi.cardreader.constants;

import com.yunqi.cardreader.app.App;

import java.io.File;

/**
 * @author ghcui
 * @time 2017/1/12
 */
public class Constants {
    /**
     * debug
     */
    public static final boolean isDebug = false;

    //======================= Path=======================
    public static final String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    public static final String LOGIN_NAME = "LoginName";
    public static final String PASSWORD = "Password";
    public static final String LOGIN_STATUS = "Login_Status";
    public static final String AUTO_LOGO = "Auto_Login";

    public static final int STATUS_UNLOGIN = 0;

    public static final int STATUS_LOGINED = 1;


}
