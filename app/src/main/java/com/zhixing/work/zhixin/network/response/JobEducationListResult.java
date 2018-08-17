package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.bean.EducationBgsBean;
import com.zhixing.work.zhixin.network.BaseResult;

import java.util.List;

/**
 * Created by lhj on 2018/8/17.
 * Description:工作信息-教育列表
 */

public class JobEducationListResult extends BaseResult {
    public List<EducationBgsBean> getContent() {
        return Content;
    }

    public void setContent(List<EducationBgsBean> content) {
        Content = content;
    }

    private List<EducationBgsBean> Content;

}
