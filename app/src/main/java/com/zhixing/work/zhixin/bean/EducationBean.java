package com.zhixing.work.zhixin.bean;

/**
 * Created by lhj on 2018/7/10.
 * Description:
 */

public class EducationBean {

    /**
     * Education : 10
     * School : 学校名称
     * GraduationDate : 2018-01-01
     * Major : 专业名称
     * ImgUrl : 111fg
     */

    private int Education;
    private String School;

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    private String EndDate;
    private String Major;
    private String ImgUrl;

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


    public String getMajor() {
        return Major;
    }

    public void setMajor(String Major) {
        this.Major = Major;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String ImgUrl) {
        this.ImgUrl = ImgUrl;
    }
}
