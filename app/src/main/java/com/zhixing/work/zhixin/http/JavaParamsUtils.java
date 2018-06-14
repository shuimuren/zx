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

    //  修改简历个人信息
    public Map<String, String> ResumePersonalInfo(String RealName, String FirstWorkTime, String Birthday) {
        Map<String, String> params = new HashMap<>();

        params.put("RealName", RealName);
        params.put("FirstWorkTime", FirstWorkTime);
        params.put("Birthday", Birthday);

        return params;
    }

    //  修改简历头像
    public Map<String, String> resumeAvatar() {
        Map<String, String> params = new HashMap<>();
        return params;
    }

    //  获取简历信息
    public Map<String, String> getResume() {
        Map<String, String> params = new HashMap<>();
        return params;
    }

    //  删除工作
    public Map<String, String> deleteWork(String id) {
        Map<String, String> params = new HashMap<>();

        params.put("Id", id);
        return params;
    }

    //  删除工作
    public Map<String, String> deleteEducation() {
        Map<String, String> params = new HashMap<>();


        return params;
    }

    //  修改工作
    public Map<String, String> upWork() {
        Map<String, String> params = new HashMap<>();

        return params;
    }

    //  修改证书
    public Map<String, String> upCertificate() {
        Map<String, String> params = new HashMap<>();

        return params;
    }

    //  项目经历
    public Map<String, String> project() {
        Map<String, String> params = new HashMap<>();

        return params;
    }

    //  求职意向
    public Map<String, String> Expect(String resumeId) {
        Map<String, String> params = new HashMap<>();
        params.put("resumeId", resumeId);
        return params;
    }

    //  提交
    public Map<String, String> setCompany() {
        Map<String, String> params = new HashMap<>();

        return params;
    }


    //  公司基本信息
    public Map<String, String> Company(String FullName, String Province,
                                       String City, String District,
                                       String Address, String ManagerName,
                                       String ManagerSex, String ManagerEmail
    ) {
        Map<String, String> params = new HashMap<>();
        params.put("FullName", FullName);
        params.put("Province", Province);
        params.put("City", City);
        params.put("District", District);
        params.put("Address", Address);
        params.put("ManagerName", ManagerName);
        params.put("ManagerSex", ManagerSex);
        params.put("ManagerEmail", ManagerEmail);
        return params;
    }
/*
    Name	string	是	产品名称
    Logo	string	否	图片名称
    Url	string	否	产品URL
    Intro*/

    //  添加产品
    public Map<String, String> CompanyProduct(String Name, String Logo,
                                              String Url, String Intro
    ) {
        Map<String, String> params = new HashMap<>();
        params.put("Name", Name);
        params.put("Logo", Logo);
        params.put("Url", Url);
        params.put("Intro", Intro);

        return params;
    }


    //  修改产品
    public Map<String, String> editCompanyProduct(String id, String Name, String Logo,
                                                  String Url, String Intro
    ) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("Name", Name);
        params.put("Logo", Logo);
        params.put("Url", Url);
        params.put("Intro", Intro);

        return params;
    }

    /*
       CompanyId	string	是	公司ID
    Name	string	是	姓名
    JotTitle	string	是	职位
    Intro	string	是	简介
    Avatar	string	是	图片名称*/
    //  添加高管
    public Map<String, String> CompanySeniorManager(String CompanyId, String Name, String Avatar,
                                                    String JotTitle, String Intro
    ) {
        Map<String, String> params = new HashMap<>();
        params.put("CompanyId", CompanyId);
        params.put("Name", Name);
        params.put("Avatar", Avatar);
        params.put("JotTitle", JotTitle);
        params.put("Intro", Intro);

        return params;
    }

    //  添加高管
    public Map<String, String> editSeniorManager(String id, String Name, String Avatar,
                                                 String JotTitle, String Intro
    ) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("Name", Name);
        params.put("Avatar", Avatar);
        params.put("JotTitle", JotTitle);
        params.put("Intro", Intro);

        return params;
    }


    //  获取高管
    public Map<String, String> getCompanySeniorManager(
    ) {
        Map<String, String> params = new HashMap<>();

        return params;
    }

    //  获取大事件
    public Map<String, String> getCompanyHistory(
    ) {
        Map<String, String> params = new HashMap<>();

        return params;
    }

    /*    Name	string	是	事件名称
        Date	string	是	日期
        Intro	string	是	简介
        Image*/
    //  获取大事件
    public Map<String, String> AddCompanyHistory(String Name, String Date, String Intro, String Image) {
        Map<String, String> params = new HashMap<>();
        params.put("Name", Name);
        params.put("Date", Date);
        params.put("Intro", Intro);
        params.put("Image", Image);


        return params;
    }

    //  获取大事件
    public Map<String, String> editCompanyHistory(String id, String Name, String Date, String Intro, String Image) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("Name", Name);
        params.put("Date", Date);
        params.put("Intro", Intro);
        params.put("Image", Image);


        return params;
    }

    //    获取卡牌信息
    public Map<String, String> getCompanyCard() {
        Map<String, String> params = new HashMap<>();

        return params;
    }

    //    获取公司产品
    public Map<String, String> getCompanyProduct() {
        Map<String, String> params = new HashMap<>();

        return params;
    }


    //  删除工作
    public Map<String, String> deleteProduct() {
        Map<String, String> params = new HashMap<>();


        return params;
    }
    //  添加产品
    public Map<String, String> addDepartment(String ParentId, String Name
    ) {
        Map<String, String> params = new HashMap<>();
        params.put("Name", Name);
        params.put("ParentId", ParentId);


        return params;
    }
    //  获取子部门
    public Map<String, String> getDepartment(String departmentId
    ) {
        Map<String, String> params = new HashMap<>();
        params.put("departmentId", departmentId);
        return params;
    }

    //  获取公司组织架构
    public Map<String, String> getCompany(
    ) {
        Map<String, String> params = new HashMap<>();

        return params;
    }
    //  修改子部门
    public Map<String, String> modifyDepartment(
    ) {
        Map<String, String> params = new HashMap<>();
        return params;
    }
    //  获取子部门
    public Map<String, String> getSetff(String departmentId
    ) {
        Map<String, String> params = new HashMap<>();
        params.put("departmentId", departmentId);
        return params;
    }


}
