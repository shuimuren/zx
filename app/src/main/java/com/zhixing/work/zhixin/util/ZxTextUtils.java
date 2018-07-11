package com.zhixing.work.zhixin.util;

import android.text.TextUtils;

import com.zhixing.work.zhixin.R;

/**
 * Created by lhj on 2018/7/10.
 * Description: 数据管理类,非空判断
 */

public class ZxTextUtils {

    public static String getTextWithDefault(String text){
        if(TextUtils.isEmpty(text)){
            return ResourceUtils.getString(R.string.text_empty_with_default);
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
