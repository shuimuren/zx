package com.zhixing.work.zhixin.requestbody;

/**
 * Created by lhj on 2018/8/1.
 * Description:
 */

public class AttendanceRecordStatisticBody {

    public AttendanceRecordStatisticBody(String date, String clockStatus, String pageIndex, String rowsCount) {
        this.date = date;
        this.clockStatus = clockStatus;
        this.pageIndex = pageIndex;
        this.rowsCount = rowsCount;
    }

    private String date;
    private String clockStatus;
    private String pageIndex;
    private String rowsCount;
}
