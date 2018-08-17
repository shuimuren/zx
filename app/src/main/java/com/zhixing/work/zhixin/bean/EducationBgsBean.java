package com.zhixing.work.zhixin.bean;

import java.io.Serializable;

/**
 * Created by lhj on 2018/8/15.
 * Description:
 */

public class EducationBgsBean implements Serializable{
    /**
     * Education : 60
     * School : 空
     * StartDate : 2018-09-07T00:00:00
     * EndDate : 2018-06-07T00:00:00
     */

    private int Education;
    private String School;
    private String StartDate;
    private String EndDate;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getEducation() {
        return Education;
    }

    public void setEducation(int Education) {
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
}
