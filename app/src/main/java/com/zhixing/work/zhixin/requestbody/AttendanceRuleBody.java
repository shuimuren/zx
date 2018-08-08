package com.zhixing.work.zhixin.requestbody;

import com.zhixing.work.zhixin.bean.WifiBean;
import com.zhixing.work.zhixin.network.BaseResult;

import java.util.List;

/**
 * Created by lhj on 2018/8/1.
 * Description:
 */

public class AttendanceRuleBody extends BaseResult {


    /**
     * Name : 考勤组1
     * StartTime : 9:00
     * EndTime : 18:00
     * FlexTime : 5
     * AbsenteeismTime : 60
     * WorkDays : [1,2,4,8,16]
     * StaffIds : [7]
     * Wifis : [{"Name":"hongyan","Bssid":"f3-g4-g6-cv-d7-8h-s4"}]
     */
    private String Id;
    private String Name;
    private String StartTime;
    private String EndTime;
    private String FlexTime;
    private String AbsenteeismTime;
    private List<Integer> WorkDays;
    private List<Integer> StaffIds;
    private List<WifiBean> Wifis;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
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

    public String getFlexTime() {
        return FlexTime;
    }

    public void setFlexTime(String FlexTime) {
        this.FlexTime = FlexTime;
    }

    public String getAbsenteeismTime() {
        return AbsenteeismTime;
    }

    public void setAbsenteeismTime(String AbsenteeismTime) {
        this.AbsenteeismTime = AbsenteeismTime;
    }

    public List<Integer> getWorkDays() {
        return WorkDays;
    }

    public void setWorkDays(List<Integer> WorkDays) {
        this.WorkDays = WorkDays;
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


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
