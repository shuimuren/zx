package com.zhixing.work.zhixin.bean;

/**
 * Created by lhj on 2018/8/10.
 * Description:
 */

public class NewMemberBean {

    /**
     * Id : 2
     * Avatar : null
     * RealName : wu
     * PhoneNum : 18676740875
     * AuditStatus : 0
     */

    private String Id;
    private String Avatar;
    private String RealName;
    private String PhoneNum;
    private int AuditStatus;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String Avatar) {
        this.Avatar = Avatar;
    }

    public String getRealName() {
        return RealName;
    }

    public void setRealName(String RealName) {
        this.RealName = RealName;
    }

    public String getPhoneNum() {
        return PhoneNum;
    }

    public void setPhoneNum(String PhoneNum) {
        this.PhoneNum = PhoneNum;
    }

    public int getAuditStatus() {
        return AuditStatus;
    }

    public void setAuditStatus(int AuditStatus) {
        this.AuditStatus = AuditStatus;
    }
}
