package com.yunqi.cardreader.util;

import android.text.TextUtils;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ghcui
 * @time 2017/1/18
 */
public class StringUtil {

    public static boolean isMobileNumber(String mobiles) {

        /**
         * 手机号码:
         * 13[0-9], 14[5,7], 15[0, 1, 2, 3, 5, 6, 7, 8, 9], 17[0, 1, 6, 7, 8], 18[0-9]
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         */
        String MOBILE = "^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$";
        /**
         * 中国移动：China Mobile
         * 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         */
        String CM = "^1(3[4-9]|4[7]|5[0-27-9]|7[08]|8[2-478])\\d{8}$";
        /**
         * 中国联通：China Unicom
         * 130,131,132,145,155,156,170,171,175,176,185,186
         */
        String CU = "^1(3[0-2]|4[5]|5[56]|7[0156]|8[56])\\d{8}$";
        /**
         * 中国电信：China Telecom
         * 133,149,153,170,173,177,180,181,189
         */
        String CT = "^1(3[3]|4[9]|53|7[037]|8[019])\\d{8}$";
        return Pattern.compile(MOBILE).matcher(mobiles).matches()
                ||  Pattern.compile(CM).matcher(mobiles).matches()
                || Pattern.compile(CU).matcher(mobiles).matches()
                || Pattern.compile(CT).matcher(mobiles).matches();

    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 校验etList是否有值
     * @param etList
     * @return
     */
    public static boolean isViewEmpty(TextView... etList) {
        if (etList == null || etList.length == 0) {
            return true;
        }
        for(int i=0;i<etList.length;i++) {
            TextView et = etList[i];
            if (et == null || TextUtils.isEmpty(et.getText().toString())) {
                return true;
            }
        }
        return false;
    }

}
