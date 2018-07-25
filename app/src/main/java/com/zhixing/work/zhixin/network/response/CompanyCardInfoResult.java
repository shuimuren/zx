package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.bean.CompanyCardInfoBean;
import com.zhixing.work.zhixin.network.BaseResult;

/**
 * Created by lhj on 2018/7/24.
 * Description:
 */

public class CompanyCardInfoResult extends BaseResult {

    private CompanyCardInfoBean Content;

    public CompanyCardInfoBean getContent() {
        return Content;
    }

    public void setContent(CompanyCardInfoBean content) {
        Content = content;
    }

}
