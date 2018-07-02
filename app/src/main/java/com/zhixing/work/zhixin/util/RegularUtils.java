package com.zhixing.work.zhixin.util;

import android.text.TextUtils;

/**
 * Created by Administrator on 2018/7/2.
 */

public class RegularUtils {

    public static boolean isMobileNo(String mobiles){
        String telRegex = "[1][123456789]\\d{9}";
        if(!TextUtils.isEmpty(mobiles)){
            return mobiles.matches(telRegex);
        }
        return false;
    }
}
