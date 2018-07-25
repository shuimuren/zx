package com.zhixing.work.zhixin.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.bean.StaffList;
import com.zhixing.work.zhixin.bean.Token;
import com.zhixing.work.zhixin.http.HttpHeadUtils;
import com.zhixing.work.zhixin.network.OkHttpUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SettingUtils {

    private static Gson gson = new Gson();

    public static class SettingItems {

        // 保存在静态变量中
        public static final String PASSWORD = "password";
        public static final String IS_FIRST_START = "is_first_start";
        public static final String IS_SHOULD_SHOW_GUID = "is_should_show_guid";  //  商户群引导
        public static final String IS_SHOULD_SHOW_GUID_PHOTO = "is_should_show_guid_photo";  //  相册引导
        public static final String IS_SHOULD_SHOW_GUID_COMMERCIAL_GROUP = "is_should_show_guid_commercial_group";  //  商户群引导
        // public static final String USER_NAME = "username";
        public static final String SESSION_ID = "sessionId";
        public static final String FANSUCENTER_SESSION_ID = "fansUcenter_session_id";
        public static final String FANSAPPAPISESSIONID = "fansAppApiSessionId";
        public static final String CUSTOMER_ID = "customerId";
        public static final String VISITORS_ID = "visitorsId";//游客ID

        public static final String SOCKET_IP = "socketId";
        public static final String PHONENUMBER = "phoneNumber";
        public static final String EXPERIENCE = "experience";
        public static final String SIGNSTATUS = "signStatus";
        public static final String SOCKET_PORT = "socketPort";
        public static final String NICKNAME = "nickName";
        public static final String USER_PHONE = "user_phone"; // 用户电话 也即用户名
        public static final String OLD_VERSION = "old_version"; // 用户电话 也即用户名
        public static final String LOGIN_TYPE = ""; // 判断是否是游客登录
        public static final String STORAGE_ID = ""; // 仓储的iD
        public static final String PASS_ID = "Pass_Id"; // iD
        public static final String USER_ID = "userID"; // 用户iD
        public static final String Avatar = "Avatar"; // 用户头像地址
        public static final String OPENID_EASEMOB = "openid_easemob"; // 用户环信id
        public static final String HISTORICAL_RECORDS = "historical_records"; // 历史记录

        public static final String TESTPAPER = "testpaper"; // 试卷
        public static final String PROVINCIALLIST = "provincialList"; // 省
        public static final String CITYLIST = "cityList"; // 城市
        public static final String JOBLIST = "JobList"; // 职业
        public static final String HOTLIST = "hotlist"; // 热门城市
        public static final String INDUSTRY = "industry"; // 行业
        public static final String AREALIST = "areaList"; // 区
        public static final String STAFFSLIST = "StaffsList"; //公司所有员工
        public static final String JOB_LIST_DATA = "jobListData";//意向
        public static final String KEY_ROLE_INFO = "roleInfo";
        /**
         * 点赞消息
         */
        public static final String NEW_PRAISE = "new_praise";
        public static final String IS_TODAY_QIAN_DAO = "is_today_qiandao"; // 今天是否签到
        /**
         * 未读聊天消息，未读群通知消息数量总和
         */
        public static final String NO_READ_MESSAGE_NUMBER = "no_read_message_number";

        public static final String CURRENT_TIMES_CITY_GTOUP = "current_times_city_group";  // 同城群发表的时间
        public static final String CURRENT_TIMES_COMMUNITY_GROUP = "current_times_community_group"; // 同小区群发表的时间
        //商圈未读消息数据
        public static final String UnReadTradeAreaData = "current_times_community_group"; // 同小区群发表的时间
        public static final String TOKENBEAN = "tokenBean"; // token信息

        public static final String TOKEN = "token";

    }

   /* *//**
     * 保存Token对象
     *//*
    public static void putTokenBean(Token token) {
        PreferenceUtils.putObject(SettingItems.TOKENBEAN, token);
    }

    public static Token getTokenBean() {
        return (Token) PreferenceUtils.getObject(SettingItems.TOKENBEAN, null);
    }*/


    /**
     * 保存TokenBean
     *
     * @param token
     */
    public static void putTokenBean(String token) {

        String tokenString = "";
        if (!TextUtils.isEmpty(token)) {
            tokenString = token;
        }
        PreferenceUtils.putString(SettingItems.TOKENBEAN, tokenString);
    }

    /**
     * 获取TokenBean
     *
     * @return
     */
    public static Token getTokenBean() {
        Token token = new Token();
        String tokenString = PreferenceUtils.getString(SettingItems.TOKENBEAN);
        if (!TextUtils.isEmpty(tokenString)) {
            token = gson.fromJson(tokenString, Token.class);
            return token;
        }
        return token;
    }

    /**
     * 保存provincialList
     *
     * @param areaList
     */
    public static void putAreaList(String areaList) {

        String list = "";
        if (!TextUtils.isEmpty(areaList)) {
            list = areaList;
        }
        PreferenceUtils.putString(SettingItems.AREALIST, list);
    }

    /**
     * areaList
     *
     * @return
     */
    public static String getAreaList() {

        String areaList = PreferenceUtils.getString(SettingItems.AREALIST);
        if (!TextUtils.isEmpty(areaList)) {
            return areaList;
        }
        return "";
    }


    /**
     * 保存所有员工
     *
     * @param staffsList
     */
    public static void putStaffList(String staffsList) {

        String list = "";
        if (!TextUtils.isEmpty(staffsList)) {
            list = staffsList;
        }
        PreferenceUtils.putString(SettingItems.STAFFSLIST, list);
    }

    /**
     * 获取所有员工
     *
     * @return
     */
    public static List<StaffList> getStaffList() {
        List<StaffList> staffsList = new ArrayList<StaffList>();
        String list = PreferenceUtils.getString(SettingItems.STAFFSLIST);
        if (!TextUtils.isEmpty(list)) {
            staffsList = gson.fromJson(list, new TypeToken<List<StaffList>>() {
            }.getType());
            return staffsList;
        }
        return staffsList;
    }

    /**
     * 保存provincialList
     *
     * @param cityList
     */
    public static void putCityList(String cityList) {

        String list = "";
        if (!TextUtils.isEmpty(cityList)) {
            list = cityList;
        }
        PreferenceUtils.putString(SettingItems.CITYLIST, list);
    }

    /**
     * 获取provincialList
     *
     * @return
     */
    public static String getCityList() {

        String provincialList = PreferenceUtils.getString(SettingItems.CITYLIST);
        if (!TextUtils.isEmpty(provincialList)) {
            return provincialList;
        }
        return "";
    }


    /**
     * 保存JobList
     *
     * @param job
     */
    public static void putJobList(String job) {

        String list = "";
        if (!TextUtils.isEmpty(job)) {
            list = job;
        }
        PreferenceUtils.putString(SettingItems.JOBLIST, list);
    }


    /**
     * 获取JobList
     *
     * @return
     */
    public static String getJobList() {

        String jobList = PreferenceUtils.getString(SettingItems.JOBLIST);
        if (!TextUtils.isEmpty(jobList)) {
            return jobList;
        }
        return "";
    }

    /**
     * 保存cityList
     *
     * @param cityList
     */
    public static void putHotCityList(String cityList) {

        String list = "";
        if (!TextUtils.isEmpty(cityList)) {
            list = cityList;
        }
        PreferenceUtils.putString(SettingItems.HOTLIST, list);
    }

    /**
     * 获取cityList
     *
     * @return
     */
    public static String getHotCityList() {

        String provincialList = PreferenceUtils.getString(SettingItems.HOTLIST);
        if (!TextUtils.isEmpty(provincialList)) {
            return provincialList;
        }
        return "";
    }


    /**
     * 保存Industry
     *
     * @param Industry
     */
    public static void putIndustry(String Industry) {

        String list = "";
        if (!TextUtils.isEmpty(Industry)) {
            list = Industry;
        }
        PreferenceUtils.putString(SettingItems.INDUSTRY, list);
    }

    /**
     * 获取Industry
     *
     * @return
     */
    public static String getIndustry() {

        String provincialList = PreferenceUtils.getString(SettingItems.INDUSTRY);
        if (!TextUtils.isEmpty(provincialList)) {
            return provincialList;
        }
        return "";
    }


    /**
     * 保存provincialList
     *
     * @param provincialList
     */
    public static void putProvincialList(String provincialList) {

        String list = "";
        if (!TextUtils.isEmpty(provincialList)) {
            list = provincialList;
        }
        PreferenceUtils.putString(SettingItems.PROVINCIALLIST, list);
    }

    /**
     * 获取provincialList
     *
     * @return
     */
    public static String getProvincialList() {

        String provincialList = PreferenceUtils.getString(SettingItems.PROVINCIALLIST);
        if (!TextUtils.isEmpty(provincialList)) {
            return provincialList;
        }
        return "";
    }


    /**
     * 保存Pass_Id
     *
     * @param
     */
    public static void putUserId(String pass_Id) {

        String eSessionId = "";
        if (!TextUtils.isEmpty(pass_Id)) {
            eSessionId = pass_Id;
        }
        PreferenceUtils.putString(SettingItems.USER_ID, eSessionId);
    }

    /**
     * 获取userid
     *
     * @return
     */
    public static String getUserId() {

        String pass_Id = PreferenceUtils.getString(SettingItems.USER_ID);
        if (!TextUtils.isEmpty(pass_Id)) {
            return pass_Id;
        }
        return "";
    }

    /**
     * 保存openid_easemob
     *
     * @param openid_easemob
     */
    public static void putOpenid_easemob(String openid_easemob) {

        String eSessionId = "";
        if (!TextUtils.isEmpty(openid_easemob)) {
            eSessionId = openid_easemob;
        }
        PreferenceUtils.putString(SettingItems.OPENID_EASEMOB, eSessionId);
    }

    /**
     * 获取openid_easemob
     *
     * @return
     */
    public static String getOpenid_easemob() {

        String openid_easemob = PreferenceUtils.getString(SettingItems.OPENID_EASEMOB);
        if (!TextUtils.isEmpty(openid_easemob)) {
            return openid_easemob;
        }
        return "";
    }

    /**
     * 保存Pass_Id
     *
     * @param pass_Id
     */
    public static void putPass_Id(String pass_Id) {

        String eSessionId = "";
        if (!TextUtils.isEmpty(pass_Id)) {
            eSessionId = pass_Id;
        }
        PreferenceUtils.putString(SettingItems.PASS_ID, eSessionId);
    }

    /**
     * 获取Pass_Id
     *
     * @return
     */
    public static String getPass_Id() {

        String pass_Id = PreferenceUtils.getString(SettingItems.PASS_ID);
        if (!TextUtils.isEmpty(pass_Id)) {
            return pass_Id;
        }
        return "";
    }


    /**
     * 保存Pass_Id
     *
     * @param testPaper
     */
    public static void putTestPaper(String testPaper) {

        String testpaper = "";
        if (!TextUtils.isEmpty(testPaper)) {
            testpaper = testPaper;
        }
        PreferenceUtils.putString(SettingItems.TESTPAPER, testpaper);
    }

    /**
     * 获取testpaper
     *
     * @return
     */
    public static String getTestPaper() {

        String testpaper = PreferenceUtils.getString(SettingItems.TESTPAPER);
        if (!TextUtils.isEmpty(testpaper)) {
            return testpaper;
        }
        return "";
    }

    /**
     * 保存token
     *
     * @param pass_Id
     */
    public static void putToken(String pass_Id) {

        String token = "";
        if (!TextUtils.isEmpty(pass_Id)) {
            token = pass_Id;
        }
        PreferenceUtils.putString(SettingItems.TOKEN, token);
    }

    public static void setHttpRequestHead(String token) {
        OkHttpUtil okHttpUtil = OkHttpUtil.getInstance();
        if (!TextUtils.isEmpty(token)) {
            okHttpUtil.setCommentHeader(HttpHeadUtils.KEY_TOKEN, token);
        }
    }

    /**
     * 获取token
     *
     * @return
     */
    public static String getToken() {

        String token = PreferenceUtils.getString(SettingItems.TOKEN);
        if (token != null) {
            int lnt = token.length();
            if (lnt == 0) {
                return "";
            }
        } else {
            token = "";
        }
        return token;
    }

    /**
     * 保存sessionId
     *
     * @param sessionId
     */
    public static void putSessionId(String sessionId) {

        String eSessionId = "";
        if (!TextUtils.isEmpty(sessionId)) {
            eSessionId = sessionId;
        }
        PreferenceUtils.putString(SettingItems.SESSION_ID, eSessionId);
    }

    /**
     * 获取sessionId
     *
     * @return
     */
    public static String getSessionId() {

        String sessionId = PreferenceUtils.getString(SettingItems.SESSION_ID);
        if (!TextUtils.isEmpty(sessionId)) {
            return sessionId;
        }
        return "";
    }

    /**
     * 保存sessionId
     *
     * @param sessionId
     */
    public static void putFansucenterSessionId(String sessionId) {

        String eSessionId = "";
        if (!TextUtils.isEmpty(sessionId)) {
            eSessionId = sessionId;
        }
        PreferenceUtils.putString(SettingItems.FANSUCENTER_SESSION_ID, eSessionId);
    }

    /**
     * 获取sessionId
     *
     * @return
     */
    public static String getFansucenterSessionId() {

        String sessionId = PreferenceUtils.getString(SettingItems.FANSUCENTER_SESSION_ID);
        if (!TextUtils.isEmpty(sessionId)) {
            return sessionId;
        }
        return "";
    }


    /**
     * 保存FansAppApiSessionId
     *
     * @param sessionId
     */
    public static void putFansAppApiSessionId(String sessionId) {

        String eSessionId = "";
        if (!TextUtils.isEmpty(sessionId)) {
            eSessionId = sessionId;
        }
        PreferenceUtils.putString(SettingItems.FANSAPPAPISESSIONID, eSessionId);
    }

    /**
     * 获取FansAppApiSessionId
     *
     * @return
     */
    public static String getFansAppApiSessionId() {

        String sessionId = PreferenceUtils.getString(SettingItems.FANSAPPAPISESSIONID);
        if (!TextUtils.isEmpty(sessionId)) {
            return sessionId;
        }
        return "";
    }


    /**
     * 保存仓储id
     *
     * @param storageId
     */
    public static void putStorageId(String storageId) {

        String estorageId = "";
        if (!TextUtils.isEmpty(storageId)) {
            estorageId = storageId;
        }
        PreferenceUtils.putString(SettingItems.STORAGE_ID, estorageId);
    }

    /**
     * 获取仓储id
     *
     * @return
     */
    public static String getStorageId() {

        String storageId = PreferenceUtils.getString(SettingItems.STORAGE_ID);
        if (!TextUtils.isEmpty(storageId)) {
            return storageId;
        }
        return "";
    }

    /**
     * 头像url
     *
     * @param avatarUrl
     */
    public static void putAvatar(String avatarUrl) {

        String avatar = "";
        if (!TextUtils.isEmpty(avatarUrl)) {
            avatar = avatarUrl;
        }
        PreferenceUtils.putString(SettingItems.Avatar, avatar);
    }

    /**
     * 头像
     *
     * @return
     */
    public static String getAvatar() {

        String avatar = PreferenceUtils.getString(SettingItems.Avatar);
        if (!TextUtils.isEmpty(avatar)) {
            return avatar;
        }
        return "";
    }

    /**
     * 头像url
     *
     * @param record
     */
    public static void putRecord(String record) {

        String content = "";
        if (!TextUtils.isEmpty(record)) {
            content = record;
        }
        PreferenceUtils.putString(SettingItems.HISTORICAL_RECORDS, content);
    }

    /**
     * 头像
     *
     * @return
     */
    public static String getRecord() {

        String record = PreferenceUtils.getString(SettingItems.HISTORICAL_RECORDS);
        if (!TextUtils.isEmpty(record)) {
            return record;
        }
        return "";
    }

    /**
     * 保存customerId
     *
     * @param customerId
     */
    public static void putCustomerId(String customerId) {

        String eSessionId = "";
        if (!TextUtils.isEmpty(customerId)) {
            eSessionId = customerId;
        }
        PreferenceUtils.putString(SettingItems.CUSTOMER_ID, eSessionId);
    }

    //保存游客id
    public static void putVisitorsId(String visitorsId) {

        String eSessionId = "";
        if (!TextUtils.isEmpty(visitorsId)) {
            eSessionId = visitorsId;
        }
        PreferenceUtils.putString(SettingItems.VISITORS_ID, eSessionId);
    }

    /**
     * 保存nickName
     *
     * @param //customerId
     */
    public static void putNickName(String NICKNAME) {

        if (!TextUtils.isEmpty(NICKNAME)) {

            PreferenceUtils.putString(SettingItems.NICKNAME, NICKNAME);
        }
    }

    /**
     * 保存手机号
     *
     * @param //customerId
     */
    public static void putPhoneNumber(String phoneNumber) {

        if (!TextUtils.isEmpty(phoneNumber)) {

            PreferenceUtils.putString(SettingItems.PHONENUMBER, phoneNumber);
        }
    }

    /**
     * 保存当前登录角色
     *
     * roleInfo 10:个人,20企业
     */
    public static void putRole(String roleInfo) {

        if (!TextUtils.isEmpty(roleInfo)) {
            PreferenceUtils.putString(SettingItems.KEY_ROLE_INFO, roleInfo);
        }
    }

    /**
     * 获取登录角色
     *
     * @return
     */
    public static String getRoleInfo() {

        String role = PreferenceUtils.getString(SettingItems.KEY_ROLE_INFO);
        if (!TextUtils.isEmpty(role)) {
            return role;
        }
        return "";
    }

    /**
     *
     *
     * @param //用户登录类型
     */

    public static void putLoginType(String type) {

        if (!TextUtils.isEmpty(type)) {

            PreferenceUtils.putString(SettingItems.LOGIN_TYPE, type);
        }
    }

    /**
     * 获取nickName
     *
     * @return
     */
    public static String getNickName() {

        String nickName = PreferenceUtils.getString(SettingItems.NICKNAME);
        if (!TextUtils.isEmpty(nickName)) {
            return nickName;
        }
        return "";
    }

    public static String getLonginType() {

        String type = PreferenceUtils.getString(SettingItems.LOGIN_TYPE);
        if (!TextUtils.isEmpty(type)) {
            return type;
        }
        return "";
    }


    public static String getNickName2() {

        String nickName = PreferenceUtils.getString2(SettingItems.NICKNAME);
        if (!TextUtils.isEmpty(nickName)) {
            return nickName;
        }
        return "";
    }

    /**
     * 获取手机号
     *
     * @return
     */
    public static String getPhoneNumber() {

        String phoneNumber = PreferenceUtils.getString(SettingItems.PHONENUMBER);
        if (!TextUtils.isEmpty(phoneNumber)) {
            return phoneNumber;
        }
        return "";
    }

    /**
     * 获取customerId
     *
     * @return
     */
    public static String getCustomerId() {

        String sessionId = PreferenceUtils.getString(SettingItems.CUSTOMER_ID);
        if (!TextUtils.isEmpty(sessionId)) {
            return sessionId;
        }
        return "";
    }

    /**
     * 获取游客id
     *
     * @return
     */
    public static String getVisitorsId() {
        String visitorsId = PreferenceUtils.getString(SettingItems.VISITORS_ID);
        if (!TextUtils.isEmpty(visitorsId)) {
            return visitorsId;
        }
        return "";
    }

    /**
     * 获取customerId 没有缓存功能
     *
     * @return
     */
    public static String getCustomerId2() {

        String sessionId = PreferenceUtils.getString2(SettingItems.CUSTOMER_ID);
        if (!TextUtils.isEmpty(sessionId)) {
            return sessionId;
        }
        return "";
    }

    /**
     * 保存socketIp
     *
     * @param //customerId
     */
    public static void putSocketIP(String socketIp) {

        String eSocketIp = "";
        if (!TextUtils.isEmpty(socketIp)) {
            eSocketIp = socketIp;
        }
        PreferenceUtils.putString(SettingItems.SOCKET_IP, eSocketIp);
    }

    /**
     * 保存socketPort
     *
     * @param //customerId
     */
    public static void putSocketPort(String socketPort) {

        String eSocketPort = "";
        if (!TextUtils.isEmpty(socketPort)) {
            eSocketPort = socketPort;
        }
        PreferenceUtils.putString(SettingItems.SOCKET_PORT, eSocketPort);
    }

    /**
     * 保存experience
     *
     * @param experience
     */
    public static void putIncreaseExpericeAfterLogin(String experience) {

        String mExperience = "";
        if (!TextUtils.isEmpty(experience)) {
            mExperience = experience;
        }
        PreferenceUtils.putString(SettingItems.EXPERIENCE, mExperience);
    }


    /**
     * 保存signStatus
     *
     * @param signStatus
     */
    public static void putSignStatusAfterLogin(String signStatus) {

        String mSignStatus = "";
        if (!TextUtils.isEmpty(signStatus)) {
            mSignStatus = signStatus;
        }
        PreferenceUtils.putString(SettingItems.SIGNSTATUS, mSignStatus);
    }

    /**
     * 获取SIGNSTATUS
     *
     * @return
     */
    public static String getSignStatus() {

        String signStatus = PreferenceUtils.getString(SettingItems.SIGNSTATUS);
        if (!TextUtils.isEmpty(signStatus)) {
            return signStatus;
        }
        return "";
    }

    /**
     * 获取Experice
     *
     * @return
     */
    public static String getIncreaseExpericeAfterLogin() {

        String experice = PreferenceUtils.getString(SettingItems.EXPERIENCE);
        if (!TextUtils.isEmpty(experice)) {
            return experice;
        }
        return "";
    }


    /**
     * 获取socketIp
     *
     * @return
     */
    public static String getSocketIP() {

        String ip = PreferenceUtils.getString2(SettingItems.SOCKET_IP);
        if (!TextUtils.isEmpty(ip)) {
            return ip;
        }
        return "";
    }

    /**
     * 获取socketPort
     *
     * @return
     */
    public static String getSocketPort() {

        String port = PreferenceUtils.getString2(SettingItems.SOCKET_PORT);
        if (!TextUtils.isEmpty(port)) {
            return port;
        }
        return "";
    }


    // /**
    // * 对用户名进行3DES加密后保存
    // * @param email
    // */
    // public static void putUsername(String username){
    //
    // String mUsername = "";
    // if(!TextUtils.isEmpty(username)){

    // }
    // PreferenceUtils.putString(SettingItems.USER_NAME,mUsername);
    // }
    //
    // /**
    // * 解密并获取用户名
    // * @return
    // */
    // public static String getUsername(){
    //
    // String email = PreferenceUtils.getString(SettingItems.USER_NAME);
    // if(!TextUtils.isEmpty(email)){

    // }
    // return email;
    //
    // }

    /**
     * 保存是否第一次安装启动信息
     *
     * @param isFirstStart
     */
    public static void putIsFirstStart(boolean isFirstStart) {
        PreferenceUtils.putBoolean(SettingItems.IS_FIRST_START, isFirstStart);
    }

    /**
     * 是否第一次安装启动
     *
     * @return
     */
    public static boolean isFirstStart() {
        return PreferenceUtils.getBoolean(SettingItems.IS_FIRST_START, true);
    }

    public static void putIsShoudShowGuid(boolean isShoudShowGuid) {
        PreferenceUtils.putBoolean(SettingItems.IS_SHOULD_SHOW_GUID, isShoudShowGuid);
    }

    /**
     * 是否必须显示遮罩引导界面
     *
     * @return
     */
    public static boolean isShoudShowGuid() {

        return PreferenceUtils.getBoolean(SettingItems.IS_SHOULD_SHOW_GUID, true);
    }


    public static void putIsShoudShowGuidPhoto(boolean isShoudShowGuid) {
        PreferenceUtils.putBoolean(SettingItems.IS_SHOULD_SHOW_GUID_PHOTO, isShoudShowGuid);
    }

    public static boolean isShoudShowGuidPhoto() {

        return PreferenceUtils.getBoolean(SettingItems.IS_SHOULD_SHOW_GUID_PHOTO, true);
    }


    public static void putIsShoudShowGuidGroup(boolean isShoudShowGuid) {
        PreferenceUtils.putBoolean(SettingItems.IS_SHOULD_SHOW_GUID_COMMERCIAL_GROUP, isShoudShowGuid);
    }

    public static boolean isShoudShowGuidGroup() {

        return PreferenceUtils.getBoolean(SettingItems.IS_SHOULD_SHOW_GUID_COMMERCIAL_GROUP, true);
    }


    /**
     * 保存当前版本
     *
     * @param oldVersion
     */
    public static void putOldVersion(String oldVersion) {

        String mOldVersion = "";
        if (!TextUtils.isEmpty(oldVersion)) {
            mOldVersion = oldVersion;
        }

        PreferenceUtils.putString(SettingItems.OLD_VERSION, mOldVersion);
    }

    /**
     * 获取老版本
     *
     * @return
     */
    public static String getOldVersion() {
        String oldVersion = PreferenceUtils.getString(SettingItems.OLD_VERSION);
        if (!TextUtils.isEmpty(oldVersion)) {
            return oldVersion;
        }
        return "";
    }

    public static void putUserphone(String phone) {

        String ePhone = "";
        if (!TextUtils.isEmpty(phone)) {
            ePhone = phone;
        }
        PreferenceUtils.putString(SettingItems.USER_PHONE, ePhone);
    }

    /**
     * 获取sessionId
     *
     * @return
     */
    public static String getUserPhone() {
        String userPhone = PreferenceUtils.getString(SettingItems.USER_PHONE);
        if (!TextUtils.isEmpty(userPhone)) {
            return userPhone;
        }
        return "";
    }

    /**
     * 保存是否今天签到
     *
     * @param //isFirstStart
     */
    public static void putIsQianDao(boolean isQianDao) {
        PreferenceUtils.putBoolean(SettingItems.IS_TODAY_QIAN_DAO, isQianDao);
    }

    /**
     * 是否今天已经签到
     *
     * @return
     */
    public static boolean isQianDao() {
        return PreferenceUtils
                .getBoolean(SettingItems.IS_TODAY_QIAN_DAO, false);
    }


    /**
     * /**
     * 点赞数量清0
     */
    public static void clearNewPraise() {
        PreferenceUtils.putInt(SettingItems.NEW_PRAISE, 0);
    }

    /**
     * 保存点赞数
     *
     * @param number
     */
    public static void putNewPraise(int number) {
        PreferenceUtils.putInt(SettingItems.NEW_PRAISE, number);
    }

    /**
     * 拿到点赞或与我相关数量
     *
     * @return
     */
    public static int getNewPraise() {
        return PreferenceUtils.getInt2(SettingItems.NEW_PRAISE, 0);
    }

    /**
     * 点赞或评论数量加1
     */
    public static void addNewPraise() {
        int number = PreferenceUtils.getInt(SettingItems.NEW_PRAISE, 0);
        number++;
        PreferenceUtils.putInt(SettingItems.NEW_PRAISE, number);
    }


    /**
     *
     */
    public static void putHasSendTodayInCityGroup() {

        Date date = new Date();
        PreferenceUtils.putLong(SettingItems.CURRENT_TIMES_CITY_GTOUP, date.getTime());
    }


    /**
     *
     */
    public static void putHasSendTodayInCommunityGroup() {

        Date date = new Date();
        PreferenceUtils.putLong(SettingItems.CURRENT_TIMES_COMMUNITY_GROUP, date.getTime());
    }

    /**
     * 别处登录的dialog是否已经显示
     *
     * @return
     */
    public static boolean isOtherLoginShow() {

        return false;
    }

    /**
     * 保存密码
     *
     * @param password
     * @param isEncrypt 是否是保存加密的还是原始的 如果是加密的则不需要再加密 否则进行加密保存
     */
    public static void putPassword(String password, boolean isEncrypt) {

        String mPassword = "";
        if (!TextUtils.isEmpty(password)) {
            if (isEncrypt) {
                mPassword = password;
            } else {
                mPassword = MD5Utils.getMd5Value(password);
            }
        }
        PreferenceUtils.putString(SettingItems.PASSWORD, mPassword);
    }

    /**
     * 获取密码
     *
     * @return
     */
    public static String getPassword() {
        String password = PreferenceUtils.getString(SettingItems.PASSWORD);
        if (password == null) {
            return "";
        } else {
            return password;
        }
    }

    /**
     * 账号是否创建过卡牌基础信息
     */
    public static boolean createCardBefore(){
        String key = getUserPhone() + getRoleInfo();
       // return true;
      return PreferenceUtils.getBoolean(key);
    }

    /**
     * 存储账号创建卡牌基本信息
     */

    public static void saveCreateCard(){
        String key = getUserPhone() + getRoleInfo();
        PreferenceUtils.putBoolean(key, true);
    }

}
