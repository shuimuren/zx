package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.bean.DailyDetailContentBean;
import com.zhixing.work.zhixin.network.BaseResult;

/**
 * Created by lhj on 2018/8/1.
 * Description:
 */

public class StatisticsDailyDetailResult extends BaseResult{


    /**
     * Content : {"TotalNumber":1,"TotalPage":1,"Data":[{"StaffId":8,"Avatar":null,"RealName":"xxx","DepartmentName":"技术部","ClockInTime":null,"ClockOutTime":null,"StartTime":"09:00:00","EndTime":"19:00:00","FlexTime":5,"AbsenteeismTime":60}]}
     */

    private DailyDetailContentBean Content;

    public DailyDetailContentBean getContent() {
        return Content;
    }

    public void setContent(DailyDetailContentBean Content) {
        this.Content = Content;
    }

}
