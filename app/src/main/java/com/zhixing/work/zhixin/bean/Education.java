package com.zhixing.work.zhixin.bean;

/**
 * Created by Administrator on 2018/5/16.
 */

public class Education {

    public Education() {
        super();
    }

    public Education(String education, String school, String startDate, String endDate, String major) {
        Education = education;
        School = school;
        StartDate = startDate;
        EndDate = endDate;
        Major = major;
    }

    public String getEducation() {
        return Education;
    }

    public void setEducation(String education) {
        Education = education;
    }

    public String getSchool() {
        return School;
    }

    public void setSchool(String school) {
        School = school;
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

    public String getMajor() {
        return Major;
    }

    public void setMajor(String major) {
        Major = major;
    }

    private String Education;
    private String  School;
    private String  StartDate;
    private String  EndDate;
    private String  Major;
}
