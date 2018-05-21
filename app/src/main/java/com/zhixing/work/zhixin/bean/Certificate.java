package com.zhixing.work.zhixin.bean;

/**
 * Created by Administrator on 2018/5/16.
 */

public class Certificate {
    public Certificate(String certificateTitle, String graduationDate, String grade) {
        CertificateTitle = certificateTitle;
        GraduationDate = graduationDate;
        Grade = grade;
    }

    public String getCertificateTitle() {
        return CertificateTitle;
    }

    public void setCertificateTitle(String certificateTitle) {
        CertificateTitle = certificateTitle;
    }

    public String getGraduationDate() {
        return GraduationDate;
    }

    public void setGraduationDate(String graduationDate) {
        GraduationDate = graduationDate;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }

    private String CertificateTitle;
    private String GraduationDate;
    private String Grade;
}
