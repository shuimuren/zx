package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.network.BaseResult;

/**
 * Created by lhj on 2018/8/10.
 * Description: 新加入会所待审核
 */

public class NewJoinMemberResult extends BaseResult{
    public int getContent() {
        return Content;
    }

    public void setContent(int content) {
        Content = content;
    }

    private int Content;
}
