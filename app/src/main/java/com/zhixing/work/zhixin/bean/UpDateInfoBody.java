package com.zhixing.work.zhixin.bean;

/**
 * Created by lhj on 2018/7/16.
 * Description:
 */

public class UpDateInfoBody {

    private String  PhoneNum;
    private String Role;
    private String NewPassword;
    private String SmsCode;

    public String getPhoneNum() {
        return PhoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        PhoneNum = phoneNum;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getNewPassword() {
        return NewPassword;
    }

    public void setNewPassword(String newPassword) {
        NewPassword = newPassword;
    }

    public String getSmsCode() {
        return SmsCode;
    }

    public void setSmsCode(String smsCode) {
        SmsCode = smsCode;
    }

}
