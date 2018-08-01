package com.zhixing.work.zhixin.common.crash;

import android.content.Context;

/**
 * Created by lhj on 2018/7/28.
 * Description:
 */

public class AppCrashHandler {

    private Context mContext;
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
    private static AppCrashHandler instance;

    private AppCrashHandler(Context context){
        this.mContext = context;
        uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                saveException(e,true);

                uncaughtExceptionHandler.uncaughtException(t, e);
            }
        });
    }

    public static AppCrashHandler getInstance(Context context){
        if(instance == null){
            instance = new AppCrashHandler(context);
        }
        return instance;
    }

    private void saveException(Throwable e, boolean b) {
        CrashSaver.save(mContext, e, b);
    }

    public void setUncaughtExceptionHandler(Thread.UncaughtExceptionHandler handler) {
        if (handler != null) {
            this.uncaughtExceptionHandler = handler;
        }
    }

}
