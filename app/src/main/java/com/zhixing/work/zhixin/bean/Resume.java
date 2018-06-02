package com.zhixing.work.zhixin.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/25.
 */

public class Resume implements Serializable {


    /**
     * Id : 1
     * Avatar : personalPortrait/5b689874-b200-43a4-847c-a983cc5de92d
     * TotalScore : 62.0
     * JobHuntingStatus : 0
     * SelfIntro : 1234567899876543210789456123
     * StartSalary : 0
     * EndSalary : 0
     * HiddenResume : true
     * HiddenEvaluate : true
     * PersonalInfo : {"RealName":"刚刚好","FirstWorkTime":"2008-08-08 00:00:00","Birthday":"2014-05-28 00:00:00"}
     * WrokBackgroundOutputs : [{"Id":9,"CompanyName":"深圳广告科技有限公司","StartDate":"2002-05-01 00:00:00","EndDate":"2010-05-01 00:00:00","PostOfDuty":"刚刚好","Department":"会解决","JobContent":"乖宝宝内牛满面","Show":true}]
     * EducationOutputs : [{"Id":34,"School":"华南理工大学","Education":40,"StartDate":"2015-05-01 00:00:00","EndDate":"2018-05-01 00:00:00","Major":"广告","Experience":null,"Show":false},{"Id":32,"School":"深圳大学","Education":40,"StartDate":"2010-09-01 00:00:00","EndDate":"2014-06-01 00:00:00","Major":"哼哼唧唧","Experience":null,"Show":true},{"Id":33,"School":"湖南大学","Education":50,"StartDate":"1999-05-25 00:00:00","EndDate":"2014-05-25 00:00:00","Major":"甲骨文","Experience":null,"Show":true}]
     * ProjectBackgroundOutputs : [{"Id":1,"ProjectName":"职业","Url":"www.baidu.com","Role":"哼哼唧唧","Description":"从VB不能","Performance":"V法国会解决","StartDate":"2015-05-28 00:00:00","EndDate":"2018-05-28 00:00:00","Show":false},{"Id":2,"ProjectName":"哈哈哈","Url":"会解决","Role":"会解决","Description":"成功后","Performance":"hhjjjjj换个方法","StartDate":"2006-05-28 00:00:00","EndDate":"2014-05-28 00:00:00","Show":true}]
     * CertificateOutputs : [{"Id":13,"CertificateTitle":"vhj","GraduationDate":"2018-05-28 00:00:00","Grade":"","Show":true},{"Id":12,"CertificateTitle":"吃鸡","GraduationDate":"2012-05-28 00:00:00","Grade":"77","Show":true}]
     */

    private int Id;
    private String Avatar;
    private double TotalScore;
    private int JobHuntingStatus;
    private String SelfIntro;
    private Integer StartSalary;
    private Integer EndSalary;
    private boolean HiddenResume;
    private boolean HiddenEvaluate;
    private PersonalInfoBean PersonalInfo;
    private List<WrokBackgroundOutputsBean> WrokBackgroundOutputs;
    private List<EducationOutputsBean> EducationOutputs;
    private List<ProjectBackgroundOutputsBean> ProjectBackgroundOutputs;
    private List<CertificateOutputsBean> CertificateOutputs;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String Avatar) {
        this.Avatar = Avatar;
    }

    public double getTotalScore() {
        return TotalScore;
    }

    public void setTotalScore(double TotalScore) {
        this.TotalScore = TotalScore;
    }

    public int getJobHuntingStatus() {
        return JobHuntingStatus;
    }

    public void setJobHuntingStatus(int JobHuntingStatus) {
        this.JobHuntingStatus = JobHuntingStatus;
    }

    public String getSelfIntro() {
        return SelfIntro;
    }

    public void setSelfIntro(String SelfIntro) {
        this.SelfIntro = SelfIntro;
    }

    public Integer getStartSalary() {
        return StartSalary;
    }

    public void setStartSalary(Integer StartSalary) {
        this.StartSalary = StartSalary;
    }

    public Integer getEndSalary() {
        return EndSalary;
    }

    public void setEndSalary(Integer EndSalary) {
        this.EndSalary = EndSalary;
    }

    public boolean isHiddenResume() {
        return HiddenResume;
    }

    public void setHiddenResume(boolean HiddenResume) {
        this.HiddenResume = HiddenResume;
    }

    public boolean isHiddenEvaluate() {
        return HiddenEvaluate;
    }

    public void setHiddenEvaluate(boolean HiddenEvaluate) {
        this.HiddenEvaluate = HiddenEvaluate;
    }

    public PersonalInfoBean getPersonalInfo() {
        return PersonalInfo;
    }

    public void setPersonalInfo(PersonalInfoBean PersonalInfo) {
        this.PersonalInfo = PersonalInfo;
    }

    public List<WrokBackgroundOutputsBean> getWrokBackgroundOutputs() {
        return WrokBackgroundOutputs;
    }

    public void setWrokBackgroundOutputs(List<WrokBackgroundOutputsBean> WrokBackgroundOutputs) {
        this.WrokBackgroundOutputs = WrokBackgroundOutputs;
    }

    public List<EducationOutputsBean> getEducationOutputs() {
        return EducationOutputs;
    }

    public void setEducationOutputs(List<EducationOutputsBean> EducationOutputs) {
        this.EducationOutputs = EducationOutputs;
    }

    public List<ProjectBackgroundOutputsBean> getProjectBackgroundOutputs() {
        return ProjectBackgroundOutputs;
    }

    public void setProjectBackgroundOutputs(List<ProjectBackgroundOutputsBean> ProjectBackgroundOutputs) {
        this.ProjectBackgroundOutputs = ProjectBackgroundOutputs;
    }

    public List<CertificateOutputsBean> getCertificateOutputs() {
        return CertificateOutputs;
    }

    public void setCertificateOutputs(List<CertificateOutputsBean> CertificateOutputs) {
        this.CertificateOutputs = CertificateOutputs;
    }

    public static class PersonalInfoBean  implements Serializable{
        /**
         * RealName : 刚刚好
         * FirstWorkTime : 2008-08-08 00:00:00
         * Birthday : 2014-05-28 00:00:00
         */

        private String RealName;
        private String FirstWorkTime;
        private String Birthday;

        public String getRealName() {
            return RealName;
        }

        public void setRealName(String RealName) {
            this.RealName = RealName;
        }

        public String getFirstWorkTime() {
            return FirstWorkTime;
        }

        public void setFirstWorkTime(String FirstWorkTime) {
            this.FirstWorkTime = FirstWorkTime;
        }

        public String getBirthday() {
            return Birthday;
        }

        public void setBirthday(String Birthday) {
            this.Birthday = Birthday;
        }
    }

    public static class WrokBackgroundOutputsBean  implements Serializable{
        /**
         * Id : 9
         * CompanyName : 深圳广告科技有限公司
         * StartDate : 2002-05-01 00:00:00
         * EndDate : 2010-05-01 00:00:00
         * PostOfDuty : 刚刚好
         * Department : 会解决
         * JobContent : 乖宝宝内牛满面
         * Show : true
         */

        private int Id;
        private String CompanyName;
        private String StartDate;
        private String EndDate;
        private String PostOfDuty;
        private String Department;
        private String JobContent;
        private boolean Show;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
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

        public boolean isShow() {
            return Show;
        }

        public void setShow(boolean Show) {
            this.Show = Show;
        }
    }

    public static class EducationOutputsBean   implements Serializable{
        /**
         * Id : 34
         * School : 华南理工大学
         * Education : 40
         * StartDate : 2015-05-01 00:00:00
         * EndDate : 2018-05-01 00:00:00
         * Major : 广告
         * Experience : null
         * Show : false
         */

        private int Id;
        private String School;
        private int Education;
        private String StartDate;
        private String EndDate;
        private String Major;
        private String Experience;
        private boolean Show;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getSchool() {
            return School;
        }

        public void setSchool(String School) {
            this.School = School;
        }

        public int getEducation() {
            return Education;
        }

        public void setEducation(int Education) {
            this.Education = Education;
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

        public boolean isShow() {
            return Show;
        }

        public void setShow(boolean Show) {
            this.Show = Show;
        }
    }

    public static class ProjectBackgroundOutputsBean   implements Serializable {
        /**
         * Id : 1
         * ProjectName : 职业
         * Url : www.baidu.com
         * Role : 哼哼唧唧
         * Description : 从VB不能
         * Performance : V法国会解决
         * StartDate : 2015-05-28 00:00:00
         * EndDate : 2018-05-28 00:00:00
         * Show : false
         */

        private int Id;
        private String ProjectName;
        private String Url;
        private String Role;
        private String Description;
        private String Performance;
        private String StartDate;
        private String EndDate;
        private boolean Show;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
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

        public boolean isShow() {
            return Show;
        }

        public void setShow(boolean Show) {
            this.Show = Show;
        }
    }

    public static class CertificateOutputsBean  implements Serializable{
        /**
         * Id : 13
         * CertificateTitle : vhj
         * GraduationDate : 2018-05-28 00:00:00
         * Grade :
         * Show : true
         */

        private int Id;
        private String CertificateTitle;
        private String GraduationDate;
        private String Grade;
        private boolean Show;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getCertificateTitle() {
            return CertificateTitle;
        }

        public void setCertificateTitle(String CertificateTitle) {
            this.CertificateTitle = CertificateTitle;
        }

        public String getGraduationDate() {
            return GraduationDate;
        }

        public void setGraduationDate(String GraduationDate) {
            this.GraduationDate = GraduationDate;
        }

        public String getGrade() {
            return Grade;
        }

        public void setGrade(String Grade) {
            this.Grade = Grade;
        }

        public boolean isShow() {
            return Show;
        }

        public void setShow(boolean Show) {
            this.Show = Show;
        }
    }
}
