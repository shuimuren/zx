package com.zhixing.work.zhixin.util;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.zhixing.work.zhixin.app.ZxApplication;

/**
 * Created by Administrator on 2018/7/2.
 */

public class ResourceUtils {
    private static Resources getResources() {
        return ZxApplication.getAppContext().getResources();
    }

    public static String getString(int stringId) {
        return ZxApplication.getAppContext().getResources().getString(stringId);
    }

    public static float getDimenFloat(int dimenId) {
        return ZxApplication.getAppContext().getResources().getDimension(dimenId);
    }

    public static int getDimenInt(int dimenId) {
        return (int) ZxApplication.getAppContext().getResources().getDimension(dimenId);
    }

    public static Drawable getDrawable(int resId) {
        return ZxApplication.getAppContext().getResources().getDrawable(resId);
    }

    public static int getColor(int resId) {
        return ZxApplication.getAppContext().getResources().getColor(resId);
    }

    public static ColorStateList getColorStateList(int resId) {
        return getResources().getColorStateList(resId);
    }
}
