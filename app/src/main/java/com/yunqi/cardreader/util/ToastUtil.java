package com.yunqi.cardreader.util;


import android.content.Context;
import com.didikee.uitoast.UIToast;

/**
 * @author ghcui
 * @time 2017/1/18
 */
public class ToastUtil {

    /**
     * 错误弹窗
     *
     * @param context
     * @param msg
     */
    public static void showErrorToast(Context context, String msg) {
        UIToast.showStyleToast(context,msg);
    }

    /**
     * 提示弹窗
     *
     * @param context
     * @param msg
     */
    public static void showNoticeToast(Context context, String msg) {
        UIToast.showStyleToast(context,msg);
    }

    /**
     * 正确弹窗
     *
     * @param context
     * @param msg
     */
    public static void showHookToast(Context context, String msg) {
        UIToast.showStyleToast(context,msg);
    }
}
