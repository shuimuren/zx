package com.zhixing.work.zhixin.requestbody;

/**
 * Created by lhj on 2018/8/1.
 * Description:
 */

public class AttendanceRecordBody {

    private String staffId;
    private String startDate;
    private String endDate;

    public AttendanceRecordBody(String staffId, String startDate, String endDate) {
        this.staffId = staffId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
