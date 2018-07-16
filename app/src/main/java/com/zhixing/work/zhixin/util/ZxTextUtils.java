package com.zhixing.work.zhixin.util;

import android.text.TextUtils;

/**
 * Created by lhj on 2018/7/10.
 * Description: 数据管理类,非空判断
 */

public class ZxTextUtils {

    public static String getTextWithDefault(String text){
        if(TextUtils.isEmpty(text)){
            return "-";
        }else{
            return text;
        }
    }

    public static String getTextNotNull(String text){
        if(TextUtils.isEmpty(text)){
            return "";
        }else {
            return text;
        }
    }
}
