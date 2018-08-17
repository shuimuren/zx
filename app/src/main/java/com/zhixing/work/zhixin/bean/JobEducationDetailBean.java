package com.zhixing.work.zhixin.bean;

/**
 * Created by lhj on 2018/8/17.
 * Description:
 */

public class JobEducationDetailBean {

    /**
     * Id : 1
     * StaffId : 7
     * Education : 50
     * School : 湖南大学
     * StartDate : 1999-05-25 00:00:00
     * EndDate : 2014-05-25 00:00:00
     * Major : 甲骨文
     * Experience : null
     */

    private String Id;
    private String StaffId;
    private Integer Education;
    private String School;
    private String StartDate;
    private String EndDate;
    private String Major;
    private String Experience;

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

    public Integer getEducation() {
        return Education;
    }

    public void setEducation(Integer Education) {
        this.Education = Education;
    }

    public String getSchool() {
        return School;
    }

    public void setSchool(String School) {
        this.School = School;
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

    public String getMajor() {
        return Major;
    }

    public void setMajor(String Major) {
        this.Major = Major;
    }

    public String getExperience() {
        return Experience;
    }

    public void setExperience(String Experience) {
        this.Experience = Experience;
    }
}
