package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.network.BaseResult;

/**
 * Created by lhj on 2018/7/13.
 * Description:
 */

public class ExpectedJobResult extends BaseResult {

    private boolean Content;
    public boolean isContent() {
        return Content;
    }

    public void setContent(boolean content) {
        Content = content;
    }

}
