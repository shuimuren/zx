package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.network.BaseResult;

/**
 * Created by lhj on 2018/8/1.
 * Description:员工打卡响应结果
 */

public class AttendanceResult extends BaseResult{


    /**
     * Content : 2018-08-09 18:23:41
     */

    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }
}
