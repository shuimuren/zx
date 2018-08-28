package com.zhixing.work.zhixin.bean;

/**
 * Created by lhj on 2018/8/24.
 * Description: 月统计
 */

public class MonthStatisticsBean {


    private int status;//待审核:0,已审核:1,待完成:2,已完成:3;
    private float integrate;
    private int month;

    public MonthStatisticsBean(int status, float integrate, int month) {
        this.status = status;
        this.integrate = integrate;
        this.month = month;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getIntegrate() {
        return integrate;
    }

    public void setIntegrate(float integrate) {
        this.integrate = integrate;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }


}
