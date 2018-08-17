package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.bean.JobCardSeniorInfoBean;
import com.zhixing.work.zhixin.network.BaseResult;

/**
 * Created by lhj on 2018/8/13.
 * Description: 工作卡牌高级信息
 */

public class JobCardSeniorInfoResult extends BaseResult {

    /**
     * Content : {"StaffId":7,"EntryTime":"2018-05-30 13:43:18","StaffType":0,"StaffStatus":0,"PositiveTime":null,"Probation":0}
     */

    private JobCardSeniorInfoBean Content;

    public JobCardSeniorInfoBean getContent() {
        return Content;
    }

    public void setContent(JobCardSeniorInfoBean Content) {
        this.Content = Content;
    }

}
