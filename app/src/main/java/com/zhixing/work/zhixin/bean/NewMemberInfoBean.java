package com.zhixing.work.zhixin.bean;

import java.util.List;

/**
 * Created by lhj on 2018/8/10.
 * Description:
 */

public class NewMemberInfoBean {
    /**
     * Id : 2
     * RealName : wu
     * Avatar : null
     * PhoneNum : 18676740875
     * DepartmentId : 8
     * DepartmentName : ["XXX有限公司","技术部"]
     * Inviter : 主管理员
     * InviterRole : null
     * ApplyTime : 2018-05-25 15:45:40
     * AuditTime : null
     * AuditStatus : 0
     * AttendanceRules : [{"Id":1,"Name":"考勤组1"}]
     */

    private int Id;
    private String RealName;
    private String Avatar;
    private String PhoneNum;
    private int DepartmentId;
    private String Inviter;
    private String InviterRole;
    private String ApplyTime;
    private String AuditTime;
    private int AuditStatus;
    private List<String> DepartmentName;
    private List<AttendanceRulesBean> AttendanceRules;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getRealName() {
        return RealName;
    }

    public void setRealName(String RealName) {
        this.RealName = RealName;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String Avatar) {
        this.Avatar = Avatar;
    }

    public String getPhoneNum() {
        return PhoneNum;
    }

    public void setPhoneNum(String PhoneNum) {
        this.PhoneNum = PhoneNum;
    }

    public int getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(int DepartmentId) {
        this.DepartmentId = DepartmentId;
    }

    public String getInviter() {
        return Inviter;
    }

    public void setInviter(String Inviter) {
        this.Inviter = Inviter;
    }

    public String getInviterRole() {
        return InviterRole;
    }

    public void setInviterRole(String InviterRole) {
        this.InviterRole = InviterRole;
    }

    public String getApplyTime() {
        return ApplyTime;
    }

    public void setApplyTime(String ApplyTime) {
        this.ApplyTime = ApplyTime;
    }

    public String getAuditTime() {
        return AuditTime;
    }

    public void setAuditTime(String AuditTime) {
        this.AuditTime = AuditTime;
    }

    public int getAuditStatus() {
        return AuditStatus;
    }

    public void setAuditStatus(int AuditStatus) {
        this.AuditStatus = AuditStatus;
    }

    public List<String> getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(List<String> DepartmentName) {
        this.DepartmentName = DepartmentName;
    }

    public List<AttendanceRulesBean> getAttendanceRules() {
        return AttendanceRules;
    }

    public void setAttendanceRules(List<AttendanceRulesBean> AttendanceRules) {
        this.AttendanceRules = AttendanceRules;
    }

    public static class AttendanceRulesBean {
        /**
         * Id : 1
         * Name : 考勤组1
         */

        private int Id;
        private String Name;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }
    }

}
