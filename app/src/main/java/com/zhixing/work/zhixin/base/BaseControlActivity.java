package com.zhixing.work.zhixin.base;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


import com.zhixing.work.zhixin.app.ZxApplication;
import com.zhixing.work.zhixin.event.AppExitEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * 公共的控制类，所有的应用的Activity都继承自次Activity
 *
 * @author Administrator
 */
public class BaseControlActivity extends FragmentActivity {
    public static final String TAG = "BJDPActivity";
    public Context context;
    public ZxApplication zxApplication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        zxApplication = ZxApplication.getInstance();
        EventBus.getDefault().register(this);
        ZxApplication.getInstance().addActivity(this);
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEventMainThread(Object messageEvent) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEventMainThread(AppExitEvent messageEvent) {
        finish();
        System.exit(0);
    }
}
