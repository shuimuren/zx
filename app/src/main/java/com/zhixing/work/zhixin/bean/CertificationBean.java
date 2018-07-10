package com.zhixing.work.zhixin.bean;

/**
 * Created by lhj on 2018/7/10.
 * Description:
 */

public class CertificationBean {

    /**
     * CertificateTitle : 证书名称
     * GraduationDate : 2018-01-01
     * Grade : 88
     * ImgUrl : https://www.baidu.com/img/bd_logo1.png?where=super,https://www.baidu.com/img/bd_logo1.png?where=super
     */

    private String CertificateTitle;
    private String GraduationDate;
    private String Grade;
    private String ImgUrl;

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

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String ImgUrl) {
        this.ImgUrl = ImgUrl;
    }
}
