package com.zhixing.work.zhixin.bean;

import java.io.Serializable;

/**
 * Created by lhj on 2018/8/17.
 * Description:
 */

public class LeaveStaffBean implements Serializable{
    /**
     * StatffId : 8
     * Avatar : null
     * RealName : xxx
     * DepartmentName : 职信 - 运营部
     * DimissionTime : 2018-06-01 11:00:00
     */

    private int StatffId;
    private String Avatar;
    private String RealName;
    private String DepartmentName;
    private String DimissionTime;

    public int getStatffId() {
        return StatffId;
    }

    public void setStatffId(int StatffId) {
        this.StatffId = StatffId;
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

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String DepartmentName) {
        this.DepartmentName = DepartmentName;
    }

    public String getDimissionTime() {
        return DimissionTime;
    }

    public void setDimissionTime(String DimissionTime) {
        this.DimissionTime = DimissionTime;
    }

}
