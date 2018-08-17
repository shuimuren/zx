package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.bean.JobEducationDetailBean;
import com.zhixing.work.zhixin.network.BaseResult;

/**
 * Created by lhj on 2018/8/17.
 * Description:
 */

public class JobEducationDetailResult extends BaseResult {
    public JobEducationDetailBean getContent() {
        return Content;
    }

    public void setContent(JobEducationDetailBean content) {
        Content = content;
    }

    private JobEducationDetailBean Content;
}
