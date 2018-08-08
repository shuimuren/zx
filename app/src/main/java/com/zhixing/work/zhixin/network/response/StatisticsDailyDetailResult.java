package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.network.BaseResult;

/**
 * Created by lhj on 2018/8/1.
 * Description:
 */

public class StatisticsDailyDetailResult extends BaseResult{

    /**
     * StaffId : 7
     * Avatar : null
     * RealName : 吴
     * DepartmentName : 技术部
     * ClockInTime : 08:51:15
     * ClockOutTime : null
     * StartTime : 09:00:00
     * EndTime : 18:00:00
     * FlexTime : 5
     * AbsenteeismTime : 60
     */

    private int StaffId;
    private String Avatar;
    private String RealName;
    private String DepartmentName;
    private String ClockInTime;
    private String ClockOutTime;
    private String StartTime;
    private String EndTime;
    private int FlexTime;
    private int AbsenteeismTime;

    public int getStaffId() {
        return StaffId;
    }

    public void setStaffId(int StaffId) {
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

    public String getClockInTime() {
        return ClockInTime;
    }

    public void setClockInTime(String ClockInTime) {
        this.ClockInTime = ClockInTime;
    }

    public String getClockOutTime() {
        return ClockOutTime;
    }

    public void setClockOutTime(String ClockOutTime) {
        this.ClockOutTime = ClockOutTime;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String StartTime) {
        this.StartTime = StartTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String EndTime) {
        this.EndTime = EndTime;
    }

    public int getFlexTime() {
        return FlexTime;
    }

    public void setFlexTime(int FlexTime) {
        this.FlexTime = FlexTime;
    }

    public int getAbsenteeismTime() {
        return AbsenteeismTime;
    }

    public void setAbsenteeismTime(int AbsenteeismTime) {
        this.AbsenteeismTime = AbsenteeismTime;
    }
}
