package com.zhixing.work.zhixin.bean;

import java.io.Serializable;

/**
 * Created by lhj on 2018/8/13.
 * Description:
 */

public class JobCardSeniorInfoBean implements Serializable{
    /**
     * StaffId : 7
     * EntryTime : 2018-05-30 13:43:18
     * StaffType : 0
     * StaffStatus : 0
     * PositiveTime : null
     * Probation : 0
     */

    private int StaffId;
    private String EntryTime;
    private int StaffType;
    private int StaffStatus;
    private String PositiveTime;
    private int Probation;

    public int getStaffId() {
        return StaffId;
    }

    public void setStaffId(int StaffId) {
        this.StaffId = StaffId;
    }

    public String getEntryTime() {
        return EntryTime;
    }

    public void setEntryTime(String EntryTime) {
        this.EntryTime = EntryTime;
    }

    public int getStaffType() {
        return StaffType;
    }

    public void setStaffType(int StaffType) {
        this.StaffType = StaffType;
    }

    public int getStaffStatus() {
        return StaffStatus;
    }

    public void setStaffStatus(int StaffStatus) {
        this.StaffStatus = StaffStatus;
    }

    public String getPositiveTime() {
        return PositiveTime;
    }

    public void setPositiveTime(String PositiveTime) {
        this.PositiveTime = PositiveTime;
    }

    public int getProbation() {
        return Probation;
    }

    public void setProbation(int Probation) {
        this.Probation = Probation;
    }
}
