package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.network.BaseResult;

/**
 * Created by lhj on 2018/8/14.
 * Description:编辑员工个人高级信息请求返回结果
 */

public class EditSeniorInfoResult extends BaseResult {
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
