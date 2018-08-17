package com.zhixing.work.zhixin.bean;

import java.io.Serializable;

/**
 * Created by lhj on 2018/8/15.
 * Description:
 */

public class CertificateBean implements Serializable{
    /**
     * CertificateTitle : 证书名称1
     * GraduationDate : 2011-01-01
     * Grade :
     */

    private String CertificateTitle;
    private String GraduationDate;
    private String Grade;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
