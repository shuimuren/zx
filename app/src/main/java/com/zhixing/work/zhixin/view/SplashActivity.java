package com.zhixing.work.zhixin.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.common.GlideImageLoader;
import com.zhixing.work.zhixin.util.PreferenceUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.util.SettingUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 开屏页
 */
public class SplashActivity extends Activity implements ViewPager.OnPageChangeListener {

    private static final int sleepTime = 2000;
    public static SplashActivity instance;
    @BindView(R.id.banner)
    Banner banner;
    //    @BindView(R.id.iv_splash_logo)
//    ImageView ivSplashLogo;
//    @BindView(R.id.tv_version)
//    TextView tvVersion;
    @BindView(R.id.rlBanner)
    RelativeLayout rlBanner;
    @BindView(R.id.iv_splash_logo)
    ImageView ivSplashLogo;
    @BindView(R.id.splash_root)
    RelativeLayout splashRoot;
    @BindView(R.id.btnFinish)
    Button btnFinish;

    private List images;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.em_activity_splash);
        ButterKnife.bind(this);

        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(1500);
        splashRoot.startAnimation(animation);
        if (TextUtils.isEmpty(PreferenceUtils.getString(PreferenceUtils.KEY_SHOW_SPLASH))) {
            rlBanner.setVisibility(View.VISIBLE);
            images = new ArrayList();
            images.add(ResourceUtils.getDrawable(R.drawable.image_banner_01));
            images.add(ResourceUtils.getDrawable(R.drawable.image_banner_02));
            images.add(ResourceUtils.getDrawable(R.drawable.image_banner_03));
            images.add(ResourceUtils.getDrawable(R.drawable.image_banner_04));
            banner.setImages(images)
                    .setImageLoader(new GlideImageLoader())
                    .isAutoPlay(false)
                    .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                    .setIndicatorGravity(BannerConfig.CENTER)
                    .start()
                    .setOnPageChangeListener(this);
        } else {
            ivSplashLogo.setVisibility(View.VISIBLE);
            //因为初始化耗时,延迟加载
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (TextUtils.isEmpty(SettingUtils.getToken())) {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    finish();

                }
            }, sleepTime);
        }

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 3) {
            banner.stopAutoPlay();
            btnFinish.setVisibility(View.VISIBLE);
        } else {
            btnFinish.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick(R.id.btnFinish)
    public void onViewClicked() {
        PreferenceUtils.putString(PreferenceUtils.KEY_SHOW_SPLASH, "hasLoad");
        if (TextUtils.isEmpty(SettingUtils.getToken())) {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
