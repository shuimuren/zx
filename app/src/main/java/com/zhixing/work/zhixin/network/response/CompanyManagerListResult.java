package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.bean.HrBean;
import com.zhixing.work.zhixin.network.BaseResult;

import java.util.List;

/**
 * Created by lhj on 2018/8/22.
 * Description:
 */

public class CompanyManagerListResult extends BaseResult{
    private List<HrBean> Content;

    public List<HrBean> getContent() {
        return Content;
    }

    public void setContent(List<HrBean> content) {
        Content = content;
    }


}
