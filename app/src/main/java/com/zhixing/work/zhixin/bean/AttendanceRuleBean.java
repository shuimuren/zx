package com.zhixing.work.zhixin.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lhj on 2018/8/8.
 * Description:
 */

public class AttendanceRuleBean implements Serializable {
    /**
     * Id : 1
     * Name : 考勤组111
     * WorkDay : 15
     * StartTime : 09:00:00
     * EndTime : 18:00:00
     * FlexTime : 5
     * AbsenteeismTime : 60
     * StaffIds : [7]
     * Wifis : [{"Name":"hongyan","Bssid":"f3-g4-g6-cv-d7-8h-s8"}]
     */

    private int Id;
    private String Name;
    private int WorkDay;
    private String StartTime;
    private String EndTime;
    private int FlexTime;
    private int AbsenteeismTime;
    private List<Integer> StaffIds;
    private List<WifiBean> Wifis;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getWorkDay() {
        return WorkDay;
    }

    public void setWorkDay(int WorkDay) {
        this.WorkDay = WorkDay;
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

    public List<Integer> getStaffIds() {
        return StaffIds;
    }

    public void setStaffIds(List<Integer> StaffIds) {
        this.StaffIds = StaffIds;
    }

    public List<WifiBean> getWifis() {
        return Wifis;
    }

    public void setWifis(List<WifiBean> Wifis) {
        this.Wifis = Wifis;
    }


}
