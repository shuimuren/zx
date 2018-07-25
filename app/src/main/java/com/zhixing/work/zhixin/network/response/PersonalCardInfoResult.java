package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.bean.PersonalCardInfoBean;
import com.zhixing.work.zhixin.network.BaseResult;

/**
 * Created by lhj on 2018/7/24.
 * Description:
 */

public class PersonalCardInfoResult extends BaseResult {

    private PersonalCardInfoBean Content;

    public PersonalCardInfoBean getContent() {
        return Content;
    }

    public void setContent(PersonalCardInfoBean content) {
        Content = content;
    }

}
