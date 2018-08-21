package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.bean.ClockDailyBean;
import com.zhixing.work.zhixin.network.BaseResult;

/**
 * Created by lhj on 2018/8/1.
 * Description:
 */

public class StatisticsDailyResult extends BaseResult {

    /**
     * Content : {"Total":2,"SignedCount":1,"LateCount":1,"EarlyCount":0,"AbsenteeismCount":1,"NotClockIn":0}
     */

    private ClockDailyBean Content;

    public ClockDailyBean getContent() {
        return Content;
    }

    public void setContent(ClockDailyBean Content) {
        this.Content = Content;
    }


}
