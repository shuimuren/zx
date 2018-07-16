package com.zhixing.work.zhixin.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lhj on 2018/7/14.
 * Description:
 */

public class PersonalCardBasicInfoBean implements Serializable{

    /**
     * RealName : wuu
     * Email : 1883828@qq.com
     * NickName : wu
     * Sex : 男
     * Birthday : 1990-08-19 00:00:00
     * Constellation : 处女座
     * Stature : 173
     * Weight : 62
     * FirstWorkTime : 2011-08-01 00:00:00
     * Motto : 座右铭00
     * Nation : 汉族
     * NativePlaceProvince : 1
     * NativePlaceCity : 2
     * Province : 1
     * City : 2
     * District : 3
     * Address : 景田
     * PoliticsFace : 普通公民
     * Married : 0
     * IdCard : 987654321987654321
     * HouseholdType : 农村户口
     * Education : 初中
     * WorkBackgroundOutputs : [{"CompanyName":"公司1","StartDate":"2011-01-01 00:00:00","EndDate":"2013-06-01 00:00:00"},{"CompanyName":"公司2","StartDate":"2013-07-01 00:00:00","EndDate":"9999-12-31 00:00:00"}]
     * EducationBackgroundOutputs : [{"School":"学校1","Education":"初中","StartDate":"1900-01-01 00:00:00","EndDate":"1900-01-01 00:00:00"},{"School":"学校2","Education":"高中","StartDate":"1900-01-01 00:00:00","EndDate":"1900-01-01 00:00:00"}]
     * CertificateBackgroundOutputs : [{"CertificateTitle":"证书名称1"},{"CertificateTitle":"证书名称2"}]
     */

    private String RealName;
    private String Email;
    private String NickName;
    private String Sex;
    private String Birthday;
    private String Constellation;
    private int Stature;
    private int Weight;
    private String FirstWorkTime;
    private String Motto;
    private String Nation;
    private int NativePlaceProvince;
    private int NativePlaceCity;
    private int Province;
    private int City;
    private int District;
    private String Address;
    private String PoliticsFace;
    private int Married;
    private String IdCard;
    private String HouseholdType;
    private String Education;
    private List<PersonalWorkBackgroundOutputsBean> WorkBackgroundOutputs;
    private List<PersonalEducationBackgroundOutputsBean> EducationBackgroundOutputs;
    private List<PersonalCertificateBackgroundOutputsBean> CertificateBackgroundOutputs;

    public String getRealName() {
        return RealName;
    }

    public void setRealName(String RealName) {
        this.RealName = RealName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String NickName) {
        this.NickName = NickName;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String Sex) {
        this.Sex = Sex;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String Birthday) {
        this.Birthday = Birthday;
    }

    public String getConstellation() {
        return Constellation;
    }

    public void setConstellation(String Constellation) {
        this.Constellation = Constellation;
    }

    public int getStature() {
        return Stature;
    }

    public void setStature(int Stature) {
        this.Stature = Stature;
    }

    public int getWeight() {
        return Weight;
    }

    public void setWeight(int Weight) {
        this.Weight = Weight;
    }

    public String getFirstWorkTime() {
        return FirstWorkTime;
    }

    public void setFirstWorkTime(String FirstWorkTime) {
        this.FirstWorkTime = FirstWorkTime;
    }

    public String getMotto() {
        return Motto;
    }

    public void setMotto(String Motto) {
        this.Motto = Motto;
    }

    public String getNation() {
        return Nation;
    }

    public void setNation(String Nation) {
        this.Nation = Nation;
    }

    public int getNativePlaceProvince() {
        return NativePlaceProvince;
    }

    public void setNativePlaceProvince(int NativePlaceProvince) {
        this.NativePlaceProvince = NativePlaceProvince;
    }

    public int getNativePlaceCity() {
        return NativePlaceCity;
    }

    public void setNativePlaceCity(int NativePlaceCity) {
        this.NativePlaceCity = NativePlaceCity;
    }

    public int getProvince() {
        return Province;
    }

    public void setProvince(int Province) {
        this.Province = Province;
    }

    public int getCity() {
        return City;
    }

    public void setCity(int City) {
        this.City = City;
    }

    public int getDistrict() {
        return District;
    }

    public void setDistrict(int District) {
        this.District = District;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getPoliticsFace() {
        return PoliticsFace;
    }

    public void setPoliticsFace(String PoliticsFace) {
        this.PoliticsFace = PoliticsFace;
    }

    public int getMarried() {
        return Married;
    }

    public void setMarried(int Married) {
        this.Married = Married;
    }

    public String getIdCard() {
        return IdCard;
    }

    public void setIdCard(String IdCard) {
        this.IdCard = IdCard;
    }

    public String getHouseholdType() {
        return HouseholdType;
    }

    public void setHouseholdType(String HouseholdType) {
        this.HouseholdType = HouseholdType;
    }

    public String getEducation() {
        return Education;
    }

    public void setEducation(String Education) {
        this.Education = Education;
    }

    public List<PersonalWorkBackgroundOutputsBean> getWorkBackgroundOutputs() {
        return WorkBackgroundOutputs;
    }

    public void setWorkBackgroundOutputs(List<PersonalWorkBackgroundOutputsBean> WorkBackgroundOutputs) {
        this.WorkBackgroundOutputs = WorkBackgroundOutputs;
    }

    public List<PersonalEducationBackgroundOutputsBean> getEducationBackgroundOutputs() {
        return EducationBackgroundOutputs;
    }

    public void setEducationBackgroundOutputs(List<PersonalEducationBackgroundOutputsBean> EducationBackgroundOutputs) {
        this.EducationBackgroundOutputs = EducationBackgroundOutputs;
    }

    public List<PersonalCertificateBackgroundOutputsBean> getCertificateBackgroundOutputs() {
        return CertificateBackgroundOutputs;
    }

    public void setCertificateBackgroundOutputs(List<PersonalCertificateBackgroundOutputsBean> CertificateBackgroundOutputs) {
        this.CertificateBackgroundOutputs = CertificateBackgroundOutputs;
    }


}
