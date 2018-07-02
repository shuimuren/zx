package com.zhixing.work.zhixin.app;

import android.app.Activity;
import android.content.Context;


import com.zhixing.work.zhixin.base.BaseApplication;
import com.zhixing.work.zhixin.util.AlertUtils;

import java.util.ArrayList;
import java.util.List;



public class ZxApplication extends BaseApplication {
    public static Context applicationContext;
    private static ZxApplication instance;

    // login user name
    public final String PREF_USERNAME = "username";
    /**
     * nickname for current User, the nickname instead of ID be shown when User receive notification from APNs
     */
    public static String currentUserNick = "";

    public List<Activity> aliveActivities = new ArrayList<>();
//    private OSSClient mOSSClient;

    public static Context getAppContext(){
        return applicationContext;
    }
    @Override
    public void onCreate() {

        super.onCreate();
        applicationContext = getAppContext();
        instance = this;
        //init demo helper
        AlertUtils.init(applicationContext,-1);

    }
    public static ZxApplication getInstance() {
        return instance;
    }
    public void addActivity(Activity activity) {
        aliveActivities.add(activity);
    }

    @Override
    public void removeActivity(Activity activity) {
        aliveActivities.remove(activity);
    }

    @Override    public void finishAllActivity() {
        for (int i = 0; i < aliveActivities.size(); i++) {
            Activity activity = aliveActivities.get(i);
            activity.finish();
        }
    }
    /**
     * 强制关闭应用
     */
    public void exit() {
        //ThreadPools.clear();
        finishAllActivity();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
