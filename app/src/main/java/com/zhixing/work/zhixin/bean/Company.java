package com.zhixing.work.zhixin.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/6/8.
 */

public class Company implements Serializable{


    /**
     * FullName : 职信(深圳)科技有限公司
     * Id : 1
     * Logo :
     * ShortName :
     * Province : 1
     * City : 2
     * District : 3
     * Address : 公司地址
     * NatureOfUnit :
     * IndustryTypeId :
     * FinancingStage :
     * StaffSize :
     * WebsiteUrl :
     */

    private String FullName;
    private int Id;
    private String Logo;
    private String ShortName;
    private Integer Province;
    private Integer City;
    private Integer District;
    private String Address;
    private String NatureOfUnit;
    private String IndustryTypeId;
    private String FinancingStage;
    private String StaffSize;
    private String WebsiteUrl;

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String Logo) {
        this.Logo = Logo;
    }

    public String getShortName() {
        return ShortName;
    }

    public void setShortName(String ShortName) {
        this.ShortName = ShortName;
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

    public String getNatureOfUnit() {
        return NatureOfUnit;
    }

    public void setNatureOfUnit(String NatureOfUnit) {
        this.NatureOfUnit = NatureOfUnit;
    }

    public String getIndustryTypeId() {
        return IndustryTypeId;
    }

    public void setIndustryTypeId(String IndustryTypeId) {
        this.IndustryTypeId = IndustryTypeId;
    }

    public String getFinancingStage() {
        return FinancingStage;
    }

    public void setFinancingStage(String FinancingStage) {
        this.FinancingStage = FinancingStage;
    }

    public String getStaffSize() {
        return StaffSize;
    }

    public void setStaffSize(String StaffSize) {
        this.StaffSize = StaffSize;
    }

    public String getWebsiteUrl() {
        return WebsiteUrl;
    }

    public void setWebsiteUrl(String WebsiteUrl) {
        this.WebsiteUrl = WebsiteUrl;
    }
}
