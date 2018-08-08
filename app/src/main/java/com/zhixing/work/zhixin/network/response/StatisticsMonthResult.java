package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.network.BaseResult;

/**
 * Created by lhj on 2018/8/1.
 * Description:
 */

public class StatisticsMonthResult extends BaseResult {

    /**
     * Content : {"Total":0,"SignedCount":0,"LateCount":1,"EarlyCount":1,"AbsenteeismCount":3,"NotClockIn":2}
     */

    private ContentBean Content;

    public ContentBean getContent() {
        return Content;
    }

    public void setContent(ContentBean Content) {
        this.Content = Content;
    }

    public static class ContentBean {
        /**
         * Total : 0
         * SignedCount : 0
         * LateCount : 1
         * EarlyCount : 1
         * AbsenteeismCount : 3
         * NotClockIn : 2
         */

        private int Total;
        private int SignedCount;
        private int LateCount;
        private int EarlyCount;
        private int AbsenteeismCount;
        private int NotClockIn;

        public int getTotal() {
            return Total;
        }

        public void setTotal(int Total) {
            this.Total = Total;
        }

        public int getSignedCount() {
            return SignedCount;
        }

        public void setSignedCount(int SignedCount) {
            this.SignedCount = SignedCount;
        }

        public int getLateCount() {
            return LateCount;
        }

        public void setLateCount(int LateCount) {
            this.LateCount = LateCount;
        }

        public int getEarlyCount() {
            return EarlyCount;
        }

        public void setEarlyCount(int EarlyCount) {
            this.EarlyCount = EarlyCount;
        }

        public int getAbsenteeismCount() {
            return AbsenteeismCount;
        }

        public void setAbsenteeismCount(int AbsenteeismCount) {
            this.AbsenteeismCount = AbsenteeismCount;
        }

        public int getNotClockIn() {
            return NotClockIn;
        }

        public void setNotClockIn(int NotClockIn) {
            this.NotClockIn = NotClockIn;
        }
    }
}
