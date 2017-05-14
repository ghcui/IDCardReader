package com.yunqi.cardreader.component;


import android.app.Application;

import com.yunqi.cardreader.BuildConfig;

/**
 * 基本应用程序类，用于程序崩溃异常捕获等
 * @ClassName:BaseApplication
 * @Description: 用于程序崩溃异常捕获等
 * @author: LiBingWu6005000224
 * @date: 2012-4-24
 *
 */
public class CrashHandlerApplication extends Application implements CrashHandler.ICrashHandler
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        if(BuildConfig.DEBUG){
            // 获取异常信息捕获类实例
            CrashHandler crashHandler = CrashHandler.getInstance(getApplicationContext());

            crashHandler.setICrashHandlerListener(this);
            // 初始化
            crashHandler.init(getApplicationContext());
        }
    }

    @Override
    public void onTerminate()
    {

        super.onTerminate();
    }

    /**
     *
     * 全局未处理异常的处理
     * <p>
     * Description: 全局未处理异常的处理，界面层可以根据需要定制自己的业务，例如上传异常日志等。
     * <p>
     * @date 2014年3月5日
     * @param thread 线程信息
     * @param ex 异常等信息
     * @return 返回true代表事件被消耗掉了，底层不再处理。
     */
    public boolean onGlobalUncaughtExceptionOccured(Thread thread, Throwable ex)
    {
        return false;
    }

    @Override
    public boolean onUncaughtExceptionOccured(Thread thread, Throwable ex)
    {
        return onGlobalUncaughtExceptionOccured(thread, ex);
    }
}
