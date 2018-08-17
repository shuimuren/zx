package com.zhixing.work.zhixin.bean;

/**
 * Created by lhj on 2018/8/17.
 * Description:
 */

public class JobCertificateDetailBean {

    /**
     * Id : 1
     * StaffId : 7
     * CertificateTitle : 证书1
     * GraduationDate : 2015-01-01 00:00:00
     * Grade : null
     */

    private String Id;
    private String StaffId;
    private String CertificateTitle;
    private String GraduationDate;
    private String  Grade;

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
}
