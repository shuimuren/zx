package com.zhixing.work.zhixin.http;


import java.util.HashMap;
import java.util.Map;

/**
 * php请求参数类
 * Created by wangsuli on 2017/4/19.
 */

public class JavaParamsUtils {
    //请求头参数 Authorization

    private static JavaParamsUtils instances;

    public static JavaParamsUtils getInstances() {
        if (instances == null) {
            instances = new JavaParamsUtils();
        }
        return instances;
    }

    //验证码
    //mobile 手机号码
    public Map<String, String> Short_Message(String PhoneNum, String UserRoleEnum, String SmsCodeTypeEnum) {
        Map<String, String> params = new HashMap<>();
        params.put("PhoneNum", PhoneNum);
        params.put("UserRoleEnum", UserRoleEnum);
        params.put("SmsCodeTypeEnum", SmsCodeTypeEnum);
        return params;
    }

    //是否注册
    //mobile 手机号码
    public Map<String, String> isRegist(String PhoneNum, String Role) {
        Map<String, String> params = new HashMap<>();
        params.put("PhoneNum", PhoneNum);
        params.put("Role", Role);

        return params;
    }

    //注册
    //PhoneNum 手机号码
    //password 密码
    public Map<String, String> Registered(String PhoneNum, String password, String Role, String VerifyCode) {
        Map<String, String> params = new HashMap<>();
        params.put("PhoneNum", PhoneNum);
        params.put("Password", password);
        params.put("Role", Role);
        params.put("VerifyCode", VerifyCode);
        return params;
    }

    //修改密码
    //PhoneNum
    //password 密码
    public Map<String, String> upPassword(String PhoneNum, String NewPassword, String Role, String SmsCode) {
        Map<String, String> params = new HashMap<>();
        params.put("PhoneNum", PhoneNum);
        params.put("NewPassword", NewPassword);
        params.put("Role", Role);
        params.put("SmsCode", SmsCode);
        return params;
    }

    //登录
    // mobile 手机号码
    //password 密码
    public Map<String, String> Login(String PhoneNum, String Password, String UserRoleEnum) {
        Map<String, String> params = new HashMap<>();
        params.put("PhoneNum", PhoneNum);
        params.put("Password", Password);
        params.put("UserRoleEnum", UserRoleEnum);

        return params;
    }

    //添加基础卡牌
//    RealName	string	是	真实姓名
//    Sex	string	是	性别	0
//    Email	string	是	邮箱地址
//    UserIdentity
    public Map<String, String> PersonalInfo(String RealName, String Email, String sex, String UserIdentity) {
        Map<String, String> params = new HashMap<>();
        params.put("RealName", RealName);
        params.put("sex", sex);
        params.put("Email", Email);
        params.put("UserIdentity", UserIdentity);

        return params;
    }


    //完善资料
//
//    UserIdentity
    public Map<String, String> personalinfo() {
        Map<String, String> params = new HashMap<>();
        return params;
    }

    //完善资料添加学历
//
//    UserIdentity
    public Map<String, String> EducationBackground() {
        Map<String, String> params = new HashMap<>();
        return params;
    }

    //    获取卡牌信息
    public Map<String, String> getCard() {
        Map<String, String> params = new HashMap<>();
        params.put("frontOrBack", "front");
        return params;
    }
    //    获取反面卡牌信息
    public Map<String, String> getCardAll() {
        Map<String, String> params = new HashMap<>();


        params.put("frontOrBack", "back");
        params.put("opt", "baseinfo");
        return params;
    }
    //    获取阿里云临时
    public Map<String, String> getOSS() {
        Map<String, String> params = new HashMap<>();
        return params;
    }
    //    获取答题
    public Map<String, String> TestPaper() {
        Map<String, String> params = new HashMap<>();
        return params;
    }

}
