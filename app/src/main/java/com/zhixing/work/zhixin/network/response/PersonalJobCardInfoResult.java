package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.bean.PersonalJobCardBean;
import com.zhixing.work.zhixin.network.BaseResult;

/**
 * Created by lhj on 2018/8/13.
 * Description:
 */

public class PersonalJobCardInfoResult extends BaseResult {

    /**
     * Content : {"Id":7,"CompanyLogo":null,"CardAvatar":null,"RealName":"wu","NickName":"wu","JobType":null,"DepartmentName":"XXX有限公司","Motto":"座右铭00","IsDimission":true,"EntryTime":"2018-05-30 13:43:18"}
     */

    private PersonalJobCardBean Content;

    public PersonalJobCardBean getContent() {
        return Content;
    }

    public void setContent(PersonalJobCardBean Content) {
        this.Content = Content;
    }

}
