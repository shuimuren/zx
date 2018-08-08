package com.zhixing.work.zhixin.constant;

/**
 * Created by lhj on 2018/7/9.
 * Description: 数据静态常量
 */

public class ResultConstant {

    // <!------------------- 个人认证 ----------------------->
    //身份证
    public static final int AUTHENTICATES_TYPE_IDENTITY_CARD = 10;
    //学历证
    public static final int AUTHENTICATES_TYPE_EDUCATION = 20;
    //证书认证
    public static final int AUTHENTICATES_TYPE_CERTIFICATE = 30;
    //工作
    public static final int AUTHENTICATES_TYPE_WORK = 40;
    //未认证
    public static final int AUTHENTICATE_STATUS_NULL = 0;
    //认证中
    public static final int AUTHENTICATE_STATUS_ING = 1;
    //认证成功
    public static final int AUTHENTICATE_STATUS_SUCCEED = 2;
    //认证失败
    public static final int AUTHENTICATE_STATUS_FAILURE = 3;

    //性别男
    public static final int USER_GERNAL_MAN = 0;
    //性别女
    public static final int USER_GERNAL_WOMAN = 1;

    public static final int USER_STAFF_ROLE_EMPLOYEE = 0; //员工
    public static final int USER_STAFF_ROLE_HR = 1; //Hr
    public static final int USER_STAFF_ROLE_MANAGER = 2;//管理员

    public static final String CLOCK_STATUS_LATE = "2"; //迟到
    public static final String CLOCK_STATUS_EARLY = "3"; //早退
    public static final String CLOCK_STATUS_ABSENTEEISM = "4";//缺卡
    public static final String CLOCK_STATUS_MISS = "5"; //旷工



}
