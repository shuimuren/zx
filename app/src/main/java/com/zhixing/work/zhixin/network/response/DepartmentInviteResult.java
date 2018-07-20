package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.network.BaseResult;

/**
 * Created by lhj on 2018/7/18.
 * Description:
 */

public class DepartmentInviteResult extends BaseResult {
    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    private String Content;
}
