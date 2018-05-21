package com.zhixing.work.zhixin.bean;

/**
 * Created by Administrator on 2018/5/17.
 */

public class Work {
    public Work(String companyName, String startDate, String endDate, String postOfDuty, String department, String jobContent) {
        CompanyName = companyName;
        StartDate = startDate;
        EndDate = endDate;
        PostOfDuty = postOfDuty;
        Department = department;
        JobContent = jobContent;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getPostOfDuty() {
        return PostOfDuty;
    }

    public void setPostOfDuty(String postOfDuty) {
        PostOfDuty = postOfDuty;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getJobContent() {
        return JobContent;
    }

    public void setJobContent(String jobContent) {
        JobContent = jobContent;
    }

    private String CompanyName;
    private String StartDate;
    private String EndDate;
    private String PostOfDuty;
    private String Department;
    private String JobContent;
}
