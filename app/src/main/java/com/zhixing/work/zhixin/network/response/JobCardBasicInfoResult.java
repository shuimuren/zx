package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.bean.JobCardBasicInfoBean;
import com.zhixing.work.zhixin.network.BaseResult;


/**
 * Created by lhj on 2018/8/13.
 * Description: 工作卡牌基础信息
 */

public class JobCardBasicInfoResult extends BaseResult {

    /**
     * Content : {"Departments":[["XXX有限公司","技术部"],["XXX有限公司","技术部","前端"]],"StaffId":7,"RealName":"wu","WorkEmail":null,"JobType":null,"WorkPhoneNum":null,"WorkNumber":null,"ExtPhoneNum":null,"OfficeAddress":null}
     */

    private JobCardBasicInfoBean Content;

    public JobCardBasicInfoBean getContent() {
        return Content;
    }

    public void setContent(JobCardBasicInfoBean Content) {
        this.Content = Content;
    }

}
