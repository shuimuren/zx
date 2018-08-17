package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.network.BaseResult;

/**
 * Created by lhj on 2018/8/17.
 * Description:
 */

public class LeaveMemberResult extends BaseResult{
    public int getContent() {
        return Content;
    }

    public void setContent(int content) {
        Content = content;
    }

    private int Content;
}
