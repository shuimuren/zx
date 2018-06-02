package com.zhixing.work.zhixin.util;

import android.content.Context;
import android.content.Intent;

import com.zhixing.work.zhixin.view.LoginActivity;


/**
 * Created by xiaopo on 2017/6/26.
 */

public class Indicator {

    public static void goLogin(Context activity) {
        Intent intent = new Intent();
        intent.setClass(activity, LoginActivity.class);
        intent.putExtra("sessionType", "true");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }
}
