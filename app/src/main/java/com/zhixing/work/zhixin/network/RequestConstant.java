package com.zhixing.work.zhixin.network;/** * Created by lhj on 18/5/8 * 用于存放请求借口及Key值 */public class RequestConstant {    //测试地址    public static final String BASE_URL = "http://192.168.1.10/";   // public static final String BASE_URL = "http://9681.vicp.net:51093/";    //是否注册    public static final String IS_REGISTER = BASE_URL + "User";    //获取短信验证码    public static final String GET_REGISTER_CODE = BASE_URL + "SmsCode";    //去注册    public static final String GO_REGISTER = BASE_URL + "User";    //登录    public static final String GO_LOGIN = BASE_URL + "token";    //注销    public static final String LOG_OUT = BASE_URL + "token";    //更新密码    public static final String UPDATE_PASSWORD = BASE_URL + "User";    //添加基础卡牌    public static final String ADD_PERSONAL_INFO = BASE_URL + "PersonalInfo";    //完善卡牌资料    public static final String EDIT_PERSONAL_INFO = BASE_URL + "personalinfo";    //获取卡牌正面信息    public static final String GET_CARD_INFO = BASE_URL + "card";    //添加教育经历    public static final String ADD_EDUCATION_EXPERIENCE = BASE_URL + "EducationBackground";    //添加证书经历    public static final String ADD_CERTIFICATE = BASE_URL + "CertificateBackground";    //添加工作经历    public static final String WorkBackground = BASE_URL + "WorkBackground";    //获取阿里云临时凭证    public static final String GET_OSS = BASE_URL + "sts";    ///获取答题    public static final String TEST_PAPER = BASE_URL + "Evaluate/TestPaper";    //提交答案    public static final String EVALUATE = BASE_URL + "Evaluate";    //获取资质信息    public static final String GET_EVALUATE_INFO = BASE_URL + "card";    ///修改简历个人资料    public static final String RESUME_PERSONAL_INFO = BASE_URL + "Resume/PersonalInfo";    //修改头像    public static final String AVATAR = BASE_URL + "Personal/Avatar";    //修改简历    public static final String RESUME = BASE_URL + "Resume";    //项目经历    public static final String PROJECT_BACKGROUND = BASE_URL + "ProjectBackground";    //添加工作城市    public static final String EXPECT_AREA = BASE_URL + "Resume/ExpectArea";    //获取求职意向    public static final String EXPECT = BASE_URL + "Resume/Expect";    //修改行业    public static final String RESUME_EXPECT_INDUSTRY = BASE_URL + "Resume/ExpectIndustry";    //修改职业    public static final String RESUME_EXPECT_JOB = BASE_URL + "Resume/ExpectJob";    //添加公司基本资料    public static final String COMPANY = BASE_URL + "Company";    //添加产品    public static final String COMPANY_PRODUCT = BASE_URL + "CompanyProduct";    //添加高管    public static final String COMPANY_SENIOR_MANAGER = BASE_URL + "CompanySeniorManager";    //公司大事件    public static final String COMPANY_HISTORY = BASE_URL + "CompanyHistory";    //获取公司卡牌正面信息    public static final String COMPANY_CARD = BASE_URL + "Company/Card";    //公司简介    public static final String COMPANY_INTRO = BASE_URL + "Company/Intro";    //公司部门    public static final String DEPARTMENT = BASE_URL + "Department";    //获取子部门    public static final String DEPARTMENT_CHILD = BASE_URL +"Department/Childens";    //获取公司的所有员工    public static final String DEPARTMENT_MEMBER = BASE_URL + "Department/DepartmentMember";    //邀请进部门    public static final String INVITE = BASE_URL + "Department/Invite";    //获取部门员工列表    public static final String STAFF = BASE_URL + "Department/Staff";    //提交公司营业执照    public static final String BUSINESS_LICENSE = BASE_URL + "CompanyCertification/BusinessLicense";    //提交公司法人认证    public static final String ID_CARD = BASE_URL + "CompanyCertification/IdCard";    //获取认证列表/提交    public static final String AUTHENTICATES = BASE_URL + "Authenticates";    //企业认证状态    public static final String COMPANY_CERTIFICATION_STATUS = "CompanyCertification";    //获取部门设置信息    public static final String GET_DEPARTMENT_SETTING_INFO = "Department";    //更新企业卡牌图片    public static final String UPDATE_COMPANY_AVATAR = "Company/CardAvatar";    //---------------------------key---------------------------------------    public static final String KEY_AUTHENTICATION_INFO = "info";    public static final String KEY_ID = "id";    public static final String KEY_FRONT_OR_BACK = "frontOrBack";    public static final String KEY_OPT = "opt";    public static final String KEY_RESUME_ID = "resumeId";    public static final String KEY_REQUEST_BODY = "body";    public static final String KEY_BACK = "back";    public static final String KEY_EVALUATE = "evaluate";    public static final String KEY_BASIC_INFO = "baseinfo";    public static final String KEY_PHONE_NUMBER = "PhoneNum";    public static final String KEY_PASSWORD = "Password";    public static final String KEY_USER_ROLE_ENUM = "UserRoleEnum";    public static final String KEY_ROLE = "Role";    public static final String KEY_SMS_CODE = "SmsCode";    public static final String KEY_SMS_CODE_TYPE_ENUM = "SmsCodeTypeEnum";    public static final String KEY_NEW_PASSWORD = "NewPassword";    public static final String KEY_UPDATE_PSAAWORD_BODY = "UserBody";    public static final String KEY_VERIFY_CODE = "VerifyCode";    public static final String KEY_PARENT_ID = "ParentId";    public static final String KEY_NAME = "Name";    public static final String KEY_DEPARTMENT_ID = "departmentId";    public static final String KEY_COMPANY_ID = "companyId";    public static final String KEY_FRONT = "front";    public static final String KEY_AVATAR = "avatar";    public static final String KEY_DATE = "Date";    public static final String KEY_INTRO = "Intro";    public static final String KEY_IMAGE = "Image";}