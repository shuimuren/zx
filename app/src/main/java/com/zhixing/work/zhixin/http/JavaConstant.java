package com.zhixing.work.zhixin.http;


/**
 *
 */
public class JavaConstant {
    //测试地址
    public static final String basehttp = "http://192.168.1.10/";
    //public static final String basehttp = "http://9681.vicp.net:51093/";
    //是否注册
    public static final String IsRegist = basehttp + "User";
    //获取短信验证码
    public static final String getRegistCode = basehttp + "SmsCode";
    //去注册
    public static final String goRegist = basehttp + "User";
    //登录
    public static final String goLogin = basehttp + "token";
    //注销
    public static final String outLogin = basehttp + "token";
    //更新密码
    public static final String UpdatePWD = basehttp + "User";
    //添加基础卡牌
    public static final String PersonalInfo = basehttp + "PersonalInfo";
    //完善卡牌资料
    public static final String personalinfo = basehttp + "personalinfo";
    //获取卡牌正面信息
    public static final String card = basehttp + "card";
    //添加教育经历
    public static final String EducationBackground = basehttp + "EducationBackground";
    //添加证书经历
    public static final String CertificateBackground = basehttp + "CertificateBackground";
    //添加工作经历
    public static final String WorkBackground = basehttp + "WorkBackground";
    //获取阿里云临时凭证
    public static final String getOSS = basehttp + "sts";
    ///获取答题
    public static final String TestPaper = basehttp + "Evaluate/TestPaper";

    //提交答案
    public static final String Evaluate = basehttp + "Evaluate";

    ///修改简历个人资料
    public static final String ResumePersonalInfo = basehttp + "Resume/PersonalInfo";

    //修改头像
    public static final String Avatar = basehttp + "Personal/Avatar";

    //修改简历
    public static final String Resume = basehttp + "Resume";
    //项目经历
    public static final String ProjectBackground = basehttp + "ProjectBackground";

    //添加工作城市
    public static final String ExpectArea = basehttp + "Resume/ExpectArea";
    //获取求职意向
    public static final String Expect = basehttp + "Resume/Expect";
    //修改行业
    public static final String ExpectIndustry = basehttp + "Resume/ExpectIndustry";
    //修改职业
    public static final String ExpectJob = basehttp + "Resume/ExpectJob";
    //添加公司基本资料
    public static final String Company = basehttp + "Company";


}
