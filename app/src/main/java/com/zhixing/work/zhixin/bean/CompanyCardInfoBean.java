package com.zhixing.work.zhixin.bean;

import java.util.List;

/**
 * Created by lhj on 2018/7/24.
 * Description:
 */

public class CompanyCardInfoBean {

    /**
     * Id : 5
     * FullName : 红包
     * Province : 350
     * City : 351
     * District : 352
     * Address : 我们
     * IndustryTypeName : null
     * Logo : null
     * CompanyPic : null
     * StaffSize : null
     * Achievement : [{"Id":1011,"AchievementType":10,"GainTime":"2018-07-11 11:17:27"}]
     * BusinessLicenseStatus : 0
     * ManagerIdCardStatus : 0
     */

    private int Id;
    private String FullName;
    private Integer Province;
    private Integer City;
    private Integer District;
    private String Address;
    private String IndustryTypeName;
    private String Logo;
    private String CompanyPic;
    private String StaffSize;
    private int BusinessLicenseStatus;
    private int ManagerIdCardStatus;
    private List<AchievementBean> Achievement;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
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

    public String getIndustryTypeName() {
        return IndustryTypeName;
    }

    public void setIndustryTypeName(String IndustryTypeName) {
        this.IndustryTypeName = IndustryTypeName;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String Logo) {
        this.Logo = Logo;
    }

    public String getCompanyPic() {
        return CompanyPic;
    }

    public void setCompanyPic(String CompanyPic) {
        this.CompanyPic = CompanyPic;
    }

    public String getStaffSize() {
        return StaffSize;
    }

    public void setStaffSize(String StaffSize) {
        this.StaffSize = StaffSize;
    }

    public int getBusinessLicenseStatus() {
        return BusinessLicenseStatus;
    }

    public void setBusinessLicenseStatus(int BusinessLicenseStatus) {
        this.BusinessLicenseStatus = BusinessLicenseStatus;
    }

    public int getManagerIdCardStatus() {
        return ManagerIdCardStatus;
    }

    public void setManagerIdCardStatus(int ManagerIdCardStatus) {
        this.ManagerIdCardStatus = ManagerIdCardStatus;
    }

    public List<AchievementBean> getAchievement() {
        return Achievement;
    }

    public void setAchievement(List<AchievementBean> Achievement) {
        this.Achievement = Achievement;
    }

    public static class AchievementBean {
        /**
         * Id : 1011
         * AchievementType : 10
         * GainTime : 2018-07-11 11:17:27
         */

        private int Id;
        private int AchievementType;
        private String GainTime;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public int getAchievementType() {
            return AchievementType;
        }

        public void setAchievementType(int AchievementType) {
            this.AchievementType = AchievementType;
        }

        public String getGainTime() {
            return GainTime;
        }

        public void setGainTime(String GainTime) {
            this.GainTime = GainTime;
        }
    }
}
