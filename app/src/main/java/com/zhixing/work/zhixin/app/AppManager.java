package com.zhixing.work.zhixin.app;

import android.content.Context;

import com.tencent.tauth.Tencent;
import com.zhixing.work.zhixin.BuildConfig;
import com.zhixing.work.zhixin.msgctrl.ControllerRegister;
import com.zhixing.work.zhixin.network.OkHttpUtil;
import com.zhixing.work.zhixin.network.RetrofitServiceFactory;

import java.io.File;

/**
 * Created by lhj on 2018/7/3.
 * Description: AppG管理类,用于一些应用的初始化
 */

public class AppManager {
    private Context mContext;

    private static final long CACHE_SIZE = 10 * 1024 * 1024l;
    private static final long CONNECT_TIME_OUT = 20000l;
    private static final long READ_TIME_OUT = 20000l;
    private static final long WRITE_TIME_OUT = 20000l;
    public static Tencent mTencent;
    private AppManager() {

    }

    private static final class InstanceHolder {

        static AppManager appManager = new AppManager();

    }

    public static AppManager getManagerInstance() {
        return InstanceHolder.appManager;
    }

    /**
     * 一系列初始化
     *
     * @param appContext
     */
    public void initialize(Context appContext) {
        mContext = appContext;
        OkHttpUtil.getInstance().init(mContext.getApplicationContext().getFilesDir() + File.separator + "ZhiXin",
                CACHE_SIZE, CONNECT_TIME_OUT, READ_TIME_OUT, WRITE_TIME_OUT);
        if (BuildConfig.DEBUG) {
            OkHttpUtil.getInstance().setLog(true);
        } else {
            OkHttpUtil.getInstance().setLog(false);
        }
        //重置网络请求
        RetrofitServiceFactory.recreateService();
        ControllerRegister.initialize();
        //QQ分享
//        if (mTencent == null) {
//            mTencent = Tencent.createInstance(ShareConstant.QQ_SHARE_APP_ID,mContext);
//        }

//        CrashHandler.getInstance().init(appContext);
    }
}
