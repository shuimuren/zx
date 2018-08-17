package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.bean.JobWorkDetailBean;
import com.zhixing.work.zhixin.network.BaseResult;

/**
 * Created by lhj on 2018/8/16.
 * Description:
 */

public class JobWorkDetailResult extends BaseResult {


    /**
     * Content : {"Id":3,"StaffId":24,"CompanyName":"gggg","StartDate":"2015-07-12 00:00:00","EndDate":"2016-07-12 00:00:00","PostOfDuty":"gghg","Department":"yuyy","JobContent":"vvghh"}
     */

    private JobWorkDetailBean Content;

    public JobWorkDetailBean getContent() {
        return Content;
    }

    public void setContent(JobWorkDetailBean Content) {
        this.Content = Content;
    }

}
