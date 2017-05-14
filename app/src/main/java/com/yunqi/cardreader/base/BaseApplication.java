package com.yunqi.cardreader.base;


import android.app.Activity;

import com.yunqi.cardreader.component.CrashHandlerApplication;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;


/**
 * Application基类
 *
 * @author ghcui
 * @version [版本号, 2017-1-11]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class BaseApplication extends CrashHandlerApplication {

    private List<Activity> allActivities;
    @Override
    public void onCreate() {
        super.onCreate();
    }



    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new ArrayList<>  ();
        }
        allActivities.add(act);
    }

    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    public void killAllActivities() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
    }

}
