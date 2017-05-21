package com.yunqi.cardreader.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Parcelable;

public class FileUtil {

    // 获取sdcard的目录
    public static String getSDPath(Context context) {
        // 判断sdcard是否存在
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // 获取根目录
            File sdDir = Environment.getExternalStorageDirectory();
            return sdDir.getPath();
        }
        return "/data/data/" + context.getPackageName();
    }

    public static String createNewFile(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return path;
    }

    /**
     * 复制asset文件到指定目录
     *
     * @param oldPath asset下的路径
     * @param newPath SD卡下保存路径
     */
    public static void copyAssets(Context context, String oldPath, String newPath) throws Exception {
        String fileNames[] = context.getAssets().list(oldPath);// 获取assets目录下的所有文件及目录名
        if (fileNames.length > 0) {// 如果是目录
            File file = new File(newPath);
            file.mkdirs();// 如果文件夹不存在，则递归
            for (String fileName : fileNames) {
                copyAssets(context, oldPath + "/" + fileName, newPath + "/" + fileName);
            }
        } else {// 如果是文件
            InputStream is = context.getAssets().open(oldPath);
            FileOutputStream fos = new FileOutputStream(new File(newPath));
            byte[] buffer = new byte[1024];
            int byteCount = 0;
            while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
                // buffer字节
                fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
            }
            fos.flush();// 刷新缓冲区
            is.close();
            fos.close();
        }
    }

    // 复制文件
    public static void copyFile(InputStream inputStream, File targetFile)
            throws IOException {
        BufferedOutputStream outBuff = null;
        try {

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inputStream.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inputStream != null)
                inputStream.close();
            if (outBuff != null)
                outBuff.close();
        }
    }

    /**
     * 文件是否已存在
     *
     * @param file
     * @return
     */
    public static boolean isFileExit(File file) {
        if (file.exists()) {
            return true;
        }
        return false;
    }

    /**
     * 判断指定目录是否有文件存在
     *
     * @param path
     * @param fileName
     * @return
     */
    public static File getFiles(String path, String fileName) {
        File f = new File(path);
        File[] files = f.listFiles();
        if (files == null) {
            return null;
        }

        if (null != fileName && !"".equals(fileName)) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (fileName.equals(file.getName())) {
                    return file;
                }
            }
        }
        return null;
    }

    /**
     * 根据文件路径获取文件名
     *
     * @return
     */
    public static String getFileName(String path) {
        if (path != null && !"".equals(path.trim())) {
            return path.substring(path.lastIndexOf("/"));
        }

        return "";
    }

    // 从asset中读取文件
    public static String getFromAssets(Context context, String fileName) {
        String result = "";
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";

            while ((line = bufReader.readLine()) != null)
                result += line;
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String FileInputStreamDemo(String path) {
        try {
            File file = new File(path);
            if (!file.exists() || file.isDirectory())
                ;
            FileInputStream fis = new FileInputStream(file);
            byte[] buf = new byte[1024];
            StringBuffer sb = new StringBuffer();
            while ((fis.read(buf)) != -1) {
                sb.append(new String(buf));
                buf = new byte[1024];// 重新生成，避免和上次读取的数据重复
            }
            return sb.toString();

        } catch (Exception e) {
        }
        return "";
    }


    /**
     * 删除目录（文件夹）下的文件
     *
     * @param path 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static void deleteDirectory(String path) {
        File dirFile = new File(path);
        File[] files = dirFile.listFiles();
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                // 删除子文件
                if (files[i].isFile()) {
                    files[i].delete();
                }
                // 删除子目录
                else {
                    deleteDirectory(files[i].getAbsolutePath());
                }
            }
        }
    }


    // 保存序列化的对象到app目录
    public static void saveSeriObj(Context context, String fileName, Object o)
            throws Exception {

        String path = context.getFilesDir() + "/";

        File dir = new File(path);
        dir.mkdirs();

        File f = new File(dir, fileName);

        if (f.exists()) {
            f.delete();
        }
        FileOutputStream os = new FileOutputStream(f);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(os);
        objectOutputStream.writeObject(o);
        objectOutputStream.close();
        os.close();
    }

    // 读取序列化的对象
    public static Object readSeriObject(Context context, String fileName)
            throws Exception {
        String path = context.getFilesDir() + "/";

        File dir = new File(path);
        dir.mkdirs();
        File file = new File(dir, fileName);
        InputStream is = new FileInputStream(file);

        ObjectInputStream objectInputStream = new ObjectInputStream(is);

        Object o = objectInputStream.readObject();

        return o;

    }

    /**
     * 保存camera capture到file
     *
     * @return 是否成功
     */
    public static boolean savePhoto(Parcelable data, String path) {
        boolean rs = false;
        //        Bitmap photo = new Intent().getExtras().getParcelable("data");
        if (null == data) {
            return rs;
        }

        try {
            Bitmap photo = (Bitmap) data;
            File file = new File(path);
            file.createNewFile();

            FileOutputStream out = new FileOutputStream(file);
            //            photo.compress(Bitmap.CompressFormat.PNG, 100, out);
            //            ByteArrayOutputStream out = new ByteArrayOutputStream();

            //TODO 调用BimmapUtil压缩
            rs = photo.compress(Bitmap.CompressFormat.JPEG, 100, out);//PNG

            out.flush();
            out.close();
            rs &= true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            rs = false;
        } catch (IOException e) {
            e.printStackTrace();
            rs = false;
        }
        return rs;
    }

}
