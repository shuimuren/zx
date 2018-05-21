package com.zhixing.work.zhixin.app;

import android.app.Activity;
import android.content.Context;


import com.zhixing.work.zhixin.base.BaseApplication;

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
    //    private DaoMaster.DevOpenHelper mHelper;
//    private SQLiteDatabase db;
//    private DaoMaster mDaoMaster;
//    private DaoSession mDaoSession;
    public List<Activity> aliveActivities = new ArrayList<>();
//    private OSSClient mOSSClient;

    @Override
    public void onCreate() {


        super.onCreate();

        instance = this;
        //init demo helper

    }

    /**
     * 设置greenDao
     */
    public static ZxApplication getInstance() {
        return instance;
    }
//    private void setDatabase() {
//        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
//        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
//        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
//        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
//        mHelper = new DaoMaster.DevOpenHelper(this, "bjdp.db", null);
//
//        db = mHelper.getWritableDatabase();
//        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
//        mDaoMaster = new DaoMaster(db);
//        mDaoSession = mDaoMaster.newSession();
//    }
//
//    public DaoSession getDaoSession() {
//        return mDaoSession;
//    }

    //    public SQLiteDatabase getDb() {
//        return db;
//    }
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
