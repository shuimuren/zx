package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.bean.AttendanceRecordsBean;
import com.zhixing.work.zhixin.network.BaseResult;

import java.util.List;

/**
 * Created by lhj on 2018/8/1.
 * Description:
 */

public class StaffAttendanceRecordResult extends BaseResult{

    /**
     * Content : {"StaffName":"吴","StaffAvatar":null,"AttendanceRecords":[{"ClockDate":"2018-06-12 00:00:00","ClockInTime":"08:51:15","ClockOutTime":null,"StartTime":"09:00:00","EndTime":"18:00:00","FlexTime":5,"AbsenteeismTime":60},{"ClockDate":"2018-06-13 00:00:00","ClockInTime":"09:06:52","ClockOutTime":null,"StartTime":"09:00:00","EndTime":"19:00:00","FlexTime":5,"AbsenteeismTime":60}]}
     */

    private ContentBean Content;

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    private int requestType; //0:打卡,1:统计

    public ContentBean getContent() {
        return Content;
    }

    public void setContent(ContentBean Content) {
        this.Content = Content;
    }

    public static class ContentBean {
        /**
         * StaffName : 吴
         * StaffAvatar : null
         * AttendanceRecords : [{"ClockDate":"2018-06-12 00:00:00","ClockInTime":"08:51:15","ClockOutTime":null,"StartTime":"09:00:00","EndTime":"18:00:00","FlexTime":5,"AbsenteeismTime":60},{"ClockDate":"2018-06-13 00:00:00","ClockInTime":"09:06:52","ClockOutTime":null,"StartTime":"09:00:00","EndTime":"19:00:00","FlexTime":5,"AbsenteeismTime":60}]
         */

        private String StaffName;
        private String StaffAvatar;
        private List<AttendanceRecordsBean> AttendanceRecords;

        public String getStaffName() {
            return StaffName;
        }

        public void setStaffName(String StaffName) {
            this.StaffName = StaffName;
        }

        public String getStaffAvatar() {
            return StaffAvatar;
        }

        public void setStaffAvatar(String StaffAvatar) {
            this.StaffAvatar = StaffAvatar;
        }

        public List<AttendanceRecordsBean> getAttendanceRecords() {
            return AttendanceRecords;
        }

        public void setAttendanceRecords(List<AttendanceRecordsBean> AttendanceRecords) {
            this.AttendanceRecords = AttendanceRecords;
        }

    }
}
