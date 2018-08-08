package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.network.BaseResult;

/**
 * Created by lhj on 2018/8/1.
 * Description: 删除考勤规则
 */

public class DeleteAttendanceRuleResult extends BaseResult {

    /**
     * Content : true
     */

    private boolean Content;

    public boolean isContent() {
        return Content;
    }

    public void setContent(boolean Content) {
        this.Content = Content;
    }
}
