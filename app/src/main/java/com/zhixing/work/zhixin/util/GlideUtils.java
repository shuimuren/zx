package com.zhixing.work.zhixin.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.aliyun.ALiYunFileURLBuilder;
import com.zhixing.work.zhixin.app.ZxApplication;

/**
 * Glide工具类
 */
@GlideModule
public class GlideUtils extends AppGlideModule {
    private static GlideUtils instance;

    public static GlideUtils getInstance() {
        if (null == instance) {
            instance = new GlideUtils();
        }
        return instance;
    }

    // 清除图片磁盘缓存，调用Glide自带方法
    public boolean clearCacheDiskSelf() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(ZxApplication.getInstance()).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(ZxApplication.getInstance()).clearDiskCache();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 清除Glide内存缓存
    public boolean clearCacheMemory() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(ZxApplication.getInstance()).clearMemory();

                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 便于更换头像 头像资源实时更新
     *
     * @param context
     * @param
     * @param imageView 圆形头像
     */
    public void loadRoundUserIconInto(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url)
                .transition(DrawableTransitionOptions.withCrossFade()).
                apply(RequestOptions.circleCropTransform())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .into(imageView);
    }

    /**
     * 便于更换头像 头像资源实时更新
     *
     * @param context
     * @param
     * @param imageView 圆角图片
     */
    public void loadGlideRoundTransform(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url)
                .transition(DrawableTransitionOptions.withCrossFade()).
                apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .into(imageView);
    }


    /**
     * 便于更换头像 头像资源实时更新
     *
     * @param context
     * @param
     * @param imageView 圆角图片
     */
    public void loadPublicRoundTransform(Context context, String url, ImageView imageView) {
        Glide.with(context).load(ALiYunFileURLBuilder.PUBLIC_END_POINT +url)
                .transition(DrawableTransitionOptions.withCrossFade()).
                apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .into(imageView);
    }
    /**
     * 便于更换头像 头像资源实时更新
     * @param context
     * @param
     * @param imageView 圆形头像
     */
    public void loadCircleUserIconInto(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).
                apply(RequestOptions.bitmapTransform(new CircleCrop())
                        .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).error(ResourceUtils.getDrawable(R.drawable.img_man)))
                .into(imageView);

    }
    /**

     * @param imageView 圆形头像本地
     */
    public void loadIconInto(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).
                apply(RequestOptions.bitmapTransform(new CircleCrop())
                        .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE)))
                .apply( new RequestOptions().skipMemoryCache( true ))
                .into(imageView);

    }

    /**
     * 带默认图片
     * @param context
     * @param url
     * @param imageView
     */

    public void loadGlideRoundTransform(Context context, Drawable drawable, String url, ImageView imageView) {
        Glide.with(context).load(url)
                .transition(DrawableTransitionOptions.withCrossFade()).
                apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE).placeholder(drawable))
                .into(imageView);
    }


}
