package com.zhixing.work.zhixin.bean;

/**
 * Created by lhj on 2018/8/13.
 * Description:
 */

public class PersonalJobCardBean {
    /**
     * Id : 7
     * CompanyLogo : null
     * CardAvatar : null
     * RealName : wu
     * NickName : wu
     * JobType : null
     * DepartmentName : XXX有限公司
     * Motto : 座右铭00
     * IsDimission : true
     * EntryTime : 2018-05-30 13:43:18
     */

    private int Id;
    private String CompanyLogo;
    private String CardAvatar;
    private String RealName;
    private String NickName;
    private String JobType;
    private String DepartmentName;
    private String Motto;
    private boolean IsDimission;
    private String EntryTime;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getCompanyLogo() {
        return CompanyLogo;
    }

    public void setCompanyLogo(String CompanyLogo) {
        this.CompanyLogo = CompanyLogo;
    }

    public String getCardAvatar() {
        return CardAvatar;
    }

    public void setCardAvatar(String CardAvatar) {
        this.CardAvatar = CardAvatar;
    }

    public String getRealName() {
        return RealName;
    }

    public void setRealName(String RealName) {
        this.RealName = RealName;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String NickName) {
        this.NickName = NickName;
    }

    public String getJobType() {
        return JobType;
    }

    public void setJobType(String JobType) {
        this.JobType = JobType;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String DepartmentName) {
        this.DepartmentName = DepartmentName;
    }

    public String getMotto() {
        return Motto;
    }

    public void setMotto(String Motto) {
        this.Motto = Motto;
    }

    public boolean isIsDimission() {
        return IsDimission;
    }

    public void setIsDimission(boolean IsDimission) {
        this.IsDimission = IsDimission;
    }

    public String getEntryTime() {
        return EntryTime;
    }

    public void setEntryTime(String EntryTime) {
        this.EntryTime = EntryTime;
    }
}
