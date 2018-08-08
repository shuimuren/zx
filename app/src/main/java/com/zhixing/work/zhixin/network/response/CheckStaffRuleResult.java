package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.bean.MemberBean;
import com.zhixing.work.zhixin.network.BaseResult;

import java.util.List;

/**
 * Created by lhj on 2018/8/1.
 * Description:
 */

public class CheckStaffRuleResult extends BaseResult {

    private List<MemberBean> Content;

    public List<MemberBean> getContent() {
        return Content;
    }

    public void setContent(List<MemberBean> Content) {
        this.Content = Content;
    }

}
