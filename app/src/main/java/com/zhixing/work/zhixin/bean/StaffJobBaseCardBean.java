package com.zhixing.work.zhixin.bean;

import java.io.Serializable;

/**
 * Created by lhj on 2018/8/15.
 * Description:
 */

public class StaffJobBaseCardBean implements Serializable{
    public String getStaffId() {
        return StaffId;
    }

    public void setStaffId(String staffId) {
        StaffId = staffId;
    }

    /**
     * RealName : 印度阿三
     * PhoneNum : 13137551611
     * Email : lhj@163.com
     * NickName : 阿三
     * Sex : 0
     * Birthday : 2018-10-14T00:00:00
     * Constellation : 3
     * Stature : 10
     * Weight : 123
     * Motto : fool呢
     * FirstWorkTime : 2018-07-09T00:00:00
     */
    private String StaffId;
    private String RealName;
    private String PhoneNum;
    private String Email;
    private String NickName;
    private int Sex;
    private String Birthday;
    private int Constellation;
    private Integer Stature;
    private Integer Weight;
    private String Motto;
    private String FirstWorkTime;

    public String getRealName() {
        return RealName;
    }

    public void setRealName(String RealName) {
        this.RealName = RealName;
    }

    public String getPhoneNum() {
        return PhoneNum;
    }

    public void setPhoneNum(String PhoneNum) {
        this.PhoneNum = PhoneNum;
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

    public int getSex() {
        return Sex;
    }

    public void setSex(int Sex) {
        this.Sex = Sex;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String Birthday) {
        this.Birthday = Birthday;
    }

    public int getConstellation() {
        return Constellation;
    }

    public void setConstellation(int Constellation) {
        this.Constellation = Constellation;
    }

    public Integer getStature() {
        return Stature;
    }

    public void setStature(Integer Stature) {
        this.Stature = Stature;
    }

    public Integer getWeight() {
        return Weight;
    }

    public void setWeight(Integer Weight) {
        this.Weight = Weight;
    }

    public String getMotto() {
        return Motto;
    }

    public void setMotto(String Motto) {
        this.Motto = Motto;
    }

    public String getFirstWorkTime() {
        return FirstWorkTime;
    }

    public void setFirstWorkTime(String FirstWorkTime) {
        this.FirstWorkTime = FirstWorkTime;
    }
}
