package com.zhixing.work.zhixin.msgctrl;/** * Created by lhj on 18/5/8 * 请求消息在此注册 */public class ControllerRegister {    public static void initialize() {        initUpgradeController();        initRequestController();        initShareController();        initALiYunController();    }    //阿里云图片管理    private static void initALiYunController() {        initController(ControllerId.ALI_YUN_IMAGE_MANAGER, new int[]{                MsgDef.MSG_DEF_ALI_YUN_IMAGE_UPLOAD,                MsgDef.MSG_DEF_ALI_YUN_GET_IMAGE        });    }    //升级相关在此注册    private static void initUpgradeController() {        initController(ControllerId.UPGRADE_CONTROLLER, new int[]{                MsgDef.MSG_DEF_AUTO_CHECK_UPGRADE,                MsgDef.MSG_DEF_MANUALLY_CHECK_UPGRADE        });    }    //请求相关在此注册    private static void initRequestController() {        initController(ControllerId.REQUEST_CONTROLLER, new int[]{                MsgDef.MSG_DEF_PERSONAL_AUTHENTICATES,                MsgDef.MSG_DEF_AUTHENTICATE_SUBMIT,                MsgDef.MSG_DEF_EDUCATION_AUTHENTICATE_SUBMIT,                MsgDef.MSG_DEF_CERTIFICATION_AUTHENTICATE_SUBMIT,                MsgDef.MSG_DEF_GET_EVALUATE_INFO,                MsgDef.MSG_DEF_UPDATE_EXPECTED_INFO,                MsgDef.MSG_DEF_GET_PERSONAL_CARD_BASIC_INFO,                MsgDef.MSG_DEF_LOGIN,                MsgDef.MSG_DEF_GET_VERIFICATION_CODE,                MsgDef.MSG_DEF_UPDATE_PASSWORD,                MsgDef.MSG_DEF_REGISTER,                MsgDef.MSG_DEF_TELEPHONE_USABLE,                MsgDef.MSG_DEF_COMPANY_CERTIFICATION_STATUS,                MsgDef.MSG_DEF_GET_ALL_DEPARTMENT_MEMBER,                MsgDef.MSG_DEF_DELETE_DEPARTMENT,                MsgDef.MSG_DEF_DEPARTMENT_INVITE,                MsgDef.MSG_DEF_UPDATE_DEPARTMENT,                MsgDef.MSG_DEF_GET_ALL_DEPARTMENT,                MsgDef.MSG_DEF_GET_CHILD_DEPARTMENT,                MsgDef.MSG_DEF_GET_CHILD_DEPARTMENT_MEMBER,                MsgDef.MSG_DEF_ADD_DEPARTMENT,                MsgDef.MSG_DEF_UPDATE_CHILD_DEPARTMENT,                MsgDef.MSG_DEF_GET_DEPARTMENT_SETTING_INFO        });    }    //分享相关在此注册    private static void initShareController() {        initController(ControllerId.SHARE_CONTROLLER, new int[]{                MsgDef.MSG_DEF_SHOW_SHARE_PLATFORM,                MsgDef.MSG_DEF_SHARE_TO_FRIEND,                MsgDef.MSG_DEF_SHARE_TO_TIMELINE,                MsgDef.MSG_DEF_SHARE_TO_OTHER,                MsgDef.MSG_DEF_SHARE_TO_QQ,                MsgDef.MSG_DEF_SHARE_TO_EMS,                MsgDef.MSG_DEF_SHARE_TO_ZX,        });    }    //    private static void initController(int controllerId, int[] msgIds) {        MsgDispatcher.register(controllerId, msgIds);    }}