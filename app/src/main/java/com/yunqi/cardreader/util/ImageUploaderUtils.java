package com.yunqi.cardreader.util;

import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @Author: Huangweicai
 * @date 2017-02-26 15:14
 */

public class ImageUploaderUtils {

    public static Map<String, RequestBody> getRequestBody(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        Map<String, RequestBody> params = new HashMap<>();
        File file = new File(filePath);
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
        params.put("file\"; filename=\""+file.getName()+"\"", fileBody);
        return params;
    }

    public static Map<String, RequestBody> getRequestBody(ArrayList<String> fileList) {
        if (fileList == null || fileList.size() == 0) {
            return null;
        }
        Map<String, RequestBody> params = new HashMap<>();
        for (String bean : fileList) {
            File file = new File(bean);
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
            params.put("image[]\"; filename=\""+file.getName()+"\"", fileBody);
        }
        return params;
    }

}
