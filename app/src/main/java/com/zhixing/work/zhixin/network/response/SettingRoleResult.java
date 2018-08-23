package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.network.BaseResult;

/**
 * Created by lhj on 2018/8/22.
 * Description:设置角色
 */

public class SettingRoleResult extends BaseResult {

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    /**
     * Content : true
     */
    private boolean isAdd;
    private boolean Content;

    public boolean isContent() {
        return Content;
    }

    public void setContent(boolean Content) {
        this.Content = Content;
    }
}
