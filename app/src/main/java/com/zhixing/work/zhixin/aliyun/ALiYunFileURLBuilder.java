package com.zhixing.work.zhixin.aliyun;


import com.zhixing.work.zhixin.common.Logger;

/**
 * 阿里云地址创建类
 */
public class ALiYunFileURLBuilder {
    private static final String TAG = ALiYunOssFileLoader.class.getSimpleName();
    public static String END_POINT = "http://oss-cn-shenzhen.aliyuncs.com";
    public static String PUBLIC_END_POINT = "http://public.img.52zhixin.cn";
    public static String SECRET_END_POINT = "http://secret.img.52zhixin.cn";
    public static String OSS_END_POINT = "http://oss.52zhixin.cn";

    // 正式环境
    public static String ACCESS_KEY_ID = "LTAIexq8oI6KIJuW";
    public static String ACCESS_KEY_SECRET = "g5claCUmeD5xH9JcIc1I9voXN3E4WH";
    public static final String OSS_FILE_URL = "http://%1$s.oss-cn-shenzhen.aliyuncs.com/";
    // 主目录
    public static final String BUCKET_SECTET = "zx-img-secret";
    public static final String BUCKET_PUBLIC = "zx-img-public";
    // public static final String BASE = "test/"; //测试环境
    public static final String BASE = "prod/"; //正式环境

    public static final String PERSONALIDCARD = "personalIdCard/";  // 身份证
    public static final String PERSONALPORTRAIT = "personalPortrait/";  // 简历头像
    public static final String BUSINESSLICENSE = "company/businessLicense/";//企业营业执照图】
    public static final String MANAGERIDCARD = "company/managerIdCard/";//企业管理员身份证图】
    public static final String PERSONALEDUCATION = "personalEducation/";   //用户学历认证上传】
    public static final String PERSONALCERTIFICATE = "personalCertificate/";//用户证书认证上传】



    public static final String COMPANYLOGO = "company/logo/";  //企业logo
    public static final String COMPANYBACKGROUND = "company/background/";  //企业背景
    public static final String COMPANYPRODUCT = "company/product/";  //企业产品图
    public static final String COMPANYHISTORY = "company/history/";  //企业大事件
    public static final String companyseniormanager = "company/seniorManager/";  //企业高管图
    public static final String PERSONALAVATAR = "personalAvatar/";  //用户个性头像
    public static final String DYNAMIC = BASE + "dynamic/";  //  动态图片

    public static final String KEY_BUCKET_NAME = "bucketName"; //主目录key值 BUCKET_SECTET/BUCKET_PUBLIC
    public static final String KEY_SUB_ITEM_CATALOGUE = "subItem"; //子目录key
    public static final String KEY_FILE_PATH = "filePath"; //图片路径
    public static final String KEY_LOAD_BASE_URL = "baseUrl"; //
    public static final String KEY_IMAGE_MARK = "imageMark"; //图片加载标记位
    public static final String KEY_IMAGE_CODE = "imageCode";//图片上传标记位

    //图片上传识别码
    public static final int IMAGE_DISCERN_CODE_PERSONAL_FRAGMENT = 1;//个人卡牌页上传图片
    public static final int IMAGE_DISCERN_CODE_COMPANY_FRAGMENT = 2;//企业卡牌页上传图片


    public static String getBase() {
        String str = String.format("http://%1$s.oss-cn-shenzhen.aliyuncs.com/", new Object[]{"zx-img-public"});
        return str;
    }

    public static String getUserIconUrl(String key) {
        String url = getBase() + key;
        Logger.i(TAG, " 用户头像地址:" + url);
        return url;
    }

}
