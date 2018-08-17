package com.zhixing.work.zhixin.requestbody;

/**
 * Created by lhj on 2018/8/14.
 * Description: 编辑个人基础信息
 */

public class StaffJobBasicBody {

    private String StaffId;
    private String RealName;
    private String WorkEmail;
    private String JobType;
    private String WorkPhoneNum;
    private String WorkNumber;
    private String ExtPhoneNum;
    private String OfficeAddress;

    public String getStaffId() {
        return StaffId;
    }

    public void setStaffId(String staffId) {
        StaffId = staffId;
    }

    public String getRealName() {
        return RealName;
    }

    public void setRealName(String realName) {
        RealName = realName;
    }

    public String getWorkEmail() {
        return WorkEmail;
    }

    public void setWorkEmail(String workEmail) {
        WorkEmail = workEmail;
    }

    public String getJobType() {
        return JobType;
    }

    public void setJobType(String jobType) {
        JobType = jobType;
    }

    public String getWorkPhoneNum() {
        return WorkPhoneNum;
    }

    public void setWorkPhoneNum(String workPhoneNum) {
        WorkPhoneNum = workPhoneNum;
    }

    public String getWorkNumber() {
        return WorkNumber;
    }

    public void setWorkNumber(String workNumber) {
        WorkNumber = workNumber;
    }

    public String getExtPhoneNum() {
        return ExtPhoneNum;
    }

    public void setExtPhoneNum(String extPhoneNum) {
        ExtPhoneNum = extPhoneNum;
    }

    public String getOfficeAddress() {
        return OfficeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        OfficeAddress = officeAddress;
    }


}
