package com.zhixing.work.zhixin.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lhj on 2018/8/15.
 * Description:
 */

public class StaffJobAdvanceBean implements Serializable{
    public String getStaffId() {
        return StaffId;
    }

    public void setStaffId(String staffId) {
        StaffId = staffId;
    }

    /**
     * Nation : null
     * NativePlaceProvince : null
     * NativePlaceCity : null
     * Married : null
     * PoliticsFace : null
     * IdCard : 123456789123456789
     * HouseholdType : null
     * Province : null
     * City : null
     * District : null
     * Address : null
     * Education : 50
     * StudyAbroad : false
     * WorkBgs : [{"CompanyName":"痛","StartDate":"2018-06-07T00:00:00","EndDate":"2018-07-10T00:00:00"},{"CompanyName":"gggg","StartDate":"2015-07-12T00:00:00","EndDate":"2016-07-12T00:00:00"}]
     * EducationBgs : [{"Education":60,"School":"空","StartDate":"2018-09-07T00:00:00","EndDate":"2018-06-07T00:00:00"},{"Education":40,"School":"Klondike","StartDate":"2018-07-07T00:00:00","EndDate":"2018-04-10T00:00:00"}]
     * CertificateBgs : []
     */
    private String StaffId;
    private String Nation;
    private Integer NativePlaceProvince;
    private Integer NativePlaceCity;
    private Integer Married;
    private Integer PoliticsFace;
    private String IdCard;
    private Integer HouseholdType;
    private Integer Province;
    private Integer City;
    private Integer District;
    private String Address;
    private String Education;
    private boolean StudyAbroad;
    private List<WorkBgsBean> WorkBgs;
    private List<EducationBgsBean> EducationBgs;
    private List<CertificateBean> CertificateBgs;

    public String getNation() {
        return Nation;
    }

    public void setNation(String Nation) {
        this.Nation = Nation;
    }

    public Integer getNativePlaceProvince() {
        return NativePlaceProvince;
    }

    public void setNativePlaceProvince(Integer NativePlaceProvince) {
        this.NativePlaceProvince = NativePlaceProvince;
    }

    public Integer getNativePlaceCity() {
        return NativePlaceCity;
    }

    public void setNativePlaceCity(Integer NativePlaceCity) {
        this.NativePlaceCity = NativePlaceCity;
    }

    public Integer getMarried() {
        return Married;
    }

    public void setMarried(Integer Married) {
        this.Married = Married;
    }

    public Integer getPoliticsFace() {
        return PoliticsFace;
    }

    public void setPoliticsFace(Integer PoliticsFace) {
        this.PoliticsFace = PoliticsFace;
    }

    public String getIdCard() {
        return IdCard;
    }

    public void setIdCard(String IdCard) {
        this.IdCard = IdCard;
    }

    public Integer getHouseholdType() {
        return HouseholdType;
    }

    public void setHouseholdType(Integer HouseholdType) {
        this.HouseholdType = HouseholdType;
    }

    public Integer getProvince() {
        return Province;
    }

    public void setProvince(Integer Province) {
        this.Province = Province;
    }

    public Integer getCity() {
        return City;
    }

    public void setCity(Integer City) {
        this.City = City;
    }

    public Integer getDistrict() {
        return District;
    }

    public void setDistrict(Integer District) {
        this.District = District;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getEducation() {
        return Education;
    }

    public void setEducation(String Education) {
        this.Education = Education;
    }

    public boolean isStudyAbroad() {
        return StudyAbroad;
    }

    public void setStudyAbroad(boolean StudyAbroad) {
        this.StudyAbroad = StudyAbroad;
    }

    public List<WorkBgsBean> getWorkBgs() {
        return WorkBgs;
    }

    public void setWorkBgs(List<WorkBgsBean> WorkBgs) {
        this.WorkBgs = WorkBgs;
    }

    public List<EducationBgsBean> getEducationBgs() {
        return EducationBgs;
    }

    public void setEducationBgs(List<EducationBgsBean> EducationBgs) {
        this.EducationBgs = EducationBgs;
    }

    public List<CertificateBean> getCertificateBgs() {
        return CertificateBgs;
    }

    public void setCertificateBgs(List<CertificateBean> CertificateBgs) {
        this.CertificateBgs = CertificateBgs;
    }





}
