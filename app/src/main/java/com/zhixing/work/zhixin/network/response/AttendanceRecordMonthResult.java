package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.network.BaseResult;

import java.util.List;

/**
 * Created by lhj on 2018/8/1.
 * Description:
 */

public class AttendanceRecordMonthResult extends BaseResult{


    /**
     * Content : {"AttendanceDays":["2018-06-11 (星期一)"],"LateDays":[{"Date":"2018-06-11 (星期一) 09:00","Minutes":8},{"Date":"2018-06-12 (星期二) 09:00","Minutes":15}],"EarlyDays":[{"Date":"2018-06-11 (星期一) 18:00","Minutes":50}],"NotClockInDays":["2018-06-12 (星期二)"],"AbsenteeismDays":["2018-06-12 (星期二)","2018-06-20 (星期三)","2018-06-28 (星期四)"]}
     */

    private ContentBean Content;

    public ContentBean getContent() {
        return Content;
    }

    public void setContent(ContentBean Content) {
        this.Content = Content;
    }

    public static class ContentBean {
        private List<String> AttendanceDays;
        private List<LateDaysBean> LateDays;
        private List<EarlyDaysBean> EarlyDays;
        private List<String> NotClockInDays;
        private List<String> AbsenteeismDays;

        public List<String> getAttendanceDays() {
            return AttendanceDays;
        }

        public void setAttendanceDays(List<String> AttendanceDays) {
            this.AttendanceDays = AttendanceDays;
        }

        public List<LateDaysBean> getLateDays() {
            return LateDays;
        }

        public void setLateDays(List<LateDaysBean> LateDays) {
            this.LateDays = LateDays;
        }

        public List<EarlyDaysBean> getEarlyDays() {
            return EarlyDays;
        }

        public void setEarlyDays(List<EarlyDaysBean> EarlyDays) {
            this.EarlyDays = EarlyDays;
        }

        public List<String> getNotClockInDays() {
            return NotClockInDays;
        }

        public void setNotClockInDays(List<String> NotClockInDays) {
            this.NotClockInDays = NotClockInDays;
        }

        public List<String> getAbsenteeismDays() {
            return AbsenteeismDays;
        }

        public void setAbsenteeismDays(List<String> AbsenteeismDays) {
            this.AbsenteeismDays = AbsenteeismDays;
        }

        public static class LateDaysBean {
            /**
             * Date : 2018-06-11 (星期一) 09:00
             * Minutes : 8
             */

            private String Date;
            private int Minutes;

            public String getDate() {
                return Date;
            }

            public void setDate(String Date) {
                this.Date = Date;
            }

            public int getMinutes() {
                return Minutes;
            }

            public void setMinutes(int Minutes) {
                this.Minutes = Minutes;
            }
        }

        public static class EarlyDaysBean {
            /**
             * Date : 2018-06-11 (星期一) 18:00
             * Minutes : 50
             */

            private String Date;
            private int Minutes;

            public String getDate() {
                return Date;
            }

            public void setDate(String Date) {
                this.Date = Date;
            }

            public int getMinutes() {
                return Minutes;
            }

            public void setMinutes(int Minutes) {
                this.Minutes = Minutes;
            }
        }
    }
}
