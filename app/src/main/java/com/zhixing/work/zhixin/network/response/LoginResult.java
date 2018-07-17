package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.network.BaseResult;

/**
 * Created by lhj on 2018/7/16.
 * Description:
 */

public class LoginResult extends BaseResult {
    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    private String Content;
}
