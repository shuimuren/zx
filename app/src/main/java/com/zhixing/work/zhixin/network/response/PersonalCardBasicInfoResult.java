package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.bean.PersonalCardBasicInfoBean;
import com.zhixing.work.zhixin.network.BaseResult;

/**
 * Created by lhj on 2018/7/14.
 * Description:
 */

public class PersonalCardBasicInfoResult extends BaseResult {
    public PersonalCardBasicInfoBean getContent() {
        return Content;
    }

    public void setContent(PersonalCardBasicInfoBean content) {
        Content = content;
    }

    PersonalCardBasicInfoBean Content;
}
