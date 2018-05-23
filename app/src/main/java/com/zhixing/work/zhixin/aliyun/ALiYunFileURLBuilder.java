package com.zhixing.work.zhixin.aliyun;


import com.zhixing.work.zhixin.util.LOG;

/**
 * 阿里云地址创建类
 * Created by wangsuli on 2017/4/27.
 */
public class ALiYunFileURLBuilder {
    private static final String TAG = ALiYunOssFileLoader.class.getSimpleName();
    public static String END_POINT = "http://oss-cn-shenzhen.aliyuncs.com";
    // 正式环境
    public static String ACCESS_KEY_ID = "LTAIexq8oI6KIJuW";
    public static String ACCESS_KEY_SECRET = "g5claCUmeD5xH9JcIc1I9voXN3E4WH";
    public static final String OSS_FILE_URL = "http://%1$s.oss-cn-shenzhen.aliyuncs.com/";
    // 主目录
    public static final String BUCKET_SECTET = "zx-img-secret";
    public static final String BUCKET_public = "zx-img-public";


    // public static final String BASE = "test/"; //测试环境
    public static final String BASE = "prod/"; //正式环境
    public static final String PERSONALIDCARD = "personalIdCard/";  // 身份证
    public static final String PERSONALPORTRAIT = "personalPortrait/";  // 简历头像


    public static final String PERSONALCERTIFICATE = "personalCertificate";//用户证书认证上传】
    public static final String PERSONALEDUCATION = "personalEducation";   //用户学历认证上传】
    public static final String PERSONALAVATAR = "personalAvatar/";  //用户个性头像
    public static final String DYNAMIC = BASE + "dynamic/";  //  动态图片
    //三级目录
    public static String getBase() {
        String str = String.format(OSS_FILE_URL, BUCKET_SECTET);
//        LOG.i(TAG, " 阿里云base:"+ str+"   长度："+str.length());
        return str;
    }
//**
//     * 获取用户头像url
//     * @param userId
//     * @return
//
//    public static String getUserIconUrl(long userId) {
//        String url=getBase() + USER_ICON  + userId;
//        LOG.i(TAG, " 用户头像地址:"+ url);
//        return url;
//    }

//    /**/*//**
//     * 获取动态url
//     * @param key
//     * @return
//     *//**//*
//    public static String getDynamicUrl(String key) {
//        String url=getBase() + DYNAMIC + key;
//        LOG.i(TAG, " 动态图片地址:"+ url);
//        return url;
//    }*/
}
