package com.zhixing.work.zhixin.requestbody;

/**
 * Created by lhj on 2018/8/14.
 * Description:编辑高级信息
 */

public class StaffJobSeniorBody {

    private String StaffId;
    private String EntryTime;
    private String PositiveTime;
    private int StaffType;
    private int StaffStatus;
    private int Probation;

    public StaffJobSeniorBody(){

    }

    public String getStaffId() {
        return StaffId;
    }

    public void setStaffId(String staffId) {
        StaffId = staffId;
    }

    public String getEntryTime() {
        return EntryTime;
    }

    public void setEntryTime(String entryTime) {
        EntryTime = entryTime;
    }

    public String getPositiveTime() {
        return PositiveTime;
    }

    public void setPositiveTime(String positiveTime) {
        PositiveTime = positiveTime;
    }

    public int getStaffType() {
        return StaffType;
    }

    public void setStaffType(int staffType) {
        StaffType = staffType;
    }

    public int getStaffStatus() {
        return StaffStatus;
    }

    public void setStaffStatus(int staffStatus) {
        StaffStatus = staffStatus;
    }

    public int getProbation() {
        return Probation;
    }

    public void setProbation(int probation) {
        Probation = probation;
    }

}
