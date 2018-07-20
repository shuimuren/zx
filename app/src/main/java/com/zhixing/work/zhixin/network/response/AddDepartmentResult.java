package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.network.BaseResult;

/**
 * Created by lhj on 2018/7/18.
 * Description: 添加部门
 */

public class AddDepartmentResult  extends BaseResult{
    public boolean isContent() {
        return Content;
    }

    public void setContent(boolean content) {
        Content = content;
    }

    private boolean Content;
}
