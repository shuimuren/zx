package com.zhixing.work.zhixin.bean;

/**
 * Created by lhj on 2018/8/27.
 * Description: 月评详情 员工列表
 */

public class StaffStatementBean {


    private String avatar;
    private String name;
    private float score;

    public StaffStatementBean(String avatar, String name, float score) {
        this.avatar = avatar;
        this.name = name;
        this.score = score;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

}
