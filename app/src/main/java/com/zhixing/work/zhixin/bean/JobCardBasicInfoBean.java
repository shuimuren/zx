package com.zhixing.work.zhixin.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lhj on 2018/8/13.
 * Description:
 */

public class JobCardBasicInfoBean implements Serializable{
    /**
     * Departments : [["XXX有限公司","技术部"],["XXX有限公司","技术部","前端"]]
     * StaffId : 7
     * RealName : wu
     * WorkEmail : null
     * JobType : null
     * WorkPhoneNum : null
     * WorkNumber : null
     * ExtPhoneNum : null
     * OfficeAddress : null
     */

    private int StaffId;
    private String RealName;
    private String WorkEmail;
    private String JobType;
    private String WorkPhoneNum;
    private String WorkNumber;
    private String ExtPhoneNum;
    private String OfficeAddress;
    private List<List<String>> Departments;

    public int getStaffId() {
        return StaffId;
    }

    public void setStaffId(int StaffId) {
        this.StaffId = StaffId;
    }

    public String getRealName() {
        return RealName;
    }

    public void setRealName(String RealName) {
        this.RealName = RealName;
    }

    public String getWorkEmail() {
        return WorkEmail;
    }

    public void setWorkEmail(String WorkEmail) {
        this.WorkEmail = WorkEmail;
    }

    public String getJobType() {
        return JobType;
    }

    public void setJobType(String JobType) {
        this.JobType = JobType;
    }

    public String getWorkPhoneNum() {
        return WorkPhoneNum;
    }

    public void setWorkPhoneNum(String WorkPhoneNum) {
        this.WorkPhoneNum = WorkPhoneNum;
    }

    public String getWorkNumber() {
        return WorkNumber;
    }

    public void setWorkNumber(String WorkNumber) {
        this.WorkNumber = WorkNumber;
    }

    public String getExtPhoneNum() {
        return ExtPhoneNum;
    }

    public void setExtPhoneNum(String ExtPhoneNum) {
        this.ExtPhoneNum = ExtPhoneNum;
    }

    public String getOfficeAddress() {
        return OfficeAddress;
    }

    public void setOfficeAddress(String OfficeAddress) {
        this.OfficeAddress = OfficeAddress;
    }

    public List<List<String>> getDepartments() {
        return Departments;
    }

    public void setDepartments(List<List<String>> Departments) {
        this.Departments = Departments;
    }

}
