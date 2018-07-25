package com.zhixing.work.zhixin.bean;

import java.util.List;

/**
 * Created by lhj on 2018/7/24.
 * Description:
 */

public class PersonalCardInfoBean {

    /**
     * UserIdentity : 20
     * Avatar : personalAvatar/1b02eec9-05d3-4dd0-961b-837f4e198724
     * RealName : 印度阿三
     * NickName : 阿三
     * Sex : 0
     * Email : lhj@163.com
     * Motto : fool呢
     * Constellation : 3
     * Achievement : [{"Id":1009,"AchievementType":10,"GainTime":"2018-07-07 14:05:01"}]
     */

    private int UserIdentity;
    private String Avatar;
    private String RealName;
    private String NickName;
    private int Sex; //0男 1: 女
    private String Email;
    private String Motto;
    private Integer Constellation;
    private List<AchievementBean> Achievement;

    public int getUserIdentity() {
        return UserIdentity;
    }

    public void setUserIdentity(int UserIdentity) {
        this.UserIdentity = UserIdentity;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String Avatar) {
        this.Avatar = Avatar;
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

    public int getSex() {
        return Sex;
    }

    public void setSex(int Sex) {
        this.Sex = Sex;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getMotto() {
        return Motto;
    }

    public void setMotto(String Motto) {
        this.Motto = Motto;
    }

    public Integer getConstellation() {
        return Constellation;
    }

    public void setConstellation(Integer Constellation) {
        this.Constellation = Constellation;
    }

    public List<AchievementBean> getAchievement() {
        return Achievement;
    }

    public void setAchievement(List<AchievementBean> Achievement) {
        this.Achievement = Achievement;
    }

    public static class AchievementBean {
        /**
         * Id : 1009
         * AchievementType : 10
         * GainTime : 2018-07-07 14:05:01
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
