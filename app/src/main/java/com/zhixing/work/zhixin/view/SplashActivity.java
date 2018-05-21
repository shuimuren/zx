package com.zhixing.work.zhixin.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.util.SettingUtils;
import com.zhy.http.okhttp.utils.L;

import java.util.Map;
/**
 * 开屏页
 */
public class SplashActivity extends Activity {

    private static final int sleepTime = 2000;

    private Gson gson;
    public static SplashActivity instance;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        setContentView(R.layout.em_activity_splash);
        RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.splash_root);
        TextView versionText = (TextView) findViewById(R.id.tv_version);
        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(1500);
        rootLayout.startAnimation(animation);


//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//
//
//            }
//        }, 1000);

    }

    @Override
    protected void onStart() {

        super.onStart();
        if (TextUtils.isEmpty(SettingUtils.getToken())) {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);

        } else {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
        }
        finish();


    }

    private void goMain() {
        long start = System.currentTimeMillis();

        long costTime = System.currentTimeMillis() - start;
        //wait
        if (sleepTime - costTime > 0) {
            try {
                Thread.sleep(sleepTime - costTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        instance = null;
        finish();
    }

    /**
     * get sdk version
     */


}
