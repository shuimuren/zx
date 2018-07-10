package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.bean.AuthenticateBean;
import com.zhixing.work.zhixin.network.BaseResult;

import java.util.List;

/**
 * Created by lhj on 2018/7/9.
 * Description: 认证列表
 */

public class AuthenticateListResult extends BaseResult {


    private List<AuthenticateBean> Content;

    public List<AuthenticateBean> getContent() {
        return Content;
    }

    public void setContent(List<AuthenticateBean> Content) {
        this.Content = Content;
    }


}
