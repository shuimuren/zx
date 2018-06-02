package com.zhixing.work.zhixin.bean;

/**
 * Created by Administrator on 2018/5/28.
 */

public class Project {

    /**
     * ProjectName : 项目1
     * Url :
     * Role : 项目经理
     * Description : 项目描述
     * Performance : 项目业绩
     * StartDate : 2017-05-01
     * EndDate : 2018-01-01
     */

    private String ProjectName;
    private String Url;
    private String Role;
    private String Description;
    private String Performance;
    private String StartDate;
    private String EndDate;

    public Project(String projectName, String url, String role, String description, String performance, String startDate, String endDate) {
        ProjectName = projectName;
        Url = url;
        Role = role;
        Description = description;
        Performance = performance;
        StartDate = startDate;
        EndDate = endDate;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String ProjectName) {
        this.ProjectName = ProjectName;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String Role) {
        this.Role = Role;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getPerformance() {
        return Performance;
    }

    public void setPerformance(String Performance) {
        this.Performance = Performance;
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
}
