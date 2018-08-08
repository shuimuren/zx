package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.bean.AttendanceRuleBean;
import com.zhixing.work.zhixin.network.BaseResult;

import java.util.List;

/**
 * Created by lhj on 2018/8/1.
 * Description:获取考勤规则
 */

public class AttendanceRuleResult extends BaseResult {

    private List<AttendanceRuleBean> Content;

    public List<AttendanceRuleBean> getContent() {
        return Content;
    }

    public void setContent(List<AttendanceRuleBean> Content) {
        this.Content = Content;
    }

}
