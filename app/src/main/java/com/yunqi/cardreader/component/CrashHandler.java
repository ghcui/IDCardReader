package com.yunqi.cardreader.component;


import android.content.Context;
import android.os.Build;
import android.os.Debug;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;


/** 
 * 异常捕获监听实现类
 * @ClassName:CrashHandler 
 * @Description: 异常捕获监听实现类
 * @date: 2012-2-27
 *  
 */
public class CrashHandler implements UncaughtExceptionHandler
{

    /** 异常日志文件路径 */
    private static String m_strErrorFilePath = null;

    /** 时间格式 */
    private final static String DATE_FORMATE = "yyyy-MM-dd_HHmm";

    /** 异常捕获监听 */
    private UncaughtExceptionHandler m_handlerDefault;

    /***/
    private ICrashHandler minstanceCrashHandler = null;

    /** CrashHandler实例 */
    private static CrashHandler m_instance = null;

    /** 异常文件目录对象 */
    private File m_fileDir;

    /** 异常文件对象 */
    private File m_fileRecord;
    private Context mctx;

    private CrashHandler(Context ctx)
    {
        m_strErrorFilePath=getApplicationExceptionFilePath(ctx);
    }

    /**
     * 
     * 获取CrashHandler实例
     * @date 2012-2-27 
     * @return CrashHandler实例
     */
    public static CrashHandler getInstance(Context ctx)
    {
        if (null == m_instance)
        {
            m_instance = new CrashHandler(ctx);
        }
        return m_instance;
    }
    /**
     *
     * 获取应用异常时日志存放路径
     * @date 2012-2-27
     * @return CrashHandler实例
     */
    private String getApplicationExceptionFilePath(Context ctx){
        String strFilePath = null;
        
        String pkgName=ctx.getPackageName();
        // 初始化默认路径
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            strFilePath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/exception/"+pkgName+"/";
        }
        // 如果没有SD卡，返回内存路径
        else
        {
            strFilePath = mctx.getFilesDir().getAbsolutePath() + "/";
        }
        // 检查文件夹存不存在，如果不存在则创建
        File file = new File(strFilePath);
        if (!file.exists())
        {
            // 创建目录
            file.mkdirs();
        }

        return strFilePath;
    }

    /**
     * 
     * 这里写方法名
     * <p>
     * Description: 这里用一句话描述这个方法的作用
     * <p>
     * @date 2014年3月5日 
     * @author Administrator
     * @param instance
     */
    public void setICrashHandlerListener(ICrashHandler instance)
    {
        minstanceCrashHandler = instance;
    }

    /**
     * 
     * 初始化方法
     * @date 2012-2-27 
     * @param context Context句柄
     */
    public void init(Context context)
    {
        if (null != context)
        {
            m_handlerDefault = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(this);

            if (!TextUtils.isEmpty(m_strErrorFilePath))
            {
                // 创建异常文件目录
                m_fileDir = new File(m_strErrorFilePath);
                m_fileDir.mkdirs();
            }
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex)
    {
        if (null == ex)
        {
            Log.w("uncaughtException", "null == ex");
            return;
        }

        if (null != minstanceCrashHandler)
        {
            minstanceCrashHandler.onUncaughtExceptionOccured(thread, ex);
        }

        // 若异常未处理或为空
        if (!handleException(ex) && null != m_handlerDefault)
        {
            m_handlerDefault.uncaughtException(thread, ex);
        }
        else
        {
            try
            {
                // 线程等待3秒
                Thread.sleep(3000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            // 退出应用程序
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    /**
     * 
     * 异常处理方法
     * @date 2012-2-27 
     * @param ex Throwable异常
     * @return 是否进行了处理
     */
    private boolean handleException(Throwable ex)
    {
        if (null == ex)
        {
            return false;
        }

        // Log打印异常信息
        ex.printStackTrace();
        // 输出流，用于异常信息文件写入
        DataOutputStream dos = null;

        try
        {
            // 获取时间Data对象实例
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMATE);
            // 获取日期格式，用于文件名
            String strFormatDate = simpleDateFormat.format(date);
            if (!TextUtils.isEmpty(m_strErrorFilePath))
            {
                // 创建文件实例
                m_fileRecord = new File(m_strErrorFilePath.concat(strFormatDate).concat(
                    ".txt"));
                // 创建异常信息存储文件
                if (m_fileRecord.createNewFile())
                {
                    dos = new DataOutputStream(new FileOutputStream(m_fileRecord));
                    //系统信息
                    printSysInfo(dos);
                    //异常信息
                    ex.printStackTrace(new PrintStream(dos));
                }
            }
        }
        catch (FileNotFoundException exFile)
        {
            exFile.printStackTrace();
        }
        catch (IOException exIO)
        {
            exIO.printStackTrace();
        }
        finally
        {
            // 关闭输出流
            if (null != dos)
            {
                try
                {
                    dos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        // 线程Toast提示异常信息
        //        new Thread()
        //        {
        //
        //            @Override
        //            public void run()
        //            {
        //                Looper.prepare();
        //                
        //                if (null != m_ctxHandler)
        //                {
        //                    Toast.makeText(
        //                        m_ctxHandler,
        //                        "Sorry!An unexpected exception occured,the application will exit now!",
        //                        Toast.LENGTH_LONG).show();
        //                }             
        //                Looper.loop();
        //            }
        //        }.start();

        return true;
    }

    /**
     * 
     * 打印系统信息
     * @date 2013-8-14  
     * @param dos 输出流
     */
    private void printSysInfo(DataOutputStream dos)
    {
        try
        {
            //os信息（sdk版本、android版本等）
            dos.writeBytes("\n" + "=========================");
            dos.writeBytes("\n" + "Model: " + Build.MODEL);
            dos.writeBytes("\n" + "Version SDK Level: " + Build.VERSION.SDK);
            dos.writeBytes("\n" + "Version Release: " + Build.VERSION.RELEASE);
            dos.writeBytes("\n" + "=========================");
            dos.writeBytes("\n" + "NativeHeapSize: " + Debug.getNativeHeapSize());
            dos.writeBytes("\n" + "NativeHeapAllocatedSize: "
                + Debug.getNativeHeapAllocatedSize());
            dos.writeBytes("\n" + "NativeHeapFreeSize: " + Debug.getNativeHeapFreeSize());
            dos.writeBytes("\n" + "=========================");

            //内存信息（总内存、可用内存）
            String strMemInfoFile = "/proc/meminfo";
            String strMemInfoItem = "";
            try
            {
                FileReader fr = new FileReader(strMemInfoFile);
                BufferedReader localBufferedReader = new BufferedReader(fr, 1024 * 2);
                while ((strMemInfoItem = localBufferedReader.readLine()) != null)
                {
                    dos.writeBytes("\n" + strMemInfoItem);
                }
                localBufferedReader.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            //cpu信息内容（cpu频率、cpu核数、cpu占用率等）
            dos.writeBytes("\n" + "=========================");
            String strCpuInfoFile = "/proc/cpuinfo";
            String strCpuInfoItem = "";

            try
            {
                FileReader fr = new FileReader(strCpuInfoFile);
                BufferedReader localBufferedReader = new BufferedReader(fr, 1024 * 2);
                while ((strCpuInfoItem = localBufferedReader.readLine()) != null)
                {
                    dos.writeBytes("\n" + strCpuInfoItem);
                }

                localBufferedReader.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            //            dos.writeBytes("\n" + "=========================" );
            //            String strStatFile = "/proc/stat";
            //            String strStatInfoItem = "";
            //
            //            try
            //            {
            //                FileReader fr = new FileReader(strStatFile);
            //                BufferedReader localBufferedReader = new BufferedReader(fr, 1024*2);
            //                while ((strStatInfoItem = localBufferedReader.readLine()) != null)
            //                {
            //                    dos.writeBytes("\n" + strStatInfoItem );
            //                }          
            //                
            //                localBufferedReader.close();
            //            }
            //            catch (IOException e)
            //            {
            //                e.printStackTrace();
            //            }

            dos.writeBytes("\n" + "=========================" + "\n");
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 
     * 某某某类
     * @ClassName:ICrashHandler 
     * @Description: 这里用一句话描述这个类的作用 
     * @date: 2014年3月5日
     *
     */
    public interface ICrashHandler
    {
        /**
         * 
         * 这里写方法名
         * <p>
         * Description: 这里用一句话描述这个方法的作用
         * <p>
         * @date 2014年3月5日 
         * @param thread
         * @param ex
         * @return
         */
        public boolean onUncaughtExceptionOccured(Thread thread, Throwable ex);
    }
}
