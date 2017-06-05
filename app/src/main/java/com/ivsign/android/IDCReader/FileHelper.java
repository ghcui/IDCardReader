package com.ivsign.android.IDCReader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/6/5.
 */

public class FileHelper {
    public FileHelper() {
    }

    public static void writeFile(File file, byte[] data, int offset, int count) throws IOException {
        FileOutputStream fos = new FileOutputStream(file, true);
        fos.write(data, offset, count);
        fos.flush();
        fos.close();
    }

    public static byte[] readFile(String FILEPATH, String filename) throws IOException {
        File file = new File(FILEPATH, filename);
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        int leng = bis.available();
        byte[] b = new byte[leng];
        bis.read(b, 0, leng);
        bis.close();
        return b;
    }
}
