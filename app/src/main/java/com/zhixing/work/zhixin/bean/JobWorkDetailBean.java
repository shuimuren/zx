package com.zhixing.work.zhixin.bean;

/**
 * Created by lhj on 2018/8/16.
 * Description:
 */

public class JobWorkDetailBean {


    /**
     * Id : 1
     * StaffId : 7
     * CompanyName : 公司1
     * StartDate : 2010-01-01 00:00:00
     * EndDate : 2013-01-01 00:00:00
     * PostOfDuty : 工作岗位
     * Department : 所属部门
     * JobContent : 工作内容
     */

    private String Id;
    private String StaffId;
    private String CompanyName;
    private String StartDate;
    private String EndDate;
    private String PostOfDuty;
    private String Department;
    private String JobContent;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getStaffId() {
        return StaffId;
    }

    public void setStaffId(String StaffId) {
        this.StaffId = StaffId;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String CompanyName) {
        this.CompanyName = CompanyName;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String StartDate) {
        this.StartDate = StartDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String EndDate) {
        this.EndDate = EndDate;
    }

    public String getPostOfDuty() {
        return PostOfDuty;
    }

    public void setPostOfDuty(String PostOfDuty) {
        this.PostOfDuty = PostOfDuty;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String Department) {
        this.Department = Department;
    }

    public String getJobContent() {
        return JobContent;
    }

    public void setJobContent(String JobContent) {
        this.JobContent = JobContent;
    }
}
