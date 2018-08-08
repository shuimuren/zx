package com.zhixing.work.zhixin.bean;

/**
 * Created by lhj on 2018/8/2.
 * Description:
 */

public class AttendanceRecordsBean {
    private String ClockDate;
    private String ClockInTime;
    private String ClockOutTime;
    private String StartTime;
    private String EndTime;
    private int FlexTime;
    private int AbsenteeismTime;

    public String getClockDate() {
        return ClockDate;
    }

    public void setClockDate(String ClockDate) {
        this.ClockDate = ClockDate;
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
