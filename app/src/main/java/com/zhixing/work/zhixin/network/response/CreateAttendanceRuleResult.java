package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.network.BaseResult;

/**
 * Created by lhj on 2018/8/1.
 * Description:
 */

public class CreateAttendanceRuleResult extends BaseResult {

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
