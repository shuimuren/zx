package com.zhixing.work.zhixin.bean;

/**
 * Created by lhj on 2018/8/24.
 * Description:生涯-奖惩
 */

public class CareerAwardPunishment {

    private String name;
    private String time;
    private int grade;

    public CareerAwardPunishment(String name, String time, int grade) {
        this.name = name;
        this.time = time;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }


}
