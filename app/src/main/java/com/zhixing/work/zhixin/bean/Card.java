package com.zhixing.work.zhixin.bean;

// FIXME generate failure  field _$Achievement77

import java.util.List;

/**
 * Created by Administrator on 2018/5/15.
 */

public class Card {


    /**
     * UserIdentity : 10
     * Avatar : null
     * RealName : 8866
     * NickName : null
     * Sex : 0
     * Email : 806446928@qq.com
     * Motto : null
     * Constellation : 0
     * Achievement : [{"Id":1012,"AchievementType":10,"GainTime":"2018-05-15 09:51:06"}]
     */

    private int UserIdentity;
    private String Avatar;
    private String RealName;
    private String NickName;
    private int Sex;
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
         * Id : 1012
         * AchievementType : 10
         * GainTime : 2018-05-15 09:51:06
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
