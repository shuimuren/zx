package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.bean.JobCertificateDetailBean;
import com.zhixing.work.zhixin.network.BaseResult;

/**
 * Created by lhj on 2018/8/17.
 * Description:
 */

public class JobCertificateDetailResult extends BaseResult {
    public JobCertificateDetailBean getContent() {
        return Content;
    }

    public void setContent(JobCertificateDetailBean content) {
        Content = content;
    }

    private JobCertificateDetailBean Content;
}
