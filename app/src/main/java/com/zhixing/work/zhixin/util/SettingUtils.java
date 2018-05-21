package com.zhixing.work.zhixin.util;

import android.text.TextUtils;


import com.zhixing.work.zhixin.http.api.Constant;

import java.util.Date;

public class SettingUtils {



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


        /**
         * 未读的添加好友请求数量
         */
        public static final String ADD_FRIENDS_REQUEST_NUMBER = "add_friends_request_number";
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
        public static final String userBean = "userBean"; // 个人信息
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
     * 保存token
     *
     * @param pass_Id
     */
    public static void putToken(String pass_Id) {

        String token = "";
        if (!TextUtils.isEmpty(pass_Id)) {
            token = pass_Id;
        }
        PreferenceUtils.putString(Constant.TOKEN, token);
    }

    /**
     * 获取token
     *
     * @return
     */
    public static String getToken() {

        String token = PreferenceUtils.getString(Constant.TOKEN);
        if (token!=null){
            int lnt= token.length();
            if (lnt==0) {
                return "";
            }
        }else {
            token="";

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
     * 保存nckName
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
     * 获取未读请求添加好友数量
     *
     * @return
     */
    public static int getFriendsRequestNumber() {
        return PreferenceUtils.getInt(SettingItems.ADD_FRIENDS_REQUEST_NUMBER,
                0);
    }

    /**
     * 未读请求添加好友数量加1
     */
    public static void addFriendsRequestNumber() {
        int number = PreferenceUtils.getInt(
                SettingItems.ADD_FRIENDS_REQUEST_NUMBER, 0);
        number++;
        PreferenceUtils.putInt(SettingItems.ADD_FRIENDS_REQUEST_NUMBER, number);
    }

    /**
     * 未读请求添加好友数量清0
     */
    public static void clearFriendsRequestNumber() {
        PreferenceUtils.putInt(SettingItems.ADD_FRIENDS_REQUEST_NUMBER, 0);
    }

    /**
     * 保存未读消息数
     *
     * @param number
     */
    public static void putNoReadMessageNumber(int number) {
        PreferenceUtils.putInt(SettingItems.NO_READ_MESSAGE_NUMBER, number);
    }

    /**
     * 拿到未读消息数量
     *
     * @return
     */
    public static int getNoReadMessageNumber() {
        return PreferenceUtils.getInt(SettingItems.ADD_FRIENDS_REQUEST_NUMBER,
                0);
    }

    /**
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
}
