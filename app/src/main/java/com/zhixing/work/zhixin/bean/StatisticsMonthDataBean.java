package com.zhixing.work.zhixin.bean;

/**
 * Created by lhj on 2018/8/3.
 * Description:
 */

public class StatisticsMonthDataBean {
    /**
     * StaffId : 8
     * Avatar : null
     * RealName : xxx
     * DepartmentName : 技术部
     * Count : 3
     * LateMinutes : 0
     */

    private String StaffId;
    private String Avatar;
    private String RealName;
    private String DepartmentName;
    private int Count;
    private int LateMinutes;

    public String getStaffId() {
        return StaffId;
    }

    public void setStaffId(String StaffId) {
        this.StaffId = StaffId;
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

    public int getCount() {
        return Count;
    }

    public void setCount(int Count) {
        this.Count = Count;
    }

    public int getLateMinutes() {
        return LateMinutes;
    }

    public void setLateMinutes(int LateMinutes) {
        this.LateMinutes = LateMinutes;
    }

}
